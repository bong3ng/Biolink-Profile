
package bio.link.model.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.time.LocalDateTime;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "comment")
public class CommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    // cái này là người được rate
    @Column(name = "profile_id")
    private Long profileId;


    @Column(name = "user_id")
    private Long userId;

    //cái này là người rate
    @Column(name = "username")
    private String username;


    @Column(name = "content_comment")
    private String comment;


    @Column(name = "create_date")
    private LocalDateTime createDate;




}
