package dev.waterticket.jobdemo.user.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USER_HISTORY")
public class UserHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "history_idx")
    private Integer idx;

    @NonNull
    @Column(name = "url")
    private String url;

    @NonNull
    @Column(name = "action_type")
    private String actionType;

    @NonNull
    @Column(name = "reg_user_idx")
    private Integer userIdx;

    @NonNull
    @Column(name = "reg_ip")
    private String ip;

    @NonNull
    @Column(name = "reg_dt")
    private String createdAt;
}
