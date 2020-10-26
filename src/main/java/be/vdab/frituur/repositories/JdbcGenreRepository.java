package be.vdab.frituur.repositories;

import be.vdab.frituur.domain.Genre;
import be.vdab.frituur.domain.Saus;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
class JdbcGenreRepository implements GenreRepository {
    private final JdbcTemplate template;
    JdbcGenreRepository(JdbcTemplate template) {

        this.template = template;
    }

    private final RowMapper<Genre> snackRowMapper = (result, rowNum) -> new Genre(
            result.getLong("id"), result.getString("naam"));

    @Override
    public List<Genre> findAll() {
        var sql = "select id, naam from genres order by id ";
        return template.query(sql,snackRowMapper);
    }

    @Override
    public Optional<Genre> findById(long id) {
        try {
            var sql ="select id,naam from genres where id = ?";
            return Optional.of(template.queryForObject(sql,snackRowMapper, id));
        } catch (IncorrectResultSizeDataAccessException ex) {
            return Optional.empty();
        }}

}
