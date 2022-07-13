package bio.link.model.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Setter
@Getter
@Builder
@Table(name = "social")
@NoArgsConstructor
@AllArgsConstructor
public class SocialEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String url;

    @Column(name = "profile_id")
    private Long profileId;

}
