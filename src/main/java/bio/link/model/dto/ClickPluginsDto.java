package bio.link.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ClickPluginsDto {
    private Long id;
    private String title;
    private String url;
    private Long clickCount;
    private Boolean isHeader;
}
