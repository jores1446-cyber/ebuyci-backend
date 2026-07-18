package ci.ebuy.ebuyci.repository;

import ci.ebuy.ebuyci.model.Achat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AchatRepository extends JpaRepository<Achat, Long> {
    List<Achat> findByStatut(String statut);
}