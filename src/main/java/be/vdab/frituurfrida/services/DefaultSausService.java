package be.vdab.frituurfrida.services;

import be.vdab.frituurfrida.domain.Saus;
import be.vdab.frituurfrida.repositories.SausRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
class DefaultSausService implements SausService{
    /*We moeten de CSVSausRepository injecteren, dit doen we door variabele te maken die de repository oproept*/
    private final SausRepository sausRepository;

    public DefaultSausService(@Qualifier("Properties") SausRepository sausRepository) {
        this.sausRepository = sausRepository;
    }
    @Override
    public List<Saus> findAll() {
        return sausRepository.findAll();
    }
    @Override
    public List<Saus> findByNaamBegintMet(char letter) {
        return sausRepository.findAll().stream()
                .filter(saus -> saus.getNaam().charAt(0) == letter)
                .collect(Collectors.toList());
    }
    @Override
    public Optional<Saus> findById(long id) {
        return sausRepository.findAll().stream()
                .filter(saus -> saus.getId() == id)
                .findFirst(); //bij opzoeken van unieke waarde altijd findFirst (en optional), geen lijst van maken
    }
}
