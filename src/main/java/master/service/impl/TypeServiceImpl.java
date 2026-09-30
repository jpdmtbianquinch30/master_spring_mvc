package master.service.impl;

import master.entity.Type;
import master.exception.ResourceNotFoundException;
import master.exception.TypeHasProductsException;
import master.repository.ProductRepository;
import master.repository.TypeRepository;
import master.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TypeServiceImpl implements TypeService {

    @Autowired
    private TypeRepository repository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Type save(Type type) { return repository.save(type); }

    @Override
    public List<Type> findAll() { return repository.findAll(); }

    @Override
    public Page<Type> findAll(Pageable pageable) { return repository.findAll(pageable); }

    @Override
    public Type findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Type " + id + " introuvable"));
    }

    @Override
    public List<Type> findByMot(String mot) { return repository.findByLibelleContaining(mot); }

    @Override
    public void delete(Long id) {
        findById(id);
        if (!productRepository.findByTypeId(id).isEmpty()) {
            throw new TypeHasProductsException(
                    "Impossible de supprimer le type " + id + " : des produits y sont encore rattaches");
        }
        repository.deleteById(id);
    }
}
