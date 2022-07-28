package bio.link.service;



import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import bio.link.model.entity.DesignEntity;

import bio.link.security.payload.Status;
import org.springframework.web.multipart.MultipartFile;

import bio.link.model.entity.DesignEntity;

public interface DesignService {

    DesignEntity create(DesignEntity designEntity, MultipartFile image, Long userId);

    DesignEntity update(DesignEntity design, MultipartFile image, Long userId, Long id);
    List<DesignEntity> getAll();
    List<DesignEntity> getAllByUserId(Long userId);
    DesignEntity getDesignById(Long id);
    Status delete(Long id);

 

 

}
