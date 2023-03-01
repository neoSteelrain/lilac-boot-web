package com.steelrain.springboot.lilac.repository;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public interface IAwsS3Repository {
    String upLoadMemberProfile(MultipartFile file, String saveFileName);

    String upLoadMemberProfile(File file);

    void deleteMemberProfile(String fileName);
}
