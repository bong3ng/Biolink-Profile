package bio.link.model.dto;

import lombok.*;

import java.time.LocalDate;
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClickProfileDto {
    private Integer clickCount;
    private LocalDate date;
}
