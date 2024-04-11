package org.iesvdm;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.InOrder;
import org.mockito.exceptions.verification.NoInteractionsWanted;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class VerifyTest {

    @Test
    final void givenInteractionWithMock_whenVerifyingInteraction_thenCorrect() {
        final List<String> mockedList = mock(ArrayList.class);
        mockedList.size();
        verify(mockedList).size();
    }

    @Test
    final void givenOneInteractionWithMock_whenVerifyingNumberOfInteractions_thenCorrect() {
        final List<String> mockedList = mock(ArrayList.class);
        mockedList.size();
        verify(mockedList, times(1)).size();
    }

    @Test
    final void givenNoInteractionWithWholeMock_whenVerifyingInteractions_thenCorrect() {
        final List<String> mockedList = mock(ArrayList.class);
        verifyNoInteractions(mockedList);
    }

    @Test
    final void givenNoInteractionWithSpecificMethod_whenVerifyingInteractions_thenCorrect() {
        final List<String> mockedList = mock(ArrayList.class);
        verify(mockedList, times(0)).size();
    }

    @Test
    final void givenUnverifiedInteraction_whenVerifyingNoUnexpectedInteractions_thenFail() {
        final List<String> mockedList = mock(ArrayList.class);
        mockedList.size();
        mockedList.clear();

        verify(mockedList).size();

        assertThrows(NoInteractionsWanted.class, () -> verifyNoMoreInteractions(mockedList));
    }

    @Test
    final void givenInteractionsInOrder_whenVerifyingOrderOfInteractions_thenCorrect() {
        final List<String> mockedList = mock(ArrayList.class);
        mockedList.size();
        mockedList.add("a parameter");
        mockedList.clear();

        final InOrder inOrder = inOrder(mockedList);
        inOrder.verify(mockedList).size();
        inOrder.verify(mockedList).add("a parameter");
        inOrder.verify(mockedList).clear();
    }

    @Test
    final void givenNoInteraction_whenVerifyingAnInteraction_thenCorrect() {
        final List<String> mockedList = mock(ArrayList.class);
        mockedList.size();

        verify(mockedList, never()).clear();
    }

    @Test
    final void givenInteractionAtLeastOnce_whenVerifyingAnInteraction_thenCorrect() {
        final List<String> mockedList = mock(ArrayList.class);
        mockedList.clear();
        mockedList.clear();
        mockedList.clear();

        verify(mockedList, atLeast(1)).clear();
        verify(mockedList, atMost(10)).clear();
    }

    // with arguments

    @Test
    final void givenInteractionWithExactArgument_whenVerifyingAnInteraction_thenCorrect() {
        final List<String> mockedList = mock(ArrayList.class);
        mockedList.add("test");

        verify(mockedList).add("test");
    }

    @Test
    final void givenInteractionWithAnyArgument_whenVerifyingAnInteraction_thenCorrect() {
        final List<String> mockedList = mock(ArrayList.class);
        mockedList.add("test");

        verify(mockedList).add(anyString());
    }

    @Test
    final void givenInteraction_whenVerifyingAnInteractionWithArgumentCapture_thenCorrect() {
        final List<String> mockedList = mock(ArrayList.class);
        mockedList.addAll(Lists.<String> newArrayList("someElement"));

        final ArgumentCaptor<List<String>> argumentCaptor = ArgumentCaptor.forClass(List.class);
        verify(mockedList).addAll(argumentCaptor.capture());

        final List<String> capturedArgument = argumentCaptor.getValue();
        assertThat(capturedArgument).contains("someElement");
    }

    @Test
    void advancedVerifyTest() {
        List<String> mockList = mock(List.class);
        mockList.add("Jose");
        mockList.size();

        verify(mockList).add("Jose");
        verify(mockList).add(anyString());
        verify(mockList).add(any(String.class));
        verify(mockList).add(ArgumentMatchers.any(String.class));

        verify(mockList, times(1)).size();
        verify(mockList, atLeastOnce()).size();
        verify(mockList, atMost(2)).size();
        verify(mockList, atLeast(1)).size();
        verify(mockList, never()).clear();

        // all interactions are verified, so below will pass
        verifyNoMoreInteractions(mockList);
        mockList.isEmpty();
        // isEmpty() no es verify, luego a continuacion falla verifyNoMoreInteractions
        // verifyNoMoreInteractions(mockList);
        Map mockMap = mock(Map.class);
        Set mockSet = mock(Set.class);
        verify(mockList).isEmpty();

        verifyNoInteractions(mockList, mockMap, mockSet);

        mockMap.isEmpty();
        verify(mockMap, only()).isEmpty();

        // verficando el orden de llamada a metodos de mocks
        // puedes saltarte metodos pero el orden debe mantenerse
        InOrder inOrder = inOrder(mockList, mockMap);
        inOrder.verify(mockList).add("Pankaj");
        inOrder.verify(mockList, calls(1)).size();
        inOrder.verify(mockList).isEmpty();
        inOrder.verify(mockMap).isEmpty();

    }
}
