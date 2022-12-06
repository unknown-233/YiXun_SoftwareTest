package com.yixun.yixun_backend.service;

import com.yixun.yixun_backend.dto.SerachinfoDTO;
import com.yixun.yixun_backend.entity.Searchinfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author hunyingzhong
* @description 针对表【yixun_searchinfo】的数据库操作Service
* @createDate 2022-12-03 12:43:39
*/
public interface SearchinfoService extends IService<Searchinfo> {
    SerachinfoDTO cutIntoSerachinfoDTO(Searchinfo searchinfo);
    List<SerachinfoDTO> cutIntoSerachinfoDTOList(List<Searchinfo> searchinfoList);
}
