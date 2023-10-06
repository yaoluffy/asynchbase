package org.hbase.async;

// Imports for the test classes and annotations
        import org.junit.Test;
        import static org.junit.Assert.assertEquals;
        import static org.junit.Assert.assertTrue;
        import static org.junit.Assert.fail;

// If you're using Mockito for mocking
        import static org.mockito.Mockito.mock;

public class TestUnknownScannerException {

    @Test
    public void testMakeWithUnknownScannerExceptionMsg() {
        HBaseRpc mockRpc = mock(HBaseRpc.class);
        UnknownScannerException existingException = new UnknownScannerException("existing message", mockRpc);
        UnknownScannerException newException = existingException.make(existingException, mockRpc);

        assertTrue(newException.getMessage().contains(existingException.getMessage()));
        assertEquals(mockRpc, newException.getFailedRpc());
    }

    @Test
    public void testMakeWithDifferentMsgType() {
        HBaseRpc mockRpc = mock(HBaseRpc.class);
        UnknownScannerException existingException = new UnknownScannerException("existing message", mockRpc);
        UnknownScannerException newException = existingException.make("new message", mockRpc);
        assertTrue(newException.getMessage().contains("new message"));
        assertEquals(mockRpc, newException.getFailedRpc());
    }

    @Test
    public void testExceptionIsNonRecoverable() {
        try {
            throw new UnknownScannerException("test", null);
        } catch (NonRecoverableException e) {
            // Expected
        } catch (RecoverableException e) {
            fail("UnknownScannerException should not be a type of RecoverableException anymore");
        }
    }
}
