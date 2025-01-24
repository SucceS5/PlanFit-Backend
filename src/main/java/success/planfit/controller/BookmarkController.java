package success.planfit.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import success.planfit.controller.utils.ControllerUtil;
import success.planfit.dto.request.SpaceBookmarkDeleteRequestDto;
import success.planfit.dto.request.SpaceBookmarkRegistrationRequestDto;
import success.planfit.dto.response.SpaceBookmarkInfoResponseDto;
import success.planfit.service.BookmarkService;

import java.security.Principal;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/bookmark")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class BookmarkController {

    private final BookmarkService bookmarkService;
    private final ControllerUtil util;

    @PostMapping("/space")
    public ResponseEntity<Void> registerSpace(Principal principal, @RequestBody SpaceBookmarkRegistrationRequestDto requestDto) {
        log.info("BookmarkController.registerSpace() called");

        Long userId = util.findUserIdByPrincipal(principal);
        bookmarkService.registerSpaceBookmark(userId, requestDto);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/space")
    public ResponseEntity<Void> deleteSpace(Principal principal, @RequestBody SpaceBookmarkDeleteRequestDto requestDto) {
        log.info("BookmarkController.deleteSpace() called");

        Long userId = util.findUserIdByPrincipal(principal);
        bookmarkService.deleteSpaceBookmark(userId, requestDto);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/space")
    public ResponseEntity<List<SpaceBookmarkInfoResponseDto>> findAllSpaceBookmarks(Principal principal) {
        log.info("BookmarkController.findAllSpaceBookmarks() called");

        Long userId = util.findUserIdByPrincipal(principal);
        List<SpaceBookmarkInfoResponseDto> responseDto = bookmarkService.findAllSpaceBookmarks(userId);

        return ResponseEntity.ok(responseDto);
    }
}