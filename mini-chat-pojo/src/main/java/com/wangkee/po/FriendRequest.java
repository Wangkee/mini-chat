package com.wangkee.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.time.LocalDateTime;

@Data
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@TableName(value ="friend_request")
public class FriendRequest {
    /**
     * 主键 id
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 用户 id
     */
    @TableField(value = "user_id")
    private Long user_id;

    /**
     * 好友 id
     */
    @TableField(value = "friend_id")
    private Long friend_id;

    /**
     * 用户给好友的备注
     */
    @TableField(value = "remark")
    private String remark;

    /**
     * 是否仅聊天，0表示否，1表示是
     */
    @TableField(value = "chat_only")
    private Boolean chatOnly;

    /**
     *
     */
    @TableField(value = "verify_message")
    private String verifyMessage;

    /**
     *
     */
    @TableField(value = "verify_status")
    private Integer verifyStatus;

    /**
     *
     */
    @TableField(value = "created_at")
    private LocalDateTime createdAt;

    /**
     *
     */
    @TableField(value = "updated_at")
    private LocalDateTime updatedAt;
}
