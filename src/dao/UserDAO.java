package dao;

import java.sql.*;
import boardVO.UserVO;
import db.DBUtil;

public class UserDAO {

	
	// 아이디 중복 체크
	public boolean isDuplicate(String userId) {
	    return isDuplicate("user_id", userId);
	}

	// 닉네임 중복 체크
	public boolean isDuplicateNickname(String nickname) {
	    return isDuplicate("nickname", nickname);
	}

	// 공통 메서드
	private boolean isDuplicate(String column, String value) {
	    String sql = "SELECT COUNT(*) FROM user_table WHERE " + column + " = ?";
	    try (Connection conn = DBUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        pstmt.setString(1, value);
	        ResultSet rs = pstmt.executeQuery();
	        if (rs.next()) {
	            return rs.getInt(1) > 0;
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return false;
	}
	
	public int validateUserDetail(String userId, String password, String userType) {
	    String sql = "SELECT password, user_type FROM user_table WHERE user_id = ?";

	    try (Connection conn = DBUtil.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {

	        pstmt.setString(1, userId);
	        ResultSet rs = pstmt.executeQuery();

	        if (rs.next()) { // 아이디 존재
	            String dbPassword = rs.getString("password");
	            String dbUserType = rs.getString("user_type");

	            if (!dbPassword.equals(password)) {
	                return 2; // 비밀번호 틀림
	            }

	            String typeCode = userType.equals("개인") ? "U" : userType.equals("기업") ? "C" : "A";
	            if (!dbUserType.equals(typeCode)) {
	                return 3; // 회원유형 다름
	            }

	            return 1; // 성공
	        } else {
	            return 0; // 아이디 없음
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	        return -1; // DB 오류
	    }
	}


	public boolean insertUser(UserVO user) {
		String sql = "INSERT INTO user_table (user_no, user_id, password, name ,nickname, user_type) "
				+ "VALUES (user_seq.NEXTVAL, ?, ?, ?, ?, ?)";

		try (Connection conn = DBUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setString(1, user.getUserId());
			pstmt.setString(2, user.getPassword());
			pstmt.setString(3, user.getName());
			pstmt.setString(4, user.getNickname());
			pstmt.setString(5, user.getUserType()); // 'U' or 'C'

			int result = pstmt.executeUpdate();
			return result > 0;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}
}
