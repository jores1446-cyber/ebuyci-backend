package ci.ebuy.ebuyci.controller;

import ci.ebuy.ebuyci.model.Achat;
import ci.ebuy.ebuyci.model.LigneAchat;
import ci.ebuy.ebuyci.model.Stock;
import ci.ebuy.ebuyci.repository.AchatRepository;
import ci.ebuy.ebuyci.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/achats")
public class AchatController {

    @Autowired
    private AchatRepository achatRepository;

    @Autowired
    private StockRepository stockRepository;

    @GetMapping
    public List<Achat> getAll() {
        return achatRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Achat> getById(@PathVariable Long id) {
        return achatRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Transactional
    public Achat create(@RequestBody Achat achat) {
        achat.setStatut("EN_ATTENTE");
        achat.setDateAchat(LocalDateTime.now());
        for (LigneAchat ligne : achat.getLignes()) {
            ligne.setAchat(achat);
        }
        return achatRepository.save(achat);
    }

    @PutMapping("/{id}/receptionner")
    @Transactional
    public ResponseEntity<Achat> receptionner(@PathVariable Long id) {
        return achatRepository.findById(id)
                .map(achat -> {
                    for (LigneAchat ligne : achat.getLignes()) {
                        Stock stock = stockRepository
                                .findByProduitIdAndMagasinId(ligne.getProduit().getId(), achat.getMagasin().getId())
                                .orElseGet(() -> {
                                    Stock nouveau = new Stock();
                                    nouveau.setProduit(ligne.getProduit());
                                    nouveau.setMagasin(achat.getMagasin());
                                    nouveau.setQuantite(0);
                                    nouveau.setSeuilMinimum(5);
                                    return nouveau;
                                });
                        stock.setQuantite(stock.getQuantite() + ligne.getQuantite());
                        stockRepository.save(stock);
                    }
                    achat.setStatut("RECUE");
                    return ResponseEntity.ok(achatRepository.save(achat));
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
