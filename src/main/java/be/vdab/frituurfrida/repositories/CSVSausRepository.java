package be.vdab.frituurfrida.repositories;

import be.vdab.frituurfrida.domain.Saus;
import be.vdab.frituurfrida.exceptions.SausRepositoryException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;



@Component @Qualifier("CSV")
class CSVSausRepository implements SausRepository {
    private final Path pad;

    CSVSausRepository(@Value("${CSVSausenPad}") Path pad) {
        this.pad = pad;
    }

    @Override
    public List<Saus> findAll() {
        try {
            return Files.lines(pad) //inlezen van gehele bestand op PAD
                    .map(this::maakSaus) //mappen of invullen zoals de functie maakSaus
                    /*lamba voor (saus -> maakSaus(saus)*/
                    .collect(Collectors.toList());//gegevens in lijst samenbrengen
        } catch (IOException ex) { //gooit exceptie als hij bestand niet vind
            throw new SausRepositoryException("Fout bij lezen " + pad);
        }
    }

    /*Functie om 1 saus uit csv bestand te halen*/
    private Saus maakSaus(String regel) { // Functie waar een string ingaat, CSV bestand zijn woorden gescheiden door komma's.
        var onderdelen = regel.split(","); //Nu steken we alles tussen de kommma's in array van onderdelen
        if(onderdelen.length < 2) {
            throw new SausRepositoryException(pad + ":" + regel + ": minder dan 2 onderdelen");
        }
        try{
            var ingredienten = Arrays.copyOfRange(onderdelen, 2, onderdelen.length);
                    /*copyOfRange kopieert specifieke range van array in nieuwe array
                    * nieuwe Array = copyOfRange(OrigineleArray, integer vanaf, int tot)
                    * hier is het integer 2, want 0 is het id, 1 is  de naam, dus vanaf 2
                    * de ingredienten*/
            return new Saus(Long.parseLong(onderdelen[0]), onderdelen[1], ingredienten);
                    /*Nu wil je de gegevens als saus terugbrengen zodat je deze in je lijst kan voegen.
                    * Saus bestaat uit Id, Naam en Ingredienten. Alle gegevens uit de CSV worden als string beschouw. Het Id op plaats 0
                    * moet dus naar long geconverteerd worden. Dan geven we de naam in, als laatste onze nieuwe array met enkel ingredienten*/
        } catch (NumberFormatException ex) { //als je id ingeeft die niet bestaat in de CSV, gat hij deze exceptie gooien.
            throw new SausRepositoryException(pad + ":" + regel + ": verkeerde id");
        }
    }
}
