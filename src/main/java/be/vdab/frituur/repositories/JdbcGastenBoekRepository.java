package be.vdab.frituur.repositories;


import be.vdab.frituur.domain.GastenBoekEntry;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
class JdbcGastenBoekRepository implements GastenBoekRepository {
    private final SimpleJdbcInsert insert;
    private final JdbcTemplate template;

    JdbcGastenBoekRepository(JdbcTemplate template) {
        this.template = template;
        this.insert = new SimpleJdbcInsert(template)
                .withTableName("gastenboek")
                .usingGeneratedKeyColumns("id");
    }
    @Override
    public long create (GastenBoekEntry entry) {
        var kolomWaarden = Map.of("naam", entry.getNaam()
        ,"datum", entry.getDatum(),"bericht", entry.getBericht());
        var id = insert.executeAndReturnKey(kolomWaarden);
        return id.longValue();
    }
    private final RowMapper<GastenBoekEntry> entryRowMapper =
            (result, rowNum) -> new GastenBoekEntry(result.getLong("id"),
                    result.getString("naam"),
                    result.getDate("datum").toLocalDate(),
                    result.getString("bericht"));
    @Override
    public List<GastenBoekEntry> findAll() {
        var sql = "select id, naam, datum, bericht from gastenboek order by datum desc";
        return template.query(sql,entryRowMapper);
    }

    @Override
    public void delete(long id) {
        template.update("delete from gastenboek where id = ?", id);
    }
}
