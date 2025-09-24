package com.sportecommerce.proyecto.v1.modules.products.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductDTOForm {
    private ProductDTORequest data;
    private MultipartFile[] images;
}
