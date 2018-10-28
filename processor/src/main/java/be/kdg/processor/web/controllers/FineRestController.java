package be.kdg.processor.web.controllers;

import be.kdg.processor.model.fine.Fine;
import be.kdg.processor.web.dto.FineChangeDTO;
import be.kdg.processor.web.dto.FineDTO;
import be.kdg.processor.web.dto.ListFineDTO;
import be.kdg.processor.web.services.FineException;
import be.kdg.processor.web.services.FineService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The FineRestController is the controller where all the API calls will arrive.
 * here, you can approve, disapprove or filter between two dates.
 */
@RestController
@RequestMapping("/api")
public class FineRestController {
    private final FineService fineService;
    private final ModelMapper modelMapper;

    public FineRestController(FineService fineService, ModelMapper modelMapper) {
        this.fineService = fineService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/fines")
    public ResponseEntity<ListFineDTO> loadAll() throws FineException {
        List<Fine> fine = fineService.loadAll();
        ListFineDTO listFineDTO = new ListFineDTO(fine);
        return new ResponseEntity<>(listFineDTO, HttpStatus.OK);
    }

    @PutMapping("/approvefine/{id}")
    public ResponseEntity<FineDTO> approve(@PathVariable long id) throws FineException {
        Fine fineIN = fineService.loadFine(id);
        Fine updatedFine =  fineService.approveFine(fineIN);
        return new ResponseEntity<>(modelMapper.map(updatedFine, FineDTO.class), HttpStatus.ACCEPTED);
    }

    @PutMapping("/changeFine/{id}")
    public ResponseEntity<FineDTO> changeFine(@PathVariable long id, @RequestBody FineChangeDTO fineChange) throws FineException {
        Fine fineIN = fineService.loadFine(id);
        Fine updatedFine =  fineService.changeFine(fineIN,fineChange);
        return new ResponseEntity<>(modelMapper.map(updatedFine, FineDTO.class), HttpStatus.ACCEPTED);
    }


    @PutMapping("/disapproveFine/{id}")
    public ResponseEntity<FineDTO> disapprove(@PathVariable long id) throws FineException {
        Fine fineIN = fineService.loadFine(id);
        Fine updatedFine =  fineService.unapproveFine(fineIN);
        return new ResponseEntity<>(modelMapper.map(updatedFine, FineDTO.class), HttpStatus.ACCEPTED);
    }

    @GetMapping("/fines/{begin}/{end}")
    public ResponseEntity<ListFineDTO> filterDate(@PathVariable String begin, @PathVariable String end) {
        List<Fine> filtered = fineService.filter(begin,end);
        ListFineDTO fineList = new ListFineDTO(filtered);
        return new ResponseEntity<>(fineList,HttpStatus.OK);
    }
}
