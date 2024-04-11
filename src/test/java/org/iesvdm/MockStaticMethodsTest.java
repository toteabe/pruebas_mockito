package org.iesvdm;


import org.iesvdm.dominio.StaticUtils;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.Arrays;

public class MockStaticMethodsTest {

    @Test
    void givenStaticMethodWithNoArgs_whenMocked_thenReturnsMockSuccessfully() {
        assertThat(StaticUtils.name()).isEqualTo("iesvdm");

        try (MockedStatic<StaticUtils> utilities = mockStatic(StaticUtils.class)) {
            utilities.when(StaticUtils::name).thenReturn("jose");
            assertThat(StaticUtils.name()).isEqualTo("jose");
        }

        assertThat(StaticUtils.name()).isEqualTo("iesvdm");

    }

    @Test
    void givenStaticMethodWithArgs_whenMocked_thenReturnsMockSuccessfully() {

        assertThat(StaticUtils.range(2, 6)).containsExactly(2, 3, 4, 5);

        try (MockedStatic<StaticUtils> utilities = mockStatic(StaticUtils.class)) {
            utilities.when(() -> StaticUtils.range(2, 6))
                    .thenReturn(Arrays.asList(10, 11, 12));

            assertThat(StaticUtils.range(2, 6)).containsExactly(10, 11, 12);
        }

        assertThat(StaticUtils.range(2, 6)).containsExactly(2, 3, 4, 5);
    }

}