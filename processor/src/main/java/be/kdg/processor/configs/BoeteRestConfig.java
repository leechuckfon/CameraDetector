package be.kdg.processor.configs;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * De BoeteRestConfig is de configuration file voor de BoeteRestController.
 */
@Configuration
public class BoeteRestConfig {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}
