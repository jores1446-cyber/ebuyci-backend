package ci.ebuy.ebuyci.repository;

import ci.ebuy.ebuyci.model.Magasin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MagasinRepository extends JpaRepository<Magasin, Long> {
}