package com.wangkee.dto;


import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class FollowshipDTO {
    private Long follower;
    private Long followee;
    @Length(max = 30, message = "备注长度不能大于30")
    private String remark;
    private Boolean totop;
    private Boolean muted;

}
