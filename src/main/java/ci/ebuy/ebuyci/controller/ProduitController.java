package ci.ebuy.ebuyci.controller;

import ci.ebuy.ebuyci.model.Produit;
import ci.ebuy.ebuyci.repository.ProduitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/produits")
@CrossOrigin(origins = "http://localhost:4200")
public class ProduitController {

    @Autowired
    private ProduitRepository produitRepository;

    @GetMapping
    public List<Produit> getAll() {
        return produitRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produit> getById(@PathVariable Long id) {
        return produitRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Produit produit) {
        if (produitRepository.existsByReference(produit.getReference())) {
            return ResponseEntity.badRequest().body("Cette référence existe déjà.");
        }
        produit.setActif(true);
        return ResponseEntity.ok(produitRepository.save(produit));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Produit> update(@PathVariable Long id, @RequestBody Produit details) {
        return produitRepository.findById(id)
                .map(produit -> {
                    produit.setNom(details.getNom());
                    produit.setCategorie(details.getCategorie());
                    produit.setPrixAchat(details.getPrixAchat());
                    produit.setPrixVente(details.getPrixVente());
                    if (details.getActif() != null) {
                        produit.setActif(details.getActif());
                    }
                    return ResponseEntity.ok(produitRepository.save(produit));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!produitRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        produitRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
