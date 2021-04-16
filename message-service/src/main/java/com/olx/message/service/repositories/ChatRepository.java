package com.olx.message.service.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.olx.message.service.models.Chat;
import com.olx.message.service.models.User;

@Repository
public interface ChatRepository extends CrudRepository<Chat, Long> {
	
	List<Chat> findByReceiver(User user);
	
	List<Chat> findBySender(User user);
}
