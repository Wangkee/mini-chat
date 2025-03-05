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
@TableName(value ="friendship")
public class Friendship{

    /**
     * 主键 id
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 用户 id
     */
    @TableField(value = "user_id")
    private Long userId;

    /**
     * 好友 id
     */
    @TableField(value = "friend_id")
    private Long friendId;

    /**
     * 用户给好友的备注
     */
    @TableField(value = "remark")
    private String remark;

    /**
     * 是否仅聊天，0表示否，1表示是
     */
    @TableField(value = "chatonly")
    private Boolean chatOnly;

    /**
     * 是否拉黑好友，0表示否，1表示是
     */
    @TableField(value = "blocked")
    private Boolean blocked;

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