package bio.link.service;



import static bio.link.controller.NameController.CURRENT_FOLDER;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import bio.link.model.dto.PluginsDto;
import bio.link.model.dto.ProfileDto;
import bio.link.model.dto.SocialDto;
import bio.link.model.entity.ClickProfileEntity;
import bio.link.model.entity.PluginsEntity;
import bio.link.model.entity.ProfileEntity;
import bio.link.model.entity.SocialEntity;
import bio.link.model.entity.UserEntity;
import bio.link.model.exception.NotFoundException;
import bio.link.model.response.ResponseData;
import bio.link.repository.ClickProfileRepository;
import bio.link.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService{
    @Autowired
    private ProfileRepository profileRepository;


    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private ClickProfileRepository clickProfileRepository;

    @Autowired
    private ClickSocialServiceImpl clickSocialService;
    @Autowired
    private SocialServiceImpl socialService;
    @Autowired
    private ClickPluginsServiceImpl clickPluginsService;
    @Autowired
    private PluginsServiceImpl pluginsService;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ResponseData getUserProfileByUsername(String username) {
        UserEntity userEntity = userService.getUserByUsername(username);
        ProfileEntity profileEntity = profileRepository.getProfileByUserId(userEntity.getId());
        Long profileId = profileEntity.getId();
        LocalDate date = LocalDate.now();

        ClickProfileEntity clickProfileEntity = clickProfileRepository.getClickCountByDate(date , profileId);
        if(clickProfileEntity == null) {
            ClickProfileEntity newClick = new ClickProfileEntity(null , 1 , date , profileId);
            clickProfileRepository.save(newClick);
        }
        else {
            ClickProfileEntity newClick = new ClickProfileEntity(clickProfileEntity.getId(),
                    clickProfileEntity.getClickCount() + 1 ,
                    date,
                    profileId);
            clickProfileRepository.save(newClick);
        }

        List<SocialEntity> listSocial = socialService.getAllSocialsByProfileId(profileId);
        List<SocialDto> listSocialDto = listSocial.stream().map(s -> modelMapper.map(s,SocialDto.class)).collect(Collectors.toList());

        List<PluginsEntity> listPlugins = pluginsService.getPluginsByProfileId(profileId);
        List<PluginsDto> listPluginsDto = listPlugins.stream().map(p -> modelMapper.map(p , PluginsDto.class)).collect(Collectors.toList());

        ProfileDto profileDto = new ProfileDto(username , profileEntity.getName(),profileEntity.getBio(),profileEntity.getImage(), listSocialDto , listPluginsDto );
        ArrayList<ProfileDto> list = new ArrayList<>();
        list.add(profileDto);

        return ResponseData.builder()
                .success(true)
                .message("Thanh cong")
                .data(list)
                .build();
    }


    public ResponseData clickUrlOfUsername(String username , String urlTitle ,  String url , Boolean isPlugins) {
        UserEntity userEntity = userService.getUserByUsername(username);
        ProfileEntity profileEntity = profileRepository.getProfileByUserId(userEntity.getId());

        urlTitle = urlTitle.trim().toLowerCase();
        url = url.trim();

        if(isPlugins == null) {
            SocialEntity socialEntity = socialService.getSocialByProfileIdAndName(profileEntity.getId() , urlTitle);
            if(socialEntity == null) {
                throw new NotFoundException("Khong tim thay link ");
            }
            clickSocialService.countClickSocial(socialEntity);
        }
        else {
            PluginsEntity pluginsEntity = pluginsService.getPluginsByProfileIdAndTitle(urlTitle, profileEntity.getId());
            if(pluginsEntity == null) {
                throw new NotFoundException("Khong tim thay link ");
            }
            clickPluginsService.countClickPlugins(pluginsEntity);
        }

//        return new RedirectView(url);
        return ResponseData.builder()
                .success(true).message("CLICK Thanh cong").data(null).build();
    }
    @Override
    public List<ProfileEntity> getAll() {
        return (List<ProfileEntity>)
                profileRepository.findAll();
    }

    @Override
    public ProfileEntity save(String name, String bio, MultipartFile image) throws IOException {
        Path staticPath = Paths.get("static");
        Path imagePath = Paths.get("images");

        ProfileEntity profile = new ProfileEntity();
        profile.setId(1l);
        profile.setName(name);
        profile.setBio(bio);
        if (image != null && !image.isEmpty()) {
            Path file = CURRENT_FOLDER.resolve(staticPath)
                    .resolve(imagePath)
                    .resolve(image.getOriginalFilename());

            try (OutputStream os = Files.newOutputStream(file)) {
                os.write(image.getBytes());

            }catch (Exception e) {
            	System.out.println(e.getMessage());

            }

            profile.setImage(
                    imagePath.resolve(image.getOriginalFilename())
                            .toString());
        }
        else profile.setImage(null);

        return profileRepository.save(profile);

    }


}
