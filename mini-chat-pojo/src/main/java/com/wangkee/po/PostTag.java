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
@TableName(value ="post_tag")
public class PostTag {
    @TableId(value = "post_id")
    private Long postId;

    @TableField(value = "tag_id")
    private Long tagId;
}