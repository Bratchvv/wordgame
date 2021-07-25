package com.wordgame.admin.service;

import org.springframework.stereotype.Service;

/**
 * @author Vladimir Bratchikov
 */
@Service
public class GenerationPropertiesService {

    public int getBufferSize() {
        return 100; // TODO тянуть из БД с кешем
    }
}
