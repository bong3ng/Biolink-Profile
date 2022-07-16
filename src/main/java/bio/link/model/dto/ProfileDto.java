package bio.link.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.List;


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

    private List<SocialDto> listSocial;
    private List<PluginsDto> listPlugins;

}
