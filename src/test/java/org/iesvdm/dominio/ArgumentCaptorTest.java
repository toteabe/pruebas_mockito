package org.iesvdm.dominio;

import org.iesvdm.dominio.Calculator;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class ArgumentCaptorTest {

    @Test
    void captorsTest() {

        Calculator calculator = mock(Calculator.class);

        when(calculator.add(1, 1)).thenReturn(2);

        when(calculator.isInteger(anyString())).thenReturn(true);

        ArgumentCaptor acInteger = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor acString = ArgumentCaptor.forClass(String.class);

        assertEquals(2, calculator.add(1, 1));

        assertTrue(calculator.isInteger("1"));

        assertTrue(calculator.isInteger("999"));

        verify(calculator).add((Integer) acInteger.capture(), (Integer) acInteger.capture());
        List allValues = acInteger.getAllValues();

        assertEquals(List.of(1, 1), allValues);

        verify(calculator, times(2)).isInteger((String) acString.capture());
        List allStringValues = acString.getAllValues();

        assertEquals(List.of("1", "999"), allStringValues);

    }

}