package master.controller;

import master.entity.Type;
import master.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/types")
public class TypeController {

    @Autowired
    private TypeService service;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("types", service.findAll());
        return "types";
    }

    @GetMapping("/new")
    public String form(Model model) {
        model.addAttribute("type", new Type());
        return "form-type";
    }

    @GetMapping("/edit/{id}")
    public String form(Model model, @PathVariable Long id) {
        model.addAttribute("type", service.findById(id));
        return "form-type";
    }

    @PostMapping
    public String save(@ModelAttribute Type type) {
        service.save(type);
        return "redirect:/types";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "redirect:/types";
    }
}