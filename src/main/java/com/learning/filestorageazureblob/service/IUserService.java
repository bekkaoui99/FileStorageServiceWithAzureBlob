package com.learning.filestorageazureblob.service;

import com.learning.filestorageazureblob.dto.request.UserRequest;
import com.learning.filestorageazureblob.dto.response.UserResponse;
import com.learning.filestorageazureblob.entity.User;

public interface IUserService extends CrudService<UserRequest, UserResponse , Long>{

}
