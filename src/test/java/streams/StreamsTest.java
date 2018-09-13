package streams;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamsTest
{
    public static Supplier<Stream<Film>> streamSupplier;
    @BeforeAll
    public static void setup()
    {
        streamSupplier = () -> Stream.of(
                new Film("Shawshank redemption",123,new Person("Frank","Darabont")),
                new Film("Forest Gump",115,new Person("Robert","Zemeckis")),
                new Film("The Green Mile",188,new Person("Frank", "Durabont"))
                );
    }

    @Test
    public void getLongestFilm()
    {
        //given
        Streams streams = new Streams(streamSupplier.get());
        //when
        Integer longest = streams.getLongestFilmDuration();
        Integer actual = 188;

        //then
        assertEquals(longest,actual);
    }

    @TestFactory
    public Iterable<DynamicTest> sortingAndCollectingDynamicTest()
    {
        //given
        Streams streams = new Streams(streamSupplier.get());

        //when
        Iterator<Film> iterator = streams.getSortedFilmsLongestToShortest().iterator();
        //then
        return Arrays.asList(
                DynamicTest.dynamicTest("188 expected",() -> assertEquals(188,iterator.next().getDurationInMinutes())),
                DynamicTest.dynamicTest("123 expected",() -> assertEquals(123,iterator.next().getDurationInMinutes())),
                DynamicTest.dynamicTest("115 expected",() -> assertEquals(115,iterator.next().getDurationInMinutes()))
        );
    }

    @Test
    public void sumFilmDurations()
    {
        //given
        Streams streams1 = new Streams(streamSupplier.get());
        Streams streams2 = new Streams(streamSupplier.get());
        Integer sum = 188+123+115;
        //when
        Integer actual1 = streams1.getSumOfFilmDurations();
        Integer actual2 = streams2.getSumOfFilmDurations2();
        //then
        assertEquals(actual1,actual2);
        assertEquals(actual1,sum);
    }

    @Test
    public void concatTest()
    {
        //given
        Stream<Film> stream1 = streamSupplier.get();
        Stream<Film> stream2 = Stream.of(
                    new Film("The Green Mile",188,new Person("Frank", "Durabont")),
                    new Film("Intouchables",112,new Person("Olivier","Nakache"))
                    );
        Stream<Film> sumed = Stream.concat(stream1,stream2).distinct();

        //when
        long count = sumed.count();

        //then
        assertEquals(count,4);
    }

    @Test
    public void flatMapTest()
    {
        //given
        List<List<Film>> list = new LinkedList<>();
        IntStream.range(0,3).forEach(i -> list.add(new LinkedList<>()));
        for(List<Film> l : list) l.addAll(streamSupplier.get().collect(Collectors.toList()));
        long filmsCount = 9;
        //when
        long filmsCounted = list.stream().flatMap(l -> l.stream()).count();
        //then
        assertEquals(filmsCount,filmsCounted);
    }

    @Test
    public void filterFilmsLongerThan2hours()
    {
        //given
        Stream<Film> stream = streamSupplier.get();
        long shouldBe = 2;
        //when
        long twoHoursFilms = stream.filter(film -> film.getDurationInMinutes() > 120).count();
        //then
        assertEquals(shouldBe,twoHoursFilms);
    }
}
