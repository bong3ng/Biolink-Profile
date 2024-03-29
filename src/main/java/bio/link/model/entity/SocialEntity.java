package bio.link.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@Builder
@Table(name = "socials")
@NoArgsConstructor
@AllArgsConstructor
public class SocialEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String url;

    @Column(name = "status" , columnDefinition = "boolean default false")
    private Boolean status = false;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "profile_id")
    private Long profileId;

}
