package bio.link.service;


import bio.link.model.entity.UserEntity;
import bio.link.model.response.ResponseData;

import java.awt.print.PrinterJob;
import java.util.List;

public interface UserService {
    UserEntity getUserByUsername(String username);

    ResponseData getStatsByUsername(Long userId, Integer days);

    List<UserEntity> getUsernameByUserId(Long id);



}
