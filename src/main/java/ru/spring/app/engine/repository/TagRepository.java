package ru.spring.app.engine.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.spring.app.engine.entity.Tags;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<Tags, Integer> {

    @Query("SELECT t " +
            "FROM Tags t " +
            "WHERE t.id <= 20 " +
            "ORDER BY t.name")
    List<Tags> getTagsOrderByName();
}
