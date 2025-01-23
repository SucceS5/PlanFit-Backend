package success.planfit.domain.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
public class UserDto {

    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    private LocalDate birthOfDate;
    private IdentityType identity;
    private String password;
    private String profilePhoto;

    public static UserDto from(User user) {
        return UserDto.builder()
                .name(user.getName())
                .profilePhoto(user.getProfilePhoto())
                .birthOfDate(user.getBirthOfDate())
                .name(user.getName())
                .phoneNumber(user.getPhoneNumber())
                .identity(user.getIdentity())
                .build();
    }


}
