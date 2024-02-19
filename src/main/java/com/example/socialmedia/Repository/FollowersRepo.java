package com.example.socialmedia.Repository;

import com.example.socialmedia.followTables.Followers;
import com.example.socialmedia.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowersRepo extends JpaRepository<Followers,Long> {
    List<Followers> findAllByUser1(User user);

    @Query("SELECT e FROM Followers e WHERE e.user1 = :user1 AND e.user2 = :user2")
    Followers findByUser1AndUser2(@Param("user1") User user1, @Param("user2") User user2);


}
