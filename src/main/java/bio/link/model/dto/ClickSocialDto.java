package bio.link.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ClickSocialDto {
    private String name;
    private String url;
    private Long clickCount;
}
