package be.kdg.processor.web.dto;

import be.kdg.processor.model.fine.FineType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FineDTO {
    private FineType type;
    private int fee;
    private String offenseInfo;
    private int cameraId;
    private boolean approved;
    private LocalDateTime offenseTime;
    private String motivation;
}
