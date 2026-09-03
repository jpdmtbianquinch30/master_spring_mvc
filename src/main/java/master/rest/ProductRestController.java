package master.rest;

import master.entity.Product;
import master.dto.ProductDTO;
import master.mapper.ProductMapper;
import master.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
public class ProductRestController {

    @Autowired
    private ProductService service;

    @Autowired
    private ProductMapper mapper;

    @GetMapping
    public List<ProductDTO> list() {
        return service.findAll()
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ProductDTO findById(@PathVariable Long id) {
        return mapper.toDto(service.findById(id));
    }

    @GetMapping("/search")
    public List<ProductDTO> search(@RequestParam String mot) {
        return service.findByMot(mot)
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDTO save(@Valid @RequestBody ProductDTO dto) {
        return mapper.toDto(service.save(mapper.toEntity(dto)));
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "Produit " + id + " supprime avec succes";
    }

    @PutMapping("/{id}")
    public ProductDTO update(@PathVariable Long id, @Valid @RequestBody ProductDTO dto) {
        Product productBd = service.findById(id);
        productBd.setLibelle(dto.getLibelle());
        productBd.setPrix(dto.getPrix());
        return mapper.toDto(service.save(productBd));
    }

    @PatchMapping("/{id}")
    public ProductDTO updatePatch(@PathVariable Long id, @RequestBody ProductDTO dto) {
        Product productBd = service.findById(id);

        if (dto.getLibelle() != null && !dto.getLibelle().trim().isEmpty()) {
            productBd.setLibelle(dto.getLibelle());
        }
        if (dto.getPrix() != null && dto.getPrix() > 0) {
            productBd.setPrix(dto.getPrix());
        }

        return mapper.toDto(service.save(productBd));
    }

}