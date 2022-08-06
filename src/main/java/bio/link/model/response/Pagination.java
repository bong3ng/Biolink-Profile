package bio.link.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class Pagination {
    private Integer page;
    private Integer pageSize;
    private Long total;
}
