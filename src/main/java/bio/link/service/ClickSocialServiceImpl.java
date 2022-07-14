package bio.link.service;


import java.time.LocalDate;

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


    @Override
    public void countClickSocial(SocialEntity socialEntity) {
        LocalDate date = LocalDate.now();
        ClickSocialEntity clickSocialEntity = clickSocialRepository.getClickCountByDate(date , socialEntity.getId());

        if(clickSocialEntity == null) {
            ClickSocialEntity newClick = new ClickSocialEntity(null, 1 , date ,socialEntity.getId());
            clickSocialRepository.save(newClick);
        }
        else {
            ClickSocialEntity newClick = new ClickSocialEntity(clickSocialEntity.getId() ,
                    clickSocialEntity.getClickCount() + 1 ,
                    date,
                    socialEntity.getId());
            clickSocialRepository.save(newClick);
        }
    }


}
