package com.wordgame.service;

import com.wordgame.admin.service.GenerationPropertiesService;
import com.wordgame.generator.model.GeneratorResult;
import com.wordgame.generator.service.GeneratorService;
import com.wordgame.generator.service.InMemWordsBufferService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.mock;
import static org.springframework.test.util.AssertionErrors.assertEquals;

public class InMemWordsBufferServiceTest {

    @Test
    public void test() {
        GeneratorService generatorService = mock(GeneratorService.class);
        GenerationPropertiesService generationPropertiesService = mock(GenerationPropertiesService.class);
        var service = new InMemWordsBufferService(generatorService, generationPropertiesService);
        var serviceSpy = Mockito.spy(service);
        Mockito.doReturn(5).when(generationPropertiesService).getBufferSize();
        Mockito.doReturn(mock(GeneratorResult.class)).when(generatorService).generateWordSequence();
        serviceSpy.init();
        Mockito.verify(serviceSpy, Mockito.times(5)).addToBuffer();

        assertEquals("Размер очереди буфера не совпадает с ожидаемым",
                5,   serviceSpy.getClq().size());

        serviceSpy.getFromBuffer();
        Mockito.verify(serviceSpy, Mockito.times(6)).addToBuffer();
    }
}
