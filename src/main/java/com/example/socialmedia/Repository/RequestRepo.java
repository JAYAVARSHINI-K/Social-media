package com.example.socialmedia.Repository;

import com.example.socialmedia.followTables.Request;
import com.example.socialmedia.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface RequestRepo extends JpaRepository<Request,Long>{
    @Query("SELECT e FROM Request e WHERE e.user1 = :user1 AND e.user2 = :user2")
    Request findByUser1AndUser2(@Param("user1") User user1, @Param("user2") User user2);

}
