package kr.mashup.branding.domain.attendance;


import javax.persistence.*;

@Entity
public class Member extends BaseEntity{

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "generation_id")
    private Generation generation;

    private String identification;

    private String password;

    private String name;

    @Enumerated(EnumType.STRING)
    private Team team;

    private boolean privatePolicyAgreed;
}
