package com.javastack.zipkinservice.config;

import org.springframework.boot.actuate.health.*;
import org.springframework.stereotype.Component;

import java.util.Map;

//@Component
public class MyZipkinHealthIndicator extends CompositeHealthIndicator {

    public MyZipkinHealthIndicator(HealthAggregator healthAggregator, Map<String, HealthIndicator> indicators) {
        super(healthAggregator, indicators);
    }

    public MyZipkinHealthIndicator(HealthAggregator healthAggregator, HealthIndicatorRegistry registry) {
        super(healthAggregator, registry);
    }
}
