package org.iesvdm;

import org.junit.jupiter.api.Test;
import org.mockito.exceptions.verification.TooFewActualInvocations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang.RandomStringUtils.randomAlphabetic;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import org.mockito.MockSettings;

public class MockMethodsTest {

    @Test
    final void whenSimpleReturnBehaviourConfigured_thenCorrect() {
        final List listMock = mock(ArrayList.class);
        when(listMock.add(anyString())).thenReturn(false);

        final boolean added = listMock.add(randomAlphabetic(6));
        assertThat(added).isFalse();
    }

    @Test
    final void whenSimpleReturnBehaviourConfiguredAlternatively_thenCorrect() {
        final List listMock = mock(ArrayList.class);
        doReturn(false).when(listMock).add(anyString());

        final boolean added = listMock.add(randomAlphabetic(6));
        assertThat(added).isFalse();
    }

    @Test
    final void givenMethodIsConfiguredToThrowException_whenCallingMethod_thenExceptionIsThrown() {
        final List listMock = mock(ArrayList.class);
        when(listMock.add(anyString())).thenThrow(IllegalStateException.class);

        assertThrows(IllegalStateException.class, () -> listMock.add(randomAlphabetic(6)));
    }

    @Test
    final void givenMethodHasNoReturnType_whenCallingMethod_thenExceptionIsThrown() {
        final List listMock = mock(ArrayList.class);
        doThrow(NullPointerException.class).when(listMock).clear();

        assertThrows(NullPointerException.class, () -> listMock.clear());
    }

    @Test
    final void givenBehaviorIsConfiguredToThrowExceptionOnSecondCall_whenCallingTwice_thenExceptionIsThrown() {
        final List listMock = mock(ArrayList.class);
        when(listMock.add(anyString()))
                .thenReturn(false)
                .thenThrow(IllegalStateException.class);

        assertThrows(IllegalStateException.class, () -> {
            listMock.add(randomAlphabetic(6));
            listMock.add(randomAlphabetic(6));
        });
    }

    @Test
    final void givenBehaviorIsConfiguredToThrowExceptionOnSecondCall_whenCallingOnlyOnce_thenNoExceptionIsThrown() {
        final List listMock = mock(ArrayList.class);
        when(listMock.add(anyString()))
                .thenReturn(false)
                .thenThrow(IllegalStateException.class);

        assertThatNoException().isThrownBy(() -> listMock.add(randomAlphabetic(6)));
    }

    @Test
    final void whenSpyBehaviourConfigured_thenCorrect() {
        final List instance = new ArrayList();
        final List spy = spy(instance);

        doThrow(NullPointerException.class).when(spy).size();

        assertThrows(NullPointerException.class, () -> spy.size());
    }

    @Test
    final void whenMockMethodCallIsConfiguredToCallTheRealMethod_thenRealMethodIsCalled() {
        final List listMock = mock(ArrayList.class);
        when(listMock.size()).thenCallRealMethod();

        assertThat(listMock).hasSize(1);
    }

    @Test
    final void whenMockMethodCallIsConfiguredWithCustomAnswer_thenCustomerAnswerIsCalled() {
        final List<String> listMock = mock(ArrayList.class);
        doAnswer(invocation -> "Always the same").when(listMock).get(anyInt());

        final String element = listMock.get(1);
        assertThat(element).isEqualTo("Always the same");
    }
    @Test
    void whenUsingSimpleMock_thenCorrect() {
        List listMock = mock(ArrayList.class);
        when(listMock.add(anyString())).thenReturn(false);

        boolean added = listMock.add(randomAlphabetic(6));

        assertThat(added).isFalse();
        verify(listMock).add(anyString());
    }

    @Test
    void givenFewActualInvocationThanConfigured_whenUsingMockWithName_thenExceptionIsThrown() {
        List listMock = mock(ArrayList.class, "myMock");

        when(listMock.add(anyString())).thenReturn(false);
        listMock.add(randomAlphabetic(6));

        assertThatThrownBy(() -> verify(listMock, times(2)).add(anyString()))
                .isInstanceOf(TooFewActualInvocations.class)
                .hasMessageContaining("myMock.add");
    }

    private static class CustomAnswer implements Answer<Boolean> {

        @Override
        public Boolean answer(InvocationOnMock invocation) throws Throwable {
            return false;
        }
    }

    @Test
    void whenUsingMockWithAnswer_thenCorrect() {
        List listMock = mock(ArrayList.class, new CustomAnswer());
        boolean added = listMock.add(randomAlphabetic(6));

        verify(listMock).add(anyString());
        assertThat(added).isFalse();
    }

    @Test
    void whenUsingMockWithSettings_thenCorrect() {
        MockSettings customSettings = withSettings().defaultAnswer(new CustomAnswer());
        List listMock = mock(ArrayList.class, customSettings);

        boolean added = listMock.add(randomAlphabetic(6));

        verify(listMock).add(anyString());
        assertThat(added).isFalse();
    }
}
