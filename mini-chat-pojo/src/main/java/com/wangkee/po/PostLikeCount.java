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
@TableName(value ="post_like_count")
public class PostLikeCount {
    @TableId(value = "post_id")
    private Long postId;

    @TableField(value = "count")
    private Long count;

}