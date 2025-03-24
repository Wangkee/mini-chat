package com.wangkee.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTO {
    private Integer type;
    private String username;
    private String password;
    private String phone;
    private String code;
}
