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
    private List<ClickProfileDto> clickProfileList;
    private List<ClickSocialDto> clickSocialList;
    private List<ClickPluginsDto> clickPluginsList;

}
