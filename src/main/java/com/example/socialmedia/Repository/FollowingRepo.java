package com.example.socialmedia.Repository;

import com.example.socialmedia.followTables.Following;
import com.example.socialmedia.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowingRepo extends JpaRepository<Following,Long> {
    List<Following> findAllByUser1(User user);

    @Query("SELECT e FROM Following e WHERE e.user1 = :user1 AND e.user2 = :user2")
    Following findByUser1AndUser2(@Param("user1") User user1, @Param("user2") User user2);

}
