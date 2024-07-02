package com.learning.filestorageazureblob;

import com.learning.filestorageazureblob.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User , Long> {

}
