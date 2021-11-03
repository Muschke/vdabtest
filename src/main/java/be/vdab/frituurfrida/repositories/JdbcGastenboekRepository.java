package be.vdab.frituurfrida.repositories;

import be.vdab.frituurfrida.domain.BerichtGastenboek;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
class JdbcGastenboekRepository implements GastenboekRepository{
    private final JdbcTemplate template;
    private final SimpleJdbcInsert insert;
    private final RowMapper<BerichtGastenboek> gastenboekRowMapper = (result, rowNum) ->
            new BerichtGastenboek(result.getLong("Id"), result.getDate("datum"), result.getString("naam"), result.getString("bericht"));

    JdbcGastenboekRepository(JdbcTemplate template) {
        this.template = template;
        insert = new SimpleJdbcInsert(template).withTableName("gastenboek").usingGeneratedKeyColumns("id");
    }

    @Override
    public List<BerichtGastenboek> findAll() {
        var sql = "select Id, datum, naam, bericht from gastenboek order by datum";
        return template.query(sql, gastenboekRowMapper);
    }

    @Override
    public long create(BerichtGastenboek berichtGastenboek) {
        return insert.executeAndReturnKey(Map.of("naam", berichtGastenboek.getNaam(),
                "datum", berichtGastenboek.getDatum(), "bericht", berichtGastenboek.getBericht())).longValue();
    }

    @Override
    public void delete(Long[] ids) {
        if(ids.length != 0) {
            var sql = "delete from gastenboek where id in (" +
                    "?,".repeat(ids.length - 1) + "?)";
            template.update(sql, ids);
        }
    }
}

