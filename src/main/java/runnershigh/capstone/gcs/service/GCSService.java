package runnershigh.capstone.gcs.service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import runnershigh.capstone.gcs.exception.FileNotFoundException;
import runnershigh.capstone.global.error.ErrorCode;

@Slf4j
@Service
public class GCSService {

    @Value("${spring.cloud.gcp.storage.credentials.location}")
    private String keyFileName;

    @Value("${spring.cloud.gcp.storage.bucket}")
    private String bucketName;

    public String upload(MultipartFile multipartFile) {
        if (multipartFile.isEmpty()) {
            return null;
        }
        Storage storage = getStorage();

        String uuid = UUID.randomUUID().toString();
        String ext = multipartFile.getContentType();
        String imgUrl = "https://storage.googleapis.com/" + bucketName + "/" + uuid;

        try {
            BlobInfo blobInfo = BlobInfo.newBuilder(bucketName, uuid)
                .setContentType(ext)
                .build();
            Blob blob = storage.create(blobInfo, multipartFile.getInputStream());
            if (blob == null) {
                throw new FileNotFoundException(ErrorCode.FILE_UPLOAD_FAILED);
            }
        } catch (IOException e) {
            throw new FileNotFoundException(ErrorCode.FILE_UPLOAD_FAILED);
        }

        return imgUrl;
    }

    public String update(String beforeImageUrl, MultipartFile multipartFile) {
        delete(beforeImageUrl);
        return upload(multipartFile);
    }

    public void delete(String imageUrl) {
        Storage storage = getStorage();
        String fileName = extractFileName(imageUrl);
        boolean deleted = storage.delete(bucketName, fileName);
        if (!deleted) {
            throw new FileNotFoundException(ErrorCode.FILE_DELETE_FAILED);
        }
    }

    private Storage getStorage() {
        try (FileInputStream keyFileInputStream = new FileInputStream(keyFileName)) {
            return StorageOptions.newBuilder()
                .setCredentials(GoogleCredentials.fromStream(keyFileInputStream))
                .build()
                .getService();
        } catch (IOException e) {
            log.error("Failed to load GCP credentials file from path: {}", keyFileName, e);
            throw new FileNotFoundException(ErrorCode.STORAGE_CREATE_FAILED);
        }
    }

    private String extractFileName(String imgUrl) {
        int lastSlashIndex = imgUrl.lastIndexOf("/");
        return imgUrl.substring(lastSlashIndex + 1);
    }
}

