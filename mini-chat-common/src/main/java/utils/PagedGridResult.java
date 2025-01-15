package utils;

import lombok.Data;

import java.util.List;

/**
 * 用于返回分页Grid的数据格式
 */
@Data
public class PagedGridResult {
	
	private long page;			// 当前页数
	private long total;			// 总页数
	private long records;		// 总记录数
	private List<?> rows;		// 每行显示的内容
}
