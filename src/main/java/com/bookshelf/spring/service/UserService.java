package com.bookshelf.spring.service;

import com.bookshelf.spring.entity.User;
import com.bookshelf.spring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserServiceIterface {

    @Autowired
    UserRepository userRepository;

    @Override
    public User findOne(Integer userId) {
        return userRepository.getOne(userId);
    }

    @Override
    public Page<User> findAll(int pageNum) {
        return userRepository.findAll(
                new PageRequest(pageNum, 5));
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User save(User users) {
        return userRepository.save(users);
    }

    @Override
    public void remove(Integer id) {
        userRepository.deleteById(id);
    }

	@Override
	public User getUserByName(String name) {
		return userRepository.findByUsername(name);
	}

    @Override
    public Page<User> pretraga(String username, int page) {
        return userRepository.pretraga(username,new PageRequest(page,5));
    }
}
