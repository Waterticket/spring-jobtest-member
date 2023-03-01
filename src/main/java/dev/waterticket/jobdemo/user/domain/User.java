package dev.waterticket.jobdemo.user.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table (name = "SYSTEM_USER")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_idx")
    private Integer idx;

    @NonNull
    @Column(name = "user_id")
    private String id;

    @NonNull
    @Column(name = "user_pw")
    private String password;

    @NonNull
    @Column(name = "user_nm")
    private String name;

    @NonNull
    @Column(name = "user_auth")
    private String auth;
}
