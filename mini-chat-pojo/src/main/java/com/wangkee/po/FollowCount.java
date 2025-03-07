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
@TableName(value ="follow_count")
public class FollowCount {
    /**
     * 用户id
     */
    @TableId(value = "user_id")
    private Long userId;

    /**
     * 关注数
     */
    @TableField(value = "following_count")
    private Integer followingCount;

    /**
     * 粉丝数
     */
    @TableField(value = "follower_count")
    private Integer followerCount;

}