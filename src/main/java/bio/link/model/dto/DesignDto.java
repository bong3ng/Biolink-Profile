package bio.link.model.dto;

import lombok.*;

import javax.persistence.Column;
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DesignDto {
    private String name;

    private String backgroundImg;

    private String background;

    private String btnRadius;

    private String btnBg;

    private String btnBorder;

    private String boxShadow;

    private String colorHeader;

    private String colorLink;
}
