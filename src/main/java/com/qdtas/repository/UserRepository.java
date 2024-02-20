package com.qdtas.repository;

import com.qdtas.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {

    public User findByEmail(String email);


    @Query(value = "select * from user u where concat(u.first_name, ' ', u.last_name)" +
            " like %:name% order by u.first_name asc, u.last_name asc", nativeQuery = true)
    List<User> findUsersByName(String name, Pageable pageable);

}