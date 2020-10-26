package be.vdab.frituur.services;

import be.vdab.frituur.domain.GastenBoekEntry;

import java.util.List;

public interface GastenBoekService {
    long create (GastenBoekEntry entry);
    List<GastenBoekEntry> findAll();
    void delete(long[] ids);
}
