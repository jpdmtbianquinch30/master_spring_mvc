package master.service;


import master.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {

    Product save(Product product);

    List<Product> findAll();

    Page<Product> findAll(Pageable pageable);

    Product findById(Long id);

    List<Product> findByMot(String mot);

    List<Product> findByTypeId(Long typeId);

    void delete(Long id);
}
