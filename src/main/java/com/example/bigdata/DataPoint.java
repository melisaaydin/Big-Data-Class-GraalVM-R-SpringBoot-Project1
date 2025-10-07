package com.example.bigdata;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "pro1")
public class DataPoint {
    private int _id;
    private double value;
}