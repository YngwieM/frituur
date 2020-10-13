package be.vdab.frituur.repositories;


import be.vdab.frituur.domain.Snack;
import be.vdab.frituur.exceptions.SnackNietGevondenException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;


import java.math.BigDecimal;

@JdbcTest
@Import(JdbcSnackRepository.class)
@Sql("/insertSnacks.sql")
class JdbcSnackRepositoryTest
        extends AbstractTransactionalJUnit4SpringContextTests {
    private final JdbcSnackRepository repository;

    JdbcSnackRepositoryTest(JdbcSnackRepository repository) {
        this.repository = repository;
    }

    private long idVanTestSnack() {
        return super.jdbcTemplate.queryForObject(
                "select id from snacks where naam='test'", Long.class);
    }
    @Test
    void update() {
        var id = idVanTestSnack();
        var snack = new Snack(id, "test", BigDecimal.ONE);
        repository.update(snack);
        assertThat(super.jdbcTemplate.queryForObject(
                "select prijs from snacks where id=?", BigDecimal.class, id)).isOne();
    }
    @Test
    void updateOnbestaandeSnack() {
        assertThatExceptionOfType(SnackNietGevondenException.class)
                .isThrownBy(() -> repository.update(new Snack(-1,"test",BigDecimal.ONE)));
    }
    @Test
    void findById() {
        assertThat(repository.findById(idVanTestSnack()).get().getNaam())
                .isEqualTo("test");
    }
    @Test
    void findByOnbestaandeIdVindtGeenSnack() {
        assertThat(repository.findById(-1)).isNotPresent();
    }
    @Test
    void findByBeginNaam() {
        assertThat(repository.findByBeginNaam("t"))
                .hasSize(super.jdbcTemplate.queryForObject(
                        "select count(*) from snacks where naam like 't%'", Integer.class))
                .extracting(snack -> snack.getNaam().toLowerCase())
                .allSatisfy(naam -> assertThat(naam.startsWith("t")))
                .isSorted();
    }
}
