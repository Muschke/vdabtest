package be.vdab.frituurfrida.forms;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

public class VindBeginSnackForm {
    @NotEmpty @NotBlank
    private final String beginSnack;

    public VindBeginSnackForm(String beginSnack) {
        this.beginSnack = beginSnack;
    }

    public String getBeginSnack() {
        return beginSnack;
    }
}
