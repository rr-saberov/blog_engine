package ru.spring.app.engine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.spring.app.engine.model.Posts;

@Repository
public interface PostRepository extends JpaRepository <Posts, Integer> {
}
