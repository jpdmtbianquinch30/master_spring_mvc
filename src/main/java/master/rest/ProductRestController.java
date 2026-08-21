package master.rest;


import master.entity.Product;
import master.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductRestController {

    @Autowired
    private ProductService service;

    @GetMapping
    public List<Product> list() {
        List<Product> products = service.findAll();
        return products;
    }



    @PostMapping
    public Product save(@RequestBody Product product) {

      Product productSave = service.save(product);

        return productSave ;
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {

        service.delete(id);

        return "Produit"+ id+ "supprime avec succes";
    }

    @PutMapping("/{id}")
    public Product update(@PathVariable Long id, @RequestBody Product product) {
        Product productBd = service.findById(id);
        productBd.setLibelle(product.getLibelle());
        productBd.setPrix(product.getPrix());

        Product productSave = service.save(productBd);

        return productSave ;
    }

    @PatchMapping("/{id}")
    public Product updatePatch(@PathVariable Long id, @RequestBody Product product) {
        Product productBd = service.findById(id);
        if(product.getLibelle()!=null){
            productBd.setLibelle(product.getLibelle());
        }
        if(product.getPrix() != 0){
            productBd.setPrix(product.getPrix());
        }

        Product productSave = service.save(productBd);

        return productSave ;
    }
}