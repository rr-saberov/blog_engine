package ru.spring.app.engine.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.spring.app.engine.model.Posts;

import java.util.Date;

public interface PostRepository extends JpaRepository<Posts, Integer> {

    @Query("SELECT p " +
            "FROM Posts p " +
            "LEFT JOIN Users user ON user.id = p.userId " +
            "LEFT JOIN PostComments pc ON pc.postId = p.id " +
            "LEFT JOIN PostVotes pvl ON pvl.postId = p.id " +
            "WHERE p.isActive = true AND p.moderationStatus = 'ACCEPTERD' AND p.date <= CURRENT_DATE " +
            "ORDER BY p.userId")
    Page<Posts> findPostsOrderByUserId (Pageable pageable);

    @Query("SELECT p " +
            "FROM Posts p " +
            "LEFT JOIN Users u ON u.id = p.userId " +
            "LEFT JOIN PostComments pc ON pc.postId = p.id " +
            "LEFT JOIN PostVotes pvl ON pvl.postId = p.id AND pvl.value = 1 " +
            "WHERE p.isActive = true AND p.moderationStatus = 'ACCEPTED' AND p.date <= CURRENT_DATE " +
            "GROUP BY p.id " +
            "ORDER BY COUNT(pvl) DESC")
    Page<Posts> findPostsOrderByLikes(Pageable pageable);

    @Query("SELECT p " +
            "FROM Posts p " +
            "LEFT JOIN Users u ON u.id = p.userId " +
            "LEFT JOIN PostComments pc ON pc.postId = p.id " +
            "LEFT JOIN PostVotes pvl ON pvl.postId = p.id " +
            "WHERE p.isActive = true AND p.moderationStatus = 'ACCEPTED' AND p.date <= CURRENT_DATE " +
            "AND p.userId = ?2 " +
            "GROUP BY p.id")
    Page<Posts> findPostsByUserId (Pageable pageable, Integer userId);

    @Query("SELECT p " +
            "FROM Posts p " +
            "LEFT JOIN Users u ON u.id = p.userId " +
            "LEFT JOIN PostComments pc ON pc.postId = p.id " +
            "LEFT JOIN PostVotes pvl ON pvl.postId = p.id " +
            "WHERE p.isActive = true AND p.moderationStatus = 'ACCEPTED' AND p.date <= CURRENT_DATE " +
            "AND p.date = ?2 " +
            "GROUP BY p.id " +
            "ORDER BY COUNT(p) DESC")
    Page<Posts> postCountByYear(Pageable pageable, Integer year);

    @Query("SELECT p " +
            "FROM Posts p " +
            "LEFT JOIN Users u ON u.id = p.userId " +
            "LEFT JOIN PostComments pc ON pc.postId = p.id " +
            "LEFT JOIN PostVotes pvl ON pvl.postId = p.id " +
            "WHERE p.isActive = true AND p.moderationStatus = 'ACCEPTED' AND p.date <= CURRENT_DATE " +
            "AND p.date = ?2 " +
            "GROUP BY p.id ")
    Page<Posts> findPostsByDate(Pageable pageable, Date date);

    @Query("SELECT p " +
            "FROM Posts p " +
            "LEFT JOIN Users u ON u.id = p.userId " +
            "LEFT JOIN PostComments pc ON pc.postId = p.id " +
            "LEFT JOIN PostVotes pvl ON pvl.postId = p.id " +
            "LEFT JOIN Tag2Post t2p ON t2p.postId = p.id " +
            "WHERE p.isActive = true AND p.moderationStatus = 'ACCEPTED' AND p.date <= CURRENT_DATE " +
            "AND t2p.tagId  = ?2 " +
            "GROUP BY p.id ")
    Page<Posts> findPostsByTag(Pageable pageable, Integer tagId);

//    @Query("SELECT p " +
//            "FROM Posts p " +
//            "WHERE p.id = ?1 AND p.isActive = true AND p.moderationStatus = 'ACCEPTED' AND p.date <= CURRENT_DATE")
//    Posts findPostById(Integer postId);

}
