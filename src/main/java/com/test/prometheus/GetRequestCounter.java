package com.test.prometheus;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

@Component
public class GetRequestCounter {
    private final Counter counter;
    public GetRequestCounter(MeterRegistry registry){
        counter = Counter.builder("kakaluote.total").description("this is from xiaojun").register(registry);
    }

    public void Increment(){
        counter.increment();
    }
}
