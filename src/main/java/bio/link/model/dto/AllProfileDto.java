package bio.link.model.dto;

import bio.link.model.entity.DesignEntity;
import bio.link.model.entity.PluginsEntity;
import bio.link.model.entity.SocialEntity;
import lombok.*;

import javax.persistence.Id;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AllProfileDto {
    @Id
    private String username;
    private String name;
    private String bio;
    private String image;

    private Long totalLike;

    private List<SocialEntity> listSocial;

    private boolean userLike = false;
}
