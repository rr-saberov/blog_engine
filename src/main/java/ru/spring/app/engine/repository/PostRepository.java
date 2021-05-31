package ru.spring.app.engine.repository;

import org.hibernate.sql.Select;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.spring.app.engine.entity.Post;
import ru.spring.app.engine.entity.PostComments;
import ru.spring.app.engine.entity.PostVotes;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    Post getPostsById(@Param("id") Long id);

    //methods to get page post

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
            "ORDER BY (SELECT SUM (value) FROM post_votes WHERE value = 1)", nativeQuery = true)
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
            "AND text LIKE %:query% " +
            "ORDER BY time DESC", nativeQuery = true)
    Page<Post> searchInText(@Param("query") String query, Pageable pageable);

    @Query(value = "SELECT * FROM posts " +
            "JOIN tag2post ON posts.id = tag2post.post_id " +
            "JOIN tags ON tags.id = tag2post.tag_id " +
            "WHERE is_active = 1 AND moderation_status = 'ACCEPTED' " +
            "AND time <= current_date AND name = :tag", nativeQuery = true)
    Page<Post> getPostsWithTag(@Param("tag") String tag, Pageable nextPage);

    @Query("SELECT p " +
            "FROM Post p " +
            "WHERE p.isActive = 1 AND p.moderationStatus = 'NEW' " +
            "ORDER BY p.time DESC")
    Page<Post> getNewPosts(Pageable pageable);

    @Query("SELECT p " +
            "FROM Post p " +
            "WHERE p.isActive = 1 AND p.moderationStatus = 'ACCEPTED' " +
            "ORDER BY p.time DESC")
    Page<Post> getAcceptedPosts(Pageable pageable);

    @Query("SELECT p " +
            "FROM Post p " +
            "WHERE p.isActive = 1 AND p.moderationStatus = 'DECLINED' " +
            "ORDER BY p.time DESC")
    Page<Post> getDeclinedPosts(Pageable pageable);

    //methods to get user posts

    @Query(value = "SELECT * FROM posts " +
            "LEFT JOIN users on posts.user_id = users.id " +
            "WHERE is_active = -1 and email = :email", nativeQuery = true)
    Page<Post> getInactivePostsByUser(Pageable pageable, @Param("email") String email);

    @Query(value = "SELECT * FROM posts " +
            "LEFT JOIN users on posts.user_id = users.id " +
            "WHERE is_active = 1 AND moderation_status = 'NEW' AND email = :email", nativeQuery = true)
    Page<Post> getPendingPostsByUser(Pageable pageable, @Param("email") String email);

    @Query(value = "SELECT * FROM posts\n" +
            "LEFT JOIN users on posts.user_id = users.id " +
            "WHERE is_active = 1 AND moderation_status = 'DECLINED' AND email = :email", nativeQuery = true)
    Page<Post> getDeclinedPostsByUser(Pageable pageable, @Param("email") String email);

    @Query(value = "SELECT * FROM posts " +
            "LEFT JOIN users on posts.user_id = users.id " +
            "WHERE is_active = 1 AND moderation_status = 'ACCEPTED' AND email = :email", nativeQuery = true)
    Page<Post> getPublishedPostsByUser(Pageable pageable, @Param("email") String email);

    //subsidiary methods

    @Query("SELECT pv " +
            "FROM PostVotes pv " +
            "WHERE pv.postId = :id")
    List<PostVotes> getVotesForPost(@Param("id") Long id);

    @Query(value = "SELECT name " +
            "FROM users " +
            "LEFT JOIN posts on posts.user_id = users.id " +
            "WHERE users.id = :id ", nativeQuery = true)
    String getNameFromPost(@Param("id") Long id);

    @Query("SELECT pc " +
            "FROM PostComments pc " +
            "WHERE pc.postId = :id")
    List<PostComments> getCommentsForPost(@Param("id") Long id);

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

    @Transactional
    @Modifying
    @Query("UPDATE Post p set p.viewCount = :view_count WHERE p.id = :id")
    void updatePostInfo(@Param("view_count")Integer viewCount, @Param("id") Long postId);

    @Transactional
    @Modifying
    @Query("UPDATE Post p set p.moderationStatus = :moderation_status WHERE p.id = :id")
    void updatePostStatus(@Param("moderation_status") String moderationStatus, @Param("id") Long postId);

    @Query(value = "SELECT SUM (view_count) FROM posts", nativeQuery = true)
    long getTotalViewCount();

    @Query(value = "SELECT COUNT(id) " +
            "FROM post_votes " +
            "WHERE value = 1", nativeQuery = true)
    long getTotalLikesCount();

    @Query(value = "SELECT COUNT(id) " +
            "FROM post_votes " +
            "WHERE value = -1", nativeQuery = true)
    long getTotalDislikesCount();

    @Query("SELECT p " +
            "FROM Post p " +
            "ORDER BY p.time")
    List<Post> getPostsOrderByTime();
}

