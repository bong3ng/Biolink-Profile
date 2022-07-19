package bio.link.service;


import java.time.LocalDate;
import java.util.HashMap;

import bio.link.model.entity.ClickSocialEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bio.link.model.entity.ClickPluginsEntity;
import bio.link.model.entity.PluginsEntity;
import bio.link.repository.ClickPluginsRepository;

@Service
public class ClickPluginsServiceImpl implements ClickPluginsService{

    @Autowired
    private ClickPluginsRepository clickPluginsRepository;

    private static LocalDate currentDate;
    private static LocalDate previousDate;
    private static HashMap<Long , Long> countClickPluginsMap = new HashMap<>();
    @Override
    public void countClickPlugins(PluginsEntity pluginsEntity) {

        currentDate = LocalDate.now();
        if(currentDate.minusDays(1).equals(previousDate)) {
            countClickPluginsMap.clear();
        }
        previousDate = currentDate;

        Long pluginsId = pluginsEntity.getId();

        Long clickCount = countClickPluginsMap.get(pluginsId);
        if(clickCount == null) {
            ClickPluginsEntity clickPluginsEntity = clickPluginsRepository.getClickCountByDate(currentDate , pluginsId);
            if(clickPluginsEntity == null) {
                countClickPluginsMap.put(pluginsId , 1L);
                clickPluginsEntity = new ClickPluginsEntity(null , 1L , currentDate , pluginsId);
            }
            else {
                clickCount = clickPluginsEntity.getClickCount() + 1L;
                countClickPluginsMap.put(pluginsId , clickCount );
                clickPluginsEntity.setClickCount(clickCount);
            }
            clickPluginsRepository.save(clickPluginsEntity);
        }
        else {
            clickCount += 1;
            countClickPluginsMap.put(pluginsId,clickCount);
            clickPluginsRepository.updatePluginsClickCount(currentDate,pluginsId,clickCount);
        }


//        ClickPluginsEntity clickPluginsEntity = clickPluginsRepository.getClickCountByDate(currentDate , pluginsEntity.getId());
//        if( clickPluginsEntity == null) {
//            ClickPluginsEntity newClick = new ClickPluginsEntity(null, 1L , currentDate , pluginsEntity.getId());
//            clickPluginsRepository.save(newClick);
//        }
//        else {
//            ClickPluginsEntity newClick = new ClickPluginsEntity(clickPluginsEntity.getId() ,
//                    clickPluginsEntity.getClickCount() + 1 ,
//                    currentDate,
//                    pluginsEntity.getId());
//            clickPluginsRepository.save(newClick);
//        }
    }
}
