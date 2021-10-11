package be.vdab.frituurfrida.domain;

public class Adres {
    private final String straat;
    private final int nummer;
    private final Gemeente gemeente;

    public Adres(String straat, int nummer, Gemeente gemeente) {
        this.straat = straat;
        this.nummer = nummer;
        this.gemeente = gemeente;
    }

    public String getStraat() {
        return straat;
    }

    public int getNummer() {
        return nummer;
    }

    public Gemeente getGemeente() {
        return gemeente;
    }
}
