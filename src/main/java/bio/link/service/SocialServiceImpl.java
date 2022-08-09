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

        SocialEntity socialInsta = new SocialEntity();
        socialInsta.setName("instagram");
        socialInsta.setStatus(false);
        socialInsta.setUserId(userId);
        socialRepository.save(socialInsta);

        SocialEntity socialTwitter = new SocialEntity();
        socialTwitter.setName("twitter");
        socialTwitter.setStatus(false);
        socialTwitter.setUserId(userId);
        socialRepository.save(socialTwitter);

        SocialEntity socialTiktok = new SocialEntity();
        socialTiktok.setName("tiktok");
        socialTiktok.setStatus(false);
        socialTiktok.setUserId(userId);

        socialRepository.save(socialTiktok);


        SocialEntity socialFacebook = new SocialEntity();
        socialFacebook.setName("facebook");
        socialFacebook.setStatus(false);
        socialFacebook.setUserId(userId);
        socialRepository.save(socialFacebook);

        SocialEntity socialMail = new SocialEntity();
        socialMail.setName("mail");
        socialMail.setStatus(false);
        socialMail.setUserId(userId);
        socialRepository.save(socialMail);

        SocialEntity socialYoutube = new SocialEntity();
        socialYoutube.setName("youtube");
        socialYoutube.setStatus(false);
        socialYoutube.setUserId(userId);
        socialRepository.save(socialYoutube);

        SocialEntity socialGithub = new SocialEntity();
        socialGithub.setName("github");
        socialGithub.setStatus(false);
        socialGithub.setUserId(userId);
        socialRepository.save(socialGithub);


    }

    @Override
    public void updateSocial(List<SocialEntity> socialEntityList, Long userId) {

        int size = socialEntityList.size();
        for (int i = 0; i < size ; i ++) {

            socialRepository.save(socialEntityList.get(i));
        }
    }


//    @Override
//    public void deleteSocialById(Long id) {
//        socialRepository.deleteById(id);
//    }


}
