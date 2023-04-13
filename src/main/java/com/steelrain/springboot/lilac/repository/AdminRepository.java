package com.steelrain.springboot.lilac.repository;

import com.steelrain.springboot.lilac.common.DateUtils;
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
    public List<YoutubePlayListDTO> findAllPlayList(int pageNum, int pageCount) {
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
    public List<YoutubePlayListDTO> findTotalLicPlayList(int[] licenseIds, int pageNum, int pageCount) {
        return m_adminMapper.findTotalLicPlayList(licenseIds, pageNum, pageCount);
    }

    @Override
    public List<YoutubePlayListDTO> findTotalSubPlayList(int[] subjectIds, int pageNum, int pageCount) {
        return m_adminMapper.findTotalSubPlayList(subjectIds, pageNum, pageCount);
    }

    @Override
    public List<YoutubePlayListDTO> findTotalLicSubPlayList(int[] licenseIds, int[] subjectIds, int pageNum, int pageCount) {
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
    public List<YoutubePlayListDTO> findPlayListByRange(String start, String end, int pageNum, int pageCount) {
        return m_adminMapper.findPlayListByRange(start, end, pageNum, pageCount);
    }

    @Override
    public List<YoutubePlayListDTO> findLicPlByRange(int[] licenseIds, String fromDate, String toDate, int pageNum, int pageCount) {
        return m_adminMapper.findLicPlByRange(licenseIds, fromDate, toDate, pageNum, pageCount);
    }

    @Override
    public List<YoutubePlayListDTO> findSubPlByRange(int[] subjectIds, String fromDate, String toDate, int pageNum, int pageCount) {
        return m_adminMapper.findSubPlByRange(subjectIds, fromDate, toDate, pageNum, pageCount);
    }

    @Override
    public List<YoutubePlayListDTO> findLicSubPlByRange(int[] licenseIds, int[] subjectIds, String fromDate, String toDate, int pageNum, int pageCount) {
        return m_adminMapper.findLicSubPlByRange(licenseIds, subjectIds, fromDate, toDate, pageNum, pageCount);
    }
}
