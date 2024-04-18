package everycoding.nalseebackend.user.service.info;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UserFeedInfo {
    private Integer feedCount;
    private Integer followingCount;
    private Integer followerCount;

    private Long userId;
    private String userImage;
    private String username;

    private Boolean isFollowed;
}
