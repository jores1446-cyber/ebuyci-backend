package ci.ebuy.ebuyci.controller;

import ci.ebuy.ebuyci.model.LigneVente;
import ci.ebuy.ebuyci.model.Stock;
import ci.ebuy.ebuyci.model.Vente;
import ci.ebuy.ebuyci.repository.StockRepository;
import ci.ebuy.ebuyci.repository.VenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ventes")
@CrossOrigin(origins = "http://localhost:4200")
public class VenteController {

    @Autowired
    private VenteRepository venteRepository;

    @Autowired
    private StockRepository stockRepository;

    @GetMapping
    public List<Vente> getAll() {
        return venteRepository.findAll();
    }

    @GetMapping("/magasin/{magasinId}")
    public List<Vente> getByMagasin(@PathVariable Long magasinId) {
        return venteRepository.findByMagasinId(magasinId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vente> getById(@PathVariable Long id) {
        return venteRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> create(@RequestBody Vente vente) {
        for (LigneVente ligne : vente.getLignes()) {
            Stock stock = stockRepository
                    .findByProduitIdAndMagasinId(ligne.getProduit().getId(), vente.getMagasin().getId())
                    .orElse(null);

            if (stock == null || stock.getQuantite() < ligne.getQuantite()) {
                return ResponseEntity.badRequest()
                        .body("Stock insuffisant pour le produit : " + ligne.getProduit().getNom());
            }
        }

        for (LigneVente ligne : vente.getLignes()) {
            ligne.setVente(vente);
            Stock stock = stockRepository
                    .findByProduitIdAndMagasinId(ligne.getProduit().getId(), vente.getMagasin().getId())
                    .get();
            stock.setQuantite(stock.getQuantite() - ligne.getQuantite());
            stockRepository.save(stock);
        }

        vente.setDateVente(java.time.LocalDateTime.now());
        return ResponseEntity.ok(venteRepository.save(vente));
    }
}