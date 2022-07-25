package bio.link.service;


import java.io.IOException;
import java.util.List;
import java.util.Objects;

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
    @Override
    public SocialEntity getSocialByUserIdAndName(Long userId , String socialName) {
        return socialRepository.getSocialByUserIdAndName(userId , socialName);
    }
    @Override
    public SocialEntity createSocial(String url, Long userId) throws IOException {

        SocialEntity social = new SocialEntity();
        social.setUrl(url);
        social.setUserId(userId);
        return socialRepository.save(social);
    }


    @Override
    public SocialEntity updateSocial(String url , Long id) {

        SocialEntity socialUp = socialRepository.findById(id).get();

        if (Objects.nonNull(url) && !"".equalsIgnoreCase(url)) {
            socialUp.setUrl(url);
        }
        return socialRepository.save(socialUp);
    }



    @Override
    public void deleteSocialById(Long id) {
        socialRepository.deleteById(id);
    }


}
