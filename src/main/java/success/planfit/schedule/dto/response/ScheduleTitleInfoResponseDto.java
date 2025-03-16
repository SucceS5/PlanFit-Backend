package success.planfit.schedule.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import success.planfit.entity.course.Course;
import success.planfit.entity.schedule.Schedule;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ScheduleTitleInfoResponseDto {

    private final String title;
    private final LocalDate date;
    private final LocalTime startTime;
    private final String location;

    public static ScheduleTitleInfoResponseDto from(Schedule schedule) {
        Course course = schedule.getCourse();

        return ScheduleTitleInfoResponseDto.builder()
                .title(schedule.getTitle())
                .date(schedule.getDate())
                .startTime(schedule.getStartTime())
                .location(course.getLocation())
                .build();
    }

}
