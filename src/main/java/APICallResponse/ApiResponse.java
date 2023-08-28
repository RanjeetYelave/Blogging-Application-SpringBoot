package APICallResponse;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class ApiResponse {
	public ApiResponse(String message, boolean success, String httpStatus) {
		super();
		Message = message;
		this.success = success;
		this.httpStatus = httpStatus;
	}

	String Message;
	boolean success;
	String httpStatus;

	public String getMessage() {
		return Message;
	}

	public void setMessage(String message) {
		Message = message;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(String httpStatus) {
		this.httpStatus = httpStatus;
	}
}
