package com.steelrain.springboot.lilac.mapper;

import com.steelrain.springboot.lilac.datamodel.YoutubePlayListDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AdminMapper {
    int insertRecommendedPlayList(List<Long> videoIdList);

    @Select("SELECT id FROM tbl_youtube_playlist")
    List<Long> selectAllPlayListLId();

    List<YoutubePlayListDTO> findAllPlayList(@Param("pageNum") int pageNum, @Param("pageCount") int pageCount);

    @Select("SELECT count(id) FROM tbl_youtube_playlist")
    int findTotalPlayListCnt();

    @Select("SELECT count(id) FROM tbl_youtube_playlist WHERE reg_date > #{fromDate} AND reg_date <= #{toDate}")
    int findPlayListCount(@Param("fromDate")String fromDate, @Param("toDate")String toDate);

    List<YoutubePlayListDTO> findPlayListByRange(@Param("start") String start, @Param("end") String end, @Param("pageNum") int pageNum, @Param("pageCount") int pageCount);

    int findTotalLicPlCount(int[] licenseIds);

    List<YoutubePlayListDTO> findTotalLicPlayList(int[] licenseIds, int pageNum, int pageCount);

    int findTotalSubPlCount(int[] subjectIds);

    List<YoutubePlayListDTO> findTotalSubPlayList(int[] subjectIds, int pageNum, int pageCount);

    int findTotalLicSubCount(int[] licenseIds, int[] subjectIds);

    List<YoutubePlayListDTO> findTotalLicSubPlayList(int[] licenseIds, int[] subjectIds, int pageNum, int pageCount);

    int findTodayLicPlCount(int[] licenseIds);

    List<YoutubePlayListDTO> findLicPlByRange(@Param("licenseIds") int[] licenseIds,@Param("fromDate") String fromDate,@Param("toDate") String toDate,@Param("pageNum") int pageNum,@Param("pageCount") int pageCount);

    int findLicPlCountByRange(@Param("licenseIds")int[] licenseIds,@Param("fromDate") String fromDate,@Param("toDate") String toDate);

    int findSubPlCountByRange(@Param("subjectIds") int[] subjectIds,@Param("fromDate") String fromDate,@Param("toDate") String toDate);

    List<YoutubePlayListDTO> findSubPlByRange(@Param("subjectIds")int[] subjectIds,@Param("fromDate") String fromDate,@Param("toDate") String toDate,@Param("pageNum") int pageNum,@Param("pageCount") int pageCount);

    int findTotalLicSubPlCountByRange(@Param("licenseIds")int[] licenseIds, @Param("subjectIds") int[] subjectIds, @Param("fromDate") String fromDate, @Param("toDate") String toDate);

    List<YoutubePlayListDTO> findLicSubPlByRange(@Param("licenseIds")int[] licenseIds,@Param("subjectIds") int[] subjectIds,@Param("fromDate") String fromDate,@Param("toDate") String toDate,@Param("pageNum") int pageNum,@Param("pageCount") int pageCount);
}
