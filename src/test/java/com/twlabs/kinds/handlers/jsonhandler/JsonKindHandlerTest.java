package com.twlabs.kinds.handlers.jsonhandler;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.junit.jupiter.api.Test;
import com.twlabs.kinds.handlers.base.KindDefaultSpec;
import com.twlabs.kinds.handlers.base.KindHandlerCommand;

public class JsonKindHandlerTest {
    @Test
    void test_execute() {
        // Arrange
        JsonKindHandler spyJsonHandlerKind = spy(new JsonKindHandler());
        KindHandlerCommand<KindDefaultSpec> mockCommand = mock(KindHandlerCommand.class);
        doNothing().when(spyJsonHandlerKind).executeDefaultFileHandlers(any(JsonFileHandler.class),
                eq(mockCommand));

        // Act
        spyJsonHandlerKind.execute(mockCommand);

        // Assert
        verify(spyJsonHandlerKind, times(1))
                .executeDefaultFileHandlers(any(JsonFileHandler.class), eq(mockCommand));
    }
}

