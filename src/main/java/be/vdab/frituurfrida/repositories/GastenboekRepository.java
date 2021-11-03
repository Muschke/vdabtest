package be.vdab.frituurfrida.repositories;

import be.vdab.frituurfrida.domain.BerichtGastenboek;

import java.util.List;

public interface GastenboekRepository {
    List<BerichtGastenboek> findAll();
    long create(BerichtGastenboek berichtGastenboek);
    void delete(Long[] ids);
}
