
package bio.link.model.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "rate")
public class RateEntity {

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

    @Column(name = "point_rate")
    private Integer pointRate;

    @Column(name = "create_date")
    private LocalDateTime createDate;




}
