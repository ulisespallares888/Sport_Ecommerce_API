package com.tucompra.proyecto;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

public class MermaidFolderStructure {

    public static void main(String[] args) {
        // Ruta del proyecto Spring (modifica esta ruta según tu entorno)
        String projectPath = "proyecto/src/main/java/com/tucompra/proyecto";
        // Ruta del archivo de salida
        String outputPath = "FolderStructure.mmd";

        try {
            // Obtener la estructura en formato Mermaid
            List<String> mermaidStructure = getMermaidStructure(Paths.get(projectPath));

            // Agregar encabezado de Mermaid
            mermaidStructure.add(0, "graph TD");

            // Guardar la estructura en un archivo
            Files.write(Paths.get(outputPath), mermaidStructure);

            System.out.println("Estructura Mermaid guardada en: " + outputPath);
        } catch (IOException e) {
            System.err.println("Error al procesar las carpetas: " + e.getMessage());
        }
    }

    /**
     * Método para obtener la estructura de carpetas y archivos en formato Mermaid.
     *
     * @param rootPath La ruta raíz del proyecto.
     * @return Una lista de cadenas con la estructura en formato Mermaid.
     * @throws IOException Si ocurre un error durante la lectura de las carpetas o archivos.
     */
    private static List<String> getMermaidStructure(Path rootPath) throws IOException {
        List<String> mermaidStructure = new ArrayList<>();
        Map<Path, String> idMap = new HashMap<>();

        // Añadir un nodo raíz
        String rootNode = "rootNode[\"" + rootPath.getFileName() + "\"]";
        mermaidStructure.add(rootNode); // Esto agrega el nodo raíz en la estructura Mermaid.

        Files.walkFileTree(rootPath, new SimpleFileVisitor<>() {

            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
                String id = getUniqueId(dir, rootPath, idMap);
                Path parentPath = dir.getParent();
                if (parentPath != null && idMap.containsKey(parentPath)) {
                    // Relación padre --> hijo (carpeta)
                    String parentId = idMap.get(parentPath);
                    mermaidStructure.add(parentId + " --> " + id);
                }
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                String id = getUniqueId(file, rootPath, idMap);
                Path parentPath = file.getParent();
                if (parentPath != null && idMap.containsKey(parentPath)) {
                    // Relación padre --> archivo
                    String parentId = idMap.get(parentPath);
                    mermaidStructure.add(parentId + " --> " + id + "[\"" + file.getFileName() + "\"]");
                }
                return FileVisitResult.CONTINUE;
            }
        });

        return mermaidStructure;
    }


    /**
     * Obtiene un identificador único basado en la ruta relativa desde la raíz.
     *
     * @param path     El camino actual.
     * @param rootPath La ruta raíz del proyecto.
     * @param idMap    Mapa para almacenar identificadores únicos.
     * @return Un identificador único para Mermaid.
     */
    private static String getUniqueId(Path path, Path rootPath, Map<Path, String> idMap) {
        return idMap.computeIfAbsent(path, p -> {
            String relativePath = rootPath.relativize(p).toString();
            return sanitizeId(relativePath);
        });
    }

    /**
     * Sanitiza un identificador para usarlo en Mermaid (remueve caracteres no válidos).
     *
     * @param name El nombre original.
     * @return El nombre sanitizado.
     */
    private static String sanitizeId(String name) {
        return name.replaceAll("[^a-zA-Z0-9_]", "_");
    }
}
