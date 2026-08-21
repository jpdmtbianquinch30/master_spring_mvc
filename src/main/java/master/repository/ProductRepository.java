package master.repository;


import master.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository  extends JpaRepository<Product, Long> {

    List<Product> findByLibelleContaining(String libelle);
    List<Product> findByLibelle(String libelle);
    List<Product> findByLibelleIgnoreCase(String libelle);
    List<Product> findByPrixGreaterThan(double prix);
    List<Product> findByPrixLessThan(double prixIsLessThan);
    List<Product> findByPrixBetween(double min, double max);
    List<Product> findByPrixOrderByPrixAsc(double prix);
    boolean existsByLibelle(String libelle);
    
    
}
