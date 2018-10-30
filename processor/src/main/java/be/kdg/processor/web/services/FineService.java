package be.kdg.processor.web.services;

import be.kdg.processor.model.CameraMessage;
import be.kdg.processor.model.fine.Fine;
import be.kdg.processor.web.dto.FineChangeDTO;
import be.kdg.processor.web.repos.FineRepo;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
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
        throw new FineException("Fine not found");
    }

    public List<Fine> loadAll() throws FineException {
        List<Fine> optionalFineList = fineRepo.findAll();
        if (optionalFineList.size() != 0) {
            return optionalFineList;
        }
        throw new FineException("No Fines found.");
    }

    public List<Fine> filter(String beginSearch, String eindSearch) throws FineException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate startOfSearch = LocalDate.parse(beginSearch,dtf);
        LocalDate endOfSearch = LocalDate.parse(eindSearch,dtf);
        List<Fine> alleFines = fineRepo.findAll();
        if (alleFines.isEmpty()) {
            throw new FineException("Fines not found");
        }
        return alleFines.stream().filter(x -> x.getOffenseTime().isBefore(endOfSearch.atStartOfDay()) && x.getOffenseTime().isAfter(startOfSearch.atStartOfDay())).collect(Collectors.toList());
    }

    public Optional<Fine> checkfordoubles(CameraMessage cm , long timeframe) throws FineException {
        List<Fine> alleFines = fineRepo.findAll();
        if (alleFines.isEmpty()) {
            throw new FineException("Fines not found");
        }
        return alleFines.stream().filter(x -> x.getLicenseplate().equals(cm.getLicensePlate()) && x.getOffenseTime().until(cm.getTimestamp(), ChronoUnit.MILLIS) < timeframe).findFirst();
    }

    public Fine approveFine(Fine unapprovedfine) throws FineException{
        if (unapprovedfine == null) {
                throw new FineException("Fines not found");
        }
        unapprovedfine.setApproved(true);
        return this.saveFine(unapprovedfine);
    }

    public Fine changeFine(Fine fineIN, FineChangeDTO fineChange) throws FineException{
        if (fineIN == null) {
            throw new FineException("Fines not found");
        }
        fineIN.setFee(Integer.parseInt(fineChange.getNewFee()));
        fineIN.setMotivation(fineChange.getMotivation());
        return this.saveFine(fineIN);
    }

    public Fine unapproveFine(Fine fineIN) throws FineException{
        if (fineIN == null) {
            throw new FineException("Fines not found");
        }
        fineIN.setApproved(false);
        return this.saveFine(fineIN);
    }
}

