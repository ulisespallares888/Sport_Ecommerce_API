package com.sportecommerce.proyecto.v1.modules.wishlist.model;

import com.sportecommerce.proyecto.v1.modules.products.model.Product;
import com.sportecommerce.proyecto.v1.modules.users.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;



@Table(name = "wish_lists")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class WishList {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    @Column(nullable = false,unique = true)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToMany(fetch =  FetchType.LAZY)
    @JoinTable(
            name = "wishlists_products",
            joinColumns = @JoinColumn(name = "wishlist_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> products;

    public void addProduct(Product product) {
        this.products.add(product);
        product.getWishLists().add(this);
    }
    public void removeProduct(Product product) {
        this.products.remove(product);
        product.getWishLists().remove(this);
    }
    public void clearProducts() {
        for (Product product : this.products) {
            product.getWishLists().remove(this);
        }
        this.products.clear();
    }

}
