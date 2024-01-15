package com.twlabs.kinds.handlers.yamlhandler;

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

public class YamlHandlerKindTest {

    @Test
    void test_execute() {
        // Arrange
        YamlHandlerKind spyYamlHandlerKind = spy(new YamlHandlerKind());
        KindHandlerCommand<KindDefaultSpec> mockCommand = mock(KindHandlerCommand.class);
        doNothing().when(spyYamlHandlerKind).executeDefaultFileHandlers(any(YamlFileHandler.class),
                eq(mockCommand));

        // Act
        spyYamlHandlerKind.execute(mockCommand);

        // Assert
        verify(spyYamlHandlerKind, times(1))
                .executeDefaultFileHandlers(any(YamlFileHandler.class), eq(mockCommand));
    }
}

