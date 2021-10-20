package be.vdab.frituurfrida.services;

import be.vdab.frituurfrida.domain.Saus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface SausService {
    List<Saus> findAll();
    List<Saus> findByNaamBegintMet(char letter);
    Optional<Saus> findById(long id);
}
