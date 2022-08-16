package bio.link.service.impl;



import bio.link.model.dto.CommentDto;
import bio.link.model.entity.ProfileEntity;
import bio.link.model.entity.CommentEntity;
import bio.link.model.entity.UserEntity;
import bio.link.repository.CommentRepository;
import bio.link.repository.UserRepository;
import bio.link.service.ProfileService;
import bio.link.service.CommentService;
import bio.link.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;


    @Autowired
    private ProfileService profileService;


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;



    @Override
    public List<CommentDto> getAllCommentByUsername(String username) {
        Long profileId = profileService.getUserByUsername(username).getId();
        List<CommentEntity> commentEntities = commentRepository.getAllRCommentByProfileId(profileId);
        List<CommentDto> rateDtoList = new ArrayList<>();

        for (int i = 0; i < commentEntities.size(); i++) {
            String usernamee = commentEntities.get(i).getUsername();
            Long userId = commentEntities.get(i).getUserId();
            ProfileEntity profileEntity = profileService.getProfileByUserId(userId);
            String image = profileEntity.getImage();

            CommentDto rateDto = CommentDto.builder().comment(commentEntities.get(i).getComment())
                    .username(commentEntities.get(i).getUsername())
                    .createDate(commentEntities.get(i).getCreateDate())
                    .imageUser(image)
                    .build();
            rateDtoList.add(rateDto);
        }
        return rateDtoList;
    }

    @Override
    public CommentEntity saveComment(String comment,
                                  Long userId,
                                  String username) throws Exception {
        CommentEntity newRate = new CommentEntity();

        Optional<UserEntity> userEntity = userRepository.findById(userId);

        newRate.setComment(comment);
        newRate.setProfileId(profileService.getUserByUsername(username).getId());
        newRate.setCreateDate(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        newRate.setUserId(userId);
        newRate.setUsername(userEntity.get().getUsername());

        return commentRepository.save(newRate);
    }
}
