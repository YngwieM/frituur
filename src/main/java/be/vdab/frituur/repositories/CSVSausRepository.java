package be.vdab.frituur.repositories;

import be.vdab.frituur.domain.Saus;
import be.vdab.frituur.exceptions.SausRepositoryException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Qualifier("CSVSausenPad")
class CSVSausRepository implements SausRepository {
    private final Path pad;
    CSVSausRepository(@Value("${CSVSausenPad}") Path pad) {
        this.pad = pad;
    }
    @Override
    public List<Saus> findAll() {
        var sauzen = new ArrayList<Saus>();
        try {
            return Files.lines(pad)
                    .filter(regel -> ! regel.isEmpty())
                    .map(regel -> maakSaus(regel))
                    .collect(Collectors.toList());
        } catch (IOException ex) {
            throw new SausRepositoryException("Fout bij lezen " + pad);
        }
    }
    private Saus maakSaus(String regel) {
        var onderdelen = regel.split(",");
        if (onderdelen.length < 2) {
            throw new SausRepositoryException(
                    pad + ":" + regel + " bevat minder dan 2 onderdelen");
        }
        try {
            var ingredienten = Arrays.copyOfRange(onderdelen, 2, onderdelen.length);
            return new Saus(Long.parseLong(onderdelen[0]),onderdelen[1],ingredienten);
        } catch (NumberFormatException ex) {
            throw new SausRepositoryException(pad + ":" + regel + " bevat verkeerde id");
        }
    }
}