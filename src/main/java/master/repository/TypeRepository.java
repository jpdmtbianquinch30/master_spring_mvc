package master.repository;

import master.entity.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TypeRepository extends JpaRepository<Type, Long> {
    List<Type> findByLibelleContaining(String libelle);
    List<Type> findByLibelle(String libelle);
    boolean existsByLibelle(String libelle);
}