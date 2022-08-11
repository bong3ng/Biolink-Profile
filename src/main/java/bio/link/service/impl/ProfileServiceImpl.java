package bio.link.service.impl;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.*;


import javax.transaction.Transactional;

import bio.link.service.ProfileService;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.models.BlobHttpHeaders;
import com.azure.storage.blob.specialized.BlockBlobClient;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import bio.link.model.dto.ProfileDto;
import bio.link.model.entity.DesignEntity;
import bio.link.model.entity.PluginsEntity;
import bio.link.model.entity.ProfileEntity;
import bio.link.model.entity.SocialEntity;
import bio.link.model.entity.UserEntity;
import bio.link.model.exception.NotFoundException;
import bio.link.model.response.ResponseData;
import bio.link.repository.ClickPluginsRepository;
import bio.link.repository.ClickProfileRepository;
import bio.link.repository.ClickSocialRepository;
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
	private ClickPluginsRepository clickPluginsRepository;
	@Autowired
	private ClickProfileRepository clickProfileRepository;

	@Autowired
	private ClickSocialRepository clickSocialRepository;
	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Autowired
	private BlobServiceClient blobServiceClient;

    @Autowired
    private UserRepository userRepository;


	@Autowired
	private SocialServiceImpl socialService;

	@Autowired
	private PluginsServiceImpl pluginsService;

	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private DesignRepository designRepository;

	@Autowired
	private PluginsRepository pluginsRepository;


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
    public ResponseData getUserProfileByUsername(String username , Boolean checkGuest) {
        UserEntity userEntity = this.getUserByUsername(username);
        Long userId = userEntity.getId();
		ProfileEntity profileEntity = profileRepository.getProfileByUserId(userId);

		if(checkGuest) {
			ClickCountServiceImpl clickService = new ClickCountServiceImpl(profileEntity , clickProfileRepository);
			Thread t = new Thread(clickService);
			t.start();
		}

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


		return ResponseData.builder().success(true).message("Thành công").data(Arrays.asList(profileDto)).build();

	}

	@Override
	public ResponseData clickSocialOfProfile(SocialEntity socialEntity) {
		if(clickSocialRepository.findById(socialEntity.getId()) == null) {
			throw new NotFoundException("Không tìm thấy link");
		}

		ClickCountServiceImpl clickService = new ClickCountServiceImpl(socialEntity , clickSocialRepository);
		Thread t = new Thread(clickService);
		t.start();

		return ResponseData.builder()
							.success(true)
							.message("CLICK Thành công")
							.data(Arrays.asList(socialEntity.getUrl()))
							.build();
	}

	@Override
	public ResponseData clickPluginsOfProfile(PluginsEntity pluginsEntity) {
		if(clickProfileRepository.findById(pluginsEntity.getId()) == null) {
			throw new NotFoundException("Không tìm thấy link");
		}

		ClickCountServiceImpl clickService = new ClickCountServiceImpl(pluginsEntity , clickPluginsRepository);
		Thread t = new Thread(clickService);
		t.start();

		return ResponseData.builder()
							.success(true)
							.message("CLICK Thành công")
							.data(Arrays.asList(pluginsEntity.getUrl()))
							.build();
	}

	@Override
	public List<ProfileEntity> getAllProfileUser() {
		return profileRepository.findAll();
	}

	@Override
	public ProfileEntity getProfileByUserId(Long userId) {
		return profileRepository.getProfileByUserId(userId);
	}

	@Override
	public String getImageByProfileId(Long profileId) {
		return profileRepository.getImageByUserId(profileId);
	}


	@Override
	public Status createFirstLogin(String name, String bio, Long userId) throws IOException {
		
		ProfileEntity profile = new ProfileEntity();
		
		profile.setUserId(userId);
		profile.setName(name);
		profile.setBio(bio);
		profile.setActiveDesign(1l);

		profileRepository.save(profile);
		return new Status(true, "Cập nhật thông tin thành công");
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
			return new Status(true, "Đã xóa thành công user: " + userDelete.getUsername());
		}
		return new Status(false, "Xóa thất bại, không tìm thấy user");
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
