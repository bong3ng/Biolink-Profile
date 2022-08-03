package bio.link.service;


//import java.io.IOException;
//import java.io.OutputStream;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.List;
//
//import java.util.Objects;


import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import bio.link.model.entity.PluginsEntity;
import bio.link.repository.PluginsRepository;

@Service
public class PluginsServiceImpl implements PluginsService{


    @Autowired
    private PluginsRepository pluginsRepository;


    @Autowired
    private Cloudinary cloudinary;


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
            String pluginName,
            Long userId) throws IOException {
        PluginsEntity links = new PluginsEntity();
        links.setUrl(url);
        links.setTitle(title);
        if ( image != null && !image.isEmpty()) {
            try {
                Map map = this.cloudinary.uploader().upload(image.getBytes(), ObjectUtils.asMap("resource_type", "auto"));
                links.setImage(map.get("url").toString());
            } catch (Exception e) {
                System.out.println(e);
            }
//            links.setImage(image.getOriginalFilename().toString());

        } else {
            links.setImage(null);
        }
        links.setIsHeader(false);
        links.setIsPlugin(false);
        links.setIsHide(false);
        links.setPluginName(null);
        links.setUserId(userId);
        // bước này để có được id
        pluginsRepository.save(links);

        //gán numLocation = với id vừa sinh ra
        links.setNumLocation(links.getId());

        //lưu vào db
        return pluginsRepository.save(links);
    }
    @Override
    public PluginsEntity createHeader(
            String title ,
            Boolean isHeader ,
            Boolean isPlugins,
            Boolean isHide ,
            String pluginName,
            Long userId) throws IOException {
        PluginsEntity header = new PluginsEntity();
        header.setTitle(title);
        header.setIsHeader(true);
        header.setIsPlugin(false);
        header.setIsHide(false);
        header.setPluginName(null);
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
            String pluginName,
            Long userId) throws IOException {
        PluginsEntity plugins = new PluginsEntity();

        plugins.setTitle(title);
        plugins.setUrl(url);
        if ( image != null && !image.isEmpty()) {
            try {
                Map map = this.cloudinary.uploader().upload(image.getBytes(), ObjectUtils.asMap("resource_type", "auto"));
                plugins.setImage(map.get("url").toString());
            } catch (Exception e) {
                System.out.println(e);
            }
        } else {
            plugins.setImage(null);
        }
        plugins.setIsHeader(false);
        plugins.setIsPlugin(true);
        plugins.setIsHide(false);
        plugins.setPluginName(pluginName);
        plugins.setUserId(userId);
        pluginsRepository.save(plugins);
        plugins.setNumLocation(plugins.getId());
        return pluginsRepository.save(plugins);
    }


    @Override
    public PluginsEntity updateContentPlugin( String title , String url, MultipartFile image , Boolean isHide, Long id) {
        PluginsEntity pluginsUp = pluginsRepository.findById(id).get();
        if (Objects.nonNull(title) && !"".equalsIgnoreCase(title)) {
            pluginsUp.setTitle(title);
        }
        if (Objects.nonNull(url) && !"".equalsIgnoreCase(url)) {
            pluginsUp.setUrl(url);
        }
        if ( image != null && !image.isEmpty()) {
            try {
                Map map = this.cloudinary.uploader().upload(image.getBytes(), ObjectUtils.asMap("resource_type", "auto"));
                pluginsUp.setImage(map.get("url").toString());
            } catch (Exception e) {
                System.out.println(e);
            }
        }
            pluginsUp.setIsHide(isHide);
        return pluginsRepository.save(pluginsUp);
    }

    @Override
    public PluginsEntity updateLocationPlugin(List<PluginsEntity> newList, long userId) {
        List<PluginsEntity> oldList  = pluginsRepository.getAllPluginsByUserId(userId);
        for ( int i = 0 ; i <  oldList.toArray().length ; i++) {
            newList.get(i).setNumLocation(oldList.get(i).getNumLocation());

            pluginsRepository.save(newList.get(i));
        }
        
        return null;
    }

    @Override
    public void deletePluginsById(Long id) {
        pluginsRepository.deleteById(id);
    }

}
