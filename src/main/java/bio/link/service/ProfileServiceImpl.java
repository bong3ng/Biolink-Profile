package bio.link.service;



import static bio.link.controller.NameController.CURRENT_FOLDER;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

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
import bio.link.repository.UserRepository;
import bio.link.security.jwt.JwtTokenProvider;
import bio.link.security.payload.Status;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ProfileServiceImpl implements ProfileService {
    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

//    @Autowired
//    private UserServiceImpl userService;
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
    
    @Autowired
    private UserRepository userRepo;


    private static HashMap<Long , Long> countClickProfileMap = new HashMap<>();

    private static LocalDate previousDate;
    private static LocalDate currentDate;
    @Override
    public ResponseData getUserProfileByUsername(String username) {
    	//Conflict
//        UserEntity userEntity = userService.getUserByUsername(username);
    	UserEntity userEntity = userRepo.findByUsername(username);
        Long userId = userEntity.getId();

        ProfileEntity profileEntity = profileRepository.getProfileByUserId(userId);
        Long profileId = profileEntity.getId();

        // khi sang ngày mới, reset HashMap
        currentDate = LocalDate.now();
        if(currentDate.minusDays(1).equals(previousDate)) {
            countClickProfileMap.clear();
        }
        previousDate = currentDate;

        Long clickCount = countClickProfileMap.get(profileId);
        if(clickCount == null) {
            ClickProfileEntity clickProfileEntity = clickProfileRepository.getClickCountByDate(currentDate, profileId);
            if(clickProfileEntity == null) {
                countClickProfileMap.put(profileId , 1L);
                clickProfileEntity = new ClickProfileEntity(null , 1L,currentDate , profileId);
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
            clickProfileRepository.updateProfileClickCount(currentDate , profileId,clickCount);
        }


//
//        ClickProfileEntity clickProfileEntity = clickProfileRepository.getClickCountByDate(presentDate , profileId);
//        if(clickProfileEntity == null) {
//            ClickProfileEntity newClick = new ClickProfileEntity(null , 1 , presentDate , profileId);
//            clickProfileRepository.save(newClick);
//        }
//        else {
//            clickProfileEntity.setClickCount(clickProfileEntity.getClickCount() + 1);
//            clickProfileRepository.save(clickProfileEntity);
//        }

        List<SocialEntity> listSocial = socialService.getAllSocialsByUserId(userId);
        List<SocialDto> listSocialDto = listSocial.stream().map(s -> modelMapper.map(s,SocialDto.class)).collect(Collectors.toList());

        List<PluginsEntity> listPlugins = pluginsService.getAllPluginsByUserId(userId);
        List<PluginsDto> listPluginsDto = listPlugins.stream().map(p -> modelMapper.map(p , PluginsDto.class)).collect(Collectors.toList());

        ProfileDto profileDto = new ProfileDto(username , profileEntity.getName(),profileEntity.getBio(),profileEntity.getImage(), listSocialDto , listPluginsDto );
        ArrayList<ProfileDto> list = new ArrayList<>();
        list.add(profileDto);

        return ResponseData.builder()
                .success(true)
                .message("Thành công")
                .data(list)
                .build();
    }


    public ResponseData clickUrlOfUsername(String username , String urlTitle ,  String url , Boolean isPlugins) {
    	
    	//Conflict
//        UserEntity userEntity = userService.getUserByUsername(username);
    	
    	UserEntity userEntity = userRepo.findByUsername(username);
        Long userId = userEntity.getId();

        urlTitle = urlTitle.trim().toLowerCase();
        url = url.trim();

        if(isPlugins == null) {
            SocialEntity socialEntity = socialService.getSocialByUserIdAndName(userId , urlTitle);
            if(socialEntity == null) {
                throw new NotFoundException("Khong tim thay link ");
            }
            clickSocialService.countClickSocial(socialEntity);
        }
        else {
            PluginsEntity pluginsEntity = pluginsService.getPluginsByUserIdAndTitle(urlTitle, userId);
            if(pluginsEntity == null) {
                throw new NotFoundException("Không tìm thấy link ");
            }
            clickPluginsService.countClickPlugins(pluginsEntity);
        }
        return ResponseData.builder()
                .success(true).message("CLICK Thành công").data(null).build();
    }

    @Override
    public ProfileEntity getProfileByUserId(Long userId) {
        return profileRepository.getProfileByUserId(userId);
    }

    @Override
    public ProfileEntity create(String name, String bio, MultipartFile image, Long userId) throws IOException {
        Path staticPath = Paths.get("static");
        Path imagePath = Paths.get("images");

        ProfileEntity profile = profileRepository.getProfileByUserId(userId);
//        profile.setId(userId);
        profile.setName(name);
        profile.setBio(bio);
        if (image != null && !image.isEmpty()) {
            Path file = CURRENT_FOLDER.resolve(staticPath)
                    .resolve(imagePath)
                    .resolve(image.getOriginalFilename());

            try (OutputStream os = Files.newOutputStream(file)) {
                os.write(image.getBytes());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            profile.setImage(
                    imagePath.resolve(image.getOriginalFilename())
                            .toString());
        }
        else profile.setImage(null);

        profile.setActiveDesign(1L);
        profile.setShowLogo(true);
        profile.setShowNSFW(true);
        profile.setUserId(userId);
        return profileRepository.save(profile);
    }

    @Override
    public ProfileEntity update(String name, String bio, MultipartFile image, Long userId) throws IOException {
        Path staticPath = Paths.get("static");
        Path imagePath = Paths.get("images");

        ProfileEntity profile = new ProfileEntity();
        profile.setId(1L);
        profile.setName(name);
        profile.setBio(bio);
        if (image != null && !image.isEmpty()) {
            Path file = CURRENT_FOLDER.resolve(staticPath)
                    .resolve(imagePath)
                    .resolve(image.getOriginalFilename());

            try (OutputStream os = Files.newOutputStream(file)) {
                os.write(image.getBytes());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            profile.setImage(
                    imagePath.resolve(image.getOriginalFilename())
                            .toString());
        }
        else profile.setImage(null);
//
//        profile.setActiveDesign(profile.getActiveDesign());
//        profile.setShowLogo(profile.getShowLogo());
//        profile.setShowNSFW(profile.getShowNSFW());
        return profileRepository.save(profile);
    }

    @Override
    public ProfileEntity updateDesign(Long userId, Long designId) {
        ProfileEntity profile = profileRepository.getProfileByUserId(userId);
        profile.setActiveDesign(designId);
        return profileRepository.save(profile);
    }

    @Override
    public ProfileEntity updateSetting(Long userId, Boolean showLogo, Boolean showNsfw) {
        ProfileEntity profile = profileRepository.getProfileByUserId(userId);
        profile.setShowLogo(showLogo);
        profile.setShowNSFW(showNsfw);
        return profileRepository.save(profile);
    }

    @Override
    public Long convertJwt(String jwt) {
        return jwtTokenProvider.getUserIdFromHeader(jwt);
    }
    @Override
    public List<UserEntity> getUserByAdmin(){
    	return userRepo.findAll();
    }
    
    @Override
    public UserEntity updateUserByAdmin(UserEntity user) {
    	return userRepo.save(user);
    	
    }
    @Override
    public Status deleteUserByAdmin(Long id) {
    	Optional<UserEntity> findUser = userRepo.findById(id);
    	if (findUser.isPresent()) {
    		UserEntity userDelete = findUser.get();
    		userDelete.setStatus(false);
    		userRepo.save(userDelete);
    		return new Status(1, "Đã xóa thành công user: " + userDelete.getUsername());
    	}return new Status(0, "Xóa thất bại, không tìm thấy user");
    	
    }
    
}
