package ru.netology.geo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import java.util.stream.Stream;
class GeoServiceImplTest {
    private GeoService geoService;

    @BeforeEach
    void setUp() {
        System.out.println("Running setUp");
        geoService = new GeoServiceImpl();
    }

    @AfterEach
    void tearDown() {
        System.out.println("Running tearDown");
        geoService = null;
    }

    @ParameterizedTest
    @MethodSource("addSource")
    void byIp(String ip, Location expected) {
        System.out.println("Running byIp: " + ip);
        // when
        Location result = geoService.byIp(ip);
        // then
        Assertions.assertTrue(locationEquals(expected, result));
    }

    public static Stream<Arguments> addSource() {
        // given
        return Stream.of(
                Arguments.of("172.15.1.10", new Location("Moscow", Country.RUSSIA, null, 0)),
                Arguments.of("96.103.2.20", new Location("New York", Country.USA, null, 0)),
                Arguments.of("44.108.10.10", null)
        );
    }

    private static boolean locationEquals(Location a, Location b) {
        if (a == null) {
            return b == null;
        }
        if (a != null && b == null) {
            return false;
        }
        String streetA = a.getStreet(), streetB = b.getStreet();
        boolean streetEquals = false;
        if (streetA == null) {
            if (streetB == null) {
                streetEquals = true;
            } else {
                streetEquals = streetA.equals(streetB);
            }
        }
        return a.getCity().equals(b.getCity())
                && a.getCountry().equals(b.getCountry())
                && a.getBuiling() == b.getBuiling()
                && streetEquals;
    }
}