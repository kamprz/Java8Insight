package streams;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Streams
{
    public Stream<Film> stream;
    public Streams(Stream<Film> stream)
    {
        this.stream=stream;
    }

    public static void main(String[] args)
    {

    }

    public Integer getLongestFilmDuration() throws RuntimeException
    {
        Optional<Integer> opt = stream.map(film -> film.durationInMinutes)
                .max(Integer::compare);
        Supplier<RuntimeException> thrower = () -> {throw new RuntimeException();};
        return opt.orElseThrow(thrower);

        /*
        Doing it like this:
        stream.map(film -> film.durationInMinutes)
                .max(Integer::compare).orElseThrow(() -> {throw new RuntimeException();});
        causes IntelliJ to show an error: Throwable must be caught or declared to be thrown
         */
    }

    public List<Film> getSortedFilmsLongestToShortest()
    {
        return stream.sorted(Comparator.comparingInt(Film::getDurationInMinutes).reversed())
                     .collect(Collectors.toList());
    }

    public Double getAverageFilmDuration()
    {
        return stream.collect(Collectors.averagingDouble(Film::getDurationInMinutes));
    }

    public Integer getSumOfFilmDurations()
    {
        return stream.map(Film::getDurationInMinutes).reduce(0,(a,b) -> a+b);
    }

    public Integer getSumOfFilmDurations2()
    {
        return stream.mapToInt(Film::getDurationInMinutes).sum();
    }



}
