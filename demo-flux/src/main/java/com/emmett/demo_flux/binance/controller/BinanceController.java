package com.emmett.demo_flux.binance.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;

@RestController
@RequestMapping("/api/v1/binance")
public class BinanceController {
    @GetMapping(value = "", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> getBinanceStream() {
        return Flux.interval(Duration.ofSeconds(1)).map(sequence -> "data");
    }
}
