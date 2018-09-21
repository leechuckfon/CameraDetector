package be.kdg.simulator.generators;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GeneratorConfig {
    @Bean("geenSourceCode")
    //de naam van deze bean is default de naam van de methode
    public MessageGenerator fileGenerator() {
        return new FileGenerator();
    }
}
