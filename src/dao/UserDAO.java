package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import boardVO.UserVO;
import boardVO.ApplyVO;
import db.DBUtil;

public class UserDAO {

	/*
	 * ========================== 1. 중복 체크 ==========================
	 */
	public boolean isDuplicate(String userId) {
		return isDuplicate("user_id", userId);
	}

	public boolean isDuplicateNickname(String nickname) {
		return isDuplicate("nickname", nickname);
	}

	private boolean isDuplicate(String column, String value) {
		String sql = "SELECT COUNT(*) FROM user_table WHERE " + column + " = ?";
		try (Connection conn = DBUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, value);
			try (ResultSet rs = pstmt.executeQuery()) {
				return rs.next() && rs.getInt(1) > 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/*
	 * ========================== 2. 회원가입 (active_yn 없음 → 컬럼/값 제거)
	 * ==========================
	 */
	public boolean insertUser(UserVO user) {
		String sql = "INSERT INTO user_table (user_no, user_id, password, name, nickname, user_type) "
				+ "VALUES (user_seq.NEXTVAL, ?, ?, ?, ?, ?)";
		try (Connection conn = DBUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, user.getUserId());
			pstmt.setString(2, user.getPassword());
			pstmt.setString(3, user.getName());
			pstmt.setString(4, user.getNickname());
			pstmt.setString(5, user.getUserType()); // 'U','C','A'
			return pstmt.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/*
	 * ========================== 3. 로그인 ==========================
	 */
	// 1: 성공, 0: 아이디 없음, 2: 비번 틀림, -1: DB 오류
	public int validateUserDetail(String userId, String password) {
		String sql = "SELECT password FROM user_table WHERE user_id = ?";
		try (Connection conn = DBUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, userId);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (!rs.next())
					return 0;
				String dbPassword = rs.getString("password");
				return dbPassword.equals(password) ? 1 : 2;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}

	/*
	 * ========================== 4. 회원 조회 ==========================
	 */
	public UserVO getUserDetail(String userId) {
		String sql = "SELECT user_no, user_id, password, name, nickname, user_type, reg_date "
				+ "FROM user_table WHERE user_id = ?";
		try (Connection conn = DBUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, userId);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (!rs.next())
					return null;
				UserVO user = new UserVO();
				user.setUserNo(rs.getInt("user_no"));
				user.setUserId(rs.getString("user_id"));
				user.setPassword(rs.getString("password"));
				user.setName(rs.getString("name"));
				user.setNickname(rs.getString("nickname"));
				user.setUserType(rs.getString("user_type"));
				user.setRegDate(rs.getDate("reg_date")); // java.util.Date
				return user;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public UserVO getUserDetailByNo(int userNo) {
		String sql = "SELECT user_no, user_id, password, name, nickname, user_type, reg_date "
				+ "FROM user_table WHERE user_no = ?";
		try (Connection conn = DBUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, userNo);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (!rs.next())
					return null;
				UserVO vo = new UserVO(); // ✅ 잘못된 생성자 호출 제거
				vo.setUserNo(rs.getInt("user_no"));
				vo.setUserId(rs.getString("user_id"));
				vo.setPassword(rs.getString("password"));
				vo.setName(rs.getString("name"));
				vo.setNickname(rs.getString("nickname"));
				vo.setUserType(rs.getString("user_type"));
				vo.setRegDate(rs.getDate("reg_date"));
				return vo;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/*
	 * ========================== 5. 회원 수정 / 삭제 ==========================
	 */
	public boolean updateUserInfo(UserVO user) {
		String sql = "UPDATE user_table " + "SET name = NVL(?, name), " + "    nickname = NVL(?, nickname), "
				+ "    password = NVL(?, password) " + "WHERE user_id = ?";
		try (Connection conn = DBUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, user.getName());
			pstmt.setString(2, user.getNickname());
			pstmt.setString(3, user.getPassword());
			pstmt.setString(4, user.getUserId());
			return pstmt.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean deleteUser(String userId) {
		String sql = "DELETE FROM user_table WHERE user_id = ?";
		try (Connection conn = DBUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, userId);
			return pstmt.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/*
	 * ========================== 6. 회원 기본 정보(번호/유형) ==========================
	 */
	public int getUserNoById(String userId) {
		String sql = "SELECT user_no FROM user_table WHERE user_id = ?";
		try (Connection conn = DBUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, userId);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next())
					return rs.getInt("user_no");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1; // 없는 경우
	}

	public String getUserTypeById(String userId) {
		String sql = "SELECT user_type FROM user_table WHERE user_id = ?";
		try (Connection conn = DBUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, userId);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next())
					return rs.getString("user_type"); // "U","C","A"
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null; // 못 찾으면 null
	}

	public static String convertUserTypeToCode(String typeName) {
		switch (typeName) {
		case "개인":
			return "U";
		case "기업":
			return "C";
		case "관리자":
			return "A";
		default:
			return "";
		}
	}

	/*
	 * ========================== 7. 기업 지원자 조회 ==========================
	 */
	public List<ApplyVO> findApplicantsByCompany(String companyId) {
		List<ApplyVO> list = new ArrayList<>();
		String sql = "SELECT a.apply_id, a.user_no, a.post_id, a.status, a.apply_date, p.title AS post_title "
				+ "FROM apply a JOIN job_post p ON a.post_id = p.post_id "
				+ "WHERE p.user_id = ? ORDER BY a.apply_date DESC";
		try (Connection conn = DBUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, companyId);
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					ApplyVO vo = new ApplyVO();
					vo.setApplyId(rs.getInt("apply_id"));
					vo.setUserNo(rs.getInt("user_no"));
					vo.setPostID(rs.getInt("post_id"));
					vo.setStatus(rs.getString("status"));
					vo.setApplyDate(rs.getTimestamp("apply_date"));
					vo.setPostTitle(rs.getString("post_title"));
					list.add(vo);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
}
