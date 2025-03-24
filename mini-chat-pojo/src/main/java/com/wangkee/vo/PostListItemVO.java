package com.wangkee.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PostListItemVO {
    private Long id;
    private Long userId;
    private String title;
    private String content;
    private String images;
    private String videoUrl;
    private String location;
    private Long createdAt;
    private Long updatedAt;
}
