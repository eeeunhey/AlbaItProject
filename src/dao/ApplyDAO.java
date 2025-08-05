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
}
