package com.twlabs.kinds.handlers.xmlhandler;

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

public class XmlHandlerKindTest {

    @Test
    void test_execute() {
        // Arrange
        XmlHandlerKind spyXMLHandlerKind = spy(new XmlHandlerKind());
        KindHandlerCommand<KindDefaultSpec> mockCommand = mock(KindHandlerCommand.class);
        doNothing().when(spyXMLHandlerKind).executeDefaultFileHandlers(any(XMLHandler.class),
                eq(mockCommand));

        // Act
        spyXMLHandlerKind.execute(mockCommand);

        // Assert
        verify(spyXMLHandlerKind, times(1))
                .executeDefaultFileHandlers(any(XMLHandler.class), eq(mockCommand));
    }
}
