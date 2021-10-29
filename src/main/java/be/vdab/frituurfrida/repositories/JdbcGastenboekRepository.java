package be.vdab.frituurfrida.repositories;

import be.vdab.frituurfrida.domain.BerichtGastenboek;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;

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
    public void create(BerichtGastenboek berichtGastenboek) {
        var sql = "insert into gastenboek(naam, bericht) values (?,?)";
        insert.usingGeneratedKeyColumns(sql, berichtGastenboek.getNaam(), berichtGastenboek.getBericht());
    }
}
