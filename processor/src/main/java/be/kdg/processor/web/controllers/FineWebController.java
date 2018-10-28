package be.kdg.processor.web.controllers;

import be.kdg.processor.model.events.PropertiesChangeEvent;
import be.kdg.processor.receiving.configs.PropertiesConfig;
import be.kdg.processor.web.dto.FineFactorChangeDTO;
import be.kdg.processor.web.dto.FineFactorsDTO;
import be.kdg.processor.web.publishers.PropertiesChangePublisher;
import be.kdg.processor.web.services.FineService;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * The FineWebController is the class that will use Thymeleaf to return htmlpages.
 */
@Controller
@RequestMapping("/web")

public class FineWebController implements ApplicationListener<PropertiesChangeEvent> {

    private final FineService fineService;
    private final ModelMapper modelMapper;
    private PropertiesConfig propertiesConfig;
    private PropertiesChangePublisher publisher;

    public FineWebController(FineService fineService, ModelMapper modelMapper, PropertiesConfig propertiesConfig,PropertiesChangePublisher propertiesChangePublisher) {
        this.fineService = fineService;
        this.modelMapper = modelMapper;
        this.propertiesConfig = propertiesConfig;
        publisher = propertiesChangePublisher;

    }

    @GetMapping("/finefactors")
    public ModelAndView showFinefactors(){
        return new ModelAndView("showboetefactoren","boetefactoren",new FineFactorsDTO(propertiesConfig.getEmissionfinefactor(),propertiesConfig.getSpeedfinefactor()));

    }

    @GetMapping("/changeFineFactors")
    public ModelAndView fineFactorForm(FineFactorChangeDTO fineFactorChangeDTO){
        return new ModelAndView("finefactorForm","fineFactorChangeDTO", fineFactorChangeDTO);
    }

    @PostMapping("/changeFineFactors.do")
    public ModelAndView fineFactorFormDO(FineFactorChangeDTO fineFactorChangeDTO){
        propertiesConfig.setEmissionfinefactor(fineFactorChangeDTO.getEmissionfinefactor());
        propertiesConfig.setSpeedfinefactor(fineFactorChangeDTO.getSpeedfinefactor());
        propertiesConfig.setTimeframeSnelheid(fineFactorChangeDTO.getTimeframeSnelheid());
        propertiesConfig.setEmissionTime(fineFactorChangeDTO.getEmissionTime());
        propertiesConfig.setMaxretries(fineFactorChangeDTO.getMaxretries());
        propertiesConfig.setRetrydelay(fineFactorChangeDTO.getRetrydelay());
        publisher.doStuffAndPublishAnEvent(propertiesConfig);
        return new ModelAndView("showproperties","propertiesModel", modelMapper.map(propertiesConfig,FineFactorChangeDTO.class));
    }

    @GetMapping("/showProperties")
    public ModelAndView showProperties(){
        FineFactorChangeDTO properties = modelMapper.map(propertiesConfig, FineFactorChangeDTO.class);
        return new ModelAndView("showproperties","propertiesModel", properties );
    }

    @Override
    public void onApplicationEvent(PropertiesChangeEvent event) {
        this.propertiesConfig = (PropertiesConfig) event.getSource();
    }
}
