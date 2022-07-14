package bio.link.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import bio.link.model.entity.PluginsEntity;

public interface PluginsService {
	PluginsEntity savePlugins(
	        String title,
	        String url,
	        MultipartFile image,
	        Boolean is_header,
	        Boolean is_plugins,
	        Boolean is_hide,
	        Long profile_id) throws IOException;
	PluginsEntity saveHeader(String title , Boolean is_header ,  Boolean is_plugins,
	                   Boolean is_hide);
	List<PluginsEntity> getAllPlugins();
	PluginsEntity updatePlugins( String title, String url, MultipartFile image , Long id);
	void deletePluginsById(Long id);
}
