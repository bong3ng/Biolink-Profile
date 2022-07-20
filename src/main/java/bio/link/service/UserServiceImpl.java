package bio.link.service;


import bio.link.model.dto.*;
import bio.link.model.entity.*;
import bio.link.model.response.ResponseData;
import bio.link.repository.ClickPluginsRepository;
import bio.link.repository.ClickProfileRepository;
import bio.link.repository.ClickSocialRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bio.link.model.exception.NotFoundException;
import bio.link.repository.UserRepository;

import javax.transaction.Transactional;
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
    private ClickSocialRepository clickSocialRepository;

    @Autowired
    private ClickPluginsRepository clickPluginsRepository;
    @Autowired
    private ProfileService profileService;
    @Autowired
    private ClickProfileRepository clickProfileRepository;
    @Autowired
    private UserRepository userRepository;

    public UserEntity getUserByUsername(String username) {
        username = username.trim();
        UserEntity userEntity = userRepository.findByUsername(username);
        if(userEntity == null) {
           throw new NotFoundException("Khong tim thay nguoi dung");
        }
        return userEntity;
    }

    public ResponseData getStatsByUsername(String username , Integer days) {
        currentDate = LocalDate.now();
        UserEntity userEntity = this.getUserByUsername(username);
        Long userId = userEntity.getId();
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
        List<ClickProfileDto> clickProfileDtoList = clickProfileEntityList.stream().map(c -> modelMapper.map(c, ClickProfileDto.class)).collect(Collectors.toList());

        List<SocialEntity> socialEntityList = socialService.getAllSocialsByUserId(userId);
        List<ClickSocialDto> clickSocialDtoList = new ArrayList<>();
        for(int i = 0 ; i< socialEntityList.size() ; i++) {
            ClickSocialDto dto = ClickSocialDto.builder().name(socialEntityList.get(i).getName())
                                                    .url(socialEntityList.get(i).getUrl())
                                                    .clickCount(clickSocialRepository.getAllClickCountBetween(socialEntityList.get(i).getId(),statsDate))
                                                    .build();
            clickSocialDtoList.add(dto);
        }

        List<PluginsEntity> pluginsEntityList = pluginsService.getAllPluginsByUserId(userId);
        List<ClickPluginsDto> clickPluginsDtoList = new ArrayList<>();
        for(int i = 0 ; i< pluginsEntityList.size() ; i++) {
            ClickPluginsDto dto = ClickPluginsDto.builder()
                                                    .title(pluginsEntityList.get(i).getTitle())
                                                    .url(pluginsEntityList.get(i).getUrl())
                                                    .clickCount(clickPluginsRepository.getAllClickCountBetween(pluginsEntityList.get(i).getId() , statsDate))
                                                    .build();
            clickPluginsDtoList.add(dto);
        }

        List<StatsDto> list = new ArrayList<>();
        StatsDto data = StatsDto.builder()
                                .clickProfileList(clickProfileDtoList)
                                .clickPluginsList(clickPluginsDtoList)
                                .clickSocialList(clickSocialDtoList)
                                .build();
        list.add(data);

        return new ResponseData(true , "Thanh cong" , list);
    }
}
