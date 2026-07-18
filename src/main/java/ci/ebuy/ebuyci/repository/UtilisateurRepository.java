package ci.ebuy.ebuyci.repository;

import ci.ebuy.ebuyci.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
    Optional<Utilisateur> findByIdentifiant(String identifiant);
    boolean existsByIdentifiant(String identifiant);
}