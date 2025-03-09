package com.bovo.Bovo.common;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 불필요한 객체 생성 방지
@AllArgsConstructor
@Entity
@Table(name = "user_auth")
public class User_Auth {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer auth_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Users users;

    @Column
    private String email = null;

    @Column
    private String password = null;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Provider provider;

    // 카카오 로그인 사용자만 가짐
    @Column
    private Long socialId = null;

    // 카카오 로그인 사용자만 가지는 카카오 엑세스 토큰
    @Column
    private String kakaoAccessToken = null;

    // 카카오 로그인 사용자만 가지는 카카오 리프레쉬 토큰
    @Column
    private String kakaoRefreshToken = null;

    // 최초 로그인으로 발급 전에는 null
    @Column
    private String refreshToken = null;

    public static User_Auth createLocalUser(Users users, String email, String password) {
        return User_Auth.builder()
                .users(users)
                .email(email)
                .password(password)
                .provider(Provider.LOCAL)
                .build();
    }

    public static User_Auth createKakaoUser(Users users, Long socialId, String kakaoAccessToken, String kakaoRefreshToken) {
        return User_Auth.builder()
                .users(users)
                .socialId(socialId)
                .kakaoAccessToken(kakaoAccessToken)
                .kakaoRefreshToken(kakaoRefreshToken)
                .provider(Provider.KAKAO)
                .build();
    }

}
