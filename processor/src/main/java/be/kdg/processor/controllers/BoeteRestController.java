package be.kdg.processor.controllers;

import be.kdg.processor.dto.BoeteAanpassingDTO;
import be.kdg.processor.dto.BoeteDTO;
import be.kdg.processor.dto.ListBoeteDTO;
import be.kdg.processor.model.boete.Boete;
import be.kdg.processor.services.BoeteException;
import be.kdg.processor.services.BoeteService;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.apache.coyote.Response;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.json.JsonObject;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/api")
public class BoeteRestController {
    private final BoeteService boeteService;
    private final ModelMapper modelMapper;

    public BoeteRestController(BoeteService boeteService, ModelMapper modelMapper) {
        this.boeteService = boeteService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/boete/getall")
    public ResponseEntity<ListBoeteDTO> loadAll() throws BoeteException {
        List<Boete> boete = boeteService.loadAll();
        ListBoeteDTO listBoeteDTO = new ListBoeteDTO(boete);
        return new ResponseEntity<>(listBoeteDTO, HttpStatus.OK);

    }

    @PutMapping("/boetegoedkeuring/{id}")
    public ResponseEntity<BoeteDTO> keurgoed(@PathVariable long id) throws BoeteException {
        Boete boeteIN = boeteService.loadBoete(id);
        boeteIN.setGoedgekeurd(true);
        Boete geupdateBoete =  boeteService.saveBoete(boeteIN);
        return new ResponseEntity<>(modelMapper.map(geupdateBoete,BoeteDTO.class), HttpStatus.ACCEPTED);
    }

    @PutMapping("/boeteaanpassing/{id}")
    public ResponseEntity<BoeteDTO> aanpassingBoete(@PathVariable long id,@RequestBody BoeteAanpassingDTO boeteAanpassing) throws BoeteException {

        Boete boeteIN = boeteService.loadBoete(id);
        boeteIN.setBetaling(Integer.parseInt(boeteAanpassing.getNieuwBedrag()));
        boeteIN.setMotivering(boeteAanpassing.getMotivatie());
        Boete geupdateBoete =  boeteService.saveBoete(boeteIN);
        return new ResponseEntity<>(modelMapper.map(geupdateBoete,BoeteDTO.class), HttpStatus.ACCEPTED);
    }


    @PutMapping("/boeteafkeuring/{id}")
    public ResponseEntity<BoeteDTO> afkeur(@PathVariable long id) throws BoeteException {
        Boete boeteIN = boeteService.loadBoete(id);
        boeteIN.setGoedgekeurd(false);
        Boete geupdateBoete =  boeteService.saveBoete(boeteIN);
        return new ResponseEntity<>(modelMapper.map(geupdateBoete,BoeteDTO.class), HttpStatus.ACCEPTED);
    }

    @GetMapping("/boetes/{begin}/{eind}")
    public ResponseEntity<ListBoeteDTO> filterDate(@PathVariable String begin, @PathVariable String eind) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate beginSearch = LocalDate.parse(begin,dtf);
        LocalDate eindSearch = LocalDate.parse(eind,dtf);
        List<Boete> gefilterd = boeteService.filter(beginSearch,eindSearch);
        ListBoeteDTO boetelijst = new ListBoeteDTO(gefilterd);
        return new ResponseEntity<>(boetelijst,HttpStatus.OK);
    }

//    @PostMapping("/boetes")
//    public ResponseEntity<BoeteDTO> createBoete(@RequestBody BoeteDTO boeteDTO) {
//        Boete mappedBoete = modelMapper.map(boeteDTO, Boete.class);
//        Boete boeteResponse = boeteService.saveBoete(mappedBoete);
//        return new ResponseEntity<>(modelMapper.map(boeteResponse, BoeteDTO.class), HttpStatus.CREATED);
//    }
}