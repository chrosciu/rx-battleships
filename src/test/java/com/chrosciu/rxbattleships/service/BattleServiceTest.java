package com.chrosciu.rxbattleships.service;

import com.chrosciu.rxbattleships.model.Field;
import com.chrosciu.rxbattleships.model.ShipPosition;
import com.chrosciu.rxbattleships.model.ShotResult;
import com.chrosciu.rxbattleships.model.Stamp;
import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import reactor.test.publisher.TestPublisher;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BattleServiceTest {
    private ShipPositionFluxService shipPositionFluxService = mock(ShipPositionFluxService.class);
    private FieldFluxService fieldFluxService = mock(FieldFluxService.class);

    private BattleService battleService = new BattleServiceImpl(shipPositionFluxService, fieldFluxService);

    @Test
    public void getShipsReadyMonoTest() {
        //given
        ShipPosition firstPosition = new ShipPosition(new Field(0, 0), 1, true);
        ShipPosition secondPosition = new ShipPosition(new Field(2, 3), 2, false);
        TestPublisher<ShipPosition> testPublisher = TestPublisher.create();
        when(shipPositionFluxService.getShipPositionFlux())
                .thenReturn(testPublisher.flux());

        //when
        testPublisher.emit(firstPosition);
        Mono<Void> shipsReadyMono = battleService.getShipsReadyMono();

        //then
        StepVerifier.withVirtualTime(() -> shipsReadyMono)
                .expectSubscription()
                .expectNoEvent(Duration.ofSeconds(5L))
                .thenCancel()
                .verify();

        //when
        testPublisher.emit(secondPosition);
        testPublisher.complete();

        //then
        StepVerifier.create(shipsReadyMono)
                .verifyComplete();
    }

    @Test
    public void getStampFluxTest() {
        //given
        List<Field> fields = Arrays.asList(
                new Field(0, 0),
                new Field(1, 1),
                new Field(2, 3),
                new Field(2, 4),
                new Field(3, 4)
        );
        ShipPosition firstPosition = new ShipPosition(fields.get(0), 1, true);
        ShipPosition secondPosition = new ShipPosition(fields.get(2), 2, false);
        when(shipPositionFluxService.getShipPositionFlux())
                .thenReturn(Flux.just(firstPosition, secondPosition));
        when(fieldFluxService.getFieldFlux())
                .thenReturn(Flux.fromIterable(fields));

        //when
        Mono<Void> shipsReadyMono = battleService.getShipsReadyMono();

        //then
        StepVerifier.create(shipsReadyMono)
                .verifyComplete();

        //when
        Flux<Stamp> stampFlux = battleService.getStampFlux();

        //then
        StepVerifier.create(stampFlux)
                .expectNext(new Stamp(fields.get(0), ShotResult.SUNK))
                .expectNext(new Stamp(fields.get(1), ShotResult.MISSED))
                .expectNext(new Stamp(fields.get(2), ShotResult.HIT))
                .expectNext(new Stamp(fields.get(2), ShotResult.SUNK))
                .expectNext(new Stamp(fields.get(3), ShotResult.SUNK))
                .verifyComplete();
    }

}
