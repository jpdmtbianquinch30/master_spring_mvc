package master.service;

import master.entity.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface TypeService {
    Type save(Type type);
    List<Type> findAll();
    Page<Type> findAll(Pageable pageable);
    Type findById(Long id);
    List<Type> findByMot(String mot);
    void delete(Long id);
}
