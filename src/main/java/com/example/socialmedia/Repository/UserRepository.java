package com.example.socialmedia.Repository;


import com.example.socialmedia.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,String>
{

    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

    Optional<User> findOneByEmailAndPassword(String email, String password);

    List<User> findAllByPrivateAccFalse();

}
