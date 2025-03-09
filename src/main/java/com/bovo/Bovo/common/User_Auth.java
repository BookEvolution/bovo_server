package com.bovo.Bovo.common;

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
    private Integer auth_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Users users;

    @Column(nullable = false)
    private String email;

    @Column(nullable = true)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Provider provider;

    // 카카오 로그인 사용자만 가짐
    @Column(nullable = true)
    private Long social_id = null; // String -> Long 변경

    // 카카오 로그인 사용자만 가지는 카카오 엑세스 토큰
    @Column(nullable = true)
    private String kakao_access_token = null;

    // 최초 로그인으로 발급 전에는 null
    @Column(nullable = true)
    private String refresh_token = null;


    @Builder
    public User_Auth(Users users, String email, String password, Provider provider) {
        this.users = users;
        this.email = email;
        this.password = password;
        this.provider = (provider != null) ? provider : Provider.LOCAL;
    }

}
