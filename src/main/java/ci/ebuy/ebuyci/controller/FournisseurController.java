package ci.ebuy.ebuyci.controller;

import ci.ebuy.ebuyci.model.Fournisseur;
import ci.ebuy.ebuyci.repository.FournisseurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fournisseurs")
@CrossOrigin(origins = "http://localhost:4200")
public class FournisseurController {

    @Autowired
    private FournisseurRepository fournisseurRepository;

    @GetMapping
    public List<Fournisseur> getAll() {
        return fournisseurRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Fournisseur> getById(@PathVariable Long id) {
        return fournisseurRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Fournisseur create(@RequestBody Fournisseur fournisseur) {
        return fournisseurRepository.save(fournisseur);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Fournisseur> update(@PathVariable Long id, @RequestBody Fournisseur details) {
        return fournisseurRepository.findById(id)
                .map(fournisseur -> {
                    fournisseur.setNom(details.getNom());
                    fournisseur.setTelephone(details.getTelephone());
                    fournisseur.setEmail(details.getEmail());
                    fournisseur.setAdresse(details.getAdresse());
                    return ResponseEntity.ok(fournisseurRepository.save(fournisseur));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!fournisseurRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        fournisseurRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}