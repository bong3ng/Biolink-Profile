package bio.link.service;


import static bio.link.controller.NameController.CURRENT_FOLDER;

//import java.io.IOException;
//import java.io.OutputStream;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.List;
//
//import java.util.Objects;


import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import bio.link.model.entity.PluginsEntity;
import bio.link.repository.PluginsRepository;

@Service
public class PluginsServiceImpl implements PluginsService{

    @Autowired
    private PluginsRepository pluginsRepository;


    public List<PluginsEntity> getPluginsByProfileId(Long profileId) {
        return pluginsRepository.getPluginsByProfileId(profileId);
    }

    public PluginsEntity getPluginsByProfileIdAndTitle(String pluginsTitle ,Long profileId ) {
        return pluginsRepository.getPluginsByProfileIdAndTitle(profileId , pluginsTitle);
    }
    @Override
    public PluginsEntity savePlugins(
            String title,
            String url,
            MultipartFile image,
            Boolean is_header,
            Boolean is_plugins,
            Boolean is_hide,
            Long profile_id) throws IOException {
        Path staticPath = Paths.get("static");
        Path imagePath = Paths.get("images");
        PluginsEntity plugins = new PluginsEntity();
        plugins.setUrl(url);
        plugins.setTitle(title);
        if ( image != null && !image.isEmpty()) {
            Path file = CURRENT_FOLDER.resolve(staticPath)
                    .resolve(imagePath).resolve(image.getOriginalFilename());
            try (OutputStream os = Files.newOutputStream(file)) {
                os.write(image.getBytes());
            } catch (Exception e) {
            	System.out.println(e.getMessage());
            }
            plugins.setImage(imagePath.resolve(image.getOriginalFilename()).toString());
        } else {
            plugins.setImage(null);
        }
        plugins.setIsHeader(is_header);
        plugins.setIsPlugin(is_plugins);
        plugins.setIsHide(is_hide);
        return pluginsRepository.save(plugins);
    }
    @Override
    public PluginsEntity saveHeader(String title , Boolean is_header ,  Boolean is_plugins,
                              Boolean is_hide) {
        PluginsEntity title_header = new PluginsEntity();
       return pluginsRepository.save(title_header);
    }
    @Override
    public List<PluginsEntity> getAllPlugins() {
       return pluginsRepository.findAll();
    }
    @Override
    public PluginsEntity updatePlugins( String title , String url, MultipartFile image , Long id) {
        PluginsEntity pluginsUp = pluginsRepository.findById(id).get();
        Path staticPath = Paths.get("static");
        Path imagePath = Paths.get("images");
        if (Objects.nonNull(title) && !"".equalsIgnoreCase(title)) {
            pluginsUp.setTitle(title);
        }
        if (Objects.nonNull(url) && !"".equalsIgnoreCase(url)) {
            pluginsUp.setUrl(url);
        }
        if ( image != null && !image.isEmpty()) {
            Path file = CURRENT_FOLDER.resolve(staticPath)
                    .resolve(imagePath).resolve(image.getOriginalFilename());
            try (OutputStream os = Files.newOutputStream(file)) {
                os.write(image.getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            pluginsUp.setImage(imagePath.resolve(image.getOriginalFilename()).toString());
        }
        return pluginsRepository.save(pluginsUp);
    }
    @Override
    public void deletePluginsById(Long id) {
        pluginsRepository.deleteById(id);
    }

}
