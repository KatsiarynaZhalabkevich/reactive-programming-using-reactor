package com.learnreactiveprogramming.service;

import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

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
}
