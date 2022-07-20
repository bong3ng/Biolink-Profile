package bio.link.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bio.link.model.entity.DesignEntity;
import bio.link.service.DesignService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("api/user/design")
@CrossOrigin("*")
public class DesignController {

    @Autowired
    private DesignService designService;
    
    
    @ApiOperation(value = "Get All danh sách Themes Design", response = List.class)
    
    
    @GetMapping("")
    public List<DesignEntity> get() {
        return designService.getAll();
    }

    @GetMapping("/find/{id}")
    public DesignEntity getDesign(
            @PathVariable("id") Long id
    ) {
        return designService.getDesignById(id);
    }
//    @PostMapping("/v1")
//    public DesignEntity getDesign(
//            @RequestParam String border,
//            @RequestParam String backGround,
//            @RequestParam String borderRadius,
//            @RequestParam String boxShadow,
//            @RequestParam String position,
//            @RequestParam String height,
//            @RequestParam String width,
//            @RequestParam String left,
//            @RequestParam String top,
//            @RequestParam String zIndex
//    ) {
//        return DesignEntity.builder()
//                .border(border)
//                .backGround(backGround)
//                .borderRadius(borderRadius)
//                .boxShadow(boxShadow)
//                .position(position)
//                .height(height)
//                .width(width)
//                .left(left)
//                .top(top)
//                .zIndex(zIndex)
//                .build();
//    }


    @PostMapping("")
    public DesignEntity create(@ApiParam(value = "Đối tượng Design cần tạo mới", required = true)
            @RequestBody DesignEntity designEntity
    ) {
        return designService.update(designEntity);
    }

    @PutMapping("")
    public DesignEntity update(
            @RequestBody DesignEntity designEntity
    ) {
        return designService.update(designEntity);
    }

    @DeleteMapping("/{id}")
    public void delete(
            @PathVariable("id") Long id
    ) {
        designService.delete(id);
    }
}
