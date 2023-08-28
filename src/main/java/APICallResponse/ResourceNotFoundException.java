package APICallResponse;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter
@Setter
@NoArgsConstructor
public class ResourceNotFoundException extends RuntimeException {
	String resourceName;
	String feild;
	long id;

	public ResourceNotFoundException(String resourceName, String feild, long id) {
		super(String.format("%s not found with %s : %s ", resourceName, feild, id));
		this.resourceName = resourceName;
		this.feild = feild;
		this.id = id;
	}
}