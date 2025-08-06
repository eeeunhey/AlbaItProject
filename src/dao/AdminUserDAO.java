package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import boardVO.AdminUserVO;
import db.DBUtil;

public class AdminUserDAO {

    // 전체 회원 조회
    public List<AdminUserVO> getAllUsers() {
        List<AdminUserVO> userList = new ArrayList<>();
        String sql = "SELECT * FROM user_info ORDER BY user_id";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                AdminUserVO user = new AdminUserVO();
                user.setUserId(rs.getInt("user_id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setNickname(rs.getString("nickname"));
                user.setUserType(rs.getString("user_type"));
                user.setStatus(rs.getString("status"));
                user.setReportCount(rs.getInt("report_count"));

                userList.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return userList;
    }

    // 회원 상태 변경 (예: 정지/정상/탈퇴)
    public boolean updateUserStatus(int userId, String newStatus) {
        String sql = "UPDATE user_info SET status = ? WHERE user_id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, newStatus);
            pstmt.setInt(2, userId);

            int result = pstmt.executeUpdate();
            return result > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // 회원 상세 조회
    public AdminUserVO getUserById(int userId) {
        String sql = "SELECT * FROM user_info WHERE user_id = ?";
        AdminUserVO user = null;

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                user = new AdminUserVO();
                user.setUserId(rs.getInt("user_id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setNickname(rs.getString("nickname"));
                user.setUserType(rs.getString("user_type"));
                user.setStatus(rs.getString("status"));
                user.setReportCount(rs.getInt("report_count"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }
}
