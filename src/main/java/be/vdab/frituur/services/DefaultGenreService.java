package be.vdab.frituur.services;


import be.vdab.frituur.domain.Genre;
import be.vdab.frituur.domain.Saus;
import be.vdab.frituur.repositories.GenreRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
class DefaultGenreService implements GenreService {
    private final GenreRepository genreRepository;

    DefaultGenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }


    @Override
    public List<Genre> findAll() {
        return  genreRepository.findAll();

    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Genre> findById(long id) {
        return genreRepository.findById(id);
    }
}
