package Food.FoodDelivery.project.validation;

import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

public class FileValidation {

    private static final long MAX_FILE_SIZE = 10 * 1024 * 1024; // 10 MB

    private static final List<String> ALLOWED_TYPES = List.of(
            "application/pdf", "image/jpeg", "image/png"
    );

    private static final List<String> ALLOWED_EXTENSIONS = List.of(
            "pdf", "jpg", "jpeg", "png"
    );

    public static Boolean validateFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "File cannot be empty");
        }

        String originalFilename = file.getOriginalFilename();
        String fileType = file.getContentType();
        long fileSize = file.getSize();
        String fileExtension = getFileExtension(originalFilename);

        if (!ALLOWED_TYPES.contains(fileType)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unsupported MIME type: " + fileType + ", Allowed Types are " + ALLOWED_TYPES);
        }

        if (!ALLOWED_EXTENSIONS.contains(fileExtension)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid file extension: " + fileExtension + ", Allowed File Extensions are " + ALLOWED_EXTENSIONS);
        }

        if (fileSize > MAX_FILE_SIZE) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "File size must be under 10MB");
        }
        return true;
    }

    private static String getFileExtension(String filename) {
        int lastDot = filename.lastIndexOf('.');
        return (lastDot == -1) ? "" : filename.substring(lastDot + 1).toLowerCase();
    }

}
