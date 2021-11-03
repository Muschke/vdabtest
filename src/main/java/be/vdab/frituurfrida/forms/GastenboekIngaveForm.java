package be.vdab.frituurfrida.forms;

import be.vdab.frituurfrida.domain.BerichtGastenboek;

import java.sql.Date;
import java.time.LocalDate;

public class GastenboekIngaveForm extends BerichtGastenboek {


    public GastenboekIngaveForm(String naam, String bericht) {
        super(0, Date.valueOf(LocalDate.now()), naam, bericht);
    }
}
