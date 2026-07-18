package ci.ebuy.ebuyci.repository;

import ci.ebuy.ebuyci.model.Fournisseur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FournisseurRepository extends JpaRepository<Fournisseur, Long> {
}