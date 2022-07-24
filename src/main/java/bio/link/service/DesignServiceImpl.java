package bio.link.service;


import java.util.List;

import bio.link.model.entity.DesignEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bio.link.repository.DesignRepository;

@Service
public class DesignServiceImpl implements DesignService {
//
//    @Autowired
//    private ProfileService profileService;

    @Autowired
    private DesignRepository designRepository;

    @Override
    public DesignEntity create(Long userId, DesignEntity designEntity) {
        designEntity.setUserId(userId);
        return designRepository.save(designEntity);
    }

    @Override
    public DesignEntity update(Long id, Long userId, DesignEntity design) {

        DesignEntity designEntity = designRepository.findOneById(id);

        designEntity.setBackground(design.getBackground());
        designEntity.setBackgroundImg(designEntity.getBackgroundImg());
        designEntity.setBoxShadow(design.getBoxShadow());
        designEntity.setBtnBdColor(design.getBtnBdColor());
        designEntity.setBtnBdStyle(design.getBtnBdStyle());
        designEntity.setBtnBdWidth(design.getBtnBdWidth());
        designEntity.setBtnBg(design.getBtnBg());
        designEntity.setBtnRadius(design.getBtnRadius());
        designEntity.setColorHeader(design.getColorHeader());
        designEntity.setColorLink(design.getColorLink());
        designEntity.setFontFamily(design.getFontFamily());
        designEntity.setName(design.getName());
        designEntity.setUserId(userId);
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
