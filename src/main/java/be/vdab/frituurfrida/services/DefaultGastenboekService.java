package be.vdab.frituurfrida.services;

import be.vdab.frituurfrida.domain.BerichtGastenboek;
import be.vdab.frituurfrida.repositories.GastenboekRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
class DefaultGastenboekService implements  GastenboekService{
    private final GastenboekRepository gastenboekRepository;

    DefaultGastenboekService(GastenboekRepository gastenboekRepository) {
        this.gastenboekRepository = gastenboekRepository;
    }

    @Override
    public void create(BerichtGastenboek berichtGastenboek) {
        gastenboekRepository.create(berichtGastenboek);
    }

    @Override
    @Transactional
    public List<BerichtGastenboek> findAll() {
        return gastenboekRepository.findAll();
    }

    @Override
    public void delete(Long[] ids) {
        gastenboekRepository.delete(ids);
    }
}
