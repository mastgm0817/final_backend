package final_backend.Board.controller;

import final_backend.Board.respository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import final_backend.Board.model.Post;
import final_backend.Board.service.PostService;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/posts")
public class PostController {
    @Autowired
    private  PostService postService;

    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts() {
        List<Post> posts = postService.getAllPosts();
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/{pid}")
    public ResponseEntity<Post> getPostById(@PathVariable("pid") Long pid) {
        Optional<Post> post = postService.getPostById(pid);
        increasePostView(pid);
        return post.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody Post post) {
        Post createdPost = postService.createPost(post);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPost);
    }

    @DeleteMapping("/{pid}")
    public ResponseEntity<Void> deletePost(@PathVariable("pid") Long pid) {
        postService.deletePost(pid);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{pid}")
    public ResponseEntity<Post> updatePost(@PathVariable("pid") Long pid, @RequestBody Post updatedPost) {
        Post updatedPostEntity = postService.updatePost(pid, updatedPost);
        if (updatedPostEntity != null) {
            return ResponseEntity.ok(updatedPostEntity);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{pid}/view")
    public ResponseEntity<Post> increasePostView(@PathVariable("pid") long pid){
        Post viewedPost=postService.getPostById(pid).get();
        Post updatedPostEntity = postService.increaseViewCount(pid);
        if (updatedPostEntity != null) {
            return ResponseEntity.ok(updatedPostEntity);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
