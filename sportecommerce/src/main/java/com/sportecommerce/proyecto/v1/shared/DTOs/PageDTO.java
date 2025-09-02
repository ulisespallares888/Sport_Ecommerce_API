package com.sportecommerce.proyecto.v1.shared.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PageDTO<T> {
    private List<T> content;
    private int page;
    private int size;
    private long totalElements;
}