package success.planfit.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import success.planfit.domain.bookmark.CourseBookmark;
import success.planfit.domain.bookmark.TimetableBookmark;
import success.planfit.domain.course.SpaceType;
import success.planfit.domain.embeddable.SpaceInformation;
import success.planfit.photo.PhotoProvider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CourseBookmarkInfoResponseDto {

    private Long id;
    private String title;
    private String titlePhoto;
    private List<Space> spaces;

    public static CourseBookmarkInfoResponseDto from(CourseBookmark courseBookmark) {
        List<TimetableBookmark> timetableBookmarks = courseBookmark.getTimetableBookmarks();
        List<Space> spaces = new ArrayList<>();

        // TimetableBookmark로부터 Space 생성
        for (TimetableBookmark timetableBookmark : timetableBookmarks) {
            SpaceInformation spaceInformation = timetableBookmark.getSpaceInformation();
            spaces.add(Space.builder()
                    .sequence(timetableBookmark.getSequence())
                    .memo(timetableBookmark.getMemo())
                    .spaceName(spaceInformation.getSpaceName())
                    .location(spaceInformation.getLocation())
                    .spaceType(spaceInformation.getSpaceTag())
                    .link(spaceInformation.getLink())
                    .latitude(spaceInformation.getLatitude())
                    .longitude(spaceInformation.getLongitude())
                    .spacePhoto(PhotoProvider.encode(spaceInformation.getSpacePhoto()))
                    .build());
        }

        // 모은 Space를 sequence에 따라 정렬
        Collections.sort(spaces);

        return CourseBookmarkInfoResponseDto.builder()
                .id(courseBookmark.getId())
                .title(courseBookmark.getTitle())
                .titlePhoto(PhotoProvider.encode(courseBookmark.getTitlePhoto()))
                .spaces(spaces)
                .build();
    }

    @Getter
    @Builder
    @AllArgsConstructor
    static class Space implements Comparable<Space> {

        private Integer sequence;
        private String memo;
        private String spaceName;
        private String location;
        private SpaceType spaceType;
        private String link;
        private Double latitude;
        private Double longitude;
        private String spacePhoto;

        @Override
        public int compareTo(Space s) {
            return this.sequence - s.sequence;
        }
    }
}
