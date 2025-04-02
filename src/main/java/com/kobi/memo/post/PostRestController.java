package com.kobi.memo.post;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kobi.memo.post.service.PostService;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/post")
@RestController
public class PostRestController {
	
	private final PostService postService;
	
	public PostRestController(PostService postService) {
		this.postService = postService;
	}
	
	@PostMapping("/create")
	public Map<String, String> createMemo(
			@RequestParam String title
			, @RequestParam String contents
			, @RequestParam(required=false) MultipartFile imageFile // 이 항목이 필수가 아니다라는것을 추가
			, HttpSession session) {
		
		int userId = (Integer)session.getAttribute("userId");
		
		Map<String, String> resultMap = new HashMap<>();
		if(postService.addPost(userId, title, contents, imageFile)) {
			resultMap.put("result", "success");
		} else {
			resultMap.put("result", "fail");
		}
		
		return resultMap;
	}
	
	@PutMapping("/update")
	public Map<String, String> updatePost(
			@RequestParam int id
			, @RequestParam String title
			, @RequestParam String contents) {
		
		Map<String, String> resultMap = new HashMap<>();
		if(postService.updatePost(id, title, contents)) {
			resultMap.put("result", "success");
		} else {
			resultMap.put("result", "fail");
		}
		return resultMap;
		
	}
	
	@DeleteMapping("/delete")
	public Map<String, String> deletePost(@RequestParam int id) {
		
		Map<String, String> resultMap = new HashMap<>();
		
		if(postService.deletePost(id)) {
			resultMap.put("result", "success");
		} else {
			resultMap.put("result", "fail");
		}
		
		return resultMap;
	}
}
