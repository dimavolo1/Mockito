package ru.netology.i18n;


import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.entity.Country;

import java.util.stream.Stream;


class LocalizationServiceImplTest {
    private LocalizationService localizationService;

    @BeforeEach
    public void setup() {
        System.out.println("Running setup");
        localizationService = new LocalizationServiceImpl();
    }

    @AfterEach
    public void tearDown() {
        System.out.println("Running tearDown");
        localizationService = null;
    }

    @ParameterizedTest
    @MethodSource("addSource")
    public void LocaleTest(Country country, String expected) {
        System.out.println("Running LocaleTest: " + country.name());
        // when
        String result = localizationService.locale(country);
        // then
        Assertions.assertEquals(expected, result);
    }

    public static Stream<Arguments> addSource() {
        // given
        return Stream.of(
                Arguments.of(Country.RUSSIA, "Добро пожаловать"),
                Arguments.of(Country.GERMANY, "Welcome"),
                Arguments.of(Country.USA, "Welcome"),
                Arguments.of(Country.BRAZIL, "Welcome")
        );
    }
}