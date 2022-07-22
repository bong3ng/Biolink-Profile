package bio.link.model.dto;

import lombok.*;

import java.io.Serializable;
import java.util.List;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PluginsDto implements Serializable {

    private boolean success = true;

    private String message;
    private List<PluginsDto> data;
}
