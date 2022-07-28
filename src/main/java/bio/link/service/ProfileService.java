package bio.link.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import bio.link.model.dto.ProfileDto;
import bio.link.model.entity.ProfileEntity;
import bio.link.model.entity.UserEntity;
import bio.link.model.response.ResponseData;
import bio.link.security.payload.Status;

public interface  ProfileService {

//    ResponseData getUserProfileByProfileName(String profileName);

    UserEntity getUserByUsername(String username);

    ResponseData getUserProfileByUsername(String username);

	Status create(
			String name,
			String bio,

			Long userId
	) throws IOException;

	ProfileEntity update(String name,
						 String bio,
						 MultipartFile image, Long userId
	) throws IOException;

	ProfileEntity getProfileByUserId(Long userId);

	ProfileEntity updateDesign(Long userId, Long designId);
	ProfileEntity updateSetting(Long userId, Boolean showLogo, Boolean showNsfw);

	String uploadImage(MultipartFile file, String containerName);

	Long convertJwt(String jwt);
	
	List<UserEntity> getUserByAdmin();
	
	UserEntity updateUserByAdmin(UserEntity user);
	
	Status deleteUserByAdmin(Long id);
	
	ProfileDto getUserProfileByJWT(String jwt);
	
	Status updateShow(Boolean showLogo, Boolean showNSFW, String jwt);
	


}
