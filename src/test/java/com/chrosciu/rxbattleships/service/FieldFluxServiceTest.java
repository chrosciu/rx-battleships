package com.chrosciu.rxbattleships.service;

import com.chrosciu.rxbattleships.gui.BoardMouseAdapter;
import com.chrosciu.rxbattleships.gui.FieldClickListener;
import com.chrosciu.rxbattleships.model.Field;
import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

public class FieldFluxServiceTest {
    private BoardMouseAdapter boardMouseAdapter = mock(BoardMouseAdapter.class);

    private FieldFluxService fieldFluxService = new FieldFluxServiceImpl(boardMouseAdapter);

    private List<Field> fields = Arrays.asList(new Field(0, 0), new Field(1, 2));

    @Test
    public void getFieldFluxTest() {
        //given
        doAnswer(invocationOnMock -> {
            FieldClickListener fieldClickListener = invocationOnMock.getArgument(0);
            fields.forEach(fieldClickListener::onFieldClicked);
            return null;
        }).when(boardMouseAdapter).registerFieldListener(any(FieldClickListener.class));

        //when
        Flux<Field> fieldFlux = fieldFluxService.getFieldFlux();

        //then
        StepVerifier.create(fieldFlux)
                .expectNextSequence(fields)
                .thenCancel()
                .verify();
    }
}
