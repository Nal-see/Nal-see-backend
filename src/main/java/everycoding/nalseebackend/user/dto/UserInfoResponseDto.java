package everycoding.nalseebackend.user.dto;

import everycoding.nalseebackend.user.domain.Constitution;
import everycoding.nalseebackend.user.domain.FashionStyle;
import everycoding.nalseebackend.user.domain.Gender;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class UserInfoResponseDto {
    private double height;
    private double weight;
    private Constitution constitution;
    private List<FashionStyle> style;
    private Gender gender;

}
