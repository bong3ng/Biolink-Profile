package bio.link.service;



import bio.link.model.entity.DesignEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface DesignService {

    DesignEntity create(DesignEntity designEntity, MultipartFile image, Long userId);

    DesignEntity update(DesignEntity design, MultipartFile image, Long userId, Long id);
    List<DesignEntity> getAll();
    List<DesignEntity> getAllByUserId(Long userId);

    DesignEntity getDesignById(Long id);
    void delete(Long id);

}
