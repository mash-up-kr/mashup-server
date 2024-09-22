package kr.mashup.branding.service.birthday;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.Headers;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class FileService {

    private final AmazonS3Client amazonS3Client;

    public String generatePresignedUrl(String bucket, String identifier, long expiresIn) {
        String fileName = createFileName(identifier);
        GeneratePresignedUrlRequest request = createPresignedUrlRequest(bucket, fileName, expiresIn);
        return amazonS3Client.generatePresignedUrl(request).toString();
    }

    private GeneratePresignedUrlRequest createPresignedUrlRequest(String bucket, String filePath, long expiresIn) {
        GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(bucket, filePath)
            .withMethod(HttpMethod.PUT)
            .withExpiration(Date.from(Instant.now().plusSeconds(expiresIn)));
        request.addRequestParameter(Headers.S3_CANNED_ACL, CannedAccessControlList.PublicRead.toString());

        return request;
    }

    private String createFileName(String fileName) {
        return String.format("%d-%s", System.currentTimeMillis(), fileName);
    }
}
