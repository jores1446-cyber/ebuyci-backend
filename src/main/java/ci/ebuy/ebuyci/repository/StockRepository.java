package ci.ebuy.ebuyci.repository;

import ci.ebuy.ebuyci.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StockRepository extends JpaRepository<Stock, Long> {
    Optional<Stock> findByProduitIdAndMagasinId(Long produitId, Long magasinId);
    List<Stock> findByMagasinId(Long magasinId);
    List<Stock> findByQuantiteLessThanEqual(Integer seuil);
}