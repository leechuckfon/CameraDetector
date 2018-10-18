package be.kdg.processor.model.boete;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "boetefactoren")
@Configuration("factoren")
@Data
public class BoeteFactoren {
    private String emissieboetefactor;
    private String snelheidboetefactor;
}
