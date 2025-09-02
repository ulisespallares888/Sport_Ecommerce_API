package com.sportecommerce.proyecto.v1.modules.products.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sportecommerce.proyecto.v1.modules.categories.model.Category;
import com.sportecommerce.proyecto.v1.modules.wishlist.model.WishList;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
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

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<ImageProduct> images = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "products_categories",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<Category> categories;

    @ManyToMany(mappedBy = "products")
    @JsonManagedReference
    private List<WishList> wishLists;



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

            if (this.categories == null) {
                this.categories = new ArrayList<>();
            }
            this.categories.add(category);

            if (!category.getProducts().contains(this)) {
                category.getProducts().add(this);
            }
        }
    }
}

