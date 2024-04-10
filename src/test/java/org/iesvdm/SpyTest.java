package org.iesvdm;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.exceptions.misusing.NotAMockException;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatNoException;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class SpyTest {

    @Spy
    private List<String> aSpyList = new ArrayList<String>();

    @Test
    void givenUsingSpyMethod_whenSpyingOnList_thenCorrect() {
        final List<String> list = new ArrayList<String>();
        final List<String> spyList = spy(list);

        spyList.add("one");
        spyList.add("two");

        verify(spyList).add("one");
        verify(spyList).add("two");

        assertThat(spyList).hasSize(2);
    }

    @Test
    void givenUsingSpyAnnotation_whenSpyingOnList_thenCorrect() {
        aSpyList.add("one");
        aSpyList.add("two");

        verify(aSpyList).add("one");
        verify(aSpyList).add("two");

        assertThat(aSpyList).hasSize(2);
    }

    @Test
    void givenASpy_whenStubbingTheBehaviour_thenCorrect() {
        final List<String> list = new ArrayList<String>();
        final List<String> spyList = spy(list);

        assertEquals(0, spyList.size());

        doReturn(100).when(spyList).size();

        assertThat(spyList).hasSize(100);
    }

    @Test
    void whenCreateMock_thenCreated() {
        final List<String> mockedList = mock(ArrayList.class);

        mockedList.add("one");
        verify(mockedList).add("one");

        assertThat(mockedList).hasSize(0);
    }

    @Test
    void whenCreateSpy_thenCreate() {
        final List<String> spyList = spy(new ArrayList<>());

        spyList.add("one");
        verify(spyList).add("one");

        assertThat(spyList).hasSize(1);
    }

    @Test
    void givenNotASpy_whenDoReturn_thenThrowNotAMock() {
        List<String> list = new ArrayList<String>();

        assertThatThrownBy(() -> doReturn(100).when(list).size())
                .isInstanceOf(NotAMockException.class)
                .hasMessageContaining("Argument passed to when() is not a mock!");
    }

    @Test
    void givenASpy_whenDoReturn_thenNoError() {
        final List<String> spyList = spy(new ArrayList<>());

        assertThatNoException().isThrownBy(() -> doReturn(100).when(spyList).size());
    }

}
