package everycoding.nalseebackend.user;

import everycoding.nalseebackend.api.ApiResponse;
import everycoding.nalseebackend.auth.customUser.CustomUserDetails;
import everycoding.nalseebackend.auth.jwt.JwtTokenProvider;
import everycoding.nalseebackend.firebase.FcmService;
import everycoding.nalseebackend.firebase.alarm.AlarmRepository;
import everycoding.nalseebackend.firebase.alarm.domain.Alarm;
import everycoding.nalseebackend.firebase.dto.FcmSendDto;
import everycoding.nalseebackend.user.domain.User;
import everycoding.nalseebackend.user.dto.UserFeedResponseDto;
import everycoding.nalseebackend.user.dto.UserInfoRequestDto;
import everycoding.nalseebackend.user.dto.UserInfoResponseDto;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final FcmService fcmService;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final AlarmRepository alarmRepository;

    // 팔로우
    @PostMapping("/api/users/{userId}/follow")
    public ApiResponse<Void> followUser(
            @PathVariable Long userId,
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            HttpServletRequest request
    ) throws IOException {
        String token = "";
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("AccessToken")) {
                token = cookie.getValue();
            }
        }
        Claims claims = jwtTokenProvider.getClaims(token);
        String userEmail = claims.getSubject();
        Optional<User> byEmail = userRepository.findByEmail(userEmail);
        User user = byEmail.orElseThrow();
        String username = user.getUsername();

        Optional<User> byId = userRepository.findById(userId);
        User owner = byId.orElseThrow();
        String userToken = owner.getFcmToken();
        String message = username +"님이 팔로우를 시작했습니다.";
        if (!owner.getId().equals(user.getId())) {
            if (userToken != null && !userToken.isEmpty()) {
                if (!userToken.equals("error")) {
                    //  FCM 메시지 생성 및 전송
                    FcmSendDto fcmSendDto = FcmSendDto.builder()
                            .token(userToken)
                            .title("팔로우 알림")
                            .body(message)
                            .userId(user.getId())
                            .build();

                    fcmService.sendMessageTo(fcmSendDto);
                }
            }
            Alarm alarm = Alarm.builder()
                    .senderId(user.getId())
                    .senderImg(user.getPicture())
                    .senderName(username)
                    .user(owner)
                    .message(message)
                    .ownerId(user.getId())
                    .build();

            alarmRepository.save(alarm);
        }

        userService.followUser(userId, customUserDetails.getId());
        return ApiResponse.ok();
    }

    // 언팔로우
    @PostMapping("/api/users/{userId}/unfollow")
    public ApiResponse<Void> unfollowUser(
            @PathVariable Long userId,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        userService.unfollowUser(userId, customUserDetails.getId());
        return ApiResponse.ok();
    }

    // 유저 개인정보 조회
    @GetMapping("/api/users/userInfo")
    public ApiResponse<UserInfoResponseDto> getUserInfo(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        return ApiResponse.ok(userService.getUserInfo(customUserDetails.getId()));
    }

    // 유저 개인정보 등록
    @PostMapping("/api/users/userInfo")
    public ApiResponse<Void> setUserInfo(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestBody UserInfoRequestDto requestDto
    ) {
        userService.setUserInfo(customUserDetails.getId(), requestDto);
        return ApiResponse.ok();
    }

    // 개인 피드 페이지
    @GetMapping("/api/users/{userId}/feed")
    public ApiResponse<UserFeedResponseDto> getFeed(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable Long userId
    ) {
        return ApiResponse.ok(userService.getFeed(customUserDetails.getId(), userId));
    }
}
