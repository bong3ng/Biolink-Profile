package bio.link.service;

import java.util.List;

import bio.link.model.entity.SocialEntity;



public interface SocialService {
	List<SocialEntity> getAllSocialsByProfileId(Long profileId);
	SocialEntity getSocialByProfileIdAndName(Long profileId , String socialName);

    SocialEntity saveSocial(String url , Long profile_id) ;


    List<SocialEntity> getAllSocial();


    SocialEntity updateSocial(String url , Long id);


    void deleteSocialById(Long id);

}
