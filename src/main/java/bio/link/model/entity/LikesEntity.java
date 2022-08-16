package bio.link.model.entity;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "likes")
public class LikesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "profile_id")
    private Long profileId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "username_cmt")
    private String usernameCmt;

    @Column(name = "status_like" , columnDefinition = "boolean default false")
    private Boolean statusLike = false;



}
