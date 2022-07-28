package bio.link.service;


import java.util.List;

import bio.link.model.entity.ProfileEntity;
import bio.link.security.payload.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import bio.link.model.entity.DesignEntity;
import bio.link.repository.DesignRepository;

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
        designRepository.save(designEntity);

        ProfileEntity profileEntity = profileService.getProfileByUserId(userId);
        profileEntity.setActiveDesign(designEntity.getId());
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
        return designRepository.findDesignEntityById(id);
    }

    @Override
    public Status delete(Long id) {
        //DesignEntity designEntity = designRepository.findDesignEntityById(id);
        Long userId = designRepository.findDesignEntityById(id).getUserId();
        ProfileEntity profileEntity = profileService.getProfileByUserId(userId);

        if (profileEntity.getActiveDesign() == id) {
            return new  Status(0, "You can't delete this :<");
        }

        designRepository.deleteById(id);
        profileEntity.setActiveDesign(1L);
        return new Status(1, "Yayyyy, Delete success.");
    }
   
}
