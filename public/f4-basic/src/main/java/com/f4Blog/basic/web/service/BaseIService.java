package com.f4Blog.basic.web.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.IService;

public interface BaseIService<M extends BaseMapper<T>, T> {
    boolean isUnique(T entity, String... fields);
}
