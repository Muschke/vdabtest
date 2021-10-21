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

@Component @Qualifier("Properties")
class PropertiesSausRepository implements SausRepository{
    private final Path pad;

    PropertiesSausRepository(@Value("${propertiesSausenPad}")Path pad) {
        this.pad = pad;
    }


    @Override
    public List<Saus> findAll() {
        try {
            return Files.lines(pad)
                    .map(this::maakSaus)
                    .collect(Collectors.toList());
        } catch (IOException ex) {
            throw new SausRepositoryException("Fout bij lezen van " + pad);
        }
    }

    private Saus maakSaus(String regel) {
        var onderdelen = regel.split(":"); //dit bestand is id: naam, ingredient1, ingredient2, ..
        /*We hebben dus nu id in onderdelen[0] en de rest in onderdelen[1]*/
        if (onderdelen.length < 2) {
            throw new SausRepositoryException(pad + ":" + regel + ": minder dan 2 onderdelen");
        }
        try {
            var naamEnIngredienten = onderdelen[1].split(","); //We willen hier dus eerst onze naam en ingredientenlijst nog eens opsplitsten
            /*Nu hebben we dus id in onderdelen[0], naam in NaamEnIngredienten[0] en de ingredienten in NaamEnIngredienten[0 + x]*/
            var ingredienten = Arrays.copyOfRange(naamEnIngredienten, 1, naamEnIngredienten.length);
            /*Nu hebben we dus id in onderdelen[0], naam in NaamEnIngredienten[0] en de ingredienten in ingredienten
            * Deze gaan we gewoon in onze sausconstructor zetten en dat terugbrengen, klaar !let op-->id converteren naar long*/
            return new Saus(Long.parseLong(onderdelen[0]), naamEnIngredienten[0], ingredienten);
        } catch (NumberFormatException ex) {
            throw new SausRepositoryException(pad + ":" + regel + "verkeerde id");
        }
    }
}
