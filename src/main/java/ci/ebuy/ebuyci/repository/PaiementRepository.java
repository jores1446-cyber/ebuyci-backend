package ci.ebuy.ebuyci.repository;

import ci.ebuy.ebuyci.model.Paiement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaiementRepository extends JpaRepository<Paiement, Long> {
    List<Paiement> findByVenteId(Long venteId);
}
