package be.vdab.frituurfrida.repositories;

import be.vdab.frituurfrida.domain.Snack;
import be.vdab.frituurfrida.exceptions.SnackNietGevondenException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
class JdbcSnackRepository implements SnackRepository {
    private final JdbcTemplate template;
    /*Rowmapper: */
    private final RowMapper<Snack> snackMapper = (result, rowNum) -> new Snack(result.getLong("id"), result.getString("naam"), result.getBigDecimal("prijs"));
    //simpleInsert class toevoegen, deze laat toe makkelijk gegevens toe te voegen in de sql database
    private final SimpleJdbcInsert insert;
    JdbcSnackRepository(JdbcTemplate template) {
        this.template = template;
        this.insert = new SimpleJdbcInsert(template).withTableName("snacks").usingGeneratedKeyColumns("id");
    }


    @Override
    public Optional<Snack> findById(long id) {
        try {
            var sql ="select id, naam, prijs from snacks where id = ?";
            return Optional.of(template.queryForObject(sql, snackMapper, id));
        } catch (IncorrectResultSizeDataAccessException ex) {
            return Optional.empty();
        }
    }
    @Override
    public long create(Snack snack) {
        return insert.executeAndReturnKey(Map.of("naam", snack.getNaam(), "prijs", snack.getPrijs())).longValue();
    }
    @Override
    public void update(Snack snack) {
        var sql = "update snacks set naam = ?, prijs = ? where id = ?";
        if(template.update(sql, snack.getNaam(), snack.getPrijs(), snack.getId()) == 0) {
            throw new SnackNietGevondenException();
        }
    }
    @Override
    /*The LIKE operator is used in a WHERE clause to search for a specified pattern in a column.
    *   - The percent sign (%) represents zero, one, or multiple characters
    *   - The underscore sign (_) represents one, single character*/
    public List<Snack> findByBeginNaam(String beginNaam) {
        var sql = "select id, naam, prijs from snacks where naam like ? order by naam";
        return template.query(sql, snackMapper,  beginNaam + "%");
    }

}
