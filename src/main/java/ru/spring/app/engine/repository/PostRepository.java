package ru.spring.app.engine.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.spring.app.engine.api.response.PostsResponse;
import ru.spring.app.engine.entity.Posts;

import java.util.Date;
import java.util.List;

public interface PostRepository extends JpaRepository<Posts, Integer> {

    @Query("SELECT p " +
            "FROM Posts p " +
            "LEFT JOIN Users u ON u.id = p.userId " +
            "LEFT JOIN PostComments pc ON pc.postId = p.id " +
            "LEFT JOIN PostVotes pvl ON pvl.postId = p.id AND pvl.value = 1 " +
            "WHERE p.isActive = 1 AND p.moderationStatus = 'NEW' AND p.time <= CURRENT_DATE " +
            "GROUP BY p.id " +
            "ORDER BY p.time DESC")
    List<Posts> getPostsByDate();

    @Query("SELECT p " +
            "FROM Posts p " +
            "LEFT JOIN Users u ON u.id = p.userId " +
            "LEFT JOIN PostComments pc ON pc.postId = p.id " +
            "LEFT JOIN PostVotes pvl ON pvl.postId = p.id AND pvl.value = 1 " +
            "WHERE p.isActive = 1 AND p.moderationStatus = 'ACCEPTED' AND p.time <= CURRENT_DATE " +
            "GROUP BY p.id " +
            "ORDER BY COUNT(pc) DESC")
    List<Posts> getPostsByCommentCount(Pageable pageable);


    @Query("SELECT p " +
            "FROM Posts p " +
            "LEFT JOIN Users u ON u.id = p.userId " +
            "LEFT JOIN PostComments pc ON pc.postId = p.id " +
            "LEFT JOIN PostVotes pvl ON pvl.postId = p.id AND pvl.value = 1 " +
            "WHERE p.isActive = 1 AND p.moderationStatus = 'ACCEPTED' AND p.time <= CURRENT_DATE " +
            "GROUP BY p.id " +
            "ORDER BY COUNT(pvl) DESC")
    List<Posts> getPostsByLikes(Pageable pageable);

    @Query("SELECT p " +
            "FROM Posts p " +
            "LEFT JOIN Users u ON u.id = p.userId " +
            "LEFT JOIN PostComments pc ON pc.postId = p.id " +
            "LEFT JOIN PostVotes pvl ON pvl.postId = p.id AND pvl.value = 1 " +
            "WHERE p.isActive = 1 AND p.moderationStatus = 'ACCEPTED' AND p.time <= CURRENT_DATE " +
            "GROUP BY p.id " +
            "ORDER BY p.time ASC")
    List<Posts> getOldPosts(Pageable pageable);

    @Query("SELECT COUNT (p) " +
            "FROM Posts p")
    Integer getPostsCount();





    @Query("SELECT p " +
            "FROM Posts p " +
            "LEFT JOIN Users u ON u.id = p.userId " +
            "LEFT JOIN PostComments pc ON pc.postId = p.id " +
            "LEFT JOIN PostVotes pvl ON pvl.postId = p.id " +
            "WHERE p.isActive = 1 AND p.moderationStatus = 'ACCEPTED' AND p.time <= CURRENT_DATE " +
            "AND p.time = ?1 " +
            "GROUP BY p.id " +
            "ORDER BY COUNT(p) DESC")
    Integer postCountByYear(Integer year);
////
//    @Query
//    List<Post>
//
//    @Query("SELECT SUM (Posts.id)" +
//            "FROM Posts p " +
//            "WHERE p.isActive = true AND p.moderationStatus = 'ACCEPTED' AND p.date <= CURRENT_DATE " +
//            "AND p.date >= ?1 ")
//    Integer postCountByYear(Integer year);

///*    @Query("SELECT p " +
//            "FROM Posts p " +
//            "LEFT JOIN Users u ON u.id = p.userId " +
//            "LEFT JOIN PostComments pc ON pc.postId = p.id " +
//            "LEFT JOIN PostVotes pvl ON pvl.postId = p.id " +
//            "WHERE p.isActive = true AND p.moderationStatus = 'ACCEPTED' AND p.date <= CURRENT_DATE " +
//            "AND p.date = ?1 " +
//            "GROUP BY p.id ")*/


/*    Page<Posts> findPostsByDate(Pageable pageable, Date date);*/

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

