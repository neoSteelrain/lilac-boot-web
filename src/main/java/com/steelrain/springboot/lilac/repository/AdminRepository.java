package com.steelrain.springboot.lilac.repository;

import com.steelrain.springboot.lilac.common.DateUtils;
import com.steelrain.springboot.lilac.datamodel.AdminYoutubePlayListDTO;
import com.steelrain.springboot.lilac.datamodel.YoutubePlayListDTO;
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
    public int findTodayPlayListCount() {
        return m_adminMapper.findPlayListCount(DateUtils.getYesterdayString(), DateUtils.getTodayDateString());
    }

    @Override
    public int findMonthPlayListCount() {
        return m_adminMapper.findPlayListCount(DateUtils.getFirstdayOfMonth(), DateUtils.getLastdayOfMonth());
    }

    @Override
    public int findWeekPlayListCount() {
        return m_adminMapper.findPlayListCount(DateUtils.getMondayOfWeekString(), DateUtils.getSundayOfWeekString());
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
    public void removeCandiPlayList(Long playlistId) {
        m_adminMapper.removeCandiPlayList(playlistId);
    }

    @Override
    public void deleteAllRecommendPlayList() {
        m_adminMapper.deleteAllRecommendPlayList();
    }

    @Override
    public void deleteAllCandiRecommendPlayList() {
        m_adminMapper.deleteAllCandiRecommendPlayList();
    }

    @Override
    public List<AdminYoutubePlayListDTO> findRecommendPlayList() {
        return m_adminMapper.findRecommendPlayList();
    }

    @Override
    public List<Long> findCandidateIdList() {
        return m_adminMapper.findCandidateIdList();
    }

    @Override
    public List<Long> findRecommendIdList() {
        return m_adminMapper.findRecommendIdList();
    }
}
