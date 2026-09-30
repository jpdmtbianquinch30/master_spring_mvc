package master.controller;

import master.entity.Product;
import master.service.ProductService;
import master.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService service;

    @Autowired
    private TypeService typeService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("products", service.findAll());
        return "products";
    }

    @GetMapping("/new")
    public String form(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("types", typeService.findAll());
        return "form-product";
    }

    @GetMapping("/edit/{id}")
    public String form(Model model, @PathVariable Long id) {
        model.addAttribute("product", service.findById(id));
        model.addAttribute("types", typeService.findAll());
        return "form-product";
    }

    @PostMapping
    public String save(@ModelAttribute Product product, @RequestParam(required = false) Long typeId) {
        if (typeId != null) {
            product.setType(typeService.findById(typeId));
        }
        service.save(product);
        return "redirect:/products";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "redirect:/products";
    }
}
