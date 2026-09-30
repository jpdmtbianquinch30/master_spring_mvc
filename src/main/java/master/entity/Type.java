package master.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "types")
public class Type {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String libelle;

    // Relation 1-N : un Type possede plusieurs Products.
    // mappedBy = "type" -> c'est le champ "type" de l'entite Product qui possede la relation (cle etrangere).
    // Pas de CascadeType.REMOVE : supprimer un Type ne doit jamais supprimer silencieusement ses Products
    // (voir TypeServiceImpl.delete qui bloque la suppression si des produits sont encore rattaches).
    // JsonIgnore evite la boucle infinie Type -> Product -> Type -> ... lors de la serialisation JSON.
    @OneToMany(mappedBy = "type", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Product> products = new ArrayList<>();

    public Type() {}

    public Type(Long id, String libelle) {
        this.id = id;
        this.libelle = libelle;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getLibelle() { return libelle; }
    public void setLibelle(String libelle) { this.libelle = libelle; }

    public List<Product> getProducts() { return products; }
    public void setProducts(List<Product> products) { this.products = products; }
}
