package ci.ebuy.ebuyci.repository;

import ci.ebuy.ebuyci.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ClientRepository extends JpaRepository<Client, Long> {

    @Query("SELECT COALESCE(SUM(v.montantTotal - v.montantPaye), 0) " +
           "FROM Vente v WHERE v.client.id = :clientId")
    Double getCreditTotalByClient(@Param("clientId") Long clientId);
}
