package streams;

public class Film
{
    String title;
    Integer durationInMinutes;
    Person director;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDurationInMinutes() {
        return durationInMinutes;
    }

    public void setDurationInMinutes(int durationInMinutes) {
        this.durationInMinutes = durationInMinutes;
    }

    public Person getDirector() {
        return director;
    }

    public void setDirector(Person director) {
        this.director = director;
    }

    public Film(String title, int durationInMinutes, Person director) {
        this.title = title;
        this.durationInMinutes = durationInMinutes;
        this.director = director;
    }

    @Override
    public String toString() {
        return "Film{" +
                "title = '" + title + '\'' +
                ", durationInMinutes = " + durationInMinutes +
                ", director = " + director +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Film film = (Film) o;

        if (title != null ? !title.equals(film.title) : film.title != null) return false;
        if (durationInMinutes != null ? !durationInMinutes.equals(film.durationInMinutes) : film.durationInMinutes != null)
            return false;
        return director != null ? director.equals(film.director) : film.director == null;
    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (durationInMinutes != null ? durationInMinutes.hashCode() : 0);
        result = 31 * result + (director != null ? director.hashCode() : 0);
        return result;
    }
}
