package com.wangkee.utils;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

import java.util.List;

/**
 * 用于返回分页Grid的数据格式
 */
@Data
public class PagedResult {
	
	private long page;			// 当前页数
	private long total;			// 总页数
	private long records;		// 总记录数
	private List<?> rows;		// 每行显示的内容

	public static PagedResult assemblePagedGridResult(Page<?> pageInfo) {
		PagedResult gridResult = new PagedResult();
		gridResult.setRows(pageInfo.getRecords());
		gridResult.setPage(pageInfo.getCurrent());
		gridResult.setRecords(pageInfo.getTotal());
		gridResult.setTotal(pageInfo.getPages());
		return gridResult;
	}
}
