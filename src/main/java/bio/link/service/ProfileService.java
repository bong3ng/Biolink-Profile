package bio.link.service;

import java.io.IOException;
import java.util.List;

import bio.link.model.dto.AllProfileDto;
import org.springframework.web.multipart.MultipartFile;

import bio.link.model.dto.ProfileDto;
import bio.link.model.entity.PluginsEntity;
import bio.link.model.entity.ProfileEntity;
import bio.link.model.entity.SocialEntity;
import bio.link.model.entity.UserEntity;
import bio.link.model.response.ResponseData;
import bio.link.security.payload.Status;

public interface  ProfileService {

//    ResponseData getUserProfileByProfileName(String profileName);

    UserEntity getUserByUsername(String username);


    ResponseData getUserProfileByUsername(String username , Boolean checkGuest);

	Status createFirstLogin(
			String name,
			String bio,

			Long userId
	) throws IOException;

	ProfileEntity update(String name,
						 String bio,
						 MultipartFile image, Long userId
	) throws IOException;

	ProfileEntity getProfileByUserId(Long userId);


	String getImageByProfileId(Long profileId);


	ProfileEntity updateDesign(Long userId, Long designId);
	ProfileEntity updateSetting(Long userId, Boolean showLogo, Boolean showNSFW);

	String uploadImage(MultipartFile file, String containerName);

	void deleteImage(String url, String containerName);
	Long convertJwt(String jwt);

	ResponseData clickSocialOfProfile(SocialEntity socialEntity);

	ResponseData clickPluginsOfProfile(PluginsEntity pluginsEntity);

	List<ProfileEntity> getAllProfileUser();

	List<UserEntity> getUserByAdmin();

	UserEntity updateUserByAdmin(UserEntity user);
	
	Status deleteUserByAdmin(Long id);
	
	ProfileDto getUserProfileByJWT(String jwt);
	

	
	List<AllProfileDto> getAllProfile(String jwt);

}
