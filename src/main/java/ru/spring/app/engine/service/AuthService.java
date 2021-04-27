package ru.spring.app.engine.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import ru.spring.app.engine.api.response.AuthResponse;
import ru.spring.app.engine.api.response.RegistrationResponse;
import ru.spring.app.engine.entity.Users;
import ru.spring.app.engine.repository.UserRepository;

import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AuthService {

    private UserRepository userRepository;

    @Autowired
    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

/*    public AuthResponse authResponse(Users user) {
        AuthResponse authResponse = new AuthResponse();

        Map<String, String> usersMap = userRepository.findAll().stream()
                .collect(Collectors.toMap(Users::getName, Users::getPassword));

        authResponse.setResult
                (usersMap.containsKey(user.getName()) && usersMap.containsValue(user.getPassword()));

        return authResponse;
    }*/

    public RegistrationResponse newUserRegistration(String email, String password, String name,
                                    String captcha, String captchaSecret) {
        Users user = new Users();
        user.setEmail(email);
        user.setIsModerator(-1);
        user.setPassword(password);
        user.setName(name);
        user.setRegTime(new Date(System.currentTimeMillis()));
        userRepository.save(user);
        return new RegistrationResponse(email, password, name, captcha, captchaSecret);
    }
/*

    public RegistrationResponse registrationResponse(Users user) {
        return null;
    }
*/

/*    public PostsResponse getPostsByTag(Integer page, Integer limit, String tag) {
        Pageable nextPage = PageRequest.of(page, limit);
        PostsResponse postsResponse = new PostsResponse();
        Page<Post> postsPage =
                postRepository.getPostsWithTag(tag, nextPage);
        postsResponse.setCount(postsPage.getTotalElements());
        postsResponse.setPosts(postsPage.get().map(this::convertToResponse).collect(Collectors.toList()));
        return postsResponse;
    }*/

    private RegistrationResponse convertToResponse() {
        return null;
    }

/*    private SinglePostResponse convertToResponse(Post post) {
        SinglePostResponse postResponse = new SinglePostResponse();
        UserResponse userResponse = new UserResponse();
        Timestamp timestamp = new Timestamp(post.getTime().getTime());
        userResponse.setId(post.getUserId());
        userResponse.setName(post.getUsersId().getName());
        postResponse.setId(post.getId());
        postResponse.setTimestamp(timestamp.getTime());
        postResponse.setTitle(post.getText());
        postResponse.setAnnounce(post.getText());
        postResponse.setLikeCount(post.getPostVotes().getValue());
        postResponse.setDislikeCount(4);
        postResponse.setCommentCount(5);
        postResponse.setViewCount(post.getViewCount());
        postResponse.setUserResponse(userResponse);
        return postResponse;
    }*/
}
