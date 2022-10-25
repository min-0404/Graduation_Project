package Graduation.CardVisor.domain;

import Graduation.CardVisor.domain.member.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "my_cards")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyCards {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "my_cards_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_code")
    private Card card;

    @JoinColumn(name = "date")
    private LocalDateTime date;

}

