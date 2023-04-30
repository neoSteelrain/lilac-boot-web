package com.steelrain.springboot.lilac.mapper;

import com.steelrain.springboot.lilac.datamodel.AdminBookDTO;
import com.steelrain.springboot.lilac.datamodel.AdminYoutubePlayListDTO;
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

    @Select("SELECT count(id) FROM tbl_youtube_playlist WHERE reg_date > #{fromDate} AND reg_date < #{toDate}")
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

    @Insert("INSERT INTO tbl_candi_recommend_playlist(youtube_playlist_id) VALUE (#{playListId})")
    int addCandiPlayList(@Param("playListId")Long playListId);

    List<AdminYoutubePlayListDTO> findCandiPlayList();

    @Delete("DELETE FROM tbl_candi_recommend_playlist WHERE youtube_playlist_id=#{playlistId}")
    void deleteCandiPlayList(@Param("playlistId") Long playlistId);

    @Delete("DELETE FROM tbl_recommended_playlist")
    void deleteAllRecommendPlayList();

    List<AdminYoutubePlayListDTO> findRecommendPlayList();

    @Select("SELECT youtube_playlist_id FROM tbl_candi_recommend_playlist")
    List<Long> findCandidatePlIdList();

    @Select("SELECT youtube_playlist_id FROM tbl_recommended_playlist")
    List<Long> findRecommendPlIdList();

    void deleteFinalCandiPlayList(@Param("plList")List<Long> plList);

    @Delete("DELETE FROM tbl_recommended_playlist WHERE youtube_playlist_id=#{playlistId}")
    void deleteRecommendPlayList(@Param("playlistId") Long playListId);

    @Select("SELECT count(id) FROM tbl_book")
    int findTotalBookCount();

    @Select("SELECT count(id) FROM tbl_book WHERE reg_date > #{fromDate} AND reg_date < #{toDate} OR update_date > #{fromDate} AND update_date < #{toDate}")
    int findBookCountByRange(@Param("fromDate") String fromDate, @Param("toDate") String toDate);

    List<AdminBookDTO> findTotalLicBookList(@Param("licenseIds")int[] licenseIds, @Param("pageNum")int pageNum, @Param("pageCount") int pageCount);

    int findTotalLicBookCount(@Param("licenseIds")int[] licenseIds);

    List<AdminBookDTO> findTotalBookList(@Param("pageNum") int pageNum, @Param("pageCount") int pageCount);

    List<AdminBookDTO> findTotalSubBookList(@Param("subjectIds") int[] subjectIds, @Param("pageNum") int pageNum, @Param("pageCount") int pageCount);

    int findTotalSubBookCount(@Param("subjectIds") int[] subjectIds);

    List<AdminBookDTO> findTotalLicSubBookList(@Param("licenseIds") int[] licenseIds, @Param("subjectIds") int[] subjectIds, @Param("pageNum") int pageNum, @Param("pageCount") int pageCount);

    int findTotalLicSubBookCount(@Param("licenseIds") int[] licenseIds, @Param("subjectIds") int[] subjectIds);

    List<AdminBookDTO> findLicBookListByRange(@Param("licenseIds") int[] licenseIds, @Param("fromDate") String fromDate, @Param("toDate") String toDate, @Param("pageCount") int pageNum, int pageCount);

    int findLicBookCountByRange(@Param("licenseIds") int[] licenseIds, @Param("fromDate") String fromDate, @Param("toDate") String toDate);

    List<AdminBookDTO> findSubBookListByRange( @Param("subjectIds") int[] subjectIds, @Param("pageNum") int pageNum, @Param("pageCount") int pageCount);

    int findSubBookCountByRange( @Param("subjectIds") int[] subjectIds, @Param("fromDate") String fromDate, @Param("toDate") String toDate);

    List<AdminBookDTO> findLicSubBookListByRange( @Param("licenseIds") int[] licenseIds, @Param("subjectIds") int[] subjectIds, @Param("fromDate") String fromDate, @Param("toDate") String toDate, @Param("pageNum") int pageNum, @Param("pageCount") int pageCount);

    int findLicSubBookCountByRange(@Param("licenseIds") int[] licenseIds, @Param("subjectIds") int[] subjectIds, @Param("fromDate") String fromDate, @Param("toDate") String toDate);

    List<AdminBookDTO> findBookListByRange(@Param("formDate") String fromDate, @Param("toDate") String toDate, @Param("pageNum") int pageNum, @Param("pageCount") int pageCount);

    @Select("SELECT book_id FROM tbl_candi_recommend_book")
    List<Long> findCandidateBookIdList();

    @Select("SELECT book_id FROM tbl_recommended_book")
    List<Long> findRecommendBookIdList();

    @Insert("INSERT INTO tbl_candi_recommend_book(book_id) VALUE (#{bookId})")
    void addCandiBook(@Param("bookId")Long bookId);

    List<AdminBookDTO> findCandiBookList();

    @Delete("DELETE FROM tbl_candi_recommend_book WHERE book_id=#{bookId}")
    void deleteCandiBookList(@Param("bookId") Long bookId);

    void deleteFinalCandiBookList(@Param("cblList") List<Long> cblList);

    @Delete("DELETE FROM tbl_recommended_book")
    void deleteAllRecommendBookList();

    void insertRecommendedBookList(@Param("cblList") List<Long> cblList);

    List<AdminBookDTO> findRecommendBookList();

    @Delete("DELETE FROM tbl_recommended_book WHERE book_id=#{bookId}")
    void deleteRecommendBook(@Param("bookId")Long bookId);

    List<AdminYoutubePlayListDTO> findPlayListByLike(@Param("desc") boolean desc, @Param("licenseIds") int[] licenseIds, @Param("subjectIds") int[] subjectIds, @Param("pageNum") int pageNum, @Param("pageCount") int pageCount);

    List<AdminYoutubePlayListDTO> findPlayListBViewCount(@Param("desc") boolean desc, @Param("licenseIds") int[] licenseIds, @Param("subjectIds") int[] subjectIds, @Param("pageNum") int pageNum, @Param("pageCount") int pageCount);
}
