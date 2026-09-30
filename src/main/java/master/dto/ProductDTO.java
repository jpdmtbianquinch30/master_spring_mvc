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

    // Identifiant du type a associer (envoye par le client)
    @NotNull(message = "Le type est obligatoire")
    private Long typeId;

    // Libelle du type, uniquement en lecture (renvoye au client, jamais lu en entree)
    private String typeLibelle;

    public ProductDTO(Long id, String libelle, Double prix, Long typeId) {
        this.id = id;
        this.libelle = libelle;
        this.prix = prix;
        this.typeId = typeId;
    }
}
