package everycoding.nalseebackend.firebase.alarm;

import everycoding.nalseebackend.auth.jwt.JwtTokenProvider;
import everycoding.nalseebackend.firebase.alarm.domain.Alarm;
import everycoding.nalseebackend.firebase.alarm.dto.AlarmDto;
import everycoding.nalseebackend.user.UserRepository;
import everycoding.nalseebackend.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AlarmService {

    private final AlarmRepository alarmRepository;

    public List<AlarmDto> getAllAlarm(User user) {
        List<Alarm> alarmsByUser = alarmRepository.findAlarmsByUser(user);
        return alarmsByUser.stream().map(alarm -> {
            AlarmDto.AlarmDtoBuilder builder = AlarmDto.builder()
                    .message(alarm.getMessage())
                    .senderId(alarm.getSenderId())
                    .senderName(alarm.getSenderName())
                    .senderImage(alarm.getSenderImg());

            if (alarm.getCommentId() != null) {
                builder.commentId(alarm.getCommentId());
            }
            if (alarm.getPostId() != null) {
                builder.postId(alarm.getPostId());
            }
            if (alarm.getOwnerId() != null) {
                builder.userId(alarm.getOwnerId());
            }

            return builder.build();
        }).collect(Collectors.toList());
    }
}
