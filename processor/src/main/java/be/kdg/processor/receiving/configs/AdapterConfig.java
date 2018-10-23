package be.kdg.processor.receiving.configs;

import be.kdg.processor.receiving.adapters.CameraAdapter;
import be.kdg.processor.receiving.adapters.LicensePlateAdapter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * The AdapterConfig is the configuration file for the making of any Adapter that communicates with the proxy services.
 */
@Configuration
public class AdapterConfig {
    @Bean
    public CameraAdapter cameraAdapter() {
        return new CameraAdapter();
    }

    @Bean
    public LicensePlateAdapter licensePlateAdapter() {
        return new LicensePlateAdapter();
    }



}
