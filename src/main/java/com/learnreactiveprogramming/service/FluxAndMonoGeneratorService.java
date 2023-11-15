package com.learnreactiveprogramming.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

public class FluxAndMonoGeneratorService {

    public Flux<String> namesFlux() {
        return Flux.fromIterable(List.of("alex", "ben", "chloe"));
    }

    public Mono<String> nameMono() {
        return Mono.just("alex");
    }

    public Mono<String> nameMonoMapFilter(int stringLen) {
        return Mono.just("alex").map(String::toUpperCase).filter(s -> s.length() > stringLen);
    }

    public Mono<List<String>> nameMonoFlatMapFilter(int stringLen) {
        return Mono.just("alex")
                .map(String::toUpperCase)
                .filter(s -> s.length() > stringLen)
                .flatMap(this::stringSplitMono);
    }

    public Flux<String> nameMonoFlatMapManyFilter(int stringLen) {
        return Mono.just("alex")
                .map(String::toUpperCase)
                .filter(s -> s.length() > stringLen)
                .flatMapMany(this::splitString).log();
    }

    private Mono<List<String>> stringSplitMono(String s) {
        var charArray = s.split("");
        return Mono.just(List.of(charArray));
    }

    public Flux<String> namesFluxMap() {
        return Flux.fromIterable(List.of("alex", "ben", "chloe")).map(String::toUpperCase);
    }

    public Flux<String> namesFluxFlatMap() {
        return Flux.fromIterable(List.of("alex", "ben", "chloe"))
                .flatMap(this::splitString)
                .map(String::toUpperCase);
    }

    public Flux<String> namesFluxConcatMap() {
        return Flux.fromIterable(List.of("alex", "ben", "chloe"))
                .concatMap(this::splitStringWithDelay)
                .map(String::toUpperCase)
                .log();
    }

    public Flux<String> namesFluxFlatMapAsync() {
        return Flux.fromIterable(List.of("alex", "ben", "chloe"))
                .flatMap(this::splitStringWithDelay)
                .map(String::toUpperCase).log();
    }

    public Flux<String> splitString(String name) {
        var letters = name.split("");
        return Flux.fromArray(letters);
    }

    public Flux<String> splitStringWithDelay(String name) {
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

    public Flux<String> namesFluxTransform(int nameLen) {
        Function<Flux<String>, Flux<String>> function = name -> name.map(String::toUpperCase)
                .filter(s -> s.length() > nameLen);
        return Flux.fromIterable(List.of("alex", "ben", "chloe"))
                .transform(function).log();
    }

    public Flux<String> namesFluxDefaultEmpty(int nameLen) {
        Function<Flux<String>, Flux<String>> function = name -> name.map(String::toUpperCase)
                .filter(s -> s.length() < nameLen);
        return Flux.fromIterable(List.of("alex", "ben", "chloe"))
                .transform(function)
                .defaultIfEmpty("default").log();
    }

    public Flux<String> namesFluxSwitchIfEmpty(int nameLen) {
        Function<Flux<String>, Flux<String>> function = name -> name.map(String::toUpperCase)
                .filter(s -> s.length() < nameLen);
        return Flux.fromIterable(List.of("alex", "ben", "chloe"))
                .transform(function)
                .switchIfEmpty(Flux.just("default").flatMap(s -> splitString(s))).log();
    }

    public Flux<String> explorerConcat() {
        var abcFlux = Flux.just("a", "b", "c");
        var defFlux = Flux.just("d", "e", "f");
        return Flux.concat(abcFlux, defFlux);
    }

    public Flux<String> explorerConcatWith() {
        var abcFlux = Flux.just("a", "b", "c");
        var defFlux = Flux.just("d", "e", "f");
        return abcFlux.concatWith(defFlux);
    }

    public Flux<String> explorerConcatWithMono() {
        var aMono = Mono.just("a");
        var bMono = Mono.just("b");
        return aMono.concatWith(bMono);
    }

    public Flux<String> explorerMerge() {
        var abcFlux = Flux.just("a", "b", "c");
        var defFlux = Flux.just("d", "e", "f");
        return Flux.merge(abcFlux, defFlux);
    }

    public Flux<String> explorerMergeWith() {
        var abcFlux = Flux.just("a", "b", "c");
        var defFlux = Flux.just("d", "e", "f");
        return abcFlux.mergeWith(defFlux);
    }

    public Flux<String> explorerMergeWithMono() {
        var aMono = Mono.just("a");
        var bMono = Mono.just("b");
        return aMono.mergeWith(bMono);
    }

    public Flux<String> explorerMergeSeq() {
        var abcFlux = Flux.just("a", "b", "c").delayElements(Duration.ofMillis(100));
        var defFlux = Flux.just("d", "e", "f").delayElements(Duration.ofMillis(125));
        return Flux.mergeSequential(abcFlux, defFlux);
    }

    public Flux<String> explorerZip() {
        var abcFlux = Flux.just("a", "b", "c");
        var defFlux = Flux.just("d", "e", "f");
        return Flux.zip(abcFlux, defFlux, (first, second) -> first + second).log();
    }

    public Flux<String> explorerZipMore() {
        var abcFlux = Flux.just("a", "b", "c");
        var defFlux = Flux.just("d", "e", "f");
        var _123Flux = Flux.just("1", "2", "3");
        var _456Flux = Flux.just("4", "5", "6");
        return Flux.zip(abcFlux, defFlux, _123Flux, _456Flux)
                .map(t4->t4.getT1()+t4.getT2()+t4.getT3()+t4.getT4())
                .log();
    }

    public Flux<String> explorerZipWith() {
        var abcFlux = Flux.just("a", "b", "c");
        var defFlux = Flux.just("d", "e", "f");
        return abcFlux.zipWith(defFlux, (first, second) -> first + second).log();
    }

    public static void main(String[] args) {
        FluxAndMonoGeneratorService fluxAndMonoGeneratorService = new FluxAndMonoGeneratorService();
        fluxAndMonoGeneratorService
                .namesFlux()
                .subscribe(name -> System.out.println("name is: " + name));

        fluxAndMonoGeneratorService.nameMono().subscribe(name -> System.out.println("Mono name is: " + name));
    }
}