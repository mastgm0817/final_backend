package final_backend.Wishlist.controller;


import final_backend.Member.model.User;
import final_backend.Member.service.UserService;
import final_backend.Wishlist.model.*;
import final_backend.Wishlist.service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/api/v1")
public class WishlistController {
    @Autowired
    private UserService userService;

    @Autowired
    private WishlistService wishlistService;

    @PostMapping("/wishlist")
    public ResponseEntity<String> saveToWishlist(@RequestBody WishlistDTO wishlistDTO) {
        System.out.println("wishlist DTO"+wishlistDTO);
        wishlistService.saveToWishlist(wishlistDTO);
        return new ResponseEntity<>("Successfully saved", HttpStatus.OK);
    }

    @PostMapping("/randomWishlist")
    public ResponseEntity<String> saveToRandomWishlist(@RequestBody RandomWishlistDTO randomWishlistDTO) {
        System.out.println("Random wishlist DTO"+randomWishlistDTO);
        wishlistService.saveToRandomWishlist(randomWishlistDTO);
        return new ResponseEntity<>("Successfully saved", HttpStatus.OK);
    }

    @GetMapping("/wishlist/{nickname}")
    public ResponseEntity<List<Course>> getWishlist(@PathVariable String nickname) {
        List<Course> courses = wishlistService.getWishlistByUser(nickname);
        return new ResponseEntity<>(courses, HttpStatus.OK);
    }

    @PostMapping("/deleteCourse")
    public ResponseEntity<?> deleteCourse(@RequestBody CourseDeleteRequestDto requestDto) {
        try {
            System.out.println("dto 내부" + requestDto.getNickName());
            System.out.println("dto 내부" + requestDto.getTargetCourse());
            wishlistService.deleteCourse(requestDto.getNickName(), requestDto.getTargetCourse());
            return ResponseEntity.ok().body("Successfully deleted.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error occurred: " + e.getMessage());
        }
    }
}

