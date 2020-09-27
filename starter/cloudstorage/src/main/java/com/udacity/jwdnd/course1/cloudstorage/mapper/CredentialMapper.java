package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.Model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.Model.Note;
import com.udacity.jwdnd.course1.cloudstorage.Model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {
    @Select("SELECT * FROM CREDENTIALS WHERE username = #{username}")
    Credential getCredential(String username);

    @Select("SELECT * FROM CREDENTIALS WHERE userid = #{userId}")
    List<Credential> getCredentials(Integer userId);

    @Insert("INSERT INTO CREDENTIALS (url, username, key, password, userid) " +
            "VALUES(#{url}, #{userName}, #{key}, #{password}, #{userId})")
    int insert(Credential credential);

    @Insert("UPDATE CREDENTIALS set url = #{url}, username=#{userName}, key= #{key}, password= #{password} where credentialid = #{credentialId}")
    int updateCredential(Credential credential);

    @Delete("Delete from CREDENTIALS where credentialid = #{credentialId}")
    int delete(int credentialId);
}
