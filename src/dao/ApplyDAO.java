package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import boardVO.ApplyVO;
import db.DBUtil;

public class ApplyDAO {

    // 지원 등록
    public boolean insertApply(ApplyVO apply) {
        String sql = "INSERT INTO APPLY (APPLY_ID, USER_NO, POST_ID, STATUS, APPLY_DATE) " +
                     "VALUES (APPLY_SEQ.NEXTVAL, ?, ?, ?, SYSDATE)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, apply.getUserNo());
            pstmt.setInt(2, apply.getPostID());
            pstmt.setString(3, apply.getStatus());

            return pstmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // 사용자 ID 기준으로 나의 지원 내역 조회
    public List<ApplyVO> getMyApplies(String loginUserId) {
        List<ApplyVO> list = new ArrayList<>();

        String sql = "SELECT a.APPLY_ID, a.USER_NO, a.POST_ID, a.STATUS, a.APPLY_DATE, p.TITLE " +
                     "FROM APPLY a " +
                     "JOIN JOB_POST p ON a.POST_ID = p.POST_ID " +
                     "WHERE a.USER_NO = (SELECT USER_NO FROM USER_TABLE WHERE USER_ID = ?) " +
                     "ORDER BY a.APPLY_DATE DESC";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, loginUserId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                ApplyVO vo = new ApplyVO();
                vo.setApplyId(rs.getInt("APPLY_ID"));
                vo.setUserNo(rs.getInt("USER_NO"));
                vo.setPostID(rs.getInt("POST_ID"));
                vo.setStatus(rs.getString("STATUS"));
                vo.setApplyDate(rs.getDate("APPLY_DATE"));
                vo.setPostTitle(rs.getString("TITLE")); // 제목까지 포함
                list.add(vo);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public List<ApplyVO> findApplicantsByCompany(String companyId) {
        String sql =
            "SELECT a.apply_id, a.user_no, a.post_id, a.status, a.apply_date, " +
            "       p.title AS post_title " +
            "  FROM apply a " +
            "  JOIN job_post p ON a.post_id = p.post_id " +
            " WHERE p.user_id = ? " +                   // 기업이 작성한 공고 기준
            " ORDER BY a.apply_date DESC";

        List<ApplyVO> list = new ArrayList<>();

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, companyId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    ApplyVO vo = new ApplyVO();
                    vo.setApplyId(rs.getInt("apply_id"));
                    vo.setUserNo(rs.getInt("user_no"));        // int 타입 주의
                    vo.setPostID(rs.getInt("post_id"));
                    vo.setStatus(rs.getString("status"));
                    // apply_date가 Timestamp일 가능성 → java.util.Date로 매핑
                    Timestamp ts = rs.getTimestamp("apply_date");
                    vo.setApplyDate(ts != null ? new java.util.Date(ts.getTime()) : null);

                    vo.setPostTitle(rs.getString("post_title")); // 공고 제목

                    list.add(vo);
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // 로깅으로 바꾸는 걸 권장
        }
        return list;
    }
    
    // 지원 여부 확인
    public boolean hasAlreadyApplied(int postId, String userId) {
        String sql = "SELECT COUNT(*) FROM APPLY WHERE post_id = ? AND user_no = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, postId);
            pstmt.setString(2, userId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // 이미 지원한 경우 true
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
}

