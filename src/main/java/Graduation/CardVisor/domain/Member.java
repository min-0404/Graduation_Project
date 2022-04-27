package Graduation.CardVisor.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Date;

@Entity(name = "member")
@Data
public class Member {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "pw")
    private String pw;

    @Column(name = "gender")
    private String gender;

    @Column(name = "birthday")
    private Date date;
}
