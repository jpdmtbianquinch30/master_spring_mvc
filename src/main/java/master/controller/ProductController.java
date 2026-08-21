package master.controller;


import master.entity.Product;
import master.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService service;

    @GetMapping
    public String list(Model model) {

        List<Product> products = service.findAll();

        model.addAttribute("products", products);

        return "products";
    }

    @GetMapping("/new")
    public String form(Model model) {

        model.addAttribute("product", new Product());

        return "form-product";
    }

    @GetMapping("/edit/{id}")
    public String form(Model model, @PathVariable int id) {

        model.addAttribute("product", service.findById((long) id));

        return "form-product";
    }

    @PostMapping
    public String save(@ModelAttribute Product product) {
        System.out.println(product.toString());
        service.save(product);

        return "redirect:/products";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {

        service.delete(id);

        return "redirect:/products";
    }

    @GetMapping("/{id}")
    public Product findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @GetMapping("/search")
    public List<Product> search(@RequestParam String mot) {
        return service.findByMot(mot);
    }
}