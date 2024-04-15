package everycoding.nalseebackend.post.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public class PostScoreDto {

    private PostResponseDto postResponseDto;
    private Double score;
}
