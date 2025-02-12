package com.yixun.yixun_backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yixun.yixun_backend.dto.SearchinfoDTO;
import com.yixun.yixun_backend.entity.*;
import com.yixun.yixun_backend.mapper.AddressMapper;
import com.yixun.yixun_backend.mapper.SearchinfoMapper;
import com.yixun.yixun_backend.mapper.VolunteerMapper;
import com.yixun.yixun_backend.service.SearchinfoFollowupService;
import com.yixun.yixun_backend.mapper.SearchinfoFollowupMapper;
import com.yixun.yixun_backend.service.SearchinfoService;
import com.yixun.yixun_backend.utils.Result;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
* @author hunyingzhong
* @description 针对表【yixun_searchinfo_followup】的数据库操作Service实现
* @createDate 2022-12-03 12:43:39
*/
@Service
public class SearchinfoFollowupServiceImpl extends ServiceImpl<SearchinfoFollowupMapper, SearchinfoFollowup>
    implements SearchinfoFollowupService{
    @Resource
    private SearchinfoMapper searchinfoMapper;
    @Resource
    private SearchinfoService searchinfoService;
    @Resource
    private AddressMapper addressMapper;
    @Resource
    private VolunteerMapper volunteerMapper;
    @Resource
    private SearchinfoFollowupMapper searchinfoFollowupMapper;
    public Result GetFollowUpInfo(int volid, int pagenum, int pagesize)
    {
        try
        {
            Result result = new Result();
            Page<Searchinfo> page = new Page<>(pagenum, pagesize);
            QueryWrapper<Searchinfo> wrapper = new QueryWrapper<Searchinfo>();
            wrapper.inSql("SEARCHINFO_ID","select SEARCHINFO_ID from yixun_searchinfo_followup where VOL_ID ="+volid).eq("ISACTIVE","Y");
            IPage iPage = searchinfoMapper.selectPage(page,wrapper);
            List<SearchinfoDTO> dtoList=searchinfoService.cutIntoSearchinfoDTOList((List<Searchinfo>)iPage.getRecords());
            result.data.put("followup_info", dtoList);
            result.data.put("total", iPage.getTotal());
            result.data.put("getcount", iPage.getRecords().size());
            result.status = true;
            result.errorCode = 200;
            return result;
        }
        catch (Exception e) {
            return Result.error();
        }
    }
    public Result RefuseFollowUp(int volid, int search_info_id){
        try
        {
            Result result = new Result();
            Searchinfo searchinfo=searchinfoMapper.selectById(search_info_id);
            Address address =addressMapper.selectById(searchinfo.getAddressId());
            Volunteer thisVol=volunteerMapper.selectById(volid);
            SearchinfoFollowup followup = searchinfoFollowupMapper.selectOne(new QueryWrapper<SearchinfoFollowup>().eq("SEARCHINFO_ID", search_info_id));
            List<Volunteer> volList = new ArrayList<>();
            if(address!=null){
                String province=address.getProvinceId();
                String city= address.getCityId();
                String area= address.getAreaId();
                volList=volunteerMapper.selectAreaNearVolID(area);
                if (volList.isEmpty()) {
                    // List为空的处理逻辑
                    volList=volunteerMapper.selectCityNearVolID(city);
                    if (volList.isEmpty()) {
                        // List为空的处理逻辑
                        volList=volunteerMapper.selectProvinceNearVolID(province);
                    }
                }

                if (volList.isEmpty()){
                    volList=volunteerMapper.selectList(new QueryWrapper<Volunteer>());
                }
                if (volList.contains(thisVol)) {
                    volList.remove(thisVol);
                }
                if (!volList.isEmpty()){
                    Random random = new Random();
                    int randomIndex = random.nextInt(volList.size());
                    int new_volId=volList.get(randomIndex).getVolId();
                    followup.setVolId(new_volId);
                    searchinfoFollowupMapper.updateById(followup);
                    result.data.put("if_succeed","true");

                }
                else{
                    result.data.put("if_succeed","false");
                }


            }
            result.status = true;
            result.errorCode = 200;
            return result;
        }
        catch (Exception e) {
            return Result.error();
        }
    }

}




