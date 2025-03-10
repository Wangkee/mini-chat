package com.wangkee.dto;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BasePageQueryDTO {
    @Min(value = 1, message = "页码必须大于等于 1")
    private Integer page = 1;

    @Min(value = 1, message = "每页大小必须大于等于 1")
    private Integer pageSize = 10;
}
