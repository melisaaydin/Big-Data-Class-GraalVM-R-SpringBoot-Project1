package com.example.bigdata;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Source;
import org.graalvm.polyglot.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.IOException;
import java.util.function.Function;

@Configuration
public class PlotConfiguration {

    @Bean
    public Function<DataHolder, String> plotFunction() throws IOException {
        Resource resource = new PathMatchingResourcePatternResolver().getResource("classpath:plot.R");
        Source source = Source.newBuilder("R", resource.getURL()).build();

        Context context = Context.newBuilder("R")
                .allowAllAccess(true)
                .build();

        Value bindings = context.eval(source);

        Value plotFunctionValue = context.getBindings("R").getMember("plotFunction");

        return dataHolder -> plotFunctionValue.execute(dataHolder).asString();
    }
}