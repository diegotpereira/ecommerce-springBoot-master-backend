package br.com.java.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.java.config.StorageProperties;
import br.com.java.exception.StorageException;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

@Service
public class FIleStoreService {
    
    private StorageProperties properties = new StorageProperties();
    Path rootLocation = Paths.get(properties.getLocation());


    public String store(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new StorageException("Falha ao armazenar arquivo vazio.");
            }

            String extension = FilenameUtils.getExtension(file.getOriginalFilename());
            String uploadedFilename = UUID.randomUUID().toString() + "." + extension;

            Path destinationFile = rootLocation.resolve(
                Paths.get(uploadedFilename))
                .normalize().toAbsolutePath();

            try(InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile,
                StandardCopyOption.REPLACE_EXISTING);

                final String baseURL = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();

                return baseURL + "/fileUpload/files/" + uploadedFilename;
            }

        } catch (IOException e) {
            //TODO: handle exception
            throw new StorageException("Falha ao armazenar arquivo.", e);
        }
    }

    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.rootLocation, 1)
                    .filter(path -> !path.equals(this.rootLocation))
                    .map(this.rootLocation::relativize);
        }
        catch (IOException e) {
            throw new StorageException("Falha ao ler os arquivos armazenados", e);
        }

    }

    public Resource load(String filename) {
        try {
            Path file = rootLocation.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                
                return resource;
            } else {
                throw new RuntimeException("Não foi possível ler o arquivo");
            }
        } catch (MalformedURLException e) {
            //TODO: handle exception
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }
}
