package ru.spring.app.engine.service;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import ru.spring.app.engine.api.response.PostResponse;
import ru.spring.app.engine.model.Posts;
import ru.spring.app.engine.repository.PostRepository;

import java.sql.ResultSet;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PostService {

    private PostRepository postRepository;
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public PostService(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public PostResponse getPosts() {
        PostResponse postResponse = new PostResponse();
        postResponse.setAnnounce("test");
        postResponse.setCommentCount(5);
        return postResponse;
    }

    public List<Posts> getSearchedPosts(String query) {
        List<Posts> filteredList = jdbcTemplate.query(query, (ResultSet rs, int rowNum) -> {
            Posts posts = new Posts();
            posts.setId(rs.getInt("id"));
            posts.setModeratorId(rs.getInt("moderatorId"));
            posts.setDate(rs.getDate("date"));
            posts.setText(rs.getString("text"));
            posts.setViewCount(rs.getInt("viewCount"));
            return posts;
        });

        return filteredList;
    }

    public Map<Integer, Integer> getPostsCountInYear() {
        Map<Integer, Integer> map = new HashMap<>();
        List<Posts> postsList = postRepository.findAll();
        for (Posts posts : postsList) {
            map.put(map.size() + 1, posts.getDate().getYear());
        }
        return map;
    }

    public Map<Integer, Date> getPostsByDate(Date postDate) {
        Map<Integer, Date> postsByDate = new HashMap<>();
        List<Posts> postsList = postRepository.findAll();
        for (Posts posts : postsList) {
            if (posts.getDate().equals(postDate)) {
                postsByDate.put(posts.getId(), postDate);
            }
        }
        return postsByDate;
    }

    public Posts getPostById(Integer id) {
        return postRepository.getOne(id);
    }
}
