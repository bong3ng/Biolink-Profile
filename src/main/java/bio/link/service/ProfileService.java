package bio.link.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import bio.link.model.entity.ProfileEntity;
import bio.link.model.response.ResponseData;

public interface ProfileService {

//    ResponseData getUserProfileByProfileName(String profileName);

	ResponseData getUserProfileByUsername(String username);

	ProfileEntity create(String name,
						 String bio,
						 MultipartFile image
	) throws IOException;

	ProfileEntity update(String name,
						 String bio,
						 MultipartFile image
	) throws IOException;

	List<ProfileEntity> getAll();

	ProfileEntity updateDesign(Long design_id);
	ProfileEntity updateLogo(Boolean show_logo);
	ProfileEntity updateNSFW(Boolean show_nsfw);

}
