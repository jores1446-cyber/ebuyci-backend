package ci.ebuy.ebuyci.repository;

import ci.ebuy.ebuyci.model.Produit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProduitRepository extends JpaRepository<Produit, Long> {
    Optional<Produit> findByReference(String reference);
    boolean existsByReference(String reference);
}