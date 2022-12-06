package com.yixun.yixun_backend.controller;

import com.yixun.yixun_backend.dto.NewsDTO;
import com.yixun.yixun_backend.mapper.AddressMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TestController {

    @Autowired
    private AddressMapper addressMapper;

    @GetMapping("/hello")
    public String hello()
    {
        return "hello world";
    }

    @GetMapping("/address")
    //查询所有用户
    public List query()
    {
        List<NewsDTO> newsDTOList=new ArrayList<>();
        NewsDTO newsDTO=new NewsDTO();
        newsDTO.setNewsContent("jhggj");
        newsDTO.setTitle("sdjfsdjfjsh");
        newsDTO.setCover("jhggj");
        newsDTO.setNewsId(11);
        newsDTOList.add(newsDTO);
        return newsDTOList;
    }
}
