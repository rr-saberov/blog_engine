package ru.spring.app.engine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.spring.app.engine.model.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {

}
