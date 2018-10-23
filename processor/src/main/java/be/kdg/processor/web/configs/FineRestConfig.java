package be.kdg.processor.web.configs;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * The FineRestConfig is the configuration file for the FineRestController.
 */
@Configuration
public class FineRestConfig {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}
