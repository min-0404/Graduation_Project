package Graduation.CardVisor.domain;

import lombok.Data;

import javax.persistence.*;

@Entity(name = "fee")
@Data
public class Fee {

    @Id
    @Column(name = "fee_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "card_code")
    private Card card;

    @Column(name = "am")
    private Integer amex;

    @Column(name = "am_f")
    private Integer amexFamily;

    @Column(name = "am_m")
    private Integer amexMobile;

    @Column(name = "bc")
    private Integer bc;

    @Column(name = "bc_g")
    private Integer bcGlobal;

    @Column(name = "bc_n")
    private Integer bcDomestic;

    @Column(name = "jc")
    private Integer jcb;

    @Column(name = "jc_o")
    private Integer jcbOneWay;

    @Column(name = "jc_g")
    private Integer jcbGold;

    @Column(name = "jc_m")
    private Integer jcbMobile;

    @Column(name = "jc_s")
    private Integer jcbSilver;

    @Column(name = "kw")
    private Integer kWorld;

    @Column(name = "kw_j")
    private Integer kWorldJcb;

    @Column(name = "kw_u")
    private Integer kWorldUpi;

    @Column(name = "kw_m")
    private Integer kWorldMobile;

    @Column(name = "ma")
    private Integer master;

    @Column(name = "ma_p")
    private Integer masterPlatinum;

    @Column(name = "ma_f")
    private Integer masterFamliy;

    @Column(name = "ma_g")
    private Integer masterGold;

    @Column(name = "ma_n")
    private Integer masterDomestic;

    @Column(name = "ma_m")
    private Integer masterMobile;

    @Column(name = "ma_s")
    private Integer masterSilver;

    @Column(name = "ma_i")
    private Integer masterOverseas;

    @Column(name = "ow")
    private Integer oneWay;

    @Column(name = "sa")
    private Integer sAnd;

    @Column(name = "un")
    private Integer unionPay;

    @Column(name = "un_n")
    private Integer unionPayDomestic;

    @Column(name = "un_m")
    private Integer unionPayMobile;

    @Column(name = "up")
    private Integer upi;

    @Column(name = "ur")
    private Integer urs;

    @Column(name = "vs")
    private Integer visa;

    @Column(name = "vs_p")
    private Integer visaPlatinum;

    @Column(name = "vs_f")
    private Integer visaFamily;

    @Column(name = "vs_g")
    private Integer visaGold;

    @Column(name = "vs_n")
    private Integer visaDomesticAndOverseas;

    @Column(name = "vs_m")
    private Integer visaMobile;

    @Column(name = "vs_s")
    private Integer visaSilver;

    @Column(name = "na")
    private Integer domestic;

    @Column(name = "na_f")
    private Integer domesticFamily;

    @Column(name = "na_m")
    private Integer domesticMobile;
}