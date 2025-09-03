package com.sportecommerce.proyecto.v1.modules.products.model;



import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "images_products")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String url;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    public void setProduct(Product product) {
        this.product = product;
        if (product != null && !product.getImages().contains(this)) {
            product.getImages().add(this);
        }
    }

}
