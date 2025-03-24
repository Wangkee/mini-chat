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
@TableName(value ="tag")
public class Tag {
    @TableId(value = "id")
    private Long id;

    @TableField(value = "name")
    private String name;
}