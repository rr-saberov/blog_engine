package ru.spring.app.engine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.spring.app.engine.entity.Tags;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<Tags, Integer> {


    @Query(value = "SELECT * " +
            "FROM tags " +
            "JOIN tag2post ON tags.id = tag2post.tags_id " +
            "JOIN posts ON posts.id = tag2post.posts_id " +
            "WHERE is_active = 1 AND moderation_status = 'ACCEPTED' AND time <= current_date " +
            "ORDER BY (SELECT DISTINCT COUNT (*))", nativeQuery = true)
    List<Tags> getTagsOrderByPopularity();


    @Query(value = "SELECT COUNT (*) " +
            "FROM posts " +
            "JOIN tag2post ON posts.id = tag2post.posts_id " +
            "JOIN tags ON tags.id = tag2post.tags_id " +
            "WHERE is_active = 1 AND moderation_status = 'ACCEPTED' AND time <= current_date AND name = :tag", nativeQuery = true)
    Long getPostsCountWithTag(@Param("tag") String tag);

    @Query(value = "SELECT COUNT (*) " +
            "FROM posts " +
            "WHERE is_active = 1 AND moderation_status = 'ACCEPTED' AND time <= current_date", nativeQuery = true)
    Long getPostsCount();
}
