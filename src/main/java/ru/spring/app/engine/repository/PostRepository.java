package ru.spring.app.engine.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.spring.app.engine.api.response.PostResponse;
import ru.spring.app.engine.entity.Posts;

import java.util.Date;

public interface PostRepository extends JpaRepository<Posts, Integer> {

    @Query("SELECT p " +
            "FROM Posts p " +
            "LEFT JOIN Users u ON u.id = p.userId " +
            "LEFT JOIN PostComments pc ON pc.postId = p.id " +
            "LEFT JOIN PostVotes pvl ON pvl.postId = p.id AND pvl.value = true " +
            "WHERE p.isActive = true AND p.moderationStatus = 'ACCEPTED' AND p.date <= CURRENT_DATE " +
            "GROUP BY p.id " +
            "ORDER BY p.date DESC")
    Page<PostResponse> getAllPostsByDate(Pageable pageable);

    @Query("SELECT p " +
            "FROM Posts p " +
            "LEFT JOIN Users u ON u.id = p.userId " +
            "LEFT JOIN PostComments pc ON pc.postId = p.id " +
            "LEFT JOIN PostVotes pvl ON pvl.postId = p.id AND pvl.value = true " +
            "WHERE p.isActive = true AND p.moderationStatus = 'ACCEPTED' AND p.date <= CURRENT_DATE " +
            "GROUP BY p.id " +
            "ORDER BY COUNT(pvl) DESC")
    Page<PostResponse> getPostsByLikes(Pageable pageable);

    @Query("SELECT p " +
            "FROM Posts p " +
            "LEFT JOIN Users u ON u.id = p.userId " +
            "LEFT JOIN PostComments pc ON pc.postId = p.id " +
            "LEFT JOIN PostVotes pvl ON pvl.postId = p.id AND pvl.value = true " +
            "WHERE p.isActive = true AND p.moderationStatus = 'ACCEPTED' AND p.date <= CURRENT_DATE " +
            "GROUP BY p.id " +
            "ORDER BY p.date ASC")
    Page<PostResponse> getAllOldPostsByDate(Pageable pageable);

    @Query("SELECT p " +
            "FROM Posts p " +
            "LEFT JOIN Users u ON u.id = p.userId " +
            "LEFT JOIN PostComments pc ON pc.postId = p.id " +
            "LEFT JOIN PostVotes pvl ON pvl.postId = p.id AND pvl.value = true " +
            "WHERE p.isActive = true AND p.moderationStatus = 'ACCEPTED' AND p.date <= CURRENT_DATE " +
            "GROUP BY p.id " +
            "ORDER BY COUNT(pc) DESC")
    Page<PostResponse> getPostsByCommentCount(Pageable pageable);

    @Query("SELECT p " +
            "FROM Posts p " +
            "LEFT JOIN Users u ON u.id = p.userId " +
            "LEFT JOIN PostComments pc ON pc.postId = p.id " +
            "LEFT JOIN PostVotes pvl ON pvl.postId = p.id " +
            "WHERE p.isActive = true AND p.moderationStatus = 'ACCEPTED' AND p.date <= CURRENT_DATE " +
            "AND p.date = ?1 " +
            "GROUP BY p.id " +
            "ORDER BY COUNT(p) DESC")
    Integer postCountByYear(Integer year);

//    @Query("SELECT SUM (Posts.id)" +
//            "FROM Posts p " +
//            "WHERE p.isActive = true AND p.moderationStatus = 'ACCEPTED' AND p.date <= CURRENT_DATE " +
//            "AND p.date >= ?1 ")
//    Integer postCountByYear(Integer year);

    @Query("SELECT p " +
            "FROM Posts p " +
            "LEFT JOIN Users u ON u.id = p.userId " +
            "LEFT JOIN PostComments pc ON pc.postId = p.id " +
            "LEFT JOIN PostVotes pvl ON pvl.postId = p.id " +
            "WHERE p.isActive = true AND p.moderationStatus = 'ACCEPTED' AND p.date <= CURRENT_DATE " +
            "AND p.date = ?1 " +
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

}

