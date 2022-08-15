package bio.link.model.dto;


import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    private String comment;

    private String username;

    private LocalDateTime createDate;

    private String imageUser;
}
