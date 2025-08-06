package com.sportecommerce.proyecto.v1.modules.products.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "product")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    private String name;
    private String description;
    private Double price;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ImageProduct> images = new ArrayList<>();


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
}

