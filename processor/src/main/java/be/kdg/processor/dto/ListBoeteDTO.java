package be.kdg.processor.dto;

import be.kdg.processor.model.boete.Boete;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ListBoeteDTO {
    private List<Boete> emissieBoetes;
}
