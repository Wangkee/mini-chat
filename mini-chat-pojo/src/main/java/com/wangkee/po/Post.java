package com.wangkee.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

@Data
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@TableName(value ="post")
public class Post {
    /**
     * 帖子 ID
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 发帖用户 ID
     */
    @TableField(value = "user_id")
    private Long userId;

    /**
     * 标题
     */
    @TableField(value = "title")
    private String title;

    /**
     * 内容
     */
    @TableField(value = "content")
    private String content;

    /**
     * 存储图片 URL 分号分割
     */
    @TableField(value = "images")
    private String images;

    /**
     * 视频 URL（如果是视频帖子）
     */
    @TableField(value = "video_url")
    private String videoUrl;

    /**
     * 位置（用户发布的地理位置）
     */
    @TableField(value = "location")
    private String location;

    /**
     * 可见性（0: 仅自己可见, 1: 公开, 2: 仅粉丝可见，3.部分用户可见 4.部分用户不可见）
     */
    @TableField(value = "visibility")
    private Integer visibility;

    /**
     * 状态（0: 正常, 1: 审核中, 2: 已删除）
     */
    @TableField(value = "status")
    private Integer status;

    /**
     * 创建时间
     */
    @TableField(value = "created_at")
    private Long createdAt;

    /**
     * 更新时间
     */
    @TableField(value = "updated_at")
    private Long updatedAt;
}