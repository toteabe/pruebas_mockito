package org.iesvdm;

import static org.assertj.core.api.Assertions.*;
import static  org.junit.jupiter.api.Assertions.*;

import org.iesvdm.dominio.Person;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.mockito.AdditionalMatchers.*;
public class ArgumentMatcherTest {

    @Test
    void anyArgumentTest() {

        Person person = mock(Person.class);

        when(person.readAccess(anyString(), anyInt(), anyList()))
                .thenReturn(true);

        assertThat(person.readAccess("jaja", 1000, new ArrayList<>()))
                .isTrue();

        assertThat(person.readAccess("user", 10, List.of("read", "write" ) ))
                .isTrue();

    }

    @Test
    void anyAndAdditionalArgumentMatchersTest() {

        Person person = mock(Person.class);

        //Una vez se utiliza un argument matcher en un argumento
        //tienes que utilizarlo en todos los argumentos
        //                              |______________________
        //                              V           V          V
        when(person.readAccess(eq("user"), anyInt(), anyList()))
                .thenReturn(false);
        when(person.readAccess(eq("admin"), anyInt(), anyList()))
                .thenReturn(true);
        //De AdditionalMatcher
        //              ---------------------
        //                                  V
        when(person.readAccess(anyString(),lt(3) , anyList()))
                .thenReturn(true);

        assertThat(person.readAccess("user", 1000, new ArrayList<>()))
                .isFalse();
        assertThat(person.readAccess("admin", 1000, new ArrayList<>()))
                .isTrue();
        assertThat(person.readAccess("admin", 1000, new ArrayList<>()))
                .isTrue();

    }

    @Test
    void verifyArgmumentMatcher() {

        Person person = mock(Person.class);

        when(person.readAccess(eq("user"), anyInt(), anyList()))
                .thenReturn(false);

        assertThat(person.readAccess("user", 1000, new ArrayList<>()))
                .isFalse();

        verify(person, atLeast(1)).readAccess(eq("user"), anyInt(), anyList());

    }

}
