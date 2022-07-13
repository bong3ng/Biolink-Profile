package bio.link.service;


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
    public List<SocialEntity> getAllSocialsByProfileId(Long profileId) {
        return socialRepository.getAllSocialsByProfileId(profileId);
    }
    @Override
    public SocialEntity getSocialByProfileIdAndName(Long profileId , String socialName) {
        return socialRepository.getSocialByProfileIdAndName(profileId , socialName);
    }
    @Override
    public SocialEntity saveSocial(String url, Long ptofile_id) {

        SocialEntity social = new SocialEntity();
        social.setUrl(url);
        social.setProfileId(ptofile_id);

        return socialRepository.save(social);
    }



    @Override
    public List<SocialEntity> getAllSocial() {
        return (List<SocialEntity>)
                socialRepository.findAll();
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
