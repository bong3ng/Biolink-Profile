package bio.link.model.dto;

import lombok.*;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SocialDto {
    private String socialName;
    private String socialUrl;
}
