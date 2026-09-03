package master.mapper;

import master.entity.Product;
import master.dto.ProductDTO;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public ProductDTO toDto(Product entity) {
        if (entity == null) {
            return null;
        }
        ProductDTO dto = new ProductDTO();
        dto.setId(entity.getId());
        dto.setLibelle(entity.getLibelle());
        dto.setPrix(entity.getPrix());
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
        return entity;
    }

}