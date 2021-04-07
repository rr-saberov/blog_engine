package ru.spring.app.engine.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.spring.app.engine.api.response.CalendarResponse;
import ru.spring.app.engine.api.response.PostInDayResponse;
import ru.spring.app.engine.api.response.PostsResponse;
import ru.spring.app.engine.entity.Posts;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface PostRepository extends JpaRepository<Posts, Integer> {

    @Query("SELECT p " +
            "FROM Posts p " +
            "LEFT JOIN Users u ON u.id = p.userId " +
            "LEFT JOIN PostComments pc ON pc.postId = p.id " +
            "LEFT JOIN PostVotes pvl ON pvl.postId = p.id AND pvl.value = 1 " +
            "WHERE p.isActive = 1 AND p.moderationStatus = 'ACCEPTED' AND p.time <= CURRENT_DATE " +
            "GROUP BY p.id " +
            "ORDER BY p.time DESC")
    List<Posts> getPostsOrderByDate();

    @Query("SELECT p " +
            "FROM Posts p " +
            "LEFT JOIN Users u ON u.id = p.userId " +
            "LEFT JOIN PostComments pc ON pc.postId = p.id " +
            "LEFT JOIN PostVotes pvl ON pvl.postId = p.id AND pvl.value = 1 " +
            "WHERE p.isActive = 1 AND p.moderationStatus = 'ACCEPTED' AND p.time <= CURRENT_DATE " +
            "GROUP BY p.id " +
            "ORDER BY p.time DESC")
    List<Posts> getPostsByDate();

    @Query("SELECT COUNT (p) " +
            "FROM Posts p " +
            "WHERE p.isActive = 1 AND p.moderationStatus = 'ACCEPTED' AND p.time <= CURRENT_DATE ")
    Integer getPostsCount();


    @Query(value = "SELECT EXTRACT(YEAR from time) as year, COUNT(*) as amount_posts " +
            "FROM posts " +
            "GROUP BY year " +
            "ORDER BY year", nativeQuery = true)
    List<PostInDayResponse> getPostsCountOnTheDay();

    @Query(value = "SELECT EXTRACT(YEAR from time) as year, COUNT(*) as amount_posts " +
            "FROM posts " +
            "WHERE year = :year " +
            "GROUP BY year " +
            "ORDER BY year", nativeQuery = true)
    List<PostInDayResponse> getPostsInYear(@Param("year") Integer year);


    @Query("SELECT p.time " +
            "FROM Posts p " +
            "GROUP BY p.time " +
            "ORDER BY p.time")
    List<Integer> getYears();

    @Query(value = ":query", nativeQuery = true)
    List<Posts> getPostsByUserQuery(@Param("query") String query);


    @Query("SELECT p " +
            "FROM Posts p " +
            "JOIN Tags t " +
            "WHERE p.isActive = 1 AND p.moderationStatus = 'ACCEPTED' AND p.time <= CURRENT_DATE " +
            "AND t.name = :tag " +
            "GROUP BY p.id " +
            "ORDER BY p.time DESC")
    List<Posts> getPostsByTag(@Param("tag") String tag);

    @Query("SELECT p " +
            "FROM Posts p " +
            "WHERE p.isActive = 1 AND p.moderationStatus = 'ACCEPTED' AND p.time <= CURRENT_DATE " +
            "AND p.time = :postDate")
    List<Posts> getPostsInDay(@Param("postDate") Date postDate);

    @Query("SELECT COUNT (p) " +
            "FROM Posts p " +
            "WHERE p.isActive = 1 AND p.moderationStatus = 'ACCEPTED' AND p.time <= CURRENT_DATE " +
            "AND p.time = :postDate")
    Integer getPostsCountOnDay(@Param("postDate") Date postDate);

    Posts getPostsById(Integer id);



    @Query("SELECT p " +
            "FROM Posts p " +
            "LEFT JOIN Users u ON u.id = p.userId " +
            "LEFT JOIN PostComments pc ON pc.postId = p.id " +
            "LEFT JOIN PostVotes pvl ON pvl.postId = p.id " +
            "LEFT JOIN Tag2Post t2p ON t2p.postId = p.id " +
            "WHERE p.isActive = 1 AND p.moderationStatus = 'ACCEPTED' AND p.time <= CURRENT_DATE " +
            "AND t2p.tagId  = ?2 " +
            "GROUP BY p.id ")
    Page<Posts> findPostsByTag(Pageable pageable, Integer tagId);

}

