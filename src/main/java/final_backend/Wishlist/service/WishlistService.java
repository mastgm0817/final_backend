package final_backend.Wishlist.service;

import final_backend.Member.exception.UserNotFoundException;
import final_backend.Member.model.User;
import final_backend.Member.repository.UserRepository;
import final_backend.Wishlist.model.Course;
import final_backend.Wishlist.model.RandomWishlistDTO;
import final_backend.Wishlist.model.ResultDTO;
import final_backend.Wishlist.model.WishlistDTO;
import final_backend.Wishlist.repository.WishlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishlistService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WishlistRepository wishlistRepository;  // 이름 변경


    public void saveToWishlist(WishlistDTO wishlistDTO) {
        User user = userRepository.findByNickName(wishlistDTO.getNickname());
        System.out.println("user 찾음" + user);
        if (user == null) {
            // 만약 유저가 null 이라면, 즉 존재하지 않는다면 예외 처리
            throw new UserNotFoundException("User does not exist");
        }

        System.out.println("1단계 리스트 체크" + wishlistDTO.getResults());

        // 프론트에서 받은 데이트 코스 리스트를 유저의 리스트에 추가
        List<ResultDTO> results = wishlistDTO.getResults();
        for (ResultDTO result : results) {
            System.out.println("2단계 리스트 순회 체크" + result);

            List<Course> courses = result.getRestaurant_prediction();
            System.out.println("3단계 리스트 순회 체크" + courses);
            for (Course course : courses) {
                System.out.println("4단계 리스트 순회 체크" + course);
                course.setUserWish(user);
                wishlistRepository.save(course);
            }
        }
    }

    public void saveToRandomWishlist(RandomWishlistDTO randomWishlistDTO) {
        User user = userRepository.findByNickName(randomWishlistDTO.getNickname());
        System.out.println("user 찾음" + user);
        if (user == null) {
            // 만약 유저가 null 이라면, 즉 존재하지 않는다면 예외 처리
            throw new UserNotFoundException("User does not exist");
        }

        System.out.println("1단계 리스트 체크" + randomWishlistDTO.getResults());

        // 프론트에서 받은 데이트 코스 리스트를 유저의 리스트에 추가
        List<Course> results = randomWishlistDTO.getResults();
        for (Course result : results) {
            System.out.println("2단계 리스트 순회 체크" + result);

            if (result == null) {
                System.out.println("result null입니다.");
                continue;  // 혹은 적절한 예외 처리
            }

                System.out.println("3단계 리스트 순회 체크" + result);
            result.setUserWish(user);
                wishlistRepository.save(result);
        }
    }

    public List<Course> getWishlistByUser(String nickname) {
        // 유저 검색 (로그인한 상태이므로 무조건 존재해야 함)
        User user = userRepository.findByNickName(nickname);
        if (user == null) {
            // 예외 처리: 로그인한 유저가 아니라면 이런 상황은 발생하면 안됩니다.
            throw new RuntimeException("User not found");
        }

        // 해당 유저의 데이트 코스 리스트 반환
        return user.getDateCourses();
    }

    public void deleteCourse(String nickName, List<Course> targetCourses) throws Exception {
        // nickName으로 사용자를 먼저 찾기
        User user = userRepository.findByNickName(nickName);

        if (user == null) {
            throw new Exception("User not found with nickname: " + nickName);
        }

        // 사용자의 모든 Courses를 가져온다.
        List<Course> userCourses = user.getDateCourses();

        // 삭제할 Course를 찾고 userCourses에서 제거한다.
        for (Course targetCourse : targetCourses) {
            Long targetWid = targetCourse.getWid();

            Course courseToRemove = userCourses.stream()
                    .filter(course -> targetWid.equals(course.getWid()))
                    .findFirst()
                    .orElse(null);

            if (courseToRemove != null) {
                userCourses.remove(courseToRemove);
            }
        }

        // User 엔터티 상태를 업데이트한다.
        user.setDateCourses(userCourses);
        userRepository.save(user); // CascadeType.ALL 설정으로 인해 연관된 Course도 업데이트됨.

        // 이제 실제로 Course를 삭제한다.
        for (Course targetCourse : targetCourses) {
            Long targetWid = targetCourse.getWid();

            try {
                wishlistRepository.deleteById(targetWid);
            } catch (Exception e) {
                throw new Exception("Error while deleting course with wid: " + targetWid);
            }
        }
    }


}

