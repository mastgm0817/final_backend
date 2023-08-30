package final_backend.Wishlist.repository;

import final_backend.Wishlist.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishlistRepository extends JpaRepository<Course, Long> {
    void deleteByWid(Long wid);

}
