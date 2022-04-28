package Graduation.CardVisor.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Date;

@Entity(name = "member")
@Data
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Column(name = "member_nickname")
    private String nickname;

    @Column(name = "member_pw")
    private String pw;

    @Column(name = "gender")
    private String gender;

    @Column(name = "birthday")
    private Date date;
}
