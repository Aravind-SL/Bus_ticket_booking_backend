package com.acker.busticketbackend.users;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/users")
@CrossOrigin(maxAge = 3600)
@RequiredArgsConstructor
public class UsersController {

    private final UserService userService;

    /**
     * Return the authenticated user's detail
     */
    @GetMapping("/me")
    public ResponseEntity<UserDetailResponse> getUserMe() {

        return ResponseEntity.ok(
                userService.getAuthenticatedUser()
        );

    }

    /**
     * Return the user's detail for the give ID.
     *
     * @param id The id of the user.
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserDetailResponse> getUserId(@PathVariable Integer id) throws Exception {
        return ResponseEntity.ok(
                userService.getUser(id)
        );
    }

    /**
     * Returns all the users' details
     */
    @GetMapping
    public ResponseEntity<ListUserResponse> getUser() {
        return ResponseEntity.ok(
                userService.getAllUsers()
        );
    }

}
