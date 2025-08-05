package com.tucompra.proyecto.v1.modules.products.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("api/v1/product")
@RequiredArgsConstructor
public class ProductController {

    @GetMapping("")
    public String hello() {
        return "Hello from ProductController";
    }

    /*
    private final ProductoService productoService;

    private static final String MEDIA_DIR = "/app/media";

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
