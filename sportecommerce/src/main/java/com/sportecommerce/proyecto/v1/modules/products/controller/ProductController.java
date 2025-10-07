package com.sportecommerce.proyecto.v1.modules.products.controller;

import com.sportecommerce.proyecto.v1.modules.products.dto.ProductDTORequest;
import com.sportecommerce.proyecto.v1.modules.products.dto.ProductDTOResponse;
import com.sportecommerce.proyecto.v1.modules.products.service.IProductService;
import com.sportecommerce.proyecto.v1.shared.DTOs.PageDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("api/v1/product")
@RequiredArgsConstructor
public class ProductController {

    private final IProductService  productService;
    private final PagedResourcesAssembler<ProductDTOResponse>  pagedResourcesAssembler;

    private PagedModel<EntityModel<ProductDTOResponse>> toPagedModel(Page<ProductDTOResponse> productDTOResponsePage) {
        return pagedResourcesAssembler.toModel(
                productDTOResponsePage,
                productDTOResponse -> {
                    EntityModel<ProductDTOResponse> entityModel = EntityModel.of(productDTOResponse);
                    entityModel.add(WebMvcLinkBuilder.linkTo(
                                    WebMvcLinkBuilder.methodOn(ProductController.class)
                                            .findById(productDTOResponse.getId()))
                            .withSelfRel());
                    return entityModel;
                }
        );
    }

    /**
     * Obtiene todos los productos con paginación y orden.
     */
    @GetMapping("")
    public ResponseEntity<PagedModel<EntityModel<ProductDTOResponse>>> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sort,
            @RequestParam(defaultValue = "desc") String direction) {

        int maxSize = 100;
        if(size>maxSize) size=maxSize;

        Pageable pageable = buildPageable(page, size, sort, direction);
        PageDTO<ProductDTOResponse> pageDTO = productService.findAll(pageable);

        Page<ProductDTOResponse> productPage = new PageImpl<>(
                pageDTO.getContent(),
                PageRequest.of(pageDTO.getPage(), pageDTO.getSize(), pageable.getSort()),
                pageDTO.getTotalElements()
        );

        if(productPage.isEmpty()) return ResponseEntity.ok(PagedModel.empty());

        return ResponseEntity.ok(toPagedModel(productPage));
    }

    @GetMapping("{id}")
    public ProductDTOResponse findById(@PathVariable Long id) {
        return productService.findById(id);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createProduct(
            @RequestPart("data") ProductDTORequest data,
            @RequestPart(value = "images", required = false) MultipartFile[] images) throws IOException {

        return ResponseEntity.ok(productService.create(data,images));
    }

    /*
        Product producto = new Product();
        producto.setNombre(datos.getNombre());
        producto.setDescripcion(datos.getDescripcion());
        producto.setPrecio(datos.getPrecio());

        for (MultipartFile imagen : images) {
            if (imagen.isEmpty()) continue;

            String extension = getExtension(imagen.getOriginalFilename());
            BufferedImage clean = stripMetadata(imagen);
            String fileName = UUID.randomUUID() + "." + extension;
            File outputFile = Paths.get(MEDIA_DIR, fileName).toFile();
            ImageIO.write(clean, extension, outputFile);

            String url = "http://localhost:8081/media/" + fileName;
            producto.addImagen(url);
        }

        Product guardado = productService.guardarProducto(producto);
        return ResponseEntity.ok(Map.of("id", guardado.getId(), "imagenes", guardado.getImagenes()));
    }


     private BufferedImage stripMetadata(MultipartFile file) throws IOException {
        BufferedImage original = ImageIO.read(file.getInputStream());
        BufferedImage cleaned = new BufferedImage(
                original.getWidth(),
                original.getHeight(),
                BufferedImage.TYPE_INT_RGB
        );
        Graphics2D g = cleaned.createGraphics();
        g.drawImage(original, 0, 0, null);
        g.dispose();
        return cleaned;
    }

    private String getExtension(String filename) {
        if (filename == null) return "";
        int dot = filename.lastIndexOf('.');
        return (dot == -1) ? "" : filename.substring(dot + 1);
    }
     */

    @PostMapping("{id}/images")
    public ResponseEntity<?> addImages(
            @RequestPart("id") Long id,
            @RequestPart(value = "images", required = false) MultipartFile[] images) throws IOException {

        return ResponseEntity.ok(productService.addImagesToProduct(id, images));
    }

    // 🔧 Nueva utilidad: interpreta correctamente el formato del sort
    private Pageable buildPageable(int page, int size, String sort, String direction) {
        if(sort==null || sort.isBlank()) return PageRequest.of(page, size);

        // Caso 1 → sort=name,asc
        if(sort.contains(",")) {
            String[] parts = sort.split(",");
            String field = parts[0];
            String dir = parts.length>1 ? parts[1] : "asc";
            Sort sortOrder = dir.equalsIgnoreCase("desc") ? Sort.by(field).descending() : Sort.by(field).ascending();
            return PageRequest.of(page, size, sortOrder);
        }

        // Caso 2 → sort=name & direction=asc
        Sort sortOrder = (direction!=null && direction.equalsIgnoreCase("desc")) ? Sort.by(sort).descending() : Sort.by(sort).ascending();
        return PageRequest.of(page, size, sortOrder);
    }
}
