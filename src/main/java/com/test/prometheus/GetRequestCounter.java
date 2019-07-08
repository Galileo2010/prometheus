package com.test.prometheus;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
public class GetRequestCounter {
    private final Counter counter;
    private final AtomicInteger atomicInteger = new AtomicInteger();
    private final Gauge gauge;

    public GetRequestCounter(MeterRegistry registry) {
        counter = Counter.builder("goodNumberCounter.total").description("the counter of good number").register(registry);
        gauge = Gauge.builder("goodNumber.sum", atomicInteger, AtomicInteger::get).description("out put the result").register(registry);
    }

    public void Increment() {
        counter.increment();
    }

    public void setValue(int var) {atomicInteger.set(var); }
}
