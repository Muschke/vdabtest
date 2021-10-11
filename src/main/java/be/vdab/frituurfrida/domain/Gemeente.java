package be.vdab.frituurfrida.domain;

public class Gemeente {
    private final String gemeente;
    private final int postcode;

    public String getGemeente() {
        return gemeente;
    }
    public int getPostcode() {
        return postcode;
    }

    public Gemeente(String gemeente, int postcode) {
        this.gemeente = gemeente;
        this.postcode = postcode;
    }
}
