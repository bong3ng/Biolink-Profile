package bio.link.controller;


import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import bio.link.repository.PluginsRepository;
import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import bio.link.model.entity.PluginsEntity;
import bio.link.security.jwt.JwtTokenProvider;
import bio.link.service.PluginsService;

@RestController
@RequestMapping("/api/user")
@CrossOrigin("*")
public class PluginsController {

    public static final Path CURRENT_FOLDER = Paths.get(System.getProperty("user.dir"));

    @Autowired
    private PluginsService pluginsService;


    @Autowired
    private PluginsRepository pluginsRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;


    @Autowired
    private Cloudinary cloudinary;



    //tạo mới links
    @PostMapping("/createLinks")
    @ResponseStatus(HttpStatus.CREATED)
    public PluginsEntity createLink(@RequestParam String title,
                          @RequestParam String url,
                          @RequestParam(required = false) MultipartFile image,
                          @RequestParam(required = false) Boolean isHeader ,
                          @RequestParam(required = false) Boolean isPlugin,
                          @RequestParam(required = false) Boolean isHide,
                          @RequestParam(required = false) String pluginName,
                          @RequestHeader("Authorization") String jwt
    ) throws IOException {

            return pluginsService.createLink(title , url , image , isHeader , isPlugin , isHide, pluginName, jwtTokenProvider.getUserIdFromHeader(jwt));
    }


    //tạo mới header
    @PostMapping("/createHeader")
    @ResponseStatus(HttpStatus.CREATED)
    public PluginsEntity createHeader(
            @RequestParam String title,
            @RequestParam(required = false) Boolean isHeader,
            @RequestParam(required = false) Boolean isPlugin,
            @RequestParam(required = false) Boolean isHide,
            @RequestParam(required = false) String pluginName,
            @RequestHeader("Authorization") String jwt
    ) throws IOException {
        return pluginsService.createHeader(title, isHeader , isPlugin , isHide , pluginName, jwtTokenProvider.getUserIdFromHeader(jwt));
    }


    //tạo mới plugins
    @PostMapping("/createPlugins")
    @ResponseStatus(HttpStatus.CREATED)
    public PluginsEntity createPlugin(
            @RequestParam String title,
            @RequestParam String url,
            @RequestParam(required = false) MultipartFile image,
            @RequestParam(required = false) Boolean isHeader ,
            @RequestParam(required = false) Boolean isPlugin,
            @RequestParam(required = false) Boolean isHide,
            @RequestParam String pluginName,
            @RequestHeader("Authorization") String jwt
    ) throws IOException {
        return pluginsService.createPlugin(title , url , image , isHeader , isPlugin , isHide, pluginName, jwtTokenProvider.getUserIdFromHeader(jwt));
    }



    @GetMapping("/plugins")
    public List<PluginsEntity> getAllPluginsByUserId(

            @RequestHeader("Authorization") String jwt) {

        return pluginsRepository.getAllPluginsByUserId(jwtTokenProvider.getUserIdFromHeader(jwt));
    }


    @PutMapping("/updateContent/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public PluginsEntity updateContentPlugins(
            @RequestParam String title,
            @RequestParam String url,
            @RequestParam(value = "image", required=false) MultipartFile  image,
            @RequestParam(required = false) Boolean isHide,
            @PathVariable("id") Long id

            ) {
        return pluginsService.updateContentPlugin(title , url , image , isHide , id);
    }


    //thay đổi vị trí links, header
    @PutMapping("/updateLocation")
    @ResponseStatus(HttpStatus.CREATED)

    public List<PluginsEntity> updateLocationPlugins(

            @RequestBody List<PluginsEntity> pluginsEntityList,
            @RequestHeader("Authorization") String jwt
    ) {
        return pluginsService.updateLocationPlugin(pluginsEntityList , jwtTokenProvider.getUserIdFromHeader(jwt));
    }


    @RequestMapping(value= "/plugins/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public void deletePlugins(
            @PathVariable("id") Long id
    ) {
          pluginsService.deletePluginsById(id);
    }


}
