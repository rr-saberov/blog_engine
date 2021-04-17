package ru.spring.app.engine.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.spring.app.engine.entity.Post;

import java.util.Date;
import java.util.Map;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

    Post getPostsById(Integer id);

    @Query(value = "SELECT EXTRACT(YEAR from time) as year, COUNT(*) as amount_posts " +
            "FROM posts " +
            "WHERE is_active = 1 AND moderation_status = 'ACCEPTED' AND time <= current_date " +
            "GROUP BY year " +
            "ORDER BY year", nativeQuery = true)
    Map<Integer, Long> getPostsCountOnTheDay();

    @Query(value = "SELECT EXTRACT(YEAR from time) as year, COUNT(*) as amount_posts " +
            "FROM posts " +
            "WHERE EXTRACT(YEAR from time) = :year AND is_active = 1 AND moderation_status = 'ACCEPTED' AND time <= current_date " +
            "GROUP BY year " +
            "ORDER BY year", nativeQuery = true)
    Map<Integer, Long> getPostsInYear(@Param("year") Integer year);

    @Query(value = "SELECT COUNT (*) " +
            "FROM posts " +
            "WHERE is_active = 1 AND moderation_status = 'ACCEPTED' AND time <= current_date", nativeQuery = true)
    Long getPostsCount();

    @Query(value = "SELECT COUNT (*) " +
            "FROM posts " +
            "JOIN tag2post ON posts.id = tag2post.posts_id " +
            "JOIN tags ON tags.id = tag2post.tags_id " +
            "WHERE is_active = 1 AND moderation_status = 'ACCEPTED' AND time <= current_date AND name = :tag", nativeQuery = true)
    Long getPostsCountWithTag(@Param("tag") String tag);

    @Query("SELECT p " +
            "FROM Post p " +
            "WHERE p.isActive = 1 AND p.moderationStatus = 'ACCEPTED' AND p.time <= CURRENT_DATE " +
            "AND p.time = :postDate " +
            "ORDER BY p.time DESC")
    Page<Post> getPostsPerDay(@Param("postDate") Date postDate, Pageable pageable);

    @Query(value = "SELECT * " +
            "FROM posts " +
            "WHERE is_active = 1 AND moderation_status = 'ACCEPTED' AND time <= current_date " +
            "ORDER BY (SELECT SUM (post_id) FROM post_comments)", nativeQuery = true)
    Page<Post> getPostsOrderByCommentCount(Pageable pageable);

    @Query(value = "SELECT * " +
            "FROM posts " +
            "WHERE is_active = 1 AND moderation_status = 'ACCEPTED' AND time <= current_date " +
            "ORDER BY (SELECT SUM (value) FROM post_votes WHERE value = 1);", nativeQuery = true)
    Page<Post> getPostsOrderByLikeCount(Pageable pageable);

    @Query("FROM Post p " +
            "WHERE p.isActive = 1 AND p.moderationStatus = 'ACCEPTED' AND p.time <= CURRENT_DATE " +
            "ORDER BY p.time")
    Page<Post> getPostsOrderByTime(Pageable pageable);

    @Query("FROM Post p " +
            "WHERE p.isActive = 1 AND p.moderationStatus = 'ACCEPTED' AND p.time <= CURRENT_DATE " +
            "ORDER BY p.time DESC")
    Page<Post> getOldPostsOrderByTime(Pageable pageable);

    @Query(value = "SELECT * " +
            "FROM posts " +
            "WHERE is_active = 1 AND moderation_status = 'ACCEPTED' AND time <= current_date " +
            "AND text LIKE :query " +
            "ORDER BY time DESC", nativeQuery = true)
    Page<Post> getPostsByCustomRequest(@Param("query") String query, Pageable pageable);

    @Query(value = "SELECT * FROM posts " +
            "JOIN tag2post ON posts.id = tag2post.posts_id " +
            "JOIN tags ON tags.id = tag2post.tags_id " +
            "WHERE is_active = 1 AND moderation_status = 'ACCEPTED' " +
            "AND time <= current_date AND name = :tag", nativeQuery = true)
    Page<Post> getPostsWithTag(@Param("tag") String tag, Pageable nextPage);


}

