package be.vdab.frituur.repositories;


import be.vdab.frituur.domain.GastenBoekEntry;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import java.time.LocalDate;
import java.util.Comparator;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Import(JdbcGastenBoekRepository.class)
@Sql("/insertGastenboek.sql")
class JdbcGastenBoekRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {
    private static final String GASTENBOEK = "gastenboek";
    private final JdbcGastenBoekRepository repository;

    JdbcGastenBoekRepositoryTest(JdbcGastenBoekRepository repository) {
        this.repository = repository;
    }
    @Test
    void create() {
        var id = repository.create(new GastenBoekEntry(0,"test2", LocalDate.now(),"test"));
        assertThat(id).isPositive();
        assertThat(super.countRowsInTableWhere(GASTENBOEK, "id=" + id)).isOne();
    }
    @Test
    void findAll() {
        assertThat(repository.findAll()).hasSize(super.countRowsInTable(GASTENBOEK))
                .extracting(entry -> entry.getDatum()).isSortedAccordingTo(Comparator.reverseOrder());
    }
    private long idVanTestEntry() {
        return super.jdbcTemplate.queryForObject(
                "select id from gastenboek where naam ='test'", long.class);
    }
    @Test
    void delete() {
        var id = idVanTestEntry();
        repository.delete(id);
        assertThat(super.countRowsInTableWhere(GASTENBOEK, "id=" +id)).isZero();
    }


}
