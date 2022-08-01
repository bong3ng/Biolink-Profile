package bio.link.service;


import bio.link.model.entity.*;
import bio.link.repository.ClickPluginsRepository;
import bio.link.repository.ClickProfileRepository;
import bio.link.repository.ClickSocialRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.HashMap;

public class ClickCountServiceImpl implements ClickCountService, Runnable {
    private SocialEntity socialEntity;
    private PluginsEntity pluginsEntity;
    private ProfileEntity profileEntity;

    @Override
    public void run() {
        if (profileEntity != null && clickProfileRepository != null) {
            this.countClickProfile();
        }
        if(socialEntity != null && clickSocialRepository != null) {
            this.countClickSocial();
        }
        if(pluginsEntity != null && clickPluginsRepository != null) {
            this.countClickPlugins();
        }
    }



    public ClickCountServiceImpl(ProfileEntity profileEntity , ClickProfileRepository clickProfileRepository) {
        this.profileEntity = profileEntity;
        this.clickProfileRepository = clickProfileRepository;
    }

    @Autowired
    private ClickProfileRepository clickProfileRepository;
    private static HashMap<Long , Long> countClickProfileMap = new HashMap<>();
    private static LocalDate currentDateProfile;
    private static LocalDate previousDateProfile;
    @Override
    public void countClickProfile() {

        // khi sang ngày mới, reset HashMap
        currentDateProfile = LocalDate.now();
        if(currentDateProfile.minusDays(1).equals(previousDateProfile)) {
            countClickProfileMap.clear();
        }
        previousDateProfile = currentDateProfile;

        Long profileId = profileEntity.getId();
        Long clickCount = countClickProfileMap.get(profileId);
        if(clickCount == null) {
            ClickProfileEntity clickProfileEntity = clickProfileRepository.getClickCountByDateAndProfileId(currentDateProfile,profileId);
            if(clickProfileEntity == null) {
                countClickProfileMap.put(profileId , 1L);
                clickProfileEntity = new ClickProfileEntity(null , 1L , currentDateProfile , profileId);
            }
            else {
                clickCount = clickProfileEntity.getClickCount() + 1;
                countClickProfileMap.put(profileId , clickCount);
                clickProfileEntity.setClickCount(clickCount);
            }
            clickProfileRepository.save(clickProfileEntity);
        }
        else {
            clickCount += 1;
            countClickProfileMap.put(profileId , clickCount);
            clickProfileRepository.updateProfileClickCount(currentDateProfile , profileId,clickCount);
        }
    }



    public ClickCountServiceImpl(SocialEntity socialEntity , ClickSocialRepository clickSocialRepository) {
        this.socialEntity = socialEntity;
        this.clickSocialRepository = clickSocialRepository;
    }
    @Autowired
    private ClickSocialRepository clickSocialRepository;
    private static LocalDate currentDateSocial;
    private static LocalDate previousDateSocial;
    private static HashMap<Long, Long> countClickSocialMap = new HashMap<>();

    @Override
    public void countClickSocial() {

        currentDateSocial = LocalDate.now();
        if (currentDateSocial.minusDays(1).equals(previousDateSocial)) {
            countClickSocialMap.clear();
        }
        previousDateSocial = currentDateSocial;

        Long socialId = socialEntity.getId();
        Long clickCount = countClickSocialMap.get(socialId);

        if (clickCount == null) {
            ClickSocialEntity clickSocialEntity = clickSocialRepository.getClickCountByDate(currentDateSocial, socialId);
            if (clickSocialEntity == null) {
                countClickSocialMap.put(socialId, 1L);
                clickSocialEntity = new ClickSocialEntity(null, 1L, currentDateSocial, socialId);
            } else {
                clickCount = clickSocialEntity.getClickCount() + 1L;
                countClickSocialMap.put(socialId, clickCount);
                clickSocialEntity.setClickCount(clickCount);
            }
            clickSocialRepository.save(clickSocialEntity);
        } else {
            clickCount += 1;
            countClickSocialMap.put(socialId, clickCount);
            clickSocialRepository.updateSocialClickCount(currentDateSocial, socialId, clickCount);
        }
    }



    @Autowired
    private ClickPluginsRepository clickPluginsRepository;
    private static HashMap<Long , Long> countClickPluginsMap = new HashMap<>();
    private static LocalDate currentDatePlugins;
    private static LocalDate previousDatePlugins;
    public ClickCountServiceImpl(PluginsEntity pluginsEntity , ClickPluginsRepository clickPluginsRepository) {
        this.pluginsEntity = pluginsEntity;
        this.clickPluginsRepository = clickPluginsRepository;
    }

    public void countClickPlugins() {

        currentDatePlugins = LocalDate.now();
        if (currentDatePlugins.minusDays(1).equals(previousDatePlugins)) {
            countClickPluginsMap.clear();
        }
        previousDatePlugins = currentDatePlugins;

        Long pluginsId = pluginsEntity.getId();
        Long clickCount = countClickPluginsMap.get(pluginsId);
        if(clickCount == null) {
            ClickPluginsEntity clickPluginsEntity = clickPluginsRepository.getClickCountByDate(currentDatePlugins , pluginsId);
            if(clickPluginsEntity == null) {
                countClickPluginsMap.put(pluginsId , 1L);
                clickPluginsEntity = new ClickPluginsEntity(null , 1L , currentDatePlugins , pluginsId);
            }
            else {
                clickCount = clickPluginsEntity.getClickCount() + 1L;
                countClickPluginsMap.put(pluginsId , clickCount );
                clickPluginsEntity.setClickCount(clickCount);
            }
            clickPluginsRepository.save(clickPluginsEntity);
        }
        else {
            clickCount += 1;
            countClickPluginsMap.put(pluginsId,clickCount);
            clickPluginsRepository.updatePluginsClickCount(currentDatePlugins , pluginsId , clickCount);
        }
    }
}