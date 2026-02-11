package org.com.programming.animal.jpa;


import org.com.programming.animal.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Query("SELECT emails FROM UserEntity emails WHERE emails.emailUser LIKE %:email%")
    UserEntity findByEmailUser(String email);

    boolean existsByEmailUser(String emailUser);
}
