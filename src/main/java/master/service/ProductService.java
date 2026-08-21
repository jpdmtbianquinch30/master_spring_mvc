package master.service;



import master.entity.Product;

import java.util.List;

public interface ProductService {

    Product save(Product product);

    List<Product> findAll();

    Product findById(Long id);

    List<Product> findByMot(String mot);

    void delete(Long id);
}