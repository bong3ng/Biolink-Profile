package bio.link.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bio.link.model.entity.DesignEntity;
import bio.link.repository.DesignRepository;
import org.springframework.web.multipart.MultipartFile;

@Service
public class DesignServiceImpl implements DesignService {

    @Autowired
    private ProfileService profileService;

    @Autowired
    private DesignRepository designRepository;

    @Override
    public DesignEntity create(DesignEntity designEntity, MultipartFile image, Long userId) {

        if (image != null) {
            try {
                String path = profileService.uploadImage(image, "files");
                designEntity.setBackgroundImg(path);
            } catch (Exception e) {
                System.out.println(e);
            }

        }
        else designEntity.setBackgroundImg(null);
        designEntity.setUserId(userId);
        return designRepository.save(designEntity);
    }

    @Override
    public DesignEntity update(DesignEntity design, MultipartFile image, Long userId, Long id) {

        DesignEntity designEntity = designRepository.findDesignEntityById(id);

        designEntity.setBackground(design.getBackground());

        if (image != null) {
            try {
                String path = profileService.uploadImage(image, "files");
                designEntity.setBackgroundImg(path);
            } catch (Exception e) {
                System.out.println(e);
            }

        }
        else designEntity.setBackgroundImg(null);

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
    public List<DesignEntity> getAllByUserId(Long userId) {
        return (List<DesignEntity>)
                designRepository.findAllByUserId(userId);
    }

    @Override
    public DesignEntity getDesignById(Long id) {
        DesignEntity designEntity = designRepository.findDesignEntityById(id);
        return designEntity;
    }

    @Override
    public void delete(Long id) {
        DesignEntity designEntity = designRepository.findDesignEntityById(id);
        designRepository.delete(designEntity);
    }
}
