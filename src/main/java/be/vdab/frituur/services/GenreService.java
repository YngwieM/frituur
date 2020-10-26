package be.vdab.frituur.services;


import be.vdab.frituur.domain.Genre;
import be.vdab.frituur.domain.Saus;

import java.util.List;
import java.util.Optional;

public interface GenreService {
    List<Genre> findAll();
    Optional<Genre> findById(long id);
}
