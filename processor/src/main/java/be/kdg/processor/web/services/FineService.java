package be.kdg.processor.web.services;

import be.kdg.processor.model.fine.Fine;
import be.kdg.processor.web.repos.FineRepo;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Is a class that stand between the controllers/calculators and the repository
 */
@Service
@Transactional
public class FineService {
    private final FineRepo fineRepo;

    public FineService(FineRepo fineRepo) {
        this.fineRepo = fineRepo;
    }


    public Fine saveFine(Fine Fine){
        return fineRepo.save(Fine);
    }

    public Fine loadFine(long id) throws FineException {
        Optional<Fine> optionalBoete = fineRepo.findById(id);
        if (optionalBoete.isPresent()) {
            return optionalBoete.get();
        }
        throw new FineException("Fine niet gevonden");
    }

    public List<Fine> loadAll() throws FineException {
        List<Fine> optionalFineList = fineRepo.findAll();
        if (optionalFineList.size() != 0) {
            return optionalFineList;
        }
        throw new FineException("geen Boetes gevonden.");
    }

    public List<Fine> filter(LocalDate beginSearch, LocalDate eindSearch) {
    List<Fine> alleFines = fineRepo.findAll();
    List<Fine> gefilterdeFines = alleFines.stream().filter(x -> x.getOffenseTime().isBefore(eindSearch.atStartOfDay()) && x.getOffenseTime().isAfter(beginSearch.atStartOfDay())).collect(Collectors.toList());
    return gefilterdeFines;
    }
}
