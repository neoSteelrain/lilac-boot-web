package com.steelrain.springboot.lilac.repository;

import com.steelrain.springboot.lilac.config.APIConfig;
import com.steelrain.springboot.lilac.exception.AwsS3RepositoryException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.exception.SdkException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * AWS S3 SDK 를 사용해서 파일을 S3에 업로드, 삭제하는 클래스
 */
@Slf4j
@RequiredArgsConstructor
@Repository
public class AwsS3Repository implements IAwsS3Repository {

    private final S3Client m_s3Client;
    private final APIConfig m_apiConfig;


    @Override
    public String upLoadMemberProfile(MultipartFile file, String saveFileName){
        PutObjectRequest objectRequest = PutObjectRequest.builder()
                .bucket(m_apiConfig.getAwsS3Bucket())
                .key(m_apiConfig.getAwsS3BaseDir() + saveFileName)
                .contentType(file.getContentType())
                .contentLength(file.getSize())
                .build();
        try{
            PutObjectResponse objectResponse = m_s3Client.putObject(objectRequest, RequestBody.fromBytes(file.getBytes()));
            URL responseURL = m_s3Client.utilities().getUrl(GetUrlRequest.builder()
                    .bucket(m_apiConfig.getAwsS3Bucket())
                    .key(createS3ObjectKey(saveFileName))
                    .build());
            return responseURL.toExternalForm();
        }catch (IOException ioe){
            log.error("AWS S3 멀티파트파일 업로드 예외 발생 : {}", ioe);
            throw new AwsS3RepositoryException(String.format("AWS S3 멀티파트파일 업로드 예외 발생 : %s", ioe.toString()), ioe, file);
        } catch (SdkException s3e){
            log.error("AWS S3 멀티파트파일 SDK 예외 : {}", s3e);
            throw new AwsS3RepositoryException(String.format("AWS S3 멀티파트파일 업로드 SDK 예외 : %s", s3e.toString()), s3e, file);
        } catch (RuntimeException ex){
            log.error("AWS S3 멀티파트파일 미정의 예외 : {}", ex);
            throw new AwsS3RepositoryException(String.format("AWS S3 멀티파트파일 업로드 미정의 예외 : %s", ex.toString()), ex, file);
        }
    }

    @Override
    public String upLoadMemberProfile(File file){
        PutObjectRequest objectRequest = PutObjectRequest.builder()
                .bucket(m_apiConfig.getAwsS3Bucket())
                .key(createS3ObjectKey(file.getName()))
                .build();
        try {
            PutObjectResponse objectResponse = m_s3Client.putObject(objectRequest, RequestBody.fromFile(file));
            URL responseURL = m_s3Client.utilities().getUrl(GetUrlRequest.builder()
                    .bucket(m_apiConfig.getAwsS3Bucket())
                    .key(createS3ObjectKey(file.getName()))
                    .build());
            return responseURL.toExternalForm();
        } catch (SdkException s3e){
            log.error("AWS S3 파일 업로드 SDK 예외 - 파일정보 : {} , 예외정보 : {}", file.toString(), s3e);
            throw new AwsS3RepositoryException(String.format("AWS S3 파일 업로드 SDK 예외 : %s", s3e.toString()), s3e, file);
        } catch (RuntimeException ex){
            log.error("AWS S3 파일 업로드 미정의 예외 - 파일정보 : {} , 예외정보 : {}", file.toString(), ex);
            throw new AwsS3RepositoryException(String.format("AWS S3 파일 업로드 미정의 예외 : %s", ex.toString()), ex, file);
        }
    }

    @Override
    public void deleteMemberProfile(String fileName){
        DeleteObjectRequest deleteObjRequest = DeleteObjectRequest.builder()
                .bucket(m_apiConfig.getAwsS3Bucket())
                .key(createS3ObjectKey(fileName))
                .build();
        try{
            DeleteObjectResponse deleteObjResponse = m_s3Client.deleteObject(deleteObjRequest);
        } catch (SdkException s3e){
            log.error("AWS S3 삭제 SDK 예외 - 파일이름 : {} , 예외정보 {}", fileName, s3e.toString());
            throw new AwsS3RepositoryException(String.format("AWS S3 삭제 SDK 예외 - 파일이름 : %s , 예외정보 : %s", fileName, s3e.toString()), s3e);
        } catch (RuntimeException ex){
            log.error("AWS S3 삭제 미정의 예외 - 파일이름 : {} , 예외정보 {}", fileName, ex);
            throw new AwsS3RepositoryException(String.format("AWS S3 삭제 미정의 예외 - 파일이름 : %s , 예외정보 : %s", fileName, ex.toString()), ex);
        }
    }

    /*
        AWS S3에서 버킷밑에 하위디렉토리가 있고, 하위디렉토리에 실제사용할 파일이 있다면 파일객체의 키값은
        "하위디렉토리 이름 + 파일이름", 예) member-profile/UUID.이미지파일확장자 가 실제 키값으로 사용된다.
        이 메서드는 S3객체의 키값을 얻기 위해 호출한다.
     */
    private String createS3ObjectKey(String fileName){
        return m_apiConfig.getAwsS3BaseDir() + fileName;
    }
}
