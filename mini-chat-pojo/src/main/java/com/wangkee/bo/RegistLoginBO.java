package com.wangkee.bo;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import com.wangkee.validate.phone.PhoneNumber;


@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RegistLoginBO {

    @PhoneNumber
    private String mobile;

    @NotBlank(message = "验证码不能为空")
    @Length(min = 6, max = 6, message = "验证码长度不正确")
    private String smsCode;
}
