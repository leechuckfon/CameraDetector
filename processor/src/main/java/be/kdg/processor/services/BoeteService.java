package be.kdg.processor.services;

import be.kdg.processor.model.boete.Boete;
import be.kdg.processor.repos.BoeteRepo;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BoeteService {
    private final BoeteRepo boeteRepo;

    public BoeteService(BoeteRepo boeteRepo) {
        this.boeteRepo = boeteRepo;
    }


    public Boete saveBoete(Boete Boete){
        return boeteRepo.save(Boete);
    }

    public Boete loadBoete(long id) throws BoeteException{
        Optional<Boete> optionalBoete = boeteRepo.findById(id);
        if (((Optional) optionalBoete).isPresent()) {
            return optionalBoete.get();
        }
        throw new BoeteException("Boete niet gevonden");
    }

    public List<Boete> loadAll() throws BoeteException{
        List<Boete> optionalBoeteList = boeteRepo.findAll();
        if (optionalBoeteList.size() != 0) {
            return optionalBoeteList;
        }
        throw new BoeteException("geen Boetes gevonden.");
    }
}
