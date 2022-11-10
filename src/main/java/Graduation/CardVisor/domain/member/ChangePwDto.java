package Graduation.CardVisor.domain.member;

import lombok.Data;

@Data
public class ChangePwDto {
    private String oldPw;
    private String newPw;
}
