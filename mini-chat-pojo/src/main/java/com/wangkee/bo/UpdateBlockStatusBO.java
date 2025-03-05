package com.wangkee.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UpdateBlockStatusBO {
    Long friendId;
    Boolean isBlocked;
}
