package com.wangkee.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wangkee.validate.chatNum.ChatNum;
import com.wangkee.validate.nickname.Nickname;
import com.wangkee.validate.phone.PhoneNumber;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserInfoDTO {

    @ChatNum
    private String chatNum;

    @PhoneNumber
    private String mobile;

    @Nickname
    private String nickname;

    @Min(value = 0, message = "性别格式错误")
    @Max(value = 2, message = "性别格式错误")
    private Integer gender;

    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    private String avatar;

    @Email
    private String email;

    @Length(max = 30, message = "个性签名长度不能大于30")
    private String signature;

    private String school;

    private String location;

    private String homepageImg;
}
