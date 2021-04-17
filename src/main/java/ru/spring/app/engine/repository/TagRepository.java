package ru.spring.app.engine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.spring.app.engine.entity.Tags;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<Tags, Integer> {

/*    @Query("SELECT t " +
            "FROM Tags t " +
            "WHERE t.id <= 20 " +
            "ORDER BY t.name")*/
    @Query(value = "SELECT * " +
            "FROM tags " +
            "JOIN tag2post ON tags.id = tag2post.tags_id " +
            "JOIN posts ON posts.id = tag2post.posts_id " +
            "WHERE is_active = 1 AND moderation_status = 'ACCEPTED' AND time <= current_date " +
            "ORDER BY (SELECT DISTINCT COUNT (*))", nativeQuery = true)
    List<Tags> getTagsOrderByPopularity();

}
