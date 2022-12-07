package com.yixun.yixun_backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yixun.yixun_backend.dto.ClueDTO;
import com.yixun.yixun_backend.dto.VolActivityDTO;
import com.yixun.yixun_backend.entity.Address;
import com.yixun.yixun_backend.entity.Clue;
import com.yixun.yixun_backend.entity.VolActivity;
import com.yixun.yixun_backend.service.ClueService;
import com.yixun.yixun_backend.mapper.ClueMapper;
import com.yixun.yixun_backend.utils.TimeTrans;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
* @author hunyingzhong
* @description 针对表【yixun_clue】的数据库操作Service实现
* @createDate 2022-12-03 12:43:39
*/
@Service
public class ClueServiceImpl extends ServiceImpl<ClueMapper, Clue>
    implements ClueService{
    public ClueDTO cutIntoClueDTO(Clue clue){
        ClueDTO dto=new ClueDTO();
        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        dto.setClueId(clue.getClueId());
        dto.setClueContent(clue.getClueContent());
        dto.setClueDate(TimeTrans.myToString(clue.getClueDate()));
        dto.setSearchinfoId(clue.getSearchinfoId());
        return dto;
    }

    public List<ClueDTO> cutIntoClueDTOList(List<Clue> clueList){
        List<ClueDTO> dtoList=new ArrayList<>();
        for(Clue clue : clueList){
            ClueDTO dto=cutIntoClueDTO(clue);
            dtoList.add(dto);
        }
        return dtoList;
    }
}




