package bio.link.service;



import java.util.List;

import bio.link.model.entity.DesignEntity;

public interface DesignService {

    DesignEntity update(DesignEntity designEntity);

    List<DesignEntity> getAll();

    DesignEntity getDesignById(Long id);
    void delete(Long id);

}
