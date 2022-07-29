package bio.link.service;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.models.BlobHttpHeaders;
import com.azure.storage.blob.specialized.BlockBlobClient;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import bio.link.model.dto.ProfileDto;
import bio.link.model.entity.ClickProfileEntity;
import bio.link.model.entity.DesignEntity;
import bio.link.model.entity.PluginsEntity;
import bio.link.model.entity.ProfileEntity;
import bio.link.model.entity.SocialEntity;
import bio.link.model.entity.UserEntity;
import bio.link.model.exception.NotFoundException;
import bio.link.model.response.ResponseData;
import bio.link.repository.ClickProfileRepository;
import bio.link.repository.DesignRepository;
import bio.link.repository.PluginsRepository;
import bio.link.repository.ProfileRepository;
import bio.link.repository.UserRepository;
import bio.link.security.jwt.JwtTokenProvider;
import bio.link.security.payload.Status;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@Transactional
public class ProfileServiceImpl implements ProfileService {
	@Autowired
	private ProfileRepository profileRepository;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Autowired
	private BlobServiceClient blobServiceClient;

    @Autowired
    private UserRepository userRepository;

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
	private DesignRepository designRepository;

	@Autowired
	private PluginsRepository pluginsRepository;

    private static HashMap<Long , Long> countClickProfileMap = new HashMap<>();

    private static LocalDate previousDate;
    private static LocalDate currentDate;
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
    public ResponseData getUserProfileByUsername(String username) {
        UserEntity userEntity = this.getUserByUsername(username);
        Long userId = userEntity.getId();

		ProfileEntity profileEntity = profileRepository.getProfileByUserId(userId);
		Long profileId = profileEntity.getId();

		// khi sang ngày mới, reset HashMap
		currentDate = LocalDate.now();
		if (currentDate.minusDays(1).equals(previousDate)) {
			countClickProfileMap.clear();
		}
		previousDate = currentDate;

		Long clickCount = countClickProfileMap.get(profileId);
		if (clickCount == null) {
			ClickProfileEntity clickProfileEntity = clickProfileRepository.getClickCountByDate(currentDate, profileId);
			if (clickProfileEntity == null) {
				countClickProfileMap.put(profileId, 1L);
				clickProfileEntity = new ClickProfileEntity(null, 1L, currentDate, profileId);
			} else {
				clickCount = clickProfileEntity.getClickCount() + 1;
				countClickProfileMap.put(profileId, clickCount);
				clickProfileEntity.setClickCount(clickCount);
			}
			clickProfileRepository.save(clickProfileEntity);
		} else {
			clickCount += 1;
			countClickProfileMap.put(profileId, clickCount);
			clickProfileRepository.updateProfileClickCount(currentDate, profileId, clickCount);
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
//		List<SocialDto> listSocialDto = listSocial.stream().map(s -> modelMapper.map(s, SocialDto.class))
//				.collect(Collectors.toList());


		List<PluginsEntity> listPlugins = pluginsService.getAllPluginsByUserId(userId);
//		List<PluginsDto> listPluginsDto = listPlugins.stream().map(p -> modelMapper.map(p, PluginsDto.class))
//				.collect(Collectors.toList());



      
        DesignEntity designEntity = designRepository.findDesignEntityById(profileEntity.getActiveDesign());
//        DesignDto designDto = modelMapper.map(designEntity, DesignDto.class);

        ProfileDto profileDto = new ProfileDto( username ,
                                                profileEntity.getName(),
                                                profileEntity.getBio(),
                                                profileEntity.getImage(),
                                                profileEntity.getShowLogo(),
                                                profileEntity.getShowNSFW(),
                                                profileEntity.getActiveDesign(),
                                                listSocial ,
                                                listPlugins,
                                                designEntity);
        ArrayList<ProfileDto> list = new ArrayList<>();
        list.add(profileDto);


		return ResponseData.builder().success(true).message("Thành công").data(list).build();
	}


    public ResponseData clickUrlOfUsername(String username , String urlTitle ,  String url , Boolean isPlugins) {

        UserEntity userEntity = this.getUserByUsername(username);
        Long userId = userEntity.getId();

		urlTitle = urlTitle.trim().toLowerCase();
		url = url.trim();

		if (isPlugins == null) {
			SocialEntity socialEntity = socialService.getSocialByUserIdAndName(userId, urlTitle);
			if (socialEntity == null) {
				throw new NotFoundException("Khong tim thay link ");
			}
			clickSocialService.countClickSocial(socialEntity);
		} else {
			PluginsEntity pluginsEntity = pluginsService.getPluginsByUserIdAndTitle(urlTitle, userId);
			if (pluginsEntity == null) {
				throw new NotFoundException("Không tìm thấy link ");
			}
			clickPluginsService.countClickPlugins(pluginsEntity);
		}
		return ResponseData.builder().success(true).message("CLICK Thành công").data(null).build();
	}

	@Override
	public ProfileEntity getProfileByUserId(Long userId) {
		return profileRepository.getProfileByUserId(userId);
	}


	@Override
	public Status create(String name, String bio, Long userId) throws IOException {

		ProfileEntity profile = profileRepository.findByUserId(userId);

		profile.setName(name);
		profile.setBio(bio);

		profileRepository.save(profile);
		return new Status(1, "Cập nhật thông tin thành công");
	}

	@Override
	public String uploadImage(MultipartFile file, String containerName) {
		BlobContainerClient blobContainerClient = blobServiceClient.getBlobContainerClient(containerName);
		String filename = file.getOriginalFilename();
		String filePath = "";
		BlockBlobClient blockBlobClient = blobContainerClient.getBlobClient(filename).getBlockBlobClient();

		try {
			if (blockBlobClient.exists()) {
				blockBlobClient.delete();
			}

			blockBlobClient.upload(new BufferedInputStream(file.getInputStream()), file.getSize(), true);

			assert filename != null;
			if (filename.endsWith(".jpg")) {
				blockBlobClient.setHttpHeaders(new BlobHttpHeaders().setContentType("image/jpeg"));
			} else if (filename.endsWith(".png")) {
				blockBlobClient.setHttpHeaders(new BlobHttpHeaders().setContentType("image/png"));
			}

			filePath = containerName + "/" + filename;
			Files.deleteIfExists(Paths.get(filePath));
		} catch (IOException e) {
			log.error(e.getLocalizedMessage());
		}
		return "https://anhtcogn.blob.core.windows.net/" + filePath;
	}


	@Override
	public ProfileEntity update(String name, String bio, MultipartFile image, Long userId) throws IOException {

		ProfileEntity profile = profileRepository.getProfileByUserId(userId);
		profile.setName(name);
		profile.setBio(bio);

		if (image != null && !image.isEmpty()) {
			profile.setImage(uploadImage(image, "files"));
		}
		else profile.setImage(profile.getImage());
		return profileRepository.save(profile);
	}

	@Override
	public ProfileEntity updateDesign(Long userId, Long designId) {
		ProfileEntity profile = profileRepository.getProfileByUserId(userId);
		profile.setActiveDesign(designId);
		return profileRepository.save(profile);
	}

	@Override
	public ProfileEntity updateSetting(Long userId, Boolean showLogo, Boolean showNSFW) {
		ProfileEntity profile = profileRepository.getProfileByUserId(userId);
		profile.setShowLogo(showLogo);
		profile.setShowNSFW(showNSFW);
		return profileRepository.save(profile);
	}

	@Override
	public Long convertJwt(String jwt) {
		return jwtTokenProvider.getUserIdFromHeader(jwt);
	}

	@Override
	public List<UserEntity> getUserByAdmin() {
		return userRepository.findAll();
	}

	@Override
	public UserEntity updateUserByAdmin(UserEntity user) {
		return userRepository.save(user);

	}

	@Override
	public Status deleteUserByAdmin(Long id) {
		Optional<UserEntity> findUser = userRepository.findById(id);
		if (findUser.isPresent()) {
			UserEntity userDelete = findUser.get();
			userDelete.setStatus(false);
			userRepository.save(userDelete);
			return new Status(1, "Đã xóa thành công user: " + userDelete.getUsername());
		}
		return new Status(0, "Xóa thất bại, không tìm thấy user");

	}
	@Override
	
	public ProfileDto getUserProfileByJWT(String jwt) {
		Long userId = convertJwt(jwt);
		ProfileEntity profileEntity = profileRepository.findByUserId(userId);
		List<SocialEntity> listSocial = socialService.getAllSocialsByUserId(userId);

		List<PluginsEntity> listPlugins = pluginsService.getAllPluginsByUserId(userId);

      
        DesignEntity designEntity = designRepository.findDesignEntityById(profileEntity.getActiveDesign());
        ProfileDto profileDto = new ProfileDto( null ,
                profileEntity.getName(),
                profileEntity.getBio(),
                profileEntity.getImage(),
                profileEntity.getShowLogo(),
                profileEntity.getShowNSFW(),
                profileEntity.getActiveDesign(),
                listSocial ,
                listPlugins,
                designEntity);
        return profileDto;
		
	}
	
	
}
