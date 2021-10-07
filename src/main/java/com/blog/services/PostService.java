package com.blog.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.entities.Post;
import com.blog.repositories.PostRepository;

@Service
public class PostService {
   
	@Autowired
	private PostRepository postRepo;
	
	public List<Post> getAllPosts()
	{
		return postRepo.findAll();
	}
	
	public List<Post> getUserAllPosts(String username)
	{
		return postRepo.findAllByUsername(username);
	}

	public void createPost(Post post) {
		
		postRepo.save(post);
		
	}
	 
}
