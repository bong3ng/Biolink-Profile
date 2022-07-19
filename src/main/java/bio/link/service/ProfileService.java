package bio.link.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import bio.link.model.entity.ProfileEntity;
import bio.link.model.response.ResponseData;

public interface  ProfileService {

//    ResponseData getUserProfileByProfileName(String profileName);

	ResponseData getUserProfileByUsername(String username);

	ProfileEntity create(
			String name,
			String bio,
			MultipartFile image,
			Long userId
	) throws IOException;

	ProfileEntity update(String name,
						 String bio,
						 MultipartFile image, Long userId
	) throws IOException;

	ProfileEntity getProfileByUserId(Long userId);

	ProfileEntity updateDesign(Long userId, Long designId);
	ProfileEntity updateSetting(Long userId, Boolean showLogo, Boolean showNsfw);

	Long convertJwt(String jwt);

}
