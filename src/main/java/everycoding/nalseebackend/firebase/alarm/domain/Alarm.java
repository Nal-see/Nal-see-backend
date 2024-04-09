package everycoding.nalseebackend.firebase.alarm.domain;

import everycoding.nalseebackend.BaseEntity;
import everycoding.nalseebackend.user.domain.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

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

    @Nullable
    private Long commentId;

    @Nullable
    private Long postId;

    @Nullable
    private Long ownerId;

    @ManyToOne
    private User user;

    @Builder
    public Alarm(Long id, String message, Long senderId, String senderImg, String senderName, @Nullable Long commentId, @Nullable Long postId, @Nullable Long ownerId, User user) {
        this.id = id;
        this.message = message;
        this.senderId = senderId;
        this.senderImg = senderImg;
        this.senderName = senderName;
        this.commentId = commentId;
        this.postId = postId;
        this.ownerId = ownerId;
        this.user = user;
    }
}
