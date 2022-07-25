package bio.link.model.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataDto<T> {
	private Boolean success;
	private String message;
	private List<T> data;
}
