package bio.link.model.dto;

import lombok.*;

import java.io.Serializable;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PluginsDto implements Serializable {
    private String title;
    private String url;
    private Boolean isPlugin;
    private String image;
}
