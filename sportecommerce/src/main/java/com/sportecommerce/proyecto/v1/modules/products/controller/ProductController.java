package com.sportecommerce.proyecto.v1.modules.products.controller;

import com.sportecommerce.proyecto.v1.modules.products.dto.ProductDTOResponse;
import com.sportecommerce.proyecto.v1.modules.products.service.IProductService;
import com.sportecommerce.proyecto.v1.shared.DTOs.PageDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("api/v1/product")
@RequiredArgsConstructor
public class ProductController {

    private final IProductService  productService;
    private final PagedResourcesAssembler<ProductDTOResponse>  pagedResourcesAssembler;


    private PagedModel<EntityModel<ProductDTOResponse>> toPagedModel(Page<ProductDTOResponse> productDTOResponsePage) {
        return pagedResourcesAssembler.toModel(
                productDTOResponsePage, productDTOResponse ->{
                    EntityModel<ProductDTOResponse> productDTOResponseEntityModel = EntityModel.of(productDTOResponse);

                    productDTOResponseEntityModel.add(
                            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProductController.class)
                                    .findById(productDTOResponse.getId())).withSelfRel());
                    return productDTOResponseEntityModel;
                }
        );
    }


    @GetMapping(value="")
    public ResponseEntity<PagedModel<EntityModel<ProductDTOResponse>>> getAllTasks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sort,
            @RequestParam(defaultValue = "desc") String direction){

        int maxsize = 100;
        if(size>maxsize){
            size = maxsize;
        }

        Sort sortOrder = direction.equalsIgnoreCase("desc")
                ? Sort.by(sort).descending()
                : Sort.by(sort).ascending();

        Pageable pageable = PageRequest.of(page,size,sortOrder);

        PageDTO<ProductDTOResponse> pageDTO = productService.findAll(pageable);

        Page<ProductDTOResponse> productDTOResponsePage = new PageImpl<>(
                pageDTO.getContent(),
                PageRequest.of(
                        pageDTO.getPage(),
                        pageDTO.getSize(),
                        sortOrder),
                pageDTO.getTotalElements()
        );

        if (productDTOResponsePage.isEmpty()){
            return ResponseEntity.ok(PagedModel.empty());
        }

        return ResponseEntity.ok(toPagedModel(productDTOResponsePage));
    }


    @GetMapping(value="{id}")
    public ProductDTOResponse findById(@PathVariable Long id) {
        return productService.findById(id);
    }

    /*


    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> crearProducto(
            @RequestPart("datos") ProductoDTO datos,
            @RequestPart("imagenes") MultipartFile[] imagenes) throws IOException {

        Producto producto = new Producto();
        producto.setNombre(datos.getNombre());
        producto.setDescripcion(datos.getDescripcion());
        producto.setPrecio(datos.getPrecio());

        for (MultipartFile imagen : imagenes) {
            if (imagen.isEmpty()) continue;

            String extension = getExtension(imagen.getOriginalFilename());
            BufferedImage clean = stripMetadata(imagen);
            String fileName = UUID.randomUUID() + "." + extension;
            File outputFile = Paths.get(MEDIA_DIR, fileName).toFile();
            ImageIO.write(clean, extension, outputFile);

            String url = "http://localhost:8081/media/" + fileName;
            producto.addImagen(url);
        }

        Producto guardado = productoService.guardarProducto(producto);
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
}

     */
}
