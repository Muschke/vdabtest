package be.vdab.frituurfrida.forms;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

public class ToevoegenBerichtForm {
    @NotBlank @NotEmpty
    private final String naam;
    @NotBlank @NotEmpty
    private final String bericht;

    public ToevoegenBerichtForm(String naam, String bericht) {
        this.naam = naam;
        this.bericht = bericht;
    }

    public String getNaam() {
        return naam;
    }

    public String getBericht() {
        return bericht;
    }
}
