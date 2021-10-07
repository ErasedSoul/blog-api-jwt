package com.blog.controllers;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.blog.entities.Post;
import com.blog.entities.User;
import com.blog.models.AuthenticationRequest;
import com.blog.models.AuthenticationResponse;
import com.blog.services.PostService;
import com.blog.services.UserService;
import com.blog.util.JwtUserDetailsService;
import com.blog.util.JwtUtil;

@RestController
public class BlogController {
      
	  @Autowired
	  private PostService postService;
	   
	  @Autowired
	  private UserService userService;
	  
	  @Autowired
	  private AuthenticationManager authenticationManager;
	  
	  @Autowired
	  private JwtUserDetailsService userDetailsService; 
	  
	  @Autowired
	  private JwtUtil jwtTokenUtil;
	  
	  @GetMapping(value="/")
	  public String index()
	  {
		  return "index";
	  }
	  
	  @PostMapping(value = "/auth/register")
	  public String register(@RequestBody AuthenticationRequest use) {
		  
		  System.out.println(use.getUsername()+" "+use.getPassword());
		  if(userService.getUser((String)use.getUsername()) != null)
			  return "Not Registered same name exits";
		  
		  User newUser = new User(use.getUsername(),use.getPassword());
		  userService.save(newUser);
		  return "User Registered";
	  }
	  
	  @GetMapping(value="/posts")
	  public List<Post> allPost()
	  {
		  return postService.getAllPosts();
	  }
	  
	  
	  @RequestMapping("posts/{username}")
	  public @ResponseBody List<Post> getPostByUsername(@PathVariable(value="username") String username){
		  
		  return postService.getUserAllPosts(username);  
	  }
	  
	  @PostMapping(value="/post")
	  public String createPost(@RequestBody Post post)
	  {
		  if(post.getDate() == null)
			  post.setDate(new Date());
		  postService.createPost(post);
		  
		  return "Post was published";
		  
	  }
	  
	  @RequestMapping(value = "/auth/authenticate", method = RequestMethod.POST)
	  public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
		  System.out.println("CCCCC");
			try {
				
				User user = userService.getUser(authenticationRequest.getUsername());
				
				if(user == null || ((user.getPassword()).equals(authenticationRequest.getPassword())) ==  false)
					throw new BadCredentialsException(null);
					
				
//				authenticationManager.authenticate(
//						new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
			}
			catch (BadCredentialsException e) {
				System.out.println("CCCCC");
				throw new Exception("Incorrect username or password", e);
			}


			final UserDetails userDetails = userDetailsService
					.loadUserByUsername(authenticationRequest.getUsername());

			final String jwt = jwtTokenUtil.generateToken(userDetails);

			return ResponseEntity.ok(new AuthenticationResponse(jwt));
		}
	  
	  
}
