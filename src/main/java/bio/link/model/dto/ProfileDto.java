package bio.link.model.dto;

import java.util.List;

import javax.persistence.Id;

import bio.link.model.entity.DesignEntity;
import bio.link.model.entity.PluginsEntity;
import bio.link.model.entity.SocialEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Builder
@AllArgsConstructor
public class ProfileDto {
    @Id
    private String username;
    private String name;
    private String bio;
    private String image;
    private Boolean showLogo;
    private Boolean showNSFW;
    private Long activeDesign;

    private List<SocialEntity> listSocial;
    private List<PluginsEntity> listPlugins;

    private DesignEntity design;
}
