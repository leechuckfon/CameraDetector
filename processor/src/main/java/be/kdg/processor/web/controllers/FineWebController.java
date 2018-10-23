package be.kdg.processor.web.controllers;

import be.kdg.processor.web.dto.FinefactorsDTO;
import be.kdg.processor.web.services.FineService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * The FineWebController is the class that will use Thymeleaf to return htmlpages.
 */
@Controller
@RequestMapping("/web")
public class FineWebController {

    private final FineService fineService;
    private final ModelMapper modelMapper;

    public FineWebController(FineService fineService, ModelMapper modelMapper) {
        this.fineService = fineService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/finefactors")
    public ModelAndView showBoetefactoren(@Value("${finefactor.emissionfinefactor}") int emissiestring, @Value("${finefactor.speedfinefactor") int snelheidstring){

        return new ModelAndView("showboetefactoren","boetefactoren",new FinefactorsDTO(emissiestring,snelheidstring));

    }
}
