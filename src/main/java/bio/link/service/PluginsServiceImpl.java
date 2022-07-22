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


    @Override
    public List<PluginsEntity> getAllPluginsByUserId(Long userId) {
        return pluginsRepository.getAllPluginsByUserId(userId);
    }


    @Override
    public PluginsEntity getPluginsByUserIdAndTitle(String pluginsTitle ,Long userId ) {
        return pluginsRepository.getPluginsByUserIdAndTitle(userId , pluginsTitle);
    }
    @Override
    public PluginsEntity createLink(
            String title,
            String url,
            MultipartFile image,
            Boolean isHeader,
            Boolean isPlugins,
            Boolean isHide,
            Long userId) throws IOException {
        Path staticPath = Paths.get("static");
        Path imagePath = Paths.get("images");
        PluginsEntity links = new PluginsEntity();
        links.setUrl(url);
        links.setTitle(title);
        if ( image != null && !image.isEmpty()) {
            Path file = CURRENT_FOLDER.resolve(staticPath)
                    .resolve(imagePath).resolve(image.getOriginalFilename());
            try (OutputStream os = Files.newOutputStream(file)) {
                os.write(image.getBytes());
            } catch (Exception e) {
            	System.out.println(e.getMessage());

            }
            links.setImage(imagePath.resolve(image.getOriginalFilename()).toString());
        } else {
            links.setImage(null);
        }
        links.setIsHeader(false);
        links.setIsPlugin(false);
        links.setIsHide(false);
        links.setUserId(userId);
        // bước này để có được id
        pluginsRepository.save(links);

        //gán numLocation = với id vừa sinh ra
        links.setNumLocation(links.getId());

        //lưu vào db
        return pluginsRepository.save(links);
    }
    @Override
    public PluginsEntity createHeader(String title , Boolean isHeader ,  Boolean isPlugins,
                              Boolean isHide , Long userId) throws IOException {
        PluginsEntity header = new PluginsEntity();
        header.setTitle(title);
        header.setIsHeader(true);
        header.setIsPlugin(false);
        header.setIsHide(false);
        header.setUserId(userId);
        pluginsRepository.save(header);
        header.setNumLocation(header.getId());

        return pluginsRepository.save(header);
    }

    @Override
    public PluginsEntity createPlugin(
            String title,
            String url,
            MultipartFile image,
            Boolean isHeader,
            Boolean isPlugin,
            Boolean isHide,
            Long userId) throws IOException {
        Path staticPath = Paths.get("static");
        Path imagePath = Paths.get("images");
        PluginsEntity plugins = new PluginsEntity();

        plugins.setTitle(title);
        plugins.setUrl(url);
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
        plugins.setIsHeader(false);
        plugins.setIsPlugin(true);
        plugins.setIsHide(false);
        plugins.setUserId(userId);
        pluginsRepository.save(plugins);
        plugins.setNumLocation(plugins.getId());
        return pluginsRepository.save(plugins);
    }


    @Override
    public PluginsEntity updateContentPlugin( String title , String url, MultipartFile image , Long id) {
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
    public PluginsEntity updateLocationPlugin(List<PluginsEntity> newList, long userId) {
        List<PluginsEntity> oldList  = pluginsRepository.getAllPluginsByUserId(userId);
        for ( int i = 0 ; i <  oldList.size() ; i++) {
            oldList.get(i).setNumLocation(newList.get(i).getNumLocation());
        }
        for (int i = 0 ;  i < oldList.size() ; i++) {
            pluginsRepository.save(oldList.get(i));
        }
        return null;
    }

    @Override
    public void deletePluginsById(Long id) {
        pluginsRepository.deleteById(id);
    }

}
