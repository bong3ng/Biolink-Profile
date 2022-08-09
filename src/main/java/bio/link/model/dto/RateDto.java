package bio.link.model.dto;


import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RateDto {
    private String comment;

    private Integer pointRate;

    private String username;

    private LocalDateTime createDate;

    private String imageUser;
}
