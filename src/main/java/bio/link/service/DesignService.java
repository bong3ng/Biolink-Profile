package bio.link.service;



import bio.link.model.entity.DesignEntity;

import java.util.List;

public interface DesignService {

    DesignEntity create(Long userId, DesignEntity designEntity);

    DesignEntity update(Long userId, Long id, DesignEntity design);

    List<DesignEntity> getAll();

    DesignEntity getDesignById(Long id);
    void delete(Long id);

}
