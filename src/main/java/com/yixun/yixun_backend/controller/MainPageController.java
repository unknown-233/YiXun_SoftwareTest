package com.yixun.yixun_backend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yixun.yixun_backend.dto.SearchinfoDTO;
import com.yixun.yixun_backend.entity.*;
import com.yixun.yixun_backend.mapper.AddressMapper;
import com.yixun.yixun_backend.mapper.ClueMapper;
import com.yixun.yixun_backend.mapper.SearchinfoMapper;
import com.yixun.yixun_backend.mapper.VolunteerMapper;
import com.yixun.yixun_backend.service.SearchinfoService;
import com.yixun.yixun_backend.utils.Result;
import com.yixun.yixun_backend.utils.TimeTrans;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

@RequestMapping("/api/MainPage")
@RestController
public class MainPageController {
    @Resource
    private SearchinfoMapper searchinfoMapper;
    @Resource
    private SearchinfoService searchinfoService;
    @Resource
    private ClueMapper clueMapper;
    @Resource
    private VolunteerMapper volunteerMapper;
    @Resource
    private AddressMapper addressMapper;
    //首页展示所有寻人信息
    @GetMapping("/GetAllSearchInfo")
    public Result GetAllSearchInfo(int pageNum, int pageSize)
    {
        try
        {
            Result result = new Result();
            Page<Searchinfo> page = new Page<>(pageNum, pageSize);
            QueryWrapper<Searchinfo> wrapper = new QueryWrapper<Searchinfo>();
            List<SearchinfoDTO> dtoList=new ArrayList<>();
            wrapper.eq("ISACTIVE","Y").orderByDesc("SEARCHINFO_DATE");
            IPage iPage = searchinfoMapper.selectPage(page,wrapper);
            dtoList=searchinfoService.cutIntoSearchinfoDTOList((List<Searchinfo>)iPage.getRecords());
            result.data.put("searchInfo_list", dtoList);
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

    //已发布项目，寻人信息总数
    @GetMapping("/GetSearchInfoNum")
    public String GetSearchInfoNum()
    {
        try
        {
            QueryWrapper<Searchinfo> wrapper = new QueryWrapper<Searchinfo>();
            String count=String.valueOf(searchinfoMapper.selectCount(wrapper));
            return count;
        }
        catch (Exception e) {
            return null;
        }
    }
    //已获得线索，线索总数
    @GetMapping("/GetCluesNum")
    public String GetCluesNum()
    {
        try
        {
            QueryWrapper<Clue> wrapper = new QueryWrapper<Clue>();
            String count=String.valueOf(clueMapper.selectCount(wrapper));
            return count;
        }
        catch (Exception e) {
            return null;
        }
    }

    //累计已帮助，就是已经找到的信息
    @GetMapping("/GetFoundInfoNum")
    public String GetFoundInfoNum()
    {
        try
        {
            QueryWrapper<Searchinfo> wrapper = new QueryWrapper<Searchinfo>();
            wrapper.eq("SOUGHT_PEOPLE_STATE","已找到");
            String count=String.valueOf(searchinfoMapper.selectCount(wrapper));
            return count;
        }
        catch (Exception e) {
            return null;
        }
    }

    //注册志愿者数
    @GetMapping("/GetVolunteerNum")
    public String GetVolunteerNum()
    {
        try
        {
            QueryWrapper<Volunteer> wrapper = new QueryWrapper<Volunteer>();
            String count=String.valueOf(volunteerMapper.selectCount(wrapper));
            return count;
        }
        catch (Exception e) {
            return null;
        }
    }
    @PostMapping("/ScreenSearchInfo")
    public Result ScreenSearchInfo(@RequestBody Map<String,Object> inputData){
        try{
            Result result=new Result();
            String search_type_1 = (String) inputData.get("search_type_1");
            String search_type_2 = (String) inputData.get("search_type_2");
            String search_type_3 = (String) inputData.get("search_type_3");
            String search_type_4 = (String) inputData.get("search_type_4");
            String search_type_5 = (String) inputData.get("search_type_5");
            String search_type_6 = (String) inputData.get("search_type_6");
            String gender = (String) inputData.get("gender");
            String birthday = (String) inputData.get("birthday");
            String lostdate = (String) inputData.get("lostdate");
            int height_low = (int) inputData.get("height_low");
            int height_high = (int) inputData.get("height_high");
            int pageNum = (int) inputData.get("pageNum");
            int pageSize = (int) inputData.get("pageSize");
            String search = (String) inputData.get("search");
            String province = (String) inputData.get("province");
            String city = (String) inputData.get("city");
            String area = (String) inputData.get("area");
            QueryWrapper<Searchinfo> wrapper=new QueryWrapper<Searchinfo>();
            if (gender!=null)
            {
                wrapper.eq("SOUGHT_PEOPLE_GENDER",gender);
            }
            if (birthday!=null)
            {
                Date tmp1=new SimpleDateFormat("yyyy-MM-dd").parse(birthday);
                String tmp11=TimeTrans.myToString(tmp1);
//                Searchinfo tmpinfo=searchinfoMapper.selectById(1);
//                Date tmp2=tmpinfo.getSearchinfoDate();
                wrapper.eq("SOUGHT_PEOPLE_BIRTHDAY", tmp11);

            }
            if (lostdate!=null)
            {
                Date tmp2=new SimpleDateFormat("yyyy-MM-dd").parse(lostdate);
//                Date noedate=new Date();
                String tmp22=TimeTrans.myToString(tmp2);
//                Searchinfo tmpinfo=searchinfoMapper.selectById(1);
//                Date tmp2=tmpinfo.getSearchinfoDate();
                wrapper.eq("SEARCHINFO_LOSTDATE", tmp22);
            }
            wrapper.between("SOUGHT_PEOPLE_HEIGHT",height_low,height_high);
            if (!Objects.equals(search, ""))
            {
                wrapper.and(i -> i.like("SOUGHT_PEOPLE_NAME",search).or().like("SOUGHT_PEOPLE_DETAIL",search));

            }

            if (province!=null)
            {
                wrapper.isNotNull("ADDRESS_ID");
                wrapper.inSql("ADDRESS_ID","select ADDRESS_ID from yixun_address where PROVINCE_ID="+province);

            }
            if (city!=null)
            {
                wrapper.inSql("ADDRESS_ID","select ADDRESS_ID from yixun_address where CITY_ID="+city);
            }
            if (area!=null)
            {
                wrapper.inSql("ADDRESS_ID","select ADDRESS_ID from yixun_address where AREA_ID="+area);
            }
            wrapper.like("SOUGHT_PEOPLE_NAME",search).or().like("SOUGHT_PEOPLE_DETAIL",search);
            if(!(search_type_1==null && search_type_2==null && search_type_3==null&&search_type_4==null && search_type_5==null&& search_type_6==null)){
                wrapper.and(i ->i.eq("SEARCH_TYPE",search_type_1).or().eq("SEARCH_TYPE",search_type_2).or().eq("SEARCH_TYPE",search_type_3).or().eq("SEARCH_TYPE",search_type_4).or().eq("SEARCH_TYPE",search_type_5).or().eq("SEARCH_TYPE",search_type_6));
            }
            Page<Searchinfo> page = new Page<>(pageNum, pageSize);
            IPage<Searchinfo> iPage = searchinfoMapper.selectPage(page,wrapper);
            List<SearchinfoDTO> list =searchinfoService.cutIntoSearchinfoDTOList((List<Searchinfo>)iPage.getRecords());
            result.data.put("searchInfo_list", list);
            result.data.put("getcount", list.size());
            result.data.put("total", iPage.getTotal());
            result.errorCode = 200;
            result.status = true;
            return result;
        }
        catch (Exception e) {
            return Result.error();
        }
    }
    //该地址码处的寻人信息
    @GetMapping("/GetSearchInfoPos")
    public Result GetSearchInfoPos(String infoAd){
        try{
            Result result=new Result();
            List<SearchinfoDTO> list=new ArrayList<SearchinfoDTO>();
            if(infoAd!=null){
                int count = 0;
                List<Address> addressList =addressMapper.selectList(new QueryWrapper<Address>().eq("AREA_ID",infoAd));
                if(addressList.size()>0){
                    for(int i=0;i<addressList.size();i++){
                        int addressId=addressList.get(i).getAddressId();
                        Searchinfo searchinfo=searchinfoMapper.selectOne(new QueryWrapper<Searchinfo>().eq("ADDRESS_ID",addressId));
                        if(searchinfo!=null){
                            if(searchinfo.getIsactive().equals("Y")){
                                SearchinfoDTO searchinfoDTO=searchinfoService.cutIntoSearchinfoDTO(searchinfo);
                                list.add(searchinfoDTO);
                            }
                        }

                    }
                }


            }
            result.data.put("aroundSearchInfo", list);
            result.status = true;
            result.errorCode = 200;
            return result;
        }
        catch (Exception e) {
            return null;
        }
    }

}
