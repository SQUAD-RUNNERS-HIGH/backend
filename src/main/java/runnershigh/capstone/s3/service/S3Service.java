package runnershigh.capstone.s3.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import runnershigh.capstone.global.error.ErrorCode;
import runnershigh.capstone.s3.exception.FileNotFoundException;

@Service
@RequiredArgsConstructor
@Slf4j
public class S3Service {

    private final AmazonS3 s3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String upload(MultipartFile multipartFile, String dirName) {

        File uploadFile = convert(multipartFile);
        String randomName = UUID.randomUUID().toString();
        String fileName = dirName + "/" + randomName;

        try {
            log.info("만들어진 사진의 경로 {}", fileName);
            return putS3(uploadFile, fileName);
        } catch (Exception e) {
            throw new FileNotFoundException(ErrorCode.FILE_UPLOAD_FAILED);
        } finally {
            removeNewFile(uploadFile);
        }
    }

    private File convert(MultipartFile file) {
        String tempDir = System.getProperty("java.io.tmpdir");
        String originalFilename = Objects.requireNonNull(file.getOriginalFilename());
        File convertFile = new File(tempDir, originalFilename);

        try {
            if (!convertFile.createNewFile()) {
                log.error("임시 파일 생성 실패: 이미 존재함 {}", convertFile.getAbsolutePath());
                throw new FileNotFoundException(ErrorCode.FILE_CONVERT_FAILED);
            }

            try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                fos.write(file.getBytes());
            }

            return convertFile;
        } catch (IOException e) {
            throw new FileNotFoundException(ErrorCode.FILE_CONVERT_FAILED);
        }
    }


    private String putS3(File uploadFile, String fileName) {
        s3Client.putObject(new PutObjectRequest(bucket, fileName, uploadFile)
            .withCannedAcl(CannedAccessControlList.PublicRead));

        return s3Client.getUrl(bucket, fileName).toString();
    }

    public String update(String oldFileUrl, MultipartFile newFile, String dirName) {
        if (oldFileUrl != null && !oldFileUrl.isEmpty()) {
            delete(oldFileUrl);
        }
        return upload(newFile, dirName);
    }

    public void delete(String fileName) {
        try {
            String splitStr = ".com/";
            String realFilename = fileName.substring(
                fileName.lastIndexOf(splitStr) + splitStr.length());

            s3Client.deleteObject(new DeleteObjectRequest(bucket, realFilename));
            log.info("S3에서 파일 삭제 성공: {}", fileName);
        } catch (Exception e) {
            log.error("S3에서 파일 삭제 실패: {}", fileName, e);
            throw new FileNotFoundException(ErrorCode.FILE_DELETE_FAILED);
        }
    }

    private void removeNewFile(File targetFile) {
        if (targetFile.delete()) {
            log.info("임시 파일이 삭제되었습니다.");
        } else {
            log.info("임시 파일이 삭제되지 못했습니다.");
        }
    }
}
