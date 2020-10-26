package be.vdab.frituur.domain;

public class Genre {
    private final long genreId;
    private final String naam;

    public Genre(long genreId, String naam) {
        this.genreId = genreId;
        this.naam = naam;
    }

    public long getGenreId() {
        return genreId;
    }

    public String getNaam() {
        return naam;
    }
}
