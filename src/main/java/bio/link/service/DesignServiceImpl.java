package bio.link.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bio.link.model.entity.DesignEntity;
import bio.link.repository.DesignRepository;

@Service
public class DesignServiceImpl implements DesignService {

    @Autowired
    private DesignRepository designRepository;

    @Override
    public DesignEntity update(DesignEntity designEntity) {
        return designRepository.save(designEntity);
    }

    @Override
    public List<DesignEntity> getAll() {
        return (List<DesignEntity>)
                designRepository.findAll();
    }

    @Override
    public DesignEntity getDesignById(Long id) {
        DesignEntity designEntity = designRepository.findOneById(id);
        return designEntity;
    }

    @Override
    public void delete(Long id) {
        DesignEntity designEntity = designRepository.findOneById(id);
        designRepository.delete(designEntity);
    }
}
