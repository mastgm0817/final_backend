package final_backend.Admin.repository;

import final_backend.Admin.model.UserBlackListDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserBlackListRepository extends JpaRepository<UserBlackListDTO,Long> {
}
