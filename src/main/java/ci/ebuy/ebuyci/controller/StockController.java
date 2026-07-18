package ci.ebuy.ebuyci.controller;

import ci.ebuy.ebuyci.model.Stock;
import ci.ebuy.ebuyci.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stocks")
@CrossOrigin(origins = "http://localhost:4200")
public class StockController {

    @Autowired
    private StockRepository stockRepository;

    @GetMapping
    public List<Stock> getAll() {
        return stockRepository.findAll();
    }

    @GetMapping("/magasin/{magasinId}")
    public List<Stock> getByMagasin(@PathVariable Long magasinId) {
        return stockRepository.findByMagasinId(magasinId);
    }

    @GetMapping("/alertes/{seuil}")
    public List<Stock> getAlertes(@PathVariable Integer seuil) {
        return stockRepository.findByQuantiteLessThanEqual(seuil);
    }

    @PostMapping
    public Stock create(@RequestBody Stock stock) {
        return stockRepository.save(stock);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Stock> update(@PathVariable Long id, @RequestBody Stock details) {
        return stockRepository.findById(id)
                .map(stock -> {
                    stock.setQuantite(details.getQuantite());
                    stock.setSeuilMinimum(details.getSeuilMinimum());
                    return ResponseEntity.ok(stockRepository.save(stock));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!stockRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        stockRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}