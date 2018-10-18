package be.kdg.processor.dto;

import be.kdg.processor.model.boete.BoeteFactoren;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
public class BoetefactorenDTO {
    private int emissieboetefactor;
    private int snelehidboetefactor;

    public BoetefactorenDTO() {
    }
}
