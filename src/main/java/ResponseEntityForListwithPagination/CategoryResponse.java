package ResponseEntityForListwithPagination;

import java.util.List;

import com.demoproject.blog.Dto.CategoryDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoryResponse {
	List<CategoryDto> listOfCategory;
	private Integer pageNo;
	private Integer Size;
	private long totalNoofElements;
	private Integer totalNoOfPages;

}
