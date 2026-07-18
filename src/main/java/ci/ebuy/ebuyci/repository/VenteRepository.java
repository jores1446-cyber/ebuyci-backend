package ci.ebuy.ebuyci.repository;

import ci.ebuy.ebuyci.model.Vente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VenteRepository extends JpaRepository<Vente, Long> {
    List<Vente> findByMagasinId(Long magasinId);
    List<Vente> findByVendeurId(Long vendeurId);
}