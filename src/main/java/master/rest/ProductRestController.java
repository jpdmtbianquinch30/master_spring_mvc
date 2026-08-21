package master.rest;

import master.entity.Product;
import master.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductRestController {

    @Autowired
    private ProductService service;

    @GetMapping
    public List<Product> list() { return service.findAll(); }

    @GetMapping("/{id}")
    public Product findById(@PathVariable Long id) { return service.findById(id); }

    @GetMapping("/search")
    public List<Product> search(@RequestParam String mot) { return service.findByMot(mot); }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product save(@RequestBody Product product) { return service.save(product); }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "Produit " + id + " supprime avec succes";
    }

    @PutMapping("/{id}")
    public Product update(@PathVariable Long id, @RequestBody Product product) {
        Product productBd = service.findById(id);
        productBd.setLibelle(product.getLibelle());
        productBd.setPrix(product.getPrix());
        return service.save(productBd);
    }

    @PatchMapping("/{id}")
    public Product updatePatch(@PathVariable Long id, @RequestBody Product product) {
        Product productBd = service.findById(id);
        if (product.getLibelle() != null) productBd.setLibelle(product.getLibelle());
        if (product.getPrix() != 0) productBd.setPrix(product.getPrix());
        return service.save(productBd);
    }
}