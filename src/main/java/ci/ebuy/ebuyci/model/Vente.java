package ci.ebuy.ebuyci.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ventes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "magasin_id", nullable = false)
    private Magasin magasin;

    @ManyToOne
    @JoinColumn(name = "vendeur_id", nullable = false)
    private Utilisateur vendeur;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @Column(name = "date_vente", nullable = false)
    private LocalDateTime dateVente = LocalDateTime.now();

    @Column(name = "montant_total", nullable = false)
    private Double montantTotal;

    @Column(name = "montant_paye", nullable = false)
    private Double montantPaye = 0.0;

    @Column(name = "mode_paiement", nullable = false)
    private String modePaiement;

    @OneToMany(mappedBy = "vente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LigneVente> lignes = new ArrayList<>();
}
