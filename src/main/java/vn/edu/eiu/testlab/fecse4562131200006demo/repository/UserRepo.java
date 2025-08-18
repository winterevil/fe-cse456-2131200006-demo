package vn.edu.eiu.testlab.fecse4562131200006demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.eiu.testlab.fecse4562131200006demo.model.User;

import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<User,Long> {
    User searchUserByUsernameIgnoreCase(String username);
}
