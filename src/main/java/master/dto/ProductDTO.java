package master.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    private Long id;

    @NotBlank(message = "Le libelle est obligatoire")
    private String libelle;

    @NotNull(message = "Le prix est obligatoire")
    @Positive(message = "Le prix doit etre superieur a 0")
    private Double prix;

}