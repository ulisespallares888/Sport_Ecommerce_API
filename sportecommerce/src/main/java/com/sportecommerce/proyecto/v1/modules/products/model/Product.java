package com.sportecommerce.proyecto.v1.modules.products.model;


import com.sportecommerce.proyecto.v1.modules.categories.model.Category;
import com.sportecommerce.proyecto.v1.modules.wishlist.model.WishList;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "products", indexes = {
        @Index(name = "index_id",columnList = "id"),
        @Index (name = "index_name", columnList = "name")
        })
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(nullable = false)
    private Double price;

    @OneToMany(
            mappedBy = "product",
            cascade = CascadeType.ALL,
            orphanRemoval = true, fetch =
            FetchType.LAZY
    )
    private List<ImageProduct> images = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "products_categories",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<Category> categories = new ArrayList<>();

    @ManyToMany(mappedBy = "products", fetch = FetchType.LAZY)
    private Set<WishList> wishLists =  new HashSet<>();



    public void addImagen(String url) {
        ImageProduct img = new ImageProduct();
        img.setUrl(url);
        img.setProduct(this);
        this.images.add(img);
    }

    public void deleteImagen(ImageProduct imagen) {
        this.images.remove(imagen);
        imagen.setProduct(null);
    }

    public void addCategory(Category category) {

        if(!categories.contains(category)) {

            this.categories.add(category);

            if (!category.getProducts().contains(this)) {
                category.getProducts().add(this);
            }
        }
    }
}

