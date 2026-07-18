package ci.ebuy.ebuyci.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "achats")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Achat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "fournisseur_id", nullable = false)
    private Fournisseur fournisseur;

    @ManyToOne
    @JoinColumn(name = "magasin_id", nullable = false)
    private Magasin magasin;

    @ManyToOne
    @JoinColumn(name = "acheteur_id", nullable = false)
    private Utilisateur acheteur;

    @Column(name = "date_achat", nullable = false)
    private LocalDateTime dateAchat = LocalDateTime.now();

    @Column(nullable = false)
    private String statut = "EN_ATTENTE";

    @OneToMany(mappedBy = "achat", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LigneAchat> lignes = new ArrayList<>();
}