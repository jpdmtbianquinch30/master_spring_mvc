package master.mapper;

import master.entity.Product;
import master.entity.Type;
import master.dto.ProductDTO;
import master.exception.ResourceNotFoundException;
import master.repository.TypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    @Autowired
    private TypeRepository typeRepository;

    public ProductDTO toDto(Product entity) {
        if (entity == null) {
            return null;
        }
        ProductDTO dto = new ProductDTO();
        dto.setId(entity.getId());
        dto.setLibelle(entity.getLibelle());
        dto.setPrix(entity.getPrix());
        if (entity.getType() != null) {
            dto.setTypeId(entity.getType().getId());
            dto.setTypeLibelle(entity.getType().getLibelle());
        }
        return dto;
    }

    public Product toEntity(ProductDTO dto) {
        if (dto == null) {
            return null;
        }
        Product entity = new Product();
        entity.setId(dto.getId());
        entity.setLibelle(dto.getLibelle());
        if (dto.getPrix() != null) {
            entity.setPrix(dto.getPrix());
        }
        if (dto.getTypeId() != null) {
            entity.setType(resolveType(dto.getTypeId()));
        }
        return entity;
    }

    public Type resolveType(Long typeId) {
        return typeRepository.findById(typeId)
                .orElseThrow(() -> new ResourceNotFoundException("Type " + typeId + " introuvable"));
    }

}
