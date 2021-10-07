package com.blog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blog.entities.Post;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {

	List<Post> findAllByUsername(String username);

}
