package com.example.socialmedia.Repository;

import com.example.socialmedia.followTables.FriendsList;
import com.example.socialmedia.model.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendsListRepo extends MongoRepository<FriendsList, ObjectId> {
    FriendsList findByUser(User user);
}
