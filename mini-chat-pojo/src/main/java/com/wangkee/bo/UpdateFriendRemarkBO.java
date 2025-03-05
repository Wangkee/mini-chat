package com.wangkee.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UpdateFriendRemarkBO {

    Long friendId;

    @Length(min = 1, max = 30, message = "备注不能超过30字")
    String newRemark;
}
