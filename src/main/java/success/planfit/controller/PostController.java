package success.planfit.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import success.planfit.controller.utils.ControllerUtil;
import success.planfit.dto.request.CoursePostsSaveByCalendarRequestDto;
import success.planfit.dto.request.CoursePostsSaveRequestDto;
import success.planfit.dto.request.TimetableCreationRequestDto;
import success.planfit.service.PostService;

import java.security.Principal;
import java.time.LocalDate;

@Slf4j
@RestController
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@RequestMapping("/post")
public class PostController {

    private final ControllerUtil controllerUtil;
    private final PostService postService;


    // "코스 생성"하면서 포스팅
    @PostMapping("/course")
    public ResponseEntity<Void> savePostByCourse(@RequestBody CoursePostsSaveRequestDto requestDto, Principal principal){
        Long userId = controllerUtil.findUserIdByPrincipal(principal);
        postService.addCoursePostByRegisteration(userId, requestDto);
        return ResponseEntity.ok().build();
    }

    // "저장된 코스 불러"오면서 포스팅
    @PostMapping("/callcourse/{calendarId}")
    public ResponseEntity<Void> savePostByCallCourse(@PathVariable Long calendarId, @RequestBody CoursePostsSaveByCalendarRequestDto requestDto, Principal principal){
        Long userId = controllerUtil.findUserIdByPrincipal(principal);
        postService.addCoursePostByCalendar(userId, calendarId, requestDto);
        return ResponseEntity.ok().build();
    }

    // 날짜를 기준으로 시간표 삭제
    @DeleteMapping("/{postId}")
    public ResponseEntity<String> removeTimetable(@PathVariable Long postId, Principal principal) {
        Long userId = controllerUtil.findUserIdByPrincipal(principal);
        postService.deleteCoursePost(userId, postId);
        return ResponseEntity.ok("Deleted successfully");
    }






}
