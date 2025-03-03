package com.bovo.Bovo.modules.user.domain;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 불필요한 객체 생성 방지
@Entity
@Table(name = "user_auth")
public class User_Auth {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long auth_id;

    @ManyToOne
    @JoinColumn(name = "id", nullable = false)
    private Users users;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Provider provider;



    @Builder
    public User_Auth(Users users, String email, String password, Provider provider) {
        this.users = users;
        this.email = email;
        this.password = password;
        this.provider = (provider != null) ? provider : Provider.LOCAL;
    }

}
