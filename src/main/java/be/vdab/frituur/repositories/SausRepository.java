package be.vdab.frituur.repositories;

import be.vdab.frituur.domain.Saus;

import java.util.List;
import java.util.Optional;

public interface SausRepository {

  List<Saus> findAll();

}