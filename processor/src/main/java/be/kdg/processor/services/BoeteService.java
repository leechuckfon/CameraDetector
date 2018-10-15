package be.kdg.processor.services;

import be.kdg.processor.model.Boete;
import be.kdg.processor.repos.BoeteRepo;

import java.util.List;
import java.util.Optional;

public class BoeteService {
    private final BoeteRepo boeteRepo;

    public BoeteService(BoeteRepo boeteRepo) {
        this.boeteRepo = boeteRepo;
    }


    public Boete save(Boete Boete){
        return boeteRepo.save(Boete);
    }

    public Boete load(long id) throws BoeteException{
        Optional<Boete> optionalBoete = boeteRepo.findById(id);
        if (((Optional) optionalBoete).isPresent()) {
            return optionalBoete.get();
        }
        throw new BoeteException("Boete niet gevonden");
    }

    public List<Boete> loadAll() throws BoeteException{
        List<Boete> optionalBoeteList = boeteRepo.findAll();
        if (optionalBoeteList.size() == 0) {
            return optionalBoeteList.get();
        }
        throw new BoeteException("geen boetes gevonden.");
    }
}
