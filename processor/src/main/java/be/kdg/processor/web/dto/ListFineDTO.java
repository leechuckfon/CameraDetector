package be.kdg.processor.web.dto;

import be.kdg.processor.model.fine.Fine;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ListFineDTO {
    private List<Fine> fines;
}
