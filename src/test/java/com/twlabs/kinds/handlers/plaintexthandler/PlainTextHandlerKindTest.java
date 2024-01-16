package com.twlabs.kinds.handlers.plaintexthandler;

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

public class PlainTextHandlerKindTest {

    @Test
    void test_execute() {
        // Arrange
        PlainTextHandlerKind spyPlainTextHandlerKind = spy(new PlainTextHandlerKind());
        KindHandlerCommand<KindDefaultSpec> mockCommand = mock(KindHandlerCommand.class);

        doNothing().when(spyPlainTextHandlerKind).executeDefaultFileHandlers(
                any(PlainTextHandler.class),
                eq(mockCommand));

        // Act
        spyPlainTextHandlerKind.execute(mockCommand);

        // Assert
        verify(spyPlainTextHandlerKind, times(1))
                .executeDefaultFileHandlers(any(PlainTextHandler.class), eq(mockCommand));
    }
}

