package master.rest;

import master.entity.Type;
import master.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/types")
public class TypeRestController {

    @Autowired
    private TypeService service;

    @GetMapping
    public List<Type> list() { return service.findAll(); }

    @GetMapping("/{id}")
    public Type findById(@PathVariable Long id) { return service.findById(id); }

    @GetMapping("/search")
    public List<Type> search(@RequestParam String mot) { return service.findByMot(mot); }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Type save(@RequestBody Type type) { return service.save(type); }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "Type " + id + " supprime avec succes";
    }

    @PutMapping("/{id}")
    public Type update(@PathVariable Long id, @RequestBody Type type) {
        Type typeBd = service.findById(id);
        typeBd.setLibelle(type.getLibelle());
        return service.save(typeBd);
    }

    @PatchMapping("/{id}")
    public Type updatePatch(@PathVariable Long id, @RequestBody Type type) {
        Type typeBd = service.findById(id);
        if (type.getLibelle() != null) typeBd.setLibelle(type.getLibelle());
        return service.save(typeBd);
    }
}