package bio.link.service;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bio.link.model.entity.UserEntity;
import bio.link.model.exception.NotFoundException;
import bio.link.repository.UserRepository;

@Service

public class UserServiceImpl implements UserService{
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;

    public UserEntity getUserByUsername(String username) {
        UserEntity userEntity = userRepository.findByUsername(username);
        if(userEntity == null) {
           throw new NotFoundException("Khong tim thay nguoi dung");
        }
        return userEntity;
    }
}
