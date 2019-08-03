package com.lu.j1993.service.impl;

import com.lu.j1993.mapper.ImgurlMapper;
import com.lu.j1993.pojo.Imgurl;
import com.lu.j1993.service.ImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2019/8/3.
 */
@Service
public class ImgServiceImpl implements ImgService {
    @Autowired
    private  ImgurlMapper imgurlMapper;
    @Override
    public int add(Imgurl imgurl) {
        int i = imgurlMapper.insertSelective(imgurl);
        return i;
    }
}
