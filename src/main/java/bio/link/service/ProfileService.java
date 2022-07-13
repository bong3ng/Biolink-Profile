package bio.link.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import bio.link.model.entity.ProfileEntity;
import bio.link.model.response.ResponseData;

public interface ProfileService {

//    ResponseData getUserProfileByProfileName(String profileName);

	ResponseData getUserProfileByUsername(String username);

	ProfileEntity save(String name, String bio, MultipartFile image) throws IOException;

	List<ProfileEntity> getAll();
}
