package bio.link.controller;


import bio.link.model.entity.PluginsEntity;
import bio.link.security.jwt.JwtTokenProvider;
import bio.link.service.PluginsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@CrossOrigin("*")
public class PluginsController {

    public static final Path CURRENT_FOLDER = Paths.get(System.getProperty("user.dir"));

    @Autowired
    private PluginsService pluginsService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;


    @PostMapping("/plugins")
    @ResponseStatus(HttpStatus.CREATED)
    public PluginsEntity create(@RequestParam String title,
                          @RequestParam String url,
                          @RequestParam MultipartFile image,
                          @RequestParam(required = false) Boolean isHeader ,
                          @RequestParam(required = false) Boolean isPlugin,
                          @RequestParam(required = false) Boolean isHide,
                                @RequestHeader("Authorization") String jwt
    ) throws IOException {
        if (isHeader == null) {
            System.out.println(1);
            System.out.println(jwtTokenProvider.getUserIdFromHeader(jwt));
            return pluginsService.savePlugins(title , url , image , isHeader , isPlugin , isHide, jwtTokenProvider.getUserIdFromHeader(jwt));

        } else {

            return pluginsService.savePlugins(title , "" , image , isHeader , isPlugin , isHide, jwtTokenProvider.getUserIdFromHeader(jwt));
        }

    }



    @GetMapping("/plugins")
    public List<PluginsEntity> getAllPlugins(long userId) {
        return  pluginsService.getAllPluginsByUserId(userId);
    }


    @PutMapping("/plugins/updateContent")
    @ResponseStatus(HttpStatus.CREATED)
    public PluginsEntity updateContentPlugins(
            @RequestParam String title,
            @RequestParam String url,
            @RequestParam(value = "image", required=false) MultipartFile  image,
            @PathVariable("id") Long id
    ) {
        return pluginsService.updateContentPlugins(title , url , image,id);
    }


    //thay đổi vị trí links, header
    @PutMapping("/plugins/updateLocation")
    public PluginsEntity updateLocationPlugins(
            @RequestBody List<PluginsEntity> pluginsEntityList
    ) {
        return pluginsService.updateLocationPlugins(pluginsEntityList , 1L);
    }


    @RequestMapping(value= "/plugins/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public void deletePlugins(
            @PathVariable("id") Long id
    ) {
          pluginsService.deletePluginsById(id);
    }


}
