package bio.link.model.dto;


import bio.link.model.entity.LikesEntity;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LikeDto {


    private Long totalLike;


}


