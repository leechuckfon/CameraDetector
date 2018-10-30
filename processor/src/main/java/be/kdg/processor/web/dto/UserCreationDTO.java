package be.kdg.processor.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserCreationDTO {
    private String username;
    private String password;
    private String[] roles;
}
