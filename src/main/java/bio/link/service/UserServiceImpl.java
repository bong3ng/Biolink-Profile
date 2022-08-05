package bio.link.service;


import bio.link.model.dto.*;
import bio.link.model.entity.*;
import bio.link.model.response.ResponseData;
import bio.link.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bio.link.model.exception.NotFoundException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service

public class UserServiceImpl implements UserService{
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
    public UserEntity getUserByUsername(String username) {
        username = username.trim();
        UserEntity userEntity = userRepository.findByUsername(username);
        if(userEntity == null) {
           throw new NotFoundException("Không tìm thấy người dùng");
        }
        return userEntity;
    }

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

        List<ClickProfileEntity> clickProfileEntityList = clickProfileRepository.getAllClickBetween(profileId , statsDate );
        Long totalClickProfile = 0L;
        int n = clickProfileEntityList.size();
        for(int i = 0 ; i < n ; i++) {
            totalClickProfile += clickProfileEntityList.get(i).getClickCount();
        }
        List<ClickProfileDto> clickProfileDtoList = clickProfileEntityList.stream().map(c -> modelMapper.map(c, ClickProfileDto.class)).collect(Collectors.toList());

        List<SocialEntity> socialEntityList = socialService.getAllSocialsByUserId(userId);
        List<ClickSocialDto> clickSocialDtoList = new ArrayList<>();
        Long totalClickSocial = 0L;
        for(int i = 0 ; i< socialEntityList.size() ; i++) {
            Long click = clickSocialRepository.getAllClickCountBetween(socialEntityList.get(i).getId(),statsDate);
            if(click != null) {
                totalClickSocial += click;
            }

            ClickSocialDto dto = ClickSocialDto.builder().id(socialEntityList.get(i).getId())
                                                    .name(socialEntityList.get(i).getName())
                                                    .url(socialEntityList.get(i).getUrl())
                                                    .clickCount(click)
                                                    .build();
            clickSocialDtoList.add(dto);
        }

        List<PluginsEntity> pluginsEntityList = pluginsRepository.getAllPluginsByUserId(userId);
        List<ClickPluginsDto> clickPluginsDtoList = new ArrayList<>();
        Long totalClickPlugins = 0L;
        for(int i = 0 ; i< pluginsEntityList.size() ; i++) {
            Long click = clickPluginsRepository.getAllClickCountBetween(pluginsEntityList.get(i).getId(), statsDate);
            if(click != null) {
                totalClickPlugins += click;
            }

            ClickPluginsDto dto = ClickPluginsDto.builder()
                                                    .id(pluginsEntityList.get(i).getId())
                                                    .title(pluginsEntityList.get(i).getTitle())
                                                    .url(pluginsEntityList.get(i).getUrl())
                                                    .clickCount( click)
                                                    .isHeader(pluginsEntityList.get(i).getIsHeader())
                                                    .build();
            clickPluginsDtoList.add(dto);
        }
        
        StatsDto data = StatsDto.builder()
                                .totalClickProfile(totalClickProfile)
                                .clickProfileList(clickProfileDtoList)
                                .totalClickPlugins(totalClickPlugins)
                                .clickPluginsList(clickPluginsDtoList)
                                .totalClickSocial(totalClickSocial)
                                .clickSocialList(clickSocialDtoList)
                                .build();
        ArrayList<StatsDto> list = new ArrayList<>();
        list.add(data);
        return new ResponseData(true , "Thành công" , list);
    }

    @Override
    public List<UserEntity> getUsernameByUserId(Long id) {
        return userRepository.getUsernameByUserId(id);
    }
}
