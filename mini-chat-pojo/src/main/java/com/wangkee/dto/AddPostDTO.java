package com.wangkee.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AddPostDTO {
    private Long userId;
    @Max(value = 50, message = "标题长度不能超过50个字符")
    private String title;
    @Max(value = 2000, message = "内容长度不能超过2000个字符")
    private String content;
    private String images;
    private String videoUrl;
    private String location;
    private Integer visibility;
    private Integer status;
    private List<Long> tags;
    private List<Long> whiteList;
    private List<Long> blackList;
}
