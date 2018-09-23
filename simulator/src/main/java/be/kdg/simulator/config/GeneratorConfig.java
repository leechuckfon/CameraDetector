package be.kdg.simulator.config;

import be.kdg.simulator.generators.FileGenerator;
import be.kdg.simulator.generators.MessageGenerator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GeneratorConfig {
    @Bean
    @ConditionalOnProperty(name = "generator.type", havingValue = "file")
    public MessageGenerator fileGenerator() {
        return new FileGenerator();
    }
}
