package com.kobi.memo.post.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kobi.memo.common.FileManager;
import com.kobi.memo.post.domain.Post;
import com.kobi.memo.post.repository.PostRepository;

import jakarta.persistence.PersistenceException;

@Service
public class PostService {
	
	private final PostRepository postRepository;
	
	public PostService(PostRepository postRepository) {
		this.postRepository = postRepository;
	}

	public boolean addPost(int userId, String title, String contents, MultipartFile file) {
		
		String urlPath = FileManager.saveFile(userId, file);
		
		Post post = Post.builder()
		.userId(userId)
		.title(title)
		.contents(contents)
		.imagePath(urlPath)
		.build();
		
		try {			
			postRepository.save(post);
		} catch(PersistenceException e) {
			return false;
		}
		
		return true;
	}
	
	public List<Post> getPostList(int userId) {
		return postRepository.findByUserIdOrderByIdDesc(userId);
	}
	
	public Post getPost(int id) {
		
		Optional<Post> optionalPost = postRepository.findById(id);
		
		return optionalPost.orElse(null);
	}
	
	public boolean updatePost(int id, String title, String contents) {
		Optional<Post> optionalPost = postRepository.findById(id);
		
		if(optionalPost.isPresent()) {
			
			Post post = optionalPost.get();
			
			post = post.toBuilder()
			.title(title)
			.contents(contents)
			.build();
			
			try {
				postRepository.save(post);
			}catch(PersistenceException e) {
				return false;
			}
			
		}else {
			return false;
		}
		return true;
		
	}
	
	public boolean deletePost(int id) {
		
		Optional<Post> optionalPost = postRepository.findById(id);
		
		if(optionalPost.isPresent()) {
			
			Post post = optionalPost.get();
			
			FileManager.removeFile(post.getImagePath());
			
			try {				
				postRepository.delete(post);
			} catch(PersistenceException e) {
				return false;
			}
			
		} else {
			return false;
		}
		
		return true;
	}
	
}