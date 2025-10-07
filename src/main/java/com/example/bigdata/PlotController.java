package com.example.bigdata;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.function.Function;

@RestController
@RequiredArgsConstructor
public class PlotController {

    private int currentIndex = 0;
    private boolean completed = false;
    private double[] values;

    private final Function<DataHolder, String> plotFunction;
    private final MongoTemplate mongoTemplate;

    @PostConstruct
    public void initValues() {
        values = getValuesFromMongoDB();
    }

    @RequestMapping(value = "/plot", produces = "image/svg+xml")
    public ResponseEntity<String> load() {
        if (completed) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Refresh", "1");

        double value;
        if (currentIndex < values.length) {
            value = values[currentIndex];
            currentIndex++;
        } else {
            completed = true;
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        String svg;
        synchronized (plotFunction) {
            svg = plotFunction.apply(new DataHolder(value));
        }

        return new ResponseEntity<>(svg, responseHeaders, HttpStatus.OK);
    }

    private double[] getValuesFromMongoDB() {
        try {
            Query query = new Query();
            query.with(Sort.by(Sort.Direction.ASC, "_id")); // _id'ye göre artan sırayla
            List<DataPoint> dataPoints = mongoTemplate.find(query, DataPoint.class, "pro1");
            return dataPoints.stream()
                    .mapToDouble(DataPoint::getValue)
                    .toArray();
        } catch (Exception e) {
            System.err.println("MongoDB Veri Çekme Hatası: " + e.getMessage());
            return new double[0];
        }
    }
}