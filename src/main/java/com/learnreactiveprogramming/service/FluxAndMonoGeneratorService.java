package com.learnreactiveprogramming.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.Random;

public class FluxAndMonoGeneratorService {

    public Flux<String> namesFlux() {
        return Flux.fromIterable(List.of("alex", "ben", "chloe"));
    }

    public Mono<String> nameMono() {
        return Mono.just("alex");
    }

    public Flux<String> namesFluxMap() {
        return Flux.fromIterable(List.of("alex", "ben", "chloe")).map(String::toUpperCase);
    }
    public Flux<String> namesFluxFlatMap() {
        return Flux.fromIterable(List.of("alex", "ben", "chloe"))
                .flatMap(this::splitString)
                .map(String::toUpperCase);
    }
    public Flux<String> namesFluxFlatMapAsync() {
        return Flux.fromIterable(List.of("alex", "ben", "chloe"))
                .flatMap(this::splitStringWithDelay)
                .map(String::toUpperCase).log();
    }

    public Flux<String> splitString(String name){
        var letters = name.split("");
       return Flux.fromArray(letters);
    }

    public Flux<String> splitStringWithDelay(String name){
        var letters = name.split("");
        var delay = new Random().nextInt(10000);
        return Flux.fromArray(letters).delayElements(Duration.ofMillis(delay));
    }


    public Flux<String> namesFluxMapImmut() {
        var nameFlux = Flux.fromIterable(List.of("alex", "ben", "chloe"));
        return nameFlux.map(String::toUpperCase);
    }

    public Flux<String> namesFluxMap(int nameLen) {
        return Flux.fromIterable(List.of("alex", "ben", "chloe"))
                .filter(s -> s.length() > nameLen)
                .map(String::toUpperCase);
    }

    public static void main(String[] args) {
        FluxAndMonoGeneratorService fluxAndMonoGeneratorService = new FluxAndMonoGeneratorService();
        fluxAndMonoGeneratorService
                .namesFlux()
                .subscribe(name -> System.out.println("name is: " + name));

        fluxAndMonoGeneratorService.nameMono().subscribe(name -> System.out.println("Mono name is: " + name));
    }
}