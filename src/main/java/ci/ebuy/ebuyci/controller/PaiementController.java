package ci.ebuy.ebuyci.controller;

import ci.ebuy.ebuyci.model.Paiement;
import ci.ebuy.ebuyci.model.Vente;
import ci.ebuy.ebuyci.repository.PaiementRepository;
import ci.ebuy.ebuyci.repository.VenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/paiements")
public class PaiementController {

    @Autowired
    private PaiementRepository paiementRepository;

    @Autowired
    private VenteRepository venteRepository;

    @GetMapping("/vente/{venteId}")
    public List<Paiement> getByVente(@PathVariable Long venteId) {
        return paiementRepository.findByVenteId(venteId);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> create(@RequestBody Paiement paiement) {
        Vente vente = venteRepository.findById(paiement.getVente().getId())
                .orElse(null);

        if (vente == null) {
            return ResponseEntity.badRequest().body("Vente introuvable.");
        }

        double resteAPayer = vente.getMontantTotal() - vente.getMontantPaye();
        if (paiement.getMontant() > resteAPayer) {
            return ResponseEntity.badRequest()
                    .body("Le montant dépasse le reste à payer (" + resteAPayer + ").");
        }

        paiement.setVente(vente);
        paiement.setDatePaiement(java.time.LocalDateTime.now());
        paiementRepository.save(paiement);

        vente.setMontantPaye(vente.getMontantPaye() + paiement.getMontant());
        venteRepository.save(vente);

        return ResponseEntity.ok(paiement);
    }
}
