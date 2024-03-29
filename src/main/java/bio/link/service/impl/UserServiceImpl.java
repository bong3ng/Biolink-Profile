package bio.link.service.impl;


import bio.link.model.dto.*;
import bio.link.model.entity.*;
import bio.link.model.response.ResponseData;
import bio.link.repository.*;
import bio.link.service.PluginsService;
import bio.link.service.ProfileService;
import bio.link.service.SocialService;
import bio.link.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bio.link.model.exception.NotFoundException;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import java.util.List;

@Service

public class UserServiceImpl implements UserService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private SocialService socialService;
    private static LocalDate currentDate;
    @Autowired
    private PluginsService pluginsService;
    @Autowired
    private PluginsRepository pluginsRepository;
    @Autowired
    private ClickSocialRepository clickSocialRepository;
    @Autowired
    private ClickPluginsRepository clickPluginsRepository;
    @Autowired
    private ProfileService profileService;
    @Autowired
    private ClickProfileRepository clickProfileRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public Boolean checkUserRole(Long userId) {
        if(userRepository.getUserRoleById(userId).equals("ROLE_ADMIN")) {
            return true;
        }
        return false;
    }
    @Override
    public UserEntity getUserByUsername(String username) {
        username = username.trim();
        UserEntity userEntity = userRepository.findByUsername(username);
        if(userEntity == null) {
           throw new NotFoundException("Không tìm thấy người dùng");
        }
        return userEntity;
    }

    @Override
    public ResponseData getStatsByUsername(Long userId , Integer days) {
        currentDate = LocalDate.now();
        ProfileEntity profileEntity = profileService.getProfileByUserId(userId);
        Long profileId = profileEntity.getId();
        LocalDate statsDate = null;

        switch (days) {
            case 7:
                statsDate = currentDate.minusDays(7);
                break;
            case 30:
                statsDate = currentDate.minusDays(30);
                break;
            case 1000:
                statsDate = LocalDate.parse("1999-01-06");
                break;
            default:
                statsDate = currentDate.minusDays(7);
                break;
        }

        Long totalClickProfile = clickProfileRepository.getClickCountBetween(profileId , statsDate);

//        List<ClickProfileEntity> clickProfileEntityList = clickProfileRepository.getAllClickBetween(profileId , statsDate );
//        int size = clickProfileEntityList.size();
//        for(int i = 0 ; i < size ; i++) {
//            totalClickProfile += clickProfileEntityList.get(i).getClickCount();
//        }
//        List<ClickProfileDto> clickProfileDtoList = clickProfileEntityList.stream().map(c -> modelMapper.map(c, ClickProfileDto.class)).collect(Collectors.toList());

        List<SocialEntity> socialEntityList = socialService.getAllSocialsByUserId(userId);
        List<ClickSocialDto> clickSocialDtoList = new ArrayList<>();
        Long totalClickSocial = 0L;
        int size = socialEntityList.size();
        for(int i = 0 ; i < size ; i++) {
            SocialEntity socialEntity = socialEntityList.get(i);
            Long click = clickSocialRepository.getAllClickCountBetween(socialEntity.getId() , statsDate);
            if(click != null) {
                totalClickSocial += click;
            }
            ClickSocialDto dto = ClickSocialDto.builder().id(socialEntity.getId())
                                                    .name(socialEntity.getName())
                                                    .url(socialEntity.getUrl())
                                                    .clickCount(click)
                                                    .build();
            clickSocialDtoList.add(dto);
        }

        List<PluginsEntity> pluginsEntityList =pluginsRepository.getAllPluginsByUserId(userId);
        List<ClickPluginsDto> clickPluginsDtoList = new ArrayList<>();
        Long totalClickPlugins = 0L;
        size = pluginsEntityList.size();
        for(int i = 0 ; i < size ; i++) {
            PluginsEntity pluginsEntity = pluginsEntityList.get(i);
            Long click = clickPluginsRepository.getAllClickCountBetween(pluginsEntity.getId() , statsDate);
            if(click != null) {
                totalClickPlugins += click;
            }
            ClickPluginsDto dto = ClickPluginsDto.builder()
                                                    .id(pluginsEntity.getId())
                                                    .title(pluginsEntity.getTitle())
                                                    .url(pluginsEntity.getUrl())
                                                    .clickCount(click)
                                                    .isHeader(pluginsEntity.getIsHeader())
                                                    .build();
            clickPluginsDtoList.add(dto);
        }
        
        StatsDto data = StatsDto.builder()
                                .totalClickProfile(totalClickProfile)
                                .totalClickPlugins(totalClickPlugins)
                                .clickPluginsList(clickPluginsDtoList)
                                .totalClickSocial(totalClickSocial)
                                .clickSocialList(clickSocialDtoList)
                                .build();

        return ResponseData.builder().success(true).message("Thành công").data(Arrays.asList(data)).build();
    }

    @Override
    public String getUsernameByUserId(Long userId) {
        return userRepository.getUsernameByUserId(userId);
    }


}
