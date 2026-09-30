package master.entity;

import javax.persistence.*;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String libelle;

    private double prix;

    // Relation N-1 : plusieurs Products appartiennent a un seul Type.
    // C'est ici (cote "Many") que se trouve la cle etrangere type_id.
    // FetchType.EAGER (comportement par defaut de @ManyToOne) : evite une LazyInitializationException
    // quand les vues Thymeleaf (products.html) affichent product.type en dehors de toute transaction.
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "type_id", nullable = true)
    private Type type;

    public Product() {
    }

    public Product(Long id, String libelle, double prix) {
        this.id = id;
        this.libelle = libelle;
        this.prix = prix;
    }

    public Product(Long id, String libelle, double prix, Type type) {
        this.id = id;
        this.libelle = libelle;
        this.prix = prix;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", libelle='" + libelle + '\'' +
                ", prix=" + prix +
                ", type=" + (type != null ? type.getId() : null) +
                '}';
    }
}
