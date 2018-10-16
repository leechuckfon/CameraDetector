package be.kdg.processor.controllers;

import be.kdg.processor.dto.BoeteDTO;
import be.kdg.processor.dto.ListBoeteDTO;
import be.kdg.processor.model.boete.Boete;
import be.kdg.processor.services.BoeteException;
import be.kdg.processor.services.BoeteService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/boetes")
    public ResponseEntity<BoeteDTO> createBoete(@RequestBody BoeteDTO boeteDTO) {
        Boete mappedBoete = modelMapper.map(boeteDTO, Boete.class);
        Boete boeteResponse = boeteService.saveBoete(mappedBoete);
        return new ResponseEntity<>(modelMapper.map(boeteResponse, BoeteDTO.class), HttpStatus.CREATED);
    }
}
