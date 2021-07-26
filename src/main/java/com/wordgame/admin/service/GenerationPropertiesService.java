package com.wordgame.admin.service;

import org.springframework.stereotype.Service;

/**
 * @author Vladimir Bratchikov
 */
@Service
public class GenerationPropertiesService {

    public int getBufferSize() {
        return 5; // TODO тянуть из БД с кешем
    }
}
