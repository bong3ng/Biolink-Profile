package bio.link.service;


import java.time.LocalDate;
import java.util.HashMap;

import bio.link.model.entity.ClickPluginsEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bio.link.model.entity.ClickSocialEntity;
import bio.link.model.entity.SocialEntity;
import bio.link.repository.ClickSocialRepository;
import lombok.Getter;
import lombok.Setter;

@Service
@Getter
@Setter
public class ClickSocialServiceImpl implements ClickSocialService {

    @Autowired
    private ClickSocialRepository clickSocialRepository;

    private static LocalDate currentDate;
    private static LocalDate previousDate;
    private static HashMap<Long , Long> countClickSocialMap = new HashMap<>();
    @Override
    public void countClickSocial(SocialEntity socialEntity) {

        currentDate = LocalDate.now();
        if(currentDate.minusDays(1).equals(previousDate)) {
            countClickSocialMap.clear();
        }
        previousDate = currentDate;

        Long socialId = socialEntity.getId();

        Long clickCount = countClickSocialMap.get(socialId);
        if(clickCount == null) {
            ClickSocialEntity clickSocialEntity = clickSocialRepository.getClickCountByDate(currentDate , socialId);
            if(clickSocialEntity == null) {
                countClickSocialMap.put(socialId , 1L);
                clickSocialEntity = new ClickSocialEntity(null , 1L , currentDate , socialId);
            }
            else {
                clickCount = clickSocialEntity.getClickCount() + 1L;
                countClickSocialMap.put(socialId , clickCount );
                clickSocialEntity.setClickCount(clickCount);
            }
            clickSocialRepository.save(clickSocialEntity);
        }
        else {
            clickCount += 1;
            countClickSocialMap.put(socialId , clickCount);
            clickSocialRepository.updateSocialClickCount(currentDate,socialId,clickCount);
        }



//        if(clickSocialEntity == null) {
//            ClickSocialEntity newClick = new ClickSocialEntity(null, 1L , date ,socialEntity.getId());
//            clickSocialRepository.save(newClick);
//        }
//        else {
//            ClickSocialEntity newClick = new ClickSocialEntity(clickSocialEntity.getId() ,
//                    clickSocialEntity.getClickCount() + 1 ,
//                    date,
//                    socialEntity.getId());
//            clickSocialRepository.save(newClick);
//        }
    }


}
