package be.vdab.frituurfrida.DTO;

public class TotaleVerkopenPerSnack {
    private final long id;
    private final String naam;
    private final int aantalVerkocht;

    public TotaleVerkopenPerSnack(long id, String naam, int aantalVerkocht) {
        this.id = id;
        this.naam = naam;
        this.aantalVerkocht = aantalVerkocht;
    }

    public long getId() {
        return id;
    }

    public String getNaam() {
        return naam;
    }

    public int getAantalVerkocht() {
        return aantalVerkocht;
    }
}
