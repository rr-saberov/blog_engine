package ru.spring.app.engine.controller;

import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.spring.app.engine.api.request.EditProfileRequest;
import ru.spring.app.engine.api.request.SettingsRequest;
import ru.spring.app.engine.api.response.*;
import ru.spring.app.engine.exceptions.AccessIsDeniedException;
import ru.spring.app.engine.service.ImageStorage;
import ru.spring.app.engine.service.PostService;
import ru.spring.app.engine.service.SettingsService;
import ru.spring.app.engine.service.UserService;

import java.io.IOException;
import java.security.Principal;

@Api
@RestController
@RequestMapping("/api")
public class ApiGeneralController {

    private final SettingsService settingsService;
    private final PostService postService;
    private final InitResponse initResponse;
    private final ImageStorage storage;
    private final UserService userService;

    public ApiGeneralController(SettingsService settingsService, PostService postService,
                                InitResponse initResponse, ImageStorage storage, UserService userService) {
        this.settingsService = settingsService;
        this.postService = postService;
        this.initResponse = initResponse;
        this.storage = storage;
        this.userService = userService;
    }

    @GetMapping("/init")
    public ResponseEntity<InitResponse> init() {
        return ResponseEntity.ok(initResponse);
    }

    @GetMapping("/settings")
    public ResponseEntity<SettingsResponse> settings() {
        return ResponseEntity.ok(settingsService.getGlobalSettings());
    }

    @PutMapping("/settings")
    @PreAuthorize("hasAuthority('user:moderate')")
    public ResponseEntity<Boolean> updateSettings(SettingsRequest request) {
        return ResponseEntity.ok(settingsService.updateGlobalSettings(request));
    }

    @GetMapping("/calendar")
    public ResponseEntity<CalendarResponse> getPostCountInYear(@RequestParam(defaultValue = "") Integer year) {
        return ResponseEntity.ok(postService.getPostsCountInTheYear(year));
    }

    @PostMapping("/image")
    @PreAuthorize("hasAuthority('user:write')")
    public String saveImage(@RequestBody MultipartFile file) throws IOException {
        String savePath = storage.saveNewImage(file);
        return (savePath);
    }

    @PostMapping("/profile/my")
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<EditProfileResponse> editProfile(@RequestBody EditProfileRequest request, Principal principal) {
        return ResponseEntity.ok(userService.updateProfile(request, principal));
    }

    @GetMapping("/statistics/my")
    @PreAuthorize("hasAuthority('user:write')")  /*TODO: переделать*/
    public ResponseEntity<StatisticsResponse> getMyStatistics(Principal principal) throws AccessIsDeniedException {
        if (settingsService.getGlobalSettings().isStatisticsIsPublic()) {
            return ResponseEntity.ok(postService.getStatistics(principal.getName()));
        } else {
            throw new AccessIsDeniedException("access is denied");
        }
    }

    @GetMapping("/statistics/all")
    @PreAuthorize("hasAuthority('user:moderate')")
    public ResponseEntity<StatisticsResponse> getStatistics(Principal principal) throws AccessIsDeniedException {
        if (settingsService.getGlobalSettings().isStatisticsIsPublic()) {
            return ResponseEntity.ok(postService.getStatistics(principal.getName()));
        } else {
            throw new AccessIsDeniedException("access is denied");
        }
    }


}
