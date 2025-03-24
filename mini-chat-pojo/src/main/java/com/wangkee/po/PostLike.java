package com.wangkee.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;


@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@TableName(value ="post_like")
public class PostLike {

    @TableId(value = "id")
    private Long id;

    @TableField(value = "user_id")
    private Long userId;

    @TableField(value = "post_id")
    private Long postId;

    @TableField(value = "created_at")
    private Long createdAt;

}