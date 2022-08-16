package bio.link.controller;



import bio.link.model.dto.CommentDto;

import bio.link.model.entity.CommentEntity;
import bio.link.security.jwt.JwtTokenProvider;
import bio.link.service.CommentService;

import bio.link.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController

@RequestMapping("/api/user")
@CrossOrigin("*")

public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired

    private UserService userService;


    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @GetMapping("/getAllComment")

    public List<CommentDto> getAllRateByProfileId(
            @RequestHeader("Authorization") String jwt
    ) {
        return commentService.getCommentByProfileId(jwtTokenProvider.getUserIdFromHeader(jwt));
    }

    @PostMapping("/saveComment")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentEntity saveRate(
            @RequestParam(required = false) String comment,
            @RequestParam String username,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {

        return commentService.saveComment(comment  ,  jwtTokenProvider.getUserIdFromHeader(jwt),username);
    }


}
