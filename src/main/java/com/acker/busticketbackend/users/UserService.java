package com.acker.busticketbackend.users;

import com.acker.busticketbackend.auth.user.User;
import com.acker.busticketbackend.auth.user.UserRepository;
import com.acker.busticketbackend.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserDetailsService userDetailsService;
    private final UserRepository userRepository;

    /**
     * Returns authenticated user's Details
     */
    public UserDetailResponse getAuthenticatedUser() {


        var user = (User) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();


        return UserDetailResponse.builder()
                .username(user.getUsername())
                .build();
    }

    public UserDetailResponse getUser(Integer id) throws Exception {
        var user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));


        return UserDetailResponse.builder()
                .username(user.getUsername())
                .build();
    }


    public ListUserResponse getAllUsers() {
        var users = StreamSupport.stream(userRepository.findAll().spliterator(), false)
                .map(user -> UserListItem.builder()
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .id(user.getId())
                        .username(user.getUsername())
                        .build()
                ).collect(Collectors.toList());
        return ListUserResponse.builder()
                .users(users)
                .build();

    }
}
