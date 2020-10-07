package be.vdab.frituur.services;

import be.vdab.frituur.domain.Saus;

import java.util.List;
import java.util.Optional;

public interface SausService {
    List<Saus> findAll();
    List<Saus> findByNaamBegintMet(char letter);
    Optional<Saus> findById(long id);
}