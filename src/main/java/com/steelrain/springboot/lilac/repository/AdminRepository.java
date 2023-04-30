package com.steelrain.springboot.lilac.repository;

import com.steelrain.springboot.lilac.datamodel.AdminBookDTO;
import com.steelrain.springboot.lilac.datamodel.AdminYoutubePlayListDTO;
import com.steelrain.springboot.lilac.mapper.AdminMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Repository
public class AdminRepository implements IAdminRepository {
    private final AdminMapper m_adminMapper;

    @Override
    public int insertRecommendedPlayList(List<Long> videoIdList) {
        return m_adminMapper.insertRecommendedPlayList(videoIdList);
    }

    @Override
    public List<Long> selectAllPlayListId() {
        return m_adminMapper.selectAllPlayListLId();
    }

    @Override
    public List<AdminYoutubePlayListDTO> findAllPlayList(int pageNum, int pageCount) {
        return m_adminMapper.findAllPlayList(pageNum, pageCount);
    }

    @Override
    public int findTotalLicPlCount(int[] licenseIds) {
        return m_adminMapper.findTotalLicPlCount(licenseIds);
    }

    @Override
    public int findTotalSubPlCount(int[] subjectIds) {
        return m_adminMapper.findTotalSubPlCount(subjectIds);
    }

    @Override
    public int findTotalLicSubCount(int[] licenseIds, int[] subjectIds) {
        return m_adminMapper.findTotalLicSubCount(licenseIds, subjectIds);
    }

    @Override
    public List<AdminYoutubePlayListDTO> findTotalLicPlayList(int[] licenseIds, int pageNum, int pageCount) {
        return m_adminMapper.findTotalLicPlayList(licenseIds, pageNum, pageCount);
    }

    @Override
    public List<AdminYoutubePlayListDTO> findTotalSubPlayList(int[] subjectIds, int pageNum, int pageCount) {
        return m_adminMapper.findTotalSubPlayList(subjectIds, pageNum, pageCount);
    }

    @Override
    public List<AdminYoutubePlayListDTO> findTotalLicSubPlayList(int[] licenseIds, int[] subjectIds, int pageNum, int pageCount) {
        return m_adminMapper.findTotalLicSubPlayList(licenseIds, subjectIds, pageNum, pageCount);
    }

    @Override
    public int findTotalPlayListCount() {
        return m_adminMapper.findTotalPlayListCnt();
    }

    public int findLicPlCountByRange(int[] licenseIds, String fromDate, String toDate){
        return m_adminMapper.findLicPlCountByRange(licenseIds, fromDate, toDate);
    }

    @Override
    public int findSubPlCountByRange(int[] subjectIds, String fromDate, String toDate) {
        return m_adminMapper.findSubPlCountByRange(subjectIds, fromDate, toDate);
    }

    @Override
    public int findTotalLicSubPlCountByRange(int[] licenseIds, int[] subjectIds, String fromDate, String toDate) {
        return m_adminMapper.findTotalLicSubPlCountByRange(licenseIds, subjectIds, fromDate, toDate);
    }

    @Override
    public int findPlayListCountByRange(String fromDate, String toDate) {
        return m_adminMapper.findPlayListCount(fromDate, toDate);
    }

    @Override
    public List<AdminYoutubePlayListDTO> findPlayListByRange(String start, String end, int pageNum, int pageCount) {
        return m_adminMapper.findPlayListByRange(start, end, pageNum, pageCount);
    }

    @Override
    public List<AdminYoutubePlayListDTO> findLicPlByRange(int[] licenseIds, String fromDate, String toDate, int pageNum, int pageCount) {
        return m_adminMapper.findLicPlByRange(licenseIds, fromDate, toDate, pageNum, pageCount);
    }

    @Override
    public List<AdminYoutubePlayListDTO> findSubPlByRange(int[] subjectIds, String fromDate, String toDate, int pageNum, int pageCount) {
        return m_adminMapper.findSubPlByRange(subjectIds, fromDate, toDate, pageNum, pageCount);
    }

    @Override
    public List<AdminYoutubePlayListDTO> findLicSubPlByRange(int[] licenseIds, int[] subjectIds, String fromDate, String toDate, int pageNum, int pageCount) {
        return m_adminMapper.findLicSubPlByRange(licenseIds, subjectIds, fromDate, toDate, pageNum, pageCount);
    }

    @Override
    public int addCandiPlayList(Long playListId) {
        return m_adminMapper.addCandiPlayList(playListId);
    }

    @Override
    public List<AdminYoutubePlayListDTO> findCandiPlayList() {
        return m_adminMapper.findCandiPlayList();
    }

    @Override
    public void removeCandiBookList(Long bookId) {
        m_adminMapper.deleteCandiBookList(bookId);
    }

    @Override
    public void removeCandiPlayList(Long playlistId) {
        m_adminMapper.deleteCandiPlayList(playlistId);
    }

    @Override
    public void removeRecommendPlayList(Long playListId) {
        m_adminMapper.deleteRecommendPlayList(playListId);
    }

    @Override
    public void deleteAllRecommendPlayList() {
        m_adminMapper.deleteAllRecommendPlayList();
    }

    @Override
    public List<AdminYoutubePlayListDTO> findRecommendPlayList() {
        return m_adminMapper.findRecommendPlayList();
    }

    @Override
    public List<Long> findCandidatePlIdList() {
        return m_adminMapper.findCandidatePlIdList();
    }

    @Override
    public List<Long> findRecommendPlIdList() {
        return m_adminMapper.findRecommendPlIdList();
    }

    @Override
    public void deleteFinalCandiPlayList(List<Long> plList) {
        m_adminMapper.deleteFinalCandiPlayList(plList);
    }

    @Override
    public int findTotalBookCount() {
        return m_adminMapper.findTotalBookCount();
    }

    @Override
    public int findBookCountRange(String fromDate, String toDate) {
        return m_adminMapper.findBookCountByRange(fromDate, toDate);
    }

    @Override
    public List<AdminBookDTO> findTotalLicBookList(int[] licenseIds, int pageNum, int pageCount) {
        return m_adminMapper.findTotalLicBookList(licenseIds, pageNum, pageCount);
    }

    @Override
    public int findTotalLicBookCount(int[] licenseIds) {
        return m_adminMapper.findTotalLicBookCount(licenseIds);
    }

    @Override
    public List<AdminBookDTO> findTotalBookList(int pageNum, int pageCount) {
        return m_adminMapper.findTotalBookList(pageNum, pageCount);
    }

    @Override
    public List<AdminBookDTO> findTotalSubBookList(int[] subjectIds, int pageNum, int pageCount) {
        return m_adminMapper.findTotalSubBookList(subjectIds, pageNum, pageCount);
    }

    @Override
    public int findTotalSubBookCount(int[] subjectIds) {
        return m_adminMapper.findTotalSubBookCount(subjectIds);
    }

    @Override
    public List<AdminBookDTO> findTotalLicSubBookList(int[] licenseIds, int[] subjectIds, int pageNum, int pageCount) {
        return m_adminMapper.findTotalLicSubBookList(licenseIds, subjectIds, pageNum, pageCount);
    }

    @Override
    public int findTotalLicSubBookCount(int[] licenseIds, int[] subjectIds) {
        return m_adminMapper.findTotalLicSubBookCount(licenseIds, subjectIds);
    }

    @Override
    public List<AdminBookDTO> findLicBookListByRange(int[] licenseIds, String fromDate, String toDate, int pageNum, int pageCount) {
        return m_adminMapper.findLicBookListByRange(licenseIds, fromDate, toDate, pageNum, pageCount);
    }

    @Override
    public int findLicBookCountByRange(int[] licenseIds, String fromDate, String toDate) {
        return m_adminMapper.findLicBookCountByRange(licenseIds, fromDate, toDate);
    }

    @Override
    public List<AdminBookDTO> findSubBookListByRange(int[] subjectIds, int pageNum, int pageCount) {
        return m_adminMapper.findSubBookListByRange(subjectIds, pageNum, pageCount);
    }

    @Override
    public int findSubBookCountByRange(int[] subjectIds, String fromDate, String toDate) {
        return m_adminMapper.findSubBookCountByRange(subjectIds, fromDate, toDate);
    }

    @Override
    public List<AdminBookDTO> findLicSubBookListByRange(int[] licenseIds, int[] subjectIds, String fromDate, String toDate, int pageNum, int pageCount) {
        return m_adminMapper.findLicSubBookListByRange(licenseIds, subjectIds, fromDate, toDate, pageNum, pageCount);
    }

    @Override
    public int findLicSubBookCountByRange(int[] licenseIds, int[] subjectIds, String fromDate, String toDate) {
        return m_adminMapper.findLicSubBookCountByRange(licenseIds, subjectIds, fromDate, toDate);
    }

    @Override
    public List<AdminBookDTO> findBookListByRange(String fromDate, String toDate, int pageNum, int pageCount) {
        return m_adminMapper.findBookListByRange(fromDate, toDate, pageNum, pageCount);
    }

    @Override
    public int findBookCountByRange(String fromDate, String toDate) {
        return m_adminMapper.findBookCountByRange(fromDate, toDate);
    }

    @Override
    public List<Long> findCandidateBookIdList() {
        return m_adminMapper.findCandidateBookIdList();
    }

    @Override
    public List<Long> findRecommendBookIdList() {
        return m_adminMapper.findRecommendBookIdList();
    }

    @Override
    public void addCandiBook(Long bookId) {
        m_adminMapper.addCandiBook(bookId);
    }

    @Override
    public List<AdminBookDTO> findCandiBookList() {
        return m_adminMapper.findCandiBookList();
    }

    @Override
    public void deleteFinalCandiBookList(List<Long> cblList) {
        m_adminMapper.deleteFinalCandiBookList(cblList);
    }

    @Override
    public void deleteAllRecommendBookList() {
        m_adminMapper.deleteAllRecommendBookList();
    }

    @Override
    public void insertRecommendedBookList(List<Long> cblList) {
        m_adminMapper.insertRecommendedBookList(cblList);
    }

    @Override
    public List<AdminBookDTO> findRecommendBookList() {
        return m_adminMapper.findRecommendBookList();
    }

    @Override
    public void deleteRecommendBook(Long bookId) {
        m_adminMapper.deleteRecommendBook(bookId);
    }

    @Override
    public List<AdminYoutubePlayListDTO> findPlayListByLike(boolean desc, int[] licenseIds, int[] subjectIds, int pageNum, int pageCount) {
        return m_adminMapper.findPlayListByLike(desc, licenseIds, subjectIds, pageNum, pageCount);
    }

    @Override
    public List<AdminYoutubePlayListDTO> findPlayListByViewCount(boolean desc, int[] licenseIds, int[] subjectIds, int pageNum, int pageCount) {
        return m_adminMapper.findPlayListBViewCount(desc, licenseIds, subjectIds, pageNum, pageCount);
    }
}
