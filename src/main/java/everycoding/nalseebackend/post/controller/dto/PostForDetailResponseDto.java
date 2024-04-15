package everycoding.nalseebackend.post.controller.dto;

import everycoding.nalseebackend.comment.controller.dto.CommentResponseDto;
import everycoding.nalseebackend.user.domain.UserDetail;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class PostForDetailResponseDto {
    private PostResponseDto postResponseDto;
    private UserDetail userDetail;
    private List<CommentResponseDto> comments;
}
