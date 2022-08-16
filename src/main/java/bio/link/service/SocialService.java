package bio.link.service;

import java.io.IOException;
import java.util.List;

import bio.link.model.entity.SocialEntity;



public interface SocialService {

    List<SocialEntity> getAllSocialsByUserId(Long userId);



    void createSocialFirstLogin(Long userId) throws IOException;

    void updateSocial(List<SocialEntity> socialEntityList, Long userId);
}