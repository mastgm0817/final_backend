package final_backend.OAuth.model.token.repository;

import final_backend.Member.model.User;
import final_backend.OAuth.model.token.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<RefreshToken, Long> {

    /* 토큰 존재 유무 확인 */
    Optional<RefreshToken> findByKey(Long key);
    Optional<RefreshToken> findByToken(String token);
}