package master.service;

import master.entity.Type;
import java.util.List;

public interface TypeService {
    Type save(Type type);
    List<Type> findAll();
    Type findById(Long id);
    List<Type> findByMot(String mot);
    void delete(Long id);
}