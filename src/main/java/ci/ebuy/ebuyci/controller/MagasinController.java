package ci.ebuy.ebuyci.controller;

import ci.ebuy.ebuyci.model.Magasin;
import ci.ebuy.ebuyci.repository.MagasinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/magasins")
@CrossOrigin(origins = "http://localhost:4200")
public class MagasinController {

    @Autowired
    private MagasinRepository magasinRepository;

    @GetMapping
    public List<Magasin> getAll() {
        return magasinRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Magasin> getById(@PathVariable Long id) {
        return magasinRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Magasin create(@RequestBody Magasin magasin) {
        return magasinRepository.save(magasin);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Magasin> update(@PathVariable Long id, @RequestBody Magasin details) {
        return magasinRepository.findById(id)
                .map(magasin -> {
                    magasin.setNom(details.getNom());
                    magasin.setAdresse(details.getAdresse());
                    magasin.setTelephone(details.getTelephone());
                    return ResponseEntity.ok(magasinRepository.save(magasin));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!magasinRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        magasinRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}