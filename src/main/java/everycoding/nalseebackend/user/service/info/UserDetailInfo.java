package everycoding.nalseebackend.user.service.info;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class UserDetailInfo {
    private String username;
    private Double height;
    private Double weight;
    private String constitution;
    private List<String> style;
    private String gender;
}
