package bio.link.service;

import java.io.IOException;
import java.util.List;

import bio.link.model.entity.SocialEntity;



public interface SocialService {
	List<SocialEntity> getAllSocialsByUserId(Long userId);


    SocialEntity getSocialByUserIdAndName(Long userId, String socialName);

    SocialEntity saveSocial(String url , Long profile_id) throws IOException;


    List<SocialEntity> getAllSocialByUserId(long userId);


    SocialEntity updateSocial(String url , Long id);


    void deleteSocialById(Long id);

}
