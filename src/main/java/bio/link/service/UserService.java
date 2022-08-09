package bio.link.service;


import bio.link.model.entity.UserEntity;

import java.awt.print.PrinterJob;
import java.util.List;

public interface UserService {
    UserEntity getUserByUsername(String username);

    List<UserEntity> getUsernameByUserId(Long id);



}
