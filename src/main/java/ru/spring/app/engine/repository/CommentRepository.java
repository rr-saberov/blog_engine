package ru.spring.app.engine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.spring.app.engine.entity.PostComments;

@Repository
public interface CommentRepository extends JpaRepository<PostComments, Long> {

}
