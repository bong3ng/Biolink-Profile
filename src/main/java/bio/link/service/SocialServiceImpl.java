package bio.link.service;


import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bio.link.model.entity.SocialEntity;
import bio.link.repository.SocialRepository;

@Service
public class SocialServiceImpl implements SocialService {

    private SocialEntity socialEntity;
    @Autowired
    private SocialRepository socialRepository;
    @Override
    public List<SocialEntity> getAllSocialsByUserId(Long userId) {
        return socialRepository.getAllSocialsByUserId(userId);
    }
//    @Override
//    public SocialEntity getSocialByUserIdAndName(Long userId , String socialName) {
//        return socialRepository.getSocialByUserIdAndName(userId , socialName);
//    }
//    @Override
//    public SocialEntity createSocial(String url, Long userId) throws IOException {
//
//        SocialEntity social = new SocialEntity();
//        social.setUrl(url);
//        social.setUserId(userId);
//        return socialRepository.save(social);
//    }


    @Override
    public void createSocialFirstLogin(Long userId) throws IOException {
        SocialEntity socialInsta = new SocialEntity(
                null,
                "instagram",
                null,
                false,
                userId
        );
        socialRepository.save(socialInsta);
        SocialEntity socialTwitter = new SocialEntity(
                null,
                "twitter",
                null,
                false,
                userId
        );
        socialRepository.save(socialTwitter);
        SocialEntity socialTiktok = new SocialEntity(
                null,
                "tiktok",
                null,
                false,
                userId
        );
        socialRepository.save(socialTiktok);
        SocialEntity socialFacebook = new SocialEntity(
                null,
                "facebook",
                null,
                false,
                userId
        );
        socialRepository.save(socialFacebook);
        SocialEntity socialMail = new SocialEntity(
                null,
                "mail",
                null,
                false,
                userId
        );
        socialRepository.save(socialMail);
        SocialEntity socialYoutube = new SocialEntity(
                null,
                "youtube",
                null,
                false,
                userId
        );
        socialRepository.save(socialYoutube);
        SocialEntity socialGithub = new SocialEntity(
                null,
                "github",
                null,
                false,
                userId
        );
        socialRepository.save(socialGithub);
    }

    @Override
    public void updateSocial(List<SocialEntity> socialEntityList, Long userId) {
        for (int i = 0; i <socialEntityList.size() ; i ++) {
            socialRepository.save(socialEntityList.get(i));
        }
    }


//    @Override
//    public void deleteSocialById(Long id) {
//        socialRepository.deleteById(id);
//    }


}
