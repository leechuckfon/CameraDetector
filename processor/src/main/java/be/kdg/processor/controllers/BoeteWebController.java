package be.kdg.processor.controllers;

import be.kdg.processor.dto.BoetefactorenDTO;
import be.kdg.processor.services.BoeteService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * De BoeteWebController is de klasse die via Thymeleaf een html pagina teruggeeft met de boetefactoren.
 */
@Controller
@RequestMapping("/web")
public class BoeteWebController {

    private final BoeteService boeteService;
    private final ModelMapper modelMapper;

    public BoeteWebController(BoeteService boeteService, ModelMapper modelMapper) {
        this.boeteService = boeteService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/boetefactoren")
    public ModelAndView showBoetefactoren(@Value("${boetefactoren.emissieboetefactor}") int emissiestring, @Value("${boetefactoren.snelheidboetefactor}") int snelheidstring){

        return new ModelAndView("showboetefactoren","boetefactoren",new BoetefactorenDTO(emissiestring,snelheidstring));

    }
}
