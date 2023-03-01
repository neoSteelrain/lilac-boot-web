package com.steelrain.springboot.lilac.repository;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@SpringBootTest
public class AwsS3RepositoryTests {
    private final static String TEST_FILE_PATH = "C:\\large_1769431.png";
    private String testFileName = null;

    @BeforeEach
    public void 파일이름_초기화(){
        File file = new File(TEST_FILE_PATH);
        testFileName = file.getName();
    }

    @Autowired
    private AwsS3Repository awsS3Repository;

    @Test
    public void AwsS3파일업로드_테스트(){
        try{
            File file = new File(TEST_FILE_PATH);
            FileItem fileItem = new DiskFileItem(file.getName(), Files.probeContentType(file.toPath()), false, file.getName(), (int) file.length(), file.getParentFile());
            InputStream inputStream = new FileInputStream(file);
            OutputStream outputStream = fileItem.getOutputStream();
            IOUtils.copy(inputStream, outputStream);
            MultipartFile multipartFile = new CommonsMultipartFile(fileItem);

            String result = awsS3Repository.upLoadMemberProfile(multipartFile, null);
            log.debug("====> 업로드파일 URL : {}", result);
            assertThat(validateURL(result)).isTrue();
        }catch(IOException ioe){
            log.debug("업로드 테스트 예외 : {}", ioe.toString());
        }
    }

    private boolean validateURL(String url){
        try {
            new URL(url).toURI();
            return true;
        }catch (URISyntaxException ue){
            return false;
        } catch (MalformedURLException e) {
            return false;
        }
    }

    @Test
    public void AwsS3파일삭제_테스트(){
        awsS3Repository.deleteMemberProfile(testFileName);
    }
}
