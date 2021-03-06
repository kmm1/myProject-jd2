package com.itacademy.service;


import com.itacademy.entity.Event;
import com.itacademy.entity.Flashmob;
import com.itacademy.service.common.BaseService;

import java.util.List;

public interface FlashmobService extends BaseService<Flashmob> {

    Flashmob findById(Long id);

    Long save(Flashmob flashmob);

    void update(Flashmob flashmob);

    void delete(Flashmob flashmob);

    List<Flashmob> findAll();

    List<Event> findAllEvents();


}
