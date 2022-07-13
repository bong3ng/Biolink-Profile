package bio.link.service;


import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bio.link.model.entity.ClickPluginsEntity;
import bio.link.model.entity.PluginsEntity;
import bio.link.repository.ClickPluginsRepository;

@Service
public class ClickPluginsServiceImpl implements ClickPluginsService{

    @Autowired
    private ClickPluginsRepository clickPluginsRepository;

    @Override
    public void countClickPlugins(PluginsEntity pluginsEntity) {
        LocalDate date = LocalDate.now();
        ClickPluginsEntity clickPluginsEntity = clickPluginsRepository.getClickCountByDate(date , pluginsEntity.getId());

        if( clickPluginsEntity == null) {
            ClickPluginsEntity newClick = new ClickPluginsEntity(null, 1 , date , pluginsEntity.getId());
            clickPluginsRepository.save(newClick);
        }
        else {
            ClickPluginsEntity newClick = new ClickPluginsEntity(clickPluginsEntity.getId() ,
                    clickPluginsEntity.getClickCount() + 1 ,
                    date,
                    pluginsEntity.getId());
            clickPluginsRepository.save(newClick);
        }
    }
}
