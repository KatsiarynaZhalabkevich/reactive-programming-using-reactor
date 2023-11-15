package com.learnreactiveprogramming.service;

import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import java.util.List;

class FluxAndMonoGeneratorServiceTest {
    FluxAndMonoGeneratorService fluxAndMonoGeneratorService = new FluxAndMonoGeneratorService();

    @Test
    void fluxNameTest() {
        var nameFlux = fluxAndMonoGeneratorService.namesFlux();

        StepVerifier.create(nameFlux)
                .expectNext("alex", "ben", "chloe")
                //.expectNextCount(3)
                .verifyComplete();
    }

    @Test
    void monoNameTest() {
        var nameMono = fluxAndMonoGeneratorService.nameMono();

        StepVerifier.create(nameMono)
                .expectNext("alex")
                .verifyComplete();

    }

    @Test
    void fluxNamesMap() {
        var nameFlux = fluxAndMonoGeneratorService.namesFluxMap();

        StepVerifier.create(nameFlux)
                .expectNext("ALEX", "BEN", "CHLOE")
                //.expectNextCount(3)
                .verifyComplete();
    }

    @Test
    void fluxNamesMapLen() {
        var nameFlux = fluxAndMonoGeneratorService.namesFluxMap(3);

        StepVerifier.create(nameFlux)
                .expectNext("ALEX", "CHLOE")
                //.expectNextCount(3)
                .verifyComplete();
    }

    @Test
    void fluxNameMapImmut() {
        var nameFlux = fluxAndMonoGeneratorService.namesFluxMapImmut();

        StepVerifier.create(nameFlux)
                .expectNext("ALEX", "BEN", "CHLOE")
                //.expectNextCount(3)
                .verifyComplete();
    }

    @Test
    void namesFluxFlatMap() {
        var nameFlux = fluxAndMonoGeneratorService.namesFluxFlatMap();
        var resultArray = "ALEXBENCHLOE".split("");
        StepVerifier.create(nameFlux)
                .expectNext(resultArray)
                //.expectNextCount(3)
                .verifyComplete();
    }

    @Test
    void namesFluxFlatMapAsync() {
        var nameFlux = fluxAndMonoGeneratorService.namesFluxFlatMapAsync();
        var resultArray = "ALEXBENCHLOE".split("");
        StepVerifier.create(nameFlux)
                //.expectNext(resultArray)
                .expectNextCount(12)
                .verifyComplete();
    }

    @Test
    void namesFluxConcatMap() {
        var nameFlux = fluxAndMonoGeneratorService.namesFluxConcatMap();
        var resultArray = "ALEXBENCHLOE".split("");
        StepVerifier.create(nameFlux)
                .expectNext(resultArray)
                // .expectNextCount(12)
                .verifyComplete();
    }

    @Test
    void nameMonoFlatMapFilter() {
        var nameMono = fluxAndMonoGeneratorService.nameMonoFlatMapFilter(3);

        StepVerifier.create(nameMono)
                .expectNext(List.of("A", "L", "E", "X"))
                .verifyComplete();
    }

    @Test
    void nameMonoFlatMapManyFilter() {
        var nameMono = fluxAndMonoGeneratorService.nameMonoFlatMapManyFilter(3);
        StepVerifier.create(nameMono)
                .expectNext("A", "L", "E", "X")
                .verifyComplete();

    }

    @Test
    void namesFluxTransform() {
        var nameFlux = fluxAndMonoGeneratorService.namesFluxTransform(3);
        var resultArray = "ALEXCHLOE".split("");
        StepVerifier.create(nameFlux)
                .expectNext("ALEX", "CHLOE")
                // .expectNextCount(12)
                .verifyComplete();
    }

    @Test
    void namesFluxDefaultIfEmpty() {
        var nameFlux = fluxAndMonoGeneratorService.namesFluxDefaultEmpty(3);
        StepVerifier.create(nameFlux)
                .expectNext("default")
                .verifyComplete();
    }

    @Test
    void namesFluxSwitchIfEmpty() {
        var nameFlux = fluxAndMonoGeneratorService.namesFluxSwitchIfEmpty(3);
        StepVerifier.create(nameFlux)
                .expectNext("d", "e", "f", "a", "u", "l", "t")
                .verifyComplete();
    }

    @Test
    void explorerConcat() {
        var nameFlux = fluxAndMonoGeneratorService.explorerConcat();
        StepVerifier.create(nameFlux)
                .expectNext("a", "b", "c", "d", "e", "f")
                .verifyComplete();
    }

    @Test
    void explorerConcatWith() {
        var nameFlux = fluxAndMonoGeneratorService.explorerConcatWith();
        StepVerifier.create(nameFlux)
                .expectNext("a", "b", "c", "d", "e", "f")
                .verifyComplete();
    }

    @Test
    void explorerConcatWithMono() {
        var nameFlux = fluxAndMonoGeneratorService.explorerConcatWithMono();
        StepVerifier.create(nameFlux)
                .expectNext("a", "b")
                .verifyComplete();
    }
}
