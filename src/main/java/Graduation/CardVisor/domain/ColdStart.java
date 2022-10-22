package Graduation.CardVisor.domain;

import Graduation.CardVisor.domain.member.Member;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "cold_start")
@Data
public class ColdStart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cold_start_id")
    private Long id;

    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "card_code")
    private Long cardCode;

    @Column(name = "ranking")
    private int rank;
}
