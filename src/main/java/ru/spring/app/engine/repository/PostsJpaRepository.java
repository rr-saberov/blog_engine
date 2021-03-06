package ru.spring.app.engine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.spring.app.engine.model.Posts;

public interface PostsJpaRepository extends JpaRepository<Posts, Integer> {

}
