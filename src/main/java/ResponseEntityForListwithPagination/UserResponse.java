package ResponseEntityForListwithPagination;

import java.util.List;

import com.demoproject.blog.Dto.UserDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserResponse {
	private List<UserDto> listOfUsers;
	private Integer pageNo;
	private Integer Size;
	private Long totalNoOfElements;
	private Integer totalNoOfPages;

}
