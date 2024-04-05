package everycoding.nalseebackend.firebase.alarm.domain;

import everycoding.nalseebackend.BaseEntity;
import everycoding.nalseebackend.user.domain.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table
public class Alarm extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;

    private Long senderId;

    private String senderImg;

    private String senderName;

    @ManyToOne
    private User user;

    @Builder
    public Alarm(Long id, String message, Long senderId, String senderImg, String senderName, User user) {
        this.id = id;
        this.message = message;
        this.senderId = senderId;
        this.senderImg = senderImg;
        this.senderName = senderName;
        this.user = user;
    }
}
