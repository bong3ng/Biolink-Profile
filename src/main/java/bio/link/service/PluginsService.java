package bio.link.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import bio.link.model.entity.PluginsEntity;

public interface PluginsService {


	List<PluginsEntity> getAllPluginsByUserId( Long userId);

	PluginsEntity getPluginsByUserIdAndTitle(String pluginsTitle, Long userId);

	PluginsEntity createLink(
	        String title,
	        String url,
	        MultipartFile image,
	        Boolean isHeader,
	        Boolean isPlugin,
	        Boolean isHide,
			String pluginName,
	        Long userId) throws IOException;


	PluginsEntity createHeader(
			String title ,
			Boolean isHeader ,
			Boolean isPlugin,
			Boolean isHide ,
			String pluginName,
			Long userid) throws IOException;


	PluginsEntity createPlugin (
			String title,
			String url,
			MultipartFile image,
			Boolean isHeader,
			Boolean isPlugin,
			Boolean isHide,
			String pluginName,
			Long userId) throws IOException;


	//update content của plugins.
	PluginsEntity updateContentPlugin( String title, String url, MultipartFile image , Boolean isHide, Long id);


	//update location ( thay đổi vị trí links , header , plugins )

	List<PluginsEntity> updateLocationPlugin(List<PluginsEntity> list , Long userId);



	void deletePluginsById(Long id);
}
