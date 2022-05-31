package cn.jxufe.repository;

import cn.jxufe.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @create: 2022-05-11 08:34
 * @author: lwz
 * @description:
 **/
public interface UserRepository extends JpaRepository<User, Long> {
    Page<User> findAllByUsernameContaining(String username, Pageable pageable);

    List<User> findAllByUsername(String username);

    User findByUsername(String username);

    User findByNickname(String nickname);

    User findAllByNickname(String nickname);

    User findByHeadImgUrl(String headImgUrl);
}
