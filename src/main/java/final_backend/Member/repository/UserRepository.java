package final_backend.Member.repository;

import final_backend.Member.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    User findByUid(Long uid);

    User findByNickName(String name);
    /* 이메일이 Login ID의 역할을 하기 때문에 이메일로 계정 찾는 메소드 구현 */

    /* 중복 가입 방지용 */
    boolean existsByEmail(String email);

    String findByProviderName(String providerName);

    boolean existsByNickName(String nickName);
    List<User> findAllByNickName(String nickName);
}
