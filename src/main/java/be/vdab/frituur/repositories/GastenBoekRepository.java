package be.vdab.frituur.repositories;

import be.vdab.frituur.domain.GastenBoekEntry;

import java.util.List;

public interface GastenBoekRepository {
    long create(GastenBoekEntry entry);
    List<GastenBoekEntry> findAll();
    void delete(long id);
}
