package bio.link.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import bio.link.model.entity.PluginsEntity;

public interface PluginsService {


	List<PluginsEntity> getAllPluginsByUserId(Long userId);

	PluginsEntity getPluginsByUserIdAndTitle(String pluginsTitle, Long userId);

	PluginsEntity savePlugins(
	        String title,
	        String url,
	        MultipartFile image,
	        Boolean isHeader,
	        Boolean isPlugins,
	        Boolean isHide,
	        Long userId) throws IOException;
	PluginsEntity saveHeader(String title , Boolean isHeader ,  Boolean isPlugins,
	                   Boolean isHide);
	List<PluginsEntity> getAllPluginsByUserId(long userId);


	//update content của plugins.
	PluginsEntity updateContentPlugins( String title, String url, MultipartFile image , Long id);


	//update location ( thay đổi vị trí links , header , plugins )
	PluginsEntity updateLocationPlugins(List<PluginsEntity> list , long userId);


	void deletePluginsById(Long id);
}
