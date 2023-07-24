package ru.netology.sender;
import org.junit.jupiter.api.Assertions;;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.i18n.LocalizationService;


import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;


class MessageSenderImplTest {
    @ParameterizedTest
    @MethodSource("addSource")
    void send(String ip, String locale, Location location) {
        System.out.println("\nRunning send: " + ip);
        // given
        GeoService geoService = Mockito.mock(GeoService.class);
        Mockito.when(geoService.byIp(ip))
                .thenReturn(location);

        LocalizationService localizationService = Mockito.mock(LocalizationService.class);
        Mockito.when(localizationService.locale(location.getCountry()))
                .thenReturn(locale);

        MessageSender messageSender = new MessageSenderImpl(geoService, localizationService);
        Map<String, String> headers = new HashMap<String, String>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, ip);
        // when
        String result = messageSender.send(headers);
        // then
        Assertions.assertEquals(locale, result);
    }

    public static Stream<Arguments> addSource() {
        // given
        return Stream.of(
                Arguments.of("172.15.1.10", "Добро пожаловать",
                        new Location("Moscow", Country.RUSSIA, null, 0)),
                Arguments.of("96.103.2.20", "Welcome",
                        new Location("New York", Country.USA, null, 0))
        );
    }
}