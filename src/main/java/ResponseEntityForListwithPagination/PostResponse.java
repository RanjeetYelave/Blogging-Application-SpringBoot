package ResponseEntityForListwithPagination;

import java.util.List;

import com.demoproject.blog.Dto.PostDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostResponse {
	private List<PostDto> listOfPosts;
	private Integer pageNo;
	private Integer Size;
	private Long totalNoOfElements;
	private Integer totalNoOfPages;
}
