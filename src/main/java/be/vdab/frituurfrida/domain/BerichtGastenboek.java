package be.vdab.frituurfrida.domain;

import java.sql.Date;
import java.time.LocalDate;

public class BerichtGastenboek {
    private final long id;
    private final LocalDate datum;
    private final String naam;
    private final String bericht;


    public BerichtGastenboek(long id, Date datum, String naam, String bericht) {
        this.id = id;
        this.datum = Date.valueOf(String.valueOf(datum)).toLocalDate();
        this.naam = naam;
        this.bericht = bericht;

    }

    public long getId() {
        return id;
    }

    public LocalDate getDatum() {
        return datum;
    }

    public String getNaam() {
        return naam;
    }

    public String getBericht() {
        return bericht;
    }
}