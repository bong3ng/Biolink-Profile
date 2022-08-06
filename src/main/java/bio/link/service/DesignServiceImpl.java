package bio.link.service;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import bio.link.model.entity.DesignEntity;
import bio.link.model.entity.ProfileEntity;
import bio.link.repository.DesignRepository;
import bio.link.security.payload.Status;

@Service
public class DesignServiceImpl implements DesignService {

    @Autowired
    private ProfileService profileService;

    @Autowired
    private DesignRepository designRepository;

    @Override
    public DesignEntity createDefault(DesignEntity designEntity, MultipartFile image) {
        DesignEntity design = new DesignEntity();

        design.setBackground(designEntity.getBackground());
        design.setBoxShadow(designEntity.getBoxShadow());
        design.setBtnBdColor(designEntity.getBtnBdColor());
        design.setBtnBdStyle(designEntity.getBtnBdStyle());
        design.setBtnBdWidth(designEntity.getBtnBdWidth());
        design.setBtnBg(designEntity.getBtnBg());
        design.setBtnRadius(designEntity.getBtnRadius());
        design.setColorHeader(designEntity.getColorHeader());
        design.setColorLink(designEntity.getColorLink());
        design.setFontFamily(designEntity.getFontFamily());
        design.setName(designEntity.getName());

        if (image != null && !image.isEmpty()) {
            String path = profileService.uploadImage(image, "designs");
            design.setBackgroundImg(path);
        }
        else design.setBackgroundImg(null);

        return designRepository.save(design);
    }

    @Override
    public DesignEntity create(DesignEntity designEntity, MultipartFile image, Long userId) {
        DesignEntity design = new DesignEntity();

        design.setBackground(designEntity.getBackground());
        design.setBoxShadow(designEntity.getBoxShadow());
        design.setBtnBdColor(designEntity.getBtnBdColor());
        design.setBtnBdStyle(designEntity.getBtnBdStyle());
        design.setBtnBdWidth(designEntity.getBtnBdWidth());
        design.setBtnBg(designEntity.getBtnBg());
        design.setBtnRadius(designEntity.getBtnRadius());
        design.setColorHeader(designEntity.getColorHeader());
        design.setColorLink(designEntity.getColorLink());
        design.setFontFamily(designEntity.getFontFamily());
        design.setName(designEntity.getName());

        if (image != null && !image.isEmpty()) {
            String path = profileService.uploadImage(image, "designs");
            design.setBackgroundImg(path);
        }
        else design.setBackgroundImg(null);

        design.setUserId(userId);
        designRepository.save(design);

        ProfileEntity profileEntity = profileService.getProfileByUserId(userId);
        profileEntity.setActiveDesign(design.getId());

        return designRepository.save(design);
    }

//    @Override
//    public DesignEntity update(DesignEntity design, MultipartFile backgroundImg, Long userId, Long id) {
//
//        DesignEntity designEntity = designRepository.findDesignEntityById(id);
//
//        designEntity.setBackground(design.getBackground());
//
//        if (image != null) {
//            try {
//                String path = profileService.uploadImage(image, "files");
//                designEntity.setBackgroundImg(path);
//            } catch (Exception e) {
//                System.out.println(e);
//            }
//
//        }
//        else designEntity.setBackgroundImg(null);
//
//        designEntity.setBoxShadow(design.getBoxShadow());
//        designEntity.setBtnBdColor(design.getBtnBdColor());
//        designEntity.setBtnBdStyle(design.getBtnBdStyle());
//        designEntity.setBtnBdWidth(design.getBtnBdWidth());
//        designEntity.setBtnBg(design.getBtnBg());
//        designEntity.setBtnRadius(design.getBtnRadius());
//        designEntity.setColorHeader(design.getColorHeader());
//        designEntity.setColorLink(design.getColorLink());
//        designEntity.setFontFamily(design.getFontFamily());
//        designEntity.setName(design.getName());
//        designEntity.setUserId(userId);
//        return designRepository.save(designEntity);
//    }


    @Override
    public List<DesignEntity> getAllByUserId(Long userId) {
        List<DesignEntity> designEntities = new ArrayList<>();
        for (Long i = 1L; i < 7L; i++) {
            designEntities.add(designRepository.findDesignEntityById(i));
        }

        designEntities.addAll(designRepository.findAllByUserId(userId));
        return designEntities;
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
            return new  Status(false, "You can't delete this :<");
        }

        designRepository.deleteById(id);
//        profileEntity.setActiveDesign(1L);
        return new Status(true, "Yayyyy, Delete success.");
    }
   
}
