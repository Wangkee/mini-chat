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
@TableName(value ="follow")
public class Follow {

    /**
     * 主键 id
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 关注者
     */
    @TableField(value = "follower")
    private Long follower;

    /**
     * 被关注者
     */
    @TableField(value = "followee")
    private Long followee;

    /**
     * 是否置顶聊天 0是 1否
     */
    @TableField(value = "totop")
    private Boolean totop;

    /**
     * 是否消息免打扰 0是 1否
     */
    @TableField(value = "muted")
    private Boolean muted;

    /**
     * 备注
     */
    @TableField(value = "remark")
    private String remark;

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