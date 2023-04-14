package com.steelrain.springboot.lilac.mapper;

import com.steelrain.springboot.lilac.datamodel.AdminYoutubePlayListDTO;
import com.steelrain.springboot.lilac.datamodel.YoutubePlayListDTO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AdminMapper {
    int insertRecommendedPlayList(@Param("plIdList") List<Long> plIdList);

    @Select("SELECT id FROM tbl_youtube_playlist")
    List<Long> selectAllPlayListLId();

    List<AdminYoutubePlayListDTO> findAllPlayList(@Param("pageNum") int pageNum, @Param("pageCount") int pageCount);

    @Select("SELECT count(id) FROM tbl_youtube_playlist")
    int findTotalPlayListCnt();

    @Select("SELECT count(id) FROM tbl_youtube_playlist WHERE reg_date > #{fromDate} AND reg_date <= #{toDate}")
    int findPlayListCount(@Param("fromDate")String fromDate, @Param("toDate")String toDate);

    List<AdminYoutubePlayListDTO> findPlayListByRange(@Param("start") String start, @Param("end") String end, @Param("pageNum") int pageNum, @Param("pageCount") int pageCount);

    int findTotalLicPlCount(@Param("licenseIds") int[] licenseIds);

    List<AdminYoutubePlayListDTO> findTotalLicPlayList(@Param("licenseIds") int[] licenseIds,@Param("pageNum") int pageNum,@Param("pageCount") int pageCount);

    int findTotalSubPlCount(@Param("subjectIds") int[] subjectIds);

    List<AdminYoutubePlayListDTO> findTotalSubPlayList(@Param("subjectIds") int[] subjectIds,@Param("pageNum") int pageNum,@Param("pageCount") int pageCount);

    int findTotalLicSubCount(@Param("licenseIds") int[] licenseIds, @Param("subjectIds") int[] subjectIds);

    List<AdminYoutubePlayListDTO> findTotalLicSubPlayList(@Param("licenseIds") int[] licenseIds,@Param("subjectIds") int[] subjectIds,@Param("pageNum") int pageNum,@Param("pageCount") int pageCount);

    List<AdminYoutubePlayListDTO> findLicPlByRange(@Param("licenseIds") int[] licenseIds,@Param("fromDate") String fromDate,@Param("toDate") String toDate,@Param("pageNum") int pageNum,@Param("pageCount") int pageCount);

    int findLicPlCountByRange(@Param("licenseIds")int[] licenseIds,@Param("fromDate") String fromDate,@Param("toDate") String toDate);

    int findSubPlCountByRange(@Param("subjectIds") int[] subjectIds,@Param("fromDate") String fromDate,@Param("toDate") String toDate);

    List<AdminYoutubePlayListDTO> findSubPlByRange(@Param("subjectIds")int[] subjectIds,@Param("fromDate") String fromDate,@Param("toDate") String toDate,@Param("pageNum") int pageNum,@Param("pageCount") int pageCount);

    int findTotalLicSubPlCountByRange(@Param("licenseIds")int[] licenseIds, @Param("subjectIds") int[] subjectIds, @Param("fromDate") String fromDate, @Param("toDate") String toDate);

    List<AdminYoutubePlayListDTO> findLicSubPlByRange(@Param("licenseIds")int[] licenseIds,@Param("subjectIds") int[] subjectIds,@Param("fromDate") String fromDate,@Param("toDate") String toDate,@Param("pageNum") int pageNum,@Param("pageCount") int pageCount);

    int addCandiPlayList(@Param("playListId")Long playListId);

    List<AdminYoutubePlayListDTO> findCandiPlayList();

    @Delete("DELETE FROM tbl_candi_recommend_playlist WHERE youtube_playlist_id=#{playlistId}")
    void removeCandiPlayList(@Param("playlistId") Long playlistId);

    @Delete("DELETE FROM tbl_recommended_playlist")
    void deleteAllRecommendPlayList();

    List<AdminYoutubePlayListDTO> findRecommendPlayList();

    @Select("SELECT youtube_playlist_id FROM tbl_candi_recommend_playlist")
    List<Long> findCandidateIdList();

    @Select("SELECT youtube_playlist_id FROM tbl_recommended_playlist")
    List<Long> findRecommendIdList();

    void deleteFinalCandiPlayList(@Param("plList")List<Long> plList);

    @Delete("DELETE FROM tbl_recommended_playlist WHERE youtube_playlist_id=#{playlistId}")
    void removeRecommendPlayList(Long playListId);
}
