package com.yixun.yixun_backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yixun.yixun_backend.dto.FollowVolDTO;
import com.yixun.yixun_backend.dto.SearchinfoDTO;
import com.yixun.yixun_backend.entity.*;
import com.yixun.yixun_backend.mapper.*;
import com.yixun.yixun_backend.service.ClueService;
import com.yixun.yixun_backend.service.SearchinfoService;
import com.yixun.yixun_backend.utils.OssUploadService;
import com.yixun.yixun_backend.utils.Result;
import com.yixun.yixun_backend.utils.TimeTrans;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
* @author hunyingzhong
* @description 针对表【yixun_searchinfo】的数据库操作Service实现
* @createDate 2022-12-03 12:43:39
*/
@Service
public class SearchinfoServiceImpl extends ServiceImpl<SearchinfoMapper, Searchinfo>
    implements SearchinfoService{
    @Resource
    private AddressMapper addressMapper;
    @Resource
    private SearchinfoMapper searchinfoMapper;
    @Resource
    private SearchinfoFollowupMapper searchinfoFollowupMapper;
    @Resource
    private VolunteerMapper volunteerMapper;
    @Resource
    private ClueMapper clueMapper;
    @Resource
    private WebUserMapper webUserMapper;
    @Resource
    private ClueService clueService;
    @Resource
    private InfoReportMapper infoReportMapper;
    @Resource
    private OssUploadService ossUploadService;
    public SearchinfoDTO cutIntoSearchinfoDTO(Searchinfo searchinfo){
        SearchinfoDTO dto=new SearchinfoDTO();
        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        dto.setSearch_info_id(searchinfo.getSearchinfoId());
        dto.setSought_people_name(searchinfo.getSoughtPeopleName());
        Address address=addressMapper.selectById(searchinfo.getAddressId());
        dto.setArea_id(address.getAreaId());
        dto.setCity_id(address.getCityId());
        dto.setProvince_id(address.getProvinceId());
        dto.setDetail(address.getDetail());
        dto.setSearch_info_lostdate(TimeTrans.myToString(searchinfo.getSearchinfoLostdate()));
        dto.setSearch_info_photourl(searchinfo.getSearchinfoPhotoUrl());
        dto.setSought_people_birthday(TimeTrans.myToString(searchinfo.getSoughtPeopleBirthday()));
        dto.setSearch_type(searchinfo.getSearchType());
        dto.setSought_people_gender(searchinfo.getSoughtPeopleGender());
        dto.setContact_method(searchinfo.getContactMethod());

        return dto;
    }

    public List<SearchinfoDTO> cutIntoSearchinfoDTOList(List<Searchinfo> searchinfoList){
        List<SearchinfoDTO> dtoList=new ArrayList<>();
//        错误处理
        if(searchinfoList!=null){
            for(Searchinfo searchinfo : searchinfoList){
                SearchinfoDTO dto= cutIntoSearchinfoDTO(searchinfo);
                dtoList.add(dto);
            }
        }
        return dtoList;
    }

    public Result GetAllSearchInfo(int pageNum, int pageSize)
    {
        try
        {
            Result result = new Result();
            Page<Searchinfo> page = new Page<>(pageNum, pageSize);
            QueryWrapper<Searchinfo> wrapper = new QueryWrapper<Searchinfo>();
            List<SearchinfoDTO> dtoList=new ArrayList<>();
            wrapper.eq("ISACTIVE","Y").eq("WHETHER_FOUND","N").orderByDesc("SEARCHINFO_DATE");
            IPage iPage = searchinfoMapper.selectPage(page,wrapper);
            dtoList=cutIntoSearchinfoDTOList((List<Searchinfo>)iPage.getRecords());
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
    public String GetSearchInfoNumber()
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
    public String GetFoundNumber()
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
    public Result SelectSearchInfoByConditions(@RequestBody Map<String,Object> inputData){
        try{
            Result result=new Result();
            String search_type_1 = (String) inputData.get("search_type_1")!=null? (String) inputData.get("search_type_1"):"pass";
            String search_type_2 = (String) inputData.get("search_type_2")!=null? (String) inputData.get("search_type_2"):"pass";
            String search_type_3 = (String) inputData.get("search_type_3")!=null? (String) inputData.get("search_type_3"):"pass";
            String search_type_4 = (String) inputData.get("search_type_4")!=null? (String) inputData.get("search_type_4"):"pass";
            String search_type_5 = (String) inputData.get("search_type_5")!=null? (String) inputData.get("search_type_5"):"pass";
            String search_type_6 = (String) inputData.get("search_type_6")!=null? (String) inputData.get("search_type_6"):"pass";

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
            String tmp2="";
            String tmp1="";
            if (gender!=null)
            {
                wrapper.eq("SOUGHT_PEOPLE_GENDER",gender);
            }
            if (birthday!=null)
            {
                Date date1=new SimpleDateFormat("yyyy-MM-dd").parse(birthday);
                tmp1=TimeTrans.myToString(date1);
//                Searchinfo tmpinfo=searchinfoMapper.selectById(1);
//                Date tmp2=tmpinfo.getSearchinfoDate();
                wrapper.eq("SOUGHT_PEOPLE_BIRTHDAY", tmp1);

            }
            if (lostdate!=null)
            {
                Date date2=new SimpleDateFormat("yyyy-MM-dd").parse(lostdate);
//                Date noedate=new Date();
                tmp2=TimeTrans.myToString(date2);
//                Searchinfo tmpinfo=searchinfoMapper.selectById(1);
//                Date tmp2=tmpinfo.getSearchinfoDate();
                //wrapper.eq("SEARCHINFO_LOSTDATE", date2);
                wrapper.inSql("SEARCHINFO_LOSTDATE","select SEARCHINFO_LOSTDATE from yixun_searchinfo where SEARCHINFO_LOSTDATE='"+tmp2+"'");
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
            if(!(Objects.equals(search_type_1, "pass") && Objects.equals(search_type_2, "pass") && Objects.equals(search_type_3, "pass")&&Objects.equals(search_type_4, "pass") && Objects.equals(search_type_5, "pass")&& Objects.equals(search_type_6, "pass"))){
                wrapper.and(i ->i.eq("SEARCH_TYPE",search_type_1).or().eq("SEARCH_TYPE",search_type_2).or().eq("SEARCH_TYPE",search_type_3).or().eq("SEARCH_TYPE",search_type_4).or().eq("SEARCH_TYPE",search_type_5).or().eq("SEARCH_TYPE",search_type_6));
            }
            //wrapper.and(i ->i.eq("SEARCH_TYPE",search_type_1).or().eq("SEARCH_TYPE",search_type_2).or().eq("SEARCH_TYPE",search_type_3).or().eq("SEARCH_TYPE",search_type_4).or().eq("SEARCH_TYPE",search_type_5).or().eq("SEARCH_TYPE",search_type_6));

            Page<Searchinfo> page = new Page<>(pageNum, pageSize);
            IPage<Searchinfo> iPage = searchinfoMapper.selectPage(page,wrapper);
            //List<Searchinfo> list =iPage.getRecords();
            List<SearchinfoDTO> list =cutIntoSearchinfoDTOList(iPage.getRecords());
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
    public Result GetSearchInfoInPosition(String infoAd){
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
                                SearchinfoDTO searchinfoDTO=cutIntoSearchinfoDTO(searchinfo);
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
    public Result GetSearchInfoDetail(int search_id)
    {
        Result result = new Result();
        try
        {
            Searchinfo searchinfo=searchinfoMapper.selectById(search_id);
            result.data.put("search_id", search_id);//data是字典，这是向result里面加输出的信息
            result.data.put("search_name", searchinfo.getSoughtPeopleName());
            result.data.put("search_birthday", TimeTrans.myToString(searchinfo.getSoughtPeopleBirthday()));
            result.data.put("search_gender", searchinfo.getSoughtPeopleGender());
            result.data.put("search_detail", searchinfo.getSoughtPeopleDetail());
            result.data.put("search_photo", searchinfo.getSearchinfoPhotoUrl());
            QueryWrapper<Clue> clueWrapper = new QueryWrapper<Clue>();
            clueWrapper.eq("SEARCHINFO_ID",search_id).eq("ISACTIVE","Y");
            List<Clue> clueDTOList=clueMapper.selectList(clueWrapper);
            result.data.put("search_clue",clueService.cutIntoClueDTOList(clueDTOList));
            List<FollowVolDTO> followVolDTO=webUserMapper.selectVolDTOByInfoID(search_id);
            result.data.put("search_vols",followVolDTO);
            result.data.put("whether_found",searchinfo.getWhetherFound());
            if (searchinfo.getAddressId() != null)
            {
                Address address=addressMapper.selectById(searchinfo.getAddressId());
                result.data.put("search_province", address.getProvinceId());
                result.data.put("search_city", address.getCityId());
                result.data.put("search_area", address.getAreaId());
                result.data.put("search_address", address.getDetail());
            }
            else
            {
                result.data.put("search_province", null);
                result.data.put("search_city", null);
                result.data.put("search_area", null);
                result.data.put("search_address", null);
            }

            result.status = true;
            result.errorCode = 200;
            return result;
        }
        catch (Exception e) {
//            错误处理
            result.status=false;
            return Result.error();
        }
    }
    public Result GetAllSearchInfoPublished(int user_id,int pageNum, int pageSize)
    {
        Result result = new Result();
        try
        {
            Page<Searchinfo> page = new Page<>(pageNum, pageSize);
            QueryWrapper<Searchinfo> wrapper = new QueryWrapper<Searchinfo>();
            wrapper.eq("USER_ID",user_id).eq("ISACTIVE","Y");
            IPage iPage = searchinfoMapper.selectPage(page,wrapper);
            List<SearchinfoDTO> dtoList=cutIntoSearchinfoDTOList((List<Searchinfo>)iPage.getRecords());
            result.data.put("searchInfo_list", dtoList);
            result.data.put("total", iPage.getTotal());
            result.data.put("getcount", iPage.getRecords().size());
            result.status = true;
            result.errorCode = 200;
            return result;
        }
        catch (Exception e) {
//            错误处理
            result.status = false;
            return Result.error();
        }
    }
    public Result AddSearchInfo(@RequestBody Map<String, Object> inputMap)
    {
        try
        {
            Result result=new Result();
            String user_idStr = (String)inputMap.get("user_id");
            int user_id=Integer.parseInt(user_idStr);
            String search_type =(String)inputMap.get("search_type");
            String sought_people_name = (String)inputMap.get("sought_people_name");
            String sought_people_gender =(String)inputMap.get("sought_people_gender");
            String sought_people_height =(String)inputMap.get("sought_people_height");
            String sought_people_detail = (String)inputMap.get("sought_people_detail");
            String sought_people_birthday =(String)inputMap.get("sought_people_birthday");
            String sought_people_state = (String)inputMap.get("sought_people_state");
            String isreport = (String)inputMap.get("isreport");
            String searchinfo_lostdate = (String)inputMap.get("searchinfo_lostdate");
            String contact_method = (String)inputMap.get("contact_method");

            Searchinfo newSearchInfo = new Searchinfo();
            newSearchInfo.setUserId(user_id);
            newSearchInfo.setSearchType(search_type);
            newSearchInfo.setSoughtPeopleName(sought_people_name);
            newSearchInfo.setSoughtPeopleGender(sought_people_gender);
            newSearchInfo.setSoughtPeopleHeight(sought_people_height);
            newSearchInfo.setSoughtPeopleDetail(sought_people_detail);
            newSearchInfo.setSoughtPeopleBirthday(new SimpleDateFormat("yyyy-MM-dd").parse(sought_people_birthday));
            newSearchInfo.setSoughtPeopleState(sought_people_state);
            newSearchInfo.setIsreport(isreport);
            newSearchInfo.setSearchinfoLostdate(new SimpleDateFormat("yyyy-MM-dd").parse(searchinfo_lostdate));
            newSearchInfo.setContactMethod(contact_method);

            String province_id = (String)inputMap.get("province_id");
            String city_id = (String)inputMap.get("city_id");
            String area_id = (String)inputMap.get("area_id");
            String address_detail = (String)inputMap.get("address_detail");
            if(province_id!=""){
                Address address=new Address();
                address.setProvinceId(province_id);
                address.setCityId(city_id);
                address.setAreaId(area_id);
                address.setDetail(address_detail);
                addressMapper.insert(address);
                List<Address> tmpList=addressMapper.selectList(new QueryWrapper<Address>().orderByDesc("ADDRESS_ID"));
                Address newAddress=tmpList.get(0);
                newSearchInfo.setAddressId(newAddress.getAddressId());
            }
            searchinfoMapper.insert(newSearchInfo);
            List<Searchinfo> tmp=searchinfoMapper.selectList(new QueryWrapper<Searchinfo>().orderByDesc("SEARCHINFO_ID"));
            Searchinfo newSearchinfo=tmp.get(0);
            result.data.put("searchInfo_id", newSearchinfo.getSearchinfoId());
            SearchinfoFollowup follow=new SearchinfoFollowup();
            follow.setSearchinfoId(newSearchinfo.getSearchinfoId());
            List<Volunteer> volList = new ArrayList<>();

            if(province_id!=""){
                volList=volunteerMapper.selectAreaNearVolID(area_id);
                if (volList.isEmpty()) {
                    // List为空的处理逻辑
                    volList=volunteerMapper.selectCityNearVolID(city_id);
                    if (volList.isEmpty()) {
                        // List为空的处理逻辑
                        volList=volunteerMapper.selectProvinceNearVolID(province_id);
                    }
                }
                if (volList.isEmpty()){
                    follow.setVolId(volunteerMapper.selectRandomOne().getVolId());
                }
                else{
                    Random random = new Random();
                    int randomIndex = random.nextInt(volList.size());
                    follow.setVolId(volList.get(randomIndex).getVolId());
                }
            }
            follow.setVolId(volunteerMapper.selectRandomOne().getVolId());
            searchinfoFollowupMapper.insert(follow);
            result.errorCode = 200;
            result.status = true;
            return result;
        }
        catch (Exception e) {
            return Result.error();
        }
    }
    public Result AddSearchInfoPic(@RequestBody Map<String, Object> inputData){
        try{
            Result result=new Result();
            String idKey = "searchInfo_id";
            int searchInfo_id = 0;
            String img_base64Key = "searchInfo_pic";
            String img_base64 = "";
            if (inputData.containsKey(idKey)) {
                searchInfo_id = (int) inputData.get(idKey);
            }
            if (inputData.containsKey(img_base64Key)) {
                img_base64 = (String) inputData.get(img_base64Key);
            }
            Searchinfo searchinfo = searchinfoMapper.selectOne(new QueryWrapper<Searchinfo>().eq("SEARCHINFO_ID", searchInfo_id));
            String type = "." + img_base64.split(",")[0].split(";")[0].split("/")[1];
            img_base64 = img_base64.split("base64,")[1];

            byte[] img_bytes = Base64.getDecoder().decode(img_base64);
            ByteArrayInputStream stream = new ByteArrayInputStream(img_bytes, 0, img_bytes.length);

            String path = "searchInfo_pic/" + Integer.toString(searchInfo_id) + type;
            ossUploadService.uploadfile(stream, path);
            String imgurl = "https://yixun-picture.oss-cn-shanghai.aliyuncs.com/" + path;
            searchinfo.setSearchinfoPhotoUrl(imgurl);
            searchinfoMapper.updateById(searchinfo);

            result.data.put("searchInfo_pic", imgurl);

            result.errorCode = 200;
            result.status = true;
            return result;
        }
        catch (Exception e) {
            return Result.error();
        }
    }
    public Result DeleteInfoByUser(int userid, int infoid){
        Result result = new Result();
        try
        {
            Searchinfo searchinfo=searchinfoMapper.selectById(infoid);
            searchinfo.setIsactive("N");
            searchinfoMapper.updateById(searchinfo);
            //删除寻人信息相关举报
            QueryWrapper<InfoReport> wrapper = new QueryWrapper<InfoReport>();
            wrapper.eq("SEARCHINFO_ID",infoid);
            List<InfoReport> infoRepoList=infoReportMapper.selectList(wrapper);
            for(InfoReport inforepo:infoRepoList)
            {
                inforepo.setIsreviewed("Y");
                inforepo.setIspass("Y");
                infoReportMapper.updateById(inforepo);
            }
            //删除相关线索和举报
            QueryWrapper<Clue> clueWrapper = new QueryWrapper<Clue>();
            clueWrapper.eq("SEARCHINFO_ID",infoid);
            List<Clue> clueList=clueMapper.selectList(clueWrapper);
            for(Clue clue:clueList)
            {
                if(!clueService.deleteClue(clue.getClueId()))
                {
                    return Result.error();
                }
            }
            result.status = true;
            result.errorCode = 200;
            return result;
        }
        catch (Exception e) {
            result.status=false;
            return Result.error();
        }
    }

    public Result UpdateInfoByUser(@RequestBody Map<String, Object> inputMap)
    {
        try{
            Result result = new Result();
            String info_idStr = (String)inputMap.get("searchinfo_id");
            int info_id=Integer.parseInt(info_idStr);
            String search_type =(String)inputMap.get("search_type");
            String sought_people_name = (String)inputMap.get("sought_people_name");
            String sought_people_gender =(String)inputMap.get("sought_people_gender");
            String sought_people_height =(String)inputMap.get("sought_people_height");
            String sought_people_detail = (String)inputMap.get("sought_people_detail");
            String sought_people_birthday =(String)inputMap.get("sought_people_birthday");
            String sought_people_state = (String)inputMap.get("sought_people_state");
            String isreport = (String)inputMap.get("isreport");
            String searchinfo_lostdate = (String)inputMap.get("searchinfo_lostdate");
            String contact_method = (String)inputMap.get("contact_method");
            Searchinfo searchinfo=searchinfoMapper.selectById(info_id);
            searchinfo.setSearchType(search_type);
            searchinfo.setSoughtPeopleName(sought_people_name);
            searchinfo.setSoughtPeopleGender(sought_people_gender);
            searchinfo.setSoughtPeopleHeight(sought_people_height);
            searchinfo.setSoughtPeopleDetail(sought_people_detail);
            searchinfo.setSoughtPeopleBirthday(new SimpleDateFormat("yyyy-MM-dd").parse(sought_people_birthday));
            searchinfo.setSoughtPeopleState(sought_people_state);
            searchinfo.setIsreport(isreport);
            searchinfo.setSearchinfoLostdate(new SimpleDateFormat("yyyy-MM-dd").parse(searchinfo_lostdate));
            searchinfo.setContactMethod(contact_method);
            String province_id = (String)inputMap.get("province_id");
            String city_id = (String)inputMap.get("city_id");
            String area_id = (String)inputMap.get("area_id");
            String address_detail = (String)inputMap.get("address_detail");
            if(province_id!=""){
                Address address=addressMapper.selectById(searchinfo.getAddressId());
                if (address==null){
                    Address address1=new Address();
                    address1.setProvinceId(province_id);
                    address1.setCityId(city_id);
                    address1.setAreaId(area_id);
                    address1.setDetail(address_detail);
                    addressMapper.insert(address1);
                }
                else{
                    address.setProvinceId(province_id);
                    address.setCityId(city_id);
                    address.setAreaId(area_id);
                    address.setDetail(address_detail);
                    addressMapper.updateById(address);
                }
            }
            searchinfoMapper.updateById(searchinfo);

            result.status = true;
            result.errorCode = 200;
            return result;
        }
        catch (Exception e) {
            return Result.error();
        }
    }

    public Result GetSearchInfoToChange(int searchinfoId)
    {
        try{
            Result result=new Result();
            Searchinfo searchinfo=searchinfoMapper.selectById(searchinfoId);
            result.data.put("search_type", searchinfo.getSearchType());
            result.data.put("search_name", searchinfo.getSoughtPeopleName());
            result.data.put("search_gender", searchinfo.getSoughtPeopleGender());
            result.data.put("search_detail", searchinfo.getSoughtPeopleDetail());
            result.data.put("search_birthday", TimeTrans.myToString(searchinfo.getSoughtPeopleBirthday()));
            result.data.put("searchinfo_lostdate", TimeTrans.myToString(searchinfo.getSearchinfoLostdate()));
            result.data.put("contact_method",searchinfo.getContactMethod());
            result.data.put("search_state",searchinfo.getSoughtPeopleState());
            result.data.put("isreport", searchinfo.getIsreport());
            result.data.put("search_height", searchinfo.getSoughtPeopleHeight());
            result.data.put("search_photo", searchinfo.getSearchinfoPhotoUrl());
            if (searchinfo.getAddressId() != null)
            {
                Address address=addressMapper.selectById(searchinfo.getAddressId());
                result.data.put("search_province", address.getProvinceId());
                result.data.put("search_city", address.getCityId());
                result.data.put("search_area", address.getAreaId());
                result.data.put("search_address", address.getDetail());
            }
            else
            {
                result.data.put("search_province", null);
                result.data.put("search_city", null);
                result.data.put("search_area", null);
                result.data.put("search_address", null);
            }
            result.errorCode = 200;
            result.status = true;
            return result;
        }
        catch (Exception e) {
            return Result.error();
        }
    }

}




