package be.vdab.frituurfrida.repositories;

import be.vdab.frituurfrida.domain.Snack;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import java.math.BigDecimal;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Import(JdbcSnackRepository.class)
@Sql("/insertSnacks.sql")
class JdbcSnackRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {
    private static final String SNACKS = "snacks";
    private final JdbcSnackRepository repository;
    JdbcSnackRepositoryTest(JdbcSnackRepository repository) {
        this.repository = repository;
    }
    //findbyid
    @Test
    @DisplayName("Opzoeken op ID werkt")
    void findById() {
        assertThat(repository.findById(idVanTestBerepoot()))
                .hasValueSatisfying(snack->assertThat(snack.getNaam()).isEqualTo("testBerepoot"));
        assertThat(repository.findById(idVanTestMammoet()))
                .hasValueSatisfying(snack->assertThat(snack.getNaam()).isEqualTo("testMammoet"));
    }
    //update
    @Test
    @DisplayName("updaten lukt correct")
    void update() {
        var id = idVanTestBerepoot();
        var snack = new Snack(id, "testBerepoot", BigDecimal.valueOf(2.90));
        repository.update(snack);
        assertThat(countRowsInTableWhere(SNACKS, "prijs=2.90 and id =" + id)).isOne();
    }
    //create
    @Test
    @DisplayName("create functie werkt")
    void create() {
        var id = repository.create(new Snack(0, "testSnack", BigDecimal.TEN));
        assertThat(id).isPositive();
        assertThat(countRowsInTableWhere(SNACKS, "id =" + id)).isOne();
    }
    //findbybeginNaam
    @Test
    @DisplayName("opzoeken van naam adhv beginletters werkt")
    void findByBeginNaam() {
        assertThat(repository.findByBeginNaam("t"))
                .hasSize(countRowsInTableWhere(SNACKS, "naam like 't%'"))
                .extracting(Snack::getNaam)
                .allSatisfy(naam -> assertThat(naam.toLowerCase(Locale.ROOT)).startsWith("t"))
                .isSortedAccordingTo(String::compareToIgnoreCase);
    }
    //functies om id uit tests te halen
    private long idVanTestBerepoot() {
        return jdbcTemplate.queryForObject("select id from snacks where naam = 'testBerepoot'", Long.class);
    }
    private long idVanTestMammoet() {
        return jdbcTemplate.queryForObject("select id from snacks where naam = 'testMammoet'", Long.class);
    }
}