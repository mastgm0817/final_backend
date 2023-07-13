package final_backend.Board.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import final_backend.Board.model.Post;
import final_backend.Board.respository.PostRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Optional<Post> getPostById(Long pid) {
        return postRepository.findById(pid);
    }

    public Post createPost(Post post) {
        LocalDateTime currentTime = LocalDateTime.now();
        post.setCreatedAt(currentTime);
        post.setUpdatedAt(currentTime);
        post.setViews(0L);
        post.setRecommendations(0L);
        return postRepository.save(post);
    }

    public void deletePost(Long pid) {
        postRepository.deleteById(pid);
    }

    public Post updatePost(Long pid, Post updatedPost) {
        Optional<Post> postOptional = postRepository.findById(pid);
        if (postOptional.isPresent()) {
            Post existingPost = postOptional.get();
            existingPost.setTitle(updatedPost.getTitle());
            existingPost.setContent(updatedPost.getContent());
            existingPost.setAuthor(updatedPost.getAuthor());
            existingPost.setUpdatedAt(LocalDateTime.now());
            return postRepository.save(existingPost);
        } else {
            return null;
        }
    }
}