package final_backend.Member.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class S3Service {
    private final AmazonS3 s3Client;

    @Autowired
    public S3Service (AmazonS3 s3Client) {
        this.s3Client = s3Client;
    }

    // 파일 업로드
    public String uploadFileToS3(MultipartFile file) throws IOException {
        String bucketName = "datastorage.avengers/images";
        String key = generateUniquekey(file.getOriginalFilename());

        PutObjectRequest request = new PutObjectRequest(bucketName, key, file.getInputStream(), null);
        s3Client.putObject(request);

        // 반환할 파일 URL 생성 (S3의 URL 형식은 "https://s3-{region}.amazonaws.com/{bucket}/{key}" 입니다)
        String fileUrl = s3Client.getUrl(bucketName, key).toString();
        return fileUrl;
    }

    private String generateUniquekey(String originalFilename) {
        // 파일 이름을 무작위로 생성하여 중복 방지
        return UUID.randomUUID().toString() + "-" + originalFilename;
    }

    // 기존 파일 삭제
    public void deleteFileFromS3(String fileUrl) {
        String bucketName = "datastorage.avengers/images";
        String key = extractKeyFromUrl(fileUrl); // 파일의 키 (S3에서의 저장 경로)

        s3Client.deleteObject(bucketName, key);
    }

    private String extractKeyFromUrl(String fileUrl) {
        String baseUrl = "https://s3.ap-northeast-2.amazonaws.com/datastorage.avengers/images/";
        return fileUrl.substring(baseUrl.length());
    }
}
