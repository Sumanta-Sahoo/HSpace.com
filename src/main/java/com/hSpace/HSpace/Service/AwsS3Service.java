package com.hSpace.HSpace.Service;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.hSpace.HSpace.Exception.CustomException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@Service
public class AwsS3Service {

    private final String bucketName = "hspace-images";
    @Value("${aws.s3.secret.key}")
    private String secretKey;
    @Value("${aws.s3.access.key}")
    private String accessKey;

    public String saveImageToS3(MultipartFile photo){
        String s3LocationImage = null;
        try{
            String s3FileName = photo.getOriginalFilename();
            BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(accessKey, secretKey);
            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                    .withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials))
                    .withRegion(Regions.US_EAST_1)
                    .build();

            InputStream inputStream = photo.getInputStream();
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType("image/jpeg");

            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, s3FileName, inputStream, objectMetadata);
            s3Client.putObject(putObjectRequest);
            s3LocationImage = "https://" + bucketName + ".s3.amazon.com/"+s3FileName;

        }catch (Exception e){
            e.printStackTrace();
            throw new CustomException("Error Occurred During (saving image to s3)");
        }
        return s3LocationImage;
    }

}
