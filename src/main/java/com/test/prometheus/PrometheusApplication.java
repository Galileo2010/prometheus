package com.test.prometheus;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class PrometheusApplication {

    @Autowired
    private GetRequestCounter getRequestCounter;
    public static void main(String[] args) {
        SpringApplication.run(PrometheusApplication.class, args);
    }

    @GetMapping("/home")
    public String home(){
        getRequestCounter.Increment();
        return "hello, xiaojun.";
    }
}
