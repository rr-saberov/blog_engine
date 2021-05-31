package ru.spring.app.engine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.spring.app.engine.entity.PostVotes;

import javax.transaction.Transactional;
import java.util.Date;

@Repository
public interface PostVotesRepository extends JpaRepository<PostVotes, Long> {

    PostVotes findByUserId(Long id);

    @Transactional
    @Query(value = "INSERT INTO post_votes (post_id, time, user_id, value ) " +
            "VALUES (:post_id, :time, :user_id, 1)", nativeQuery = true)
    void addLike(@Param("post_id") Long postId, @Param("time") Date time, @Param("user_id") Long userId);

    @Transactional
    @Modifying
    @Query("UPDATE PostVotes pv set pv.value = 1 WHERE pv.userId = :id")
    void changeDislikeToLike(@Param("id") Long userId);

    @Transactional
    @Query(value = "INSERT INTO post_votes (post_id, time, user_id, value ) " +
            "VALUES (:post_id, :time, :user_id, -1)", nativeQuery = true)
    void addDislike(@Param("post_id") Long postId, @Param("time") Date time, @Param("user_id") Long userId);

    @Transactional
    @Modifying
    @Query("UPDATE PostVotes pv set pv.value = -1 WHERE pv.userId = :id")
    void changeLikeToDislike(@Param("id") Long userId);

}
