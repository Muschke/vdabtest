package be.vdab.frituurfrida.domain;

import java.util.Date;

public class BerichtGastenboek {
    private final long id;
    private final Date datum;
    private final String naam;
    private final String bericht;


    public BerichtGastenboek(long id, Date datum, String naam, String bericht) {
        this.id = id;
        this.datum = datum;
        this.naam = naam;
        this.bericht = bericht;

    }

    public long getId() {
        return id;
    }

    public Date getDatum() {
        return datum;
    }

    public String getNaam() {
        return naam;
    }

    public String getBericht() {
        return bericht;
    }
}