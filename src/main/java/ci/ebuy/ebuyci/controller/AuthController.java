package ci.ebuy.ebuyci.controller;

import ci.ebuy.ebuyci.dto.LoginRequest;
import ci.ebuy.ebuyci.dto.LoginResponse;
import ci.ebuy.ebuyci.model.Utilisateur;
import ci.ebuy.ebuyci.repository.UtilisateurRepository;
import ci.ebuy.ebuyci.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getIdentifiant(), request.getMotDePasse())
            );
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body("Identifiant ou mot de passe incorrect.");
        }

        Utilisateur utilisateur = utilisateurRepository.findByIdentifiant(request.getIdentifiant())
                .orElseThrow();

        String token = jwtUtil.generateToken(utilisateur);

        return ResponseEntity.ok(new LoginResponse(
                token,
                utilisateur.getId(),
                utilisateur.getNom(),
                utilisateur.getIdentifiant(),
                utilisateur.getRole().name()
        ));
    }
}
