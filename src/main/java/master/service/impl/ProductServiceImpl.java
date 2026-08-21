package master.service.impl;

import master.entity.Product;
import master.exception.ResourceNotFoundException;
import master.repository.ProductRepository;
import master.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository repository;

    @Override
    public Product save(Product product) { return repository.save(product); }

    @Override
    public List<Product> findAll() { return repository.findAll(); }

    @Override
    public Product findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produit " + id + " introuvable"));
    }

    @Override
    public List<Product> findByMot(String mot) { return repository.findByLibelleContaining(mot); }

    @Override
    public void delete(Long id) {
        findById(id);
        repository.deleteById(id);
    }
}