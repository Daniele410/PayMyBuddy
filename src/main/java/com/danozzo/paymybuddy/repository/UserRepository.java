package com.danozzo.paymybuddy.repository;

import com.danozzo.paymybuddy.model.User;
import com.danozzo.paymybuddy.web.dto.FriendDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<FriendDto> findByFriendsId(Long id);

    void deleteByFriendsId(Long id);

    User findByEmail(String email);

    Boolean existsByEmail(String email);


}
