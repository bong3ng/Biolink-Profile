package bio.link.controller;



import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import bio.link.model.entity.PluginsEntity;
import bio.link.service.PluginsService;

@RestController
@CrossOrigin("*")
public class PluginsController {

    public static final Path CURRENT_FOLDER = Paths.get(System.getProperty("user.dir"));

    @Autowired
    private PluginsService pluginsService;


    @PostMapping("/plugins")
    @ResponseStatus(HttpStatus.CREATED)
    public PluginsEntity create(@RequestParam String title,
                          @RequestParam String url,
                          @RequestParam MultipartFile image,
                          @RequestParam(required = false) Boolean is_header ,
                          @RequestParam(required = false) Boolean is_plugin,
                          @RequestParam(required = false) Boolean is_hide,
                          @RequestParam Long profile_id
    ) throws IOException {
        if (is_header == null) {
            System.out.println(1);
            return pluginsService.savePlugins(title , url , image , is_header , is_plugin , is_hide, profile_id);
        } else {

            return pluginsService.savePlugins(title , "" , image , is_header , is_plugin , is_hide, profile_id);
        }

    }



    @GetMapping("/plugins")
    public List<PluginsEntity> getAllPlugins() {
        return  pluginsService.getAllPlugins();
    }


    @PutMapping("/plugins/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public PluginsEntity updatePlugins(
            @RequestParam String title,
            @RequestParam String url,
            @RequestParam(value = "image", required=false) MultipartFile  image,
            @PathVariable("id") Long id
    ) {
        return pluginsService.updatePlugins(title , url , image,id);
    }


    @RequestMapping(value= "/plugins/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public void deletePlugins(
            @PathVariable("id") Long id
    ) {
          pluginsService.deletePluginsById(id);
    }


}
