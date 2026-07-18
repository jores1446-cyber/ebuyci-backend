package ci.ebuy.ebuyci.repository;

import ci.ebuy.ebuyci.model.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategorieRepository extends JpaRepository<Categorie, Long> {
}