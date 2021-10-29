package be.vdab.frituurfrida.services;

import be.vdab.frituurfrida.domain.BerichtGastenboek;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface GastenboekService {
    void create(BerichtGastenboek berichtGastenboek);
    List<BerichtGastenboek> findAll();
}
