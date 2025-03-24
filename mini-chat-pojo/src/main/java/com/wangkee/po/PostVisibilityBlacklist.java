package com.wangkee.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

@Data
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@TableName(value ="post_visibility_blacklist")
public class PostVisibilityBlacklist {

    @TableId(value = "post_id")
    private Long postId;


    @TableField(value = "user_id")
    private Long userId;

}