package be.vdab.frituurfrida.repositories;

import be.vdab.frituurfrida.DTO.TotaleVerkopenPerSnack;
import be.vdab.frituurfrida.domain.Snack;

import java.util.List;
import java.util.Optional;

public interface SnackRepository {
    long create (Snack snack);
    void update(Snack snack);
    Optional<Snack> findById(long id);
    List<Snack> findByBeginNaam(String beginNaam);
    //invoegen DTO
    public List<TotaleVerkopenPerSnack> findTotaleVerkopenPerSnack();
}
