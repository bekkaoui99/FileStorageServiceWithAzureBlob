package com.learning.filestorageazureblob.service;

import org.springframework.data.domain.Page;

import java.util.List;

public interface CrudService<REQUEST , RESPONSE , ID> {

    RESPONSE create(REQUEST request);
    RESPONSE update(REQUEST request , ID id);
    RESPONSE delete(ID id);
    RESPONSE getOne(ID id);
    List<RESPONSE> getAll();
    Page<RESPONSE> getAll(int pageNumber , int pageSize);


}
