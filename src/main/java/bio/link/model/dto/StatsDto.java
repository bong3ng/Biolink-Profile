package bio.link.model.dto;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@Builder
public class StatsDto {
    private Long totalClickProfile;
    private List<ClickProfileDto> clickProfileList;

    private Long totalClickSocial;
    private List<ClickSocialDto> clickSocialList;

    private Long totalClickPlugins;
    private List<ClickPluginsDto> clickPluginsList;

}
