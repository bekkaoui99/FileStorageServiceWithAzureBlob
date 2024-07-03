package com.learning.filestorageazureblob.repository;

import com.learning.filestorageazureblob.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User , Long> {

}
