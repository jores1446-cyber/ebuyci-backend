package ci.ebuy.ebuyci.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {
    private String token;
    private Long id;
    private String nom;
    private String identifiant;
    private String role;
}
