package com.yixun.yixun_backend.service;

import com.yixun.yixun_backend.dto.ClueDTO;
import com.yixun.yixun_backend.entity.Clue;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yixun.yixun_backend.utils.Result;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

/**
* @author hunyingzhong
* @description 针对表【yixun_clue】的数据库操作Service
* @createDate 2022-12-03 12:43:39
*/
public interface ClueService extends IService<Clue> {
    ClueDTO cutIntoClueDTO(Clue clue);
    List<ClueDTO> cutIntoClueDTOList(List<Clue> clueList);
    Boolean deleteClue(int clueID);
    public String GetCluesNumber();
    public Result GetAllCLuesPublished(int user_id, int pageNum, int pageSize);
    public Result AddOneClue(@RequestBody Map<String, Object> inputMap);
    public Result DeleteClueByUser(int userid, int clueid);
}
