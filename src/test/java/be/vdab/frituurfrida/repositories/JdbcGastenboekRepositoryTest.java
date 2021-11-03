package be.vdab.frituurfrida.repositories;

import be.vdab.frituurfrida.domain.BerichtGastenboek;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Import(JdbcGastenboekRepository.class)
@Sql("/insertgastenboek.sql")
class JdbcGastenboekRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {
    private static final String GASTENBOEK = "gastenboek";
    private final JdbcGastenboekRepository gastenboekRepository;
    JdbcGastenboekRepositoryTest(JdbcGastenboekRepository gastenboekRepository) {
        this.gastenboekRepository = gastenboekRepository;
    }

    @Test
    @DisplayName("Controle dat findAll evenveel rijen weergeeft als de tabel bevat")
    void findAantal() {
        assertThat(gastenboekRepository.findAll())
                .hasSize(countRowsInTable(GASTENBOEK))
                .extracting(BerichtGastenboek::getDatum)
                .isSorted();
    }

    @Test
    @DisplayName("methode create - automatische nummering en datestamp ok")
    void create() {
        gastenboekRepository.create(new BerichtGastenboek(0, new java.sql.Date(02-9-19998), "testnaam3", "testberichtdrie"));
        var id = jdbcTemplate.queryForObject(
                "select id from gastenboek where naam = 'testnaam3'", Long.class);
        assertThat(id).isPositive();
        assertThat(countRowsInTableWhere(GASTENBOEK, "id =" + id)).isOne();

    }

    @Test
    void delete() {
        var id = idVanTestBericht1();
        var id2 = idVanTestBericht2();
        gastenboekRepository.delete(new Long[] {id, id2});
        assertThat(countRowsInTableWhere(GASTENBOEK, "id in (" + id + "," + id2 + ")")).isZero();
    }

    @Test
    void deleteMetLegeArrayGeeftGeenException() {
        gastenboekRepository.delete(new Long[]{});
    }

    private long idVanTestBericht1() {
        return jdbcTemplate.queryForObject(
                "select id from gastenboek where naam = 'testnaam1'", Long.class);
    }
    private long idVanTestBericht2() {
        return jdbcTemplate.queryForObject(
                "select id from gastenboek where naam = 'testnaam2'", Long.class);
    }

}