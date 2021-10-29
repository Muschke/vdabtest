package be.vdab.frituurfrida.repositories;

import be.vdab.frituurfrida.domain.BerichtGastenboek;

import java.util.List;

public interface GastenboekRepository {
    List<BerichtGastenboek> findAll();
    void create(BerichtGastenboek berichtGastenboek);

}
