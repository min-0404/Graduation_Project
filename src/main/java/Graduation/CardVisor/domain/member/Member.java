package Graduation.CardVisor.domain.member;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity(name = "member")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(name = "member_nickname")
    private String nickname;

    @Column(name = "member_pw")
    private String pw;

    @Column(name = "gender")
    private Gender gender;

    @Column(name="roles")
    private String roles; // USER 또는 ADMIN

    @Column(name = "age")
    private Age age;


    // Member 의 role 리스트에 담아 반환
    public List<String> getRolesList() {
        if(this.roles.length() > 0) {
            return Arrays.asList(this.roles.split(","));
        }
        return new ArrayList<>();
    }

    // male = 0, female = 1
    public enum Gender {
        male, female
    }

    public enum Age {
        one, two, three, four, five, six
    }
}
