package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import boardVO.UserVO;
import boardVO.ApplyVO;
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

	// 공통 중복 체크 메서드
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

	// 로그인 검증: 아이디 + 비밀번호만 확인
	public int validateUserDetail(String userId, String password) {
		String sql = "SELECT password FROM user_table WHERE user_id = ?";

		try (Connection conn = DBUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setString(1, userId);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				String dbPassword = rs.getString("password");

				if (!dbPassword.equals(password)) {
					return 2; // 비밀번호 틀림
				}
				return 1; // 로그인 성공
			} else {
				return 0; // 아이디 없음
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return -1; // DB 오류
		}
	}

	// 사용자 등록
	public boolean insertUser(UserVO user) {
		String sql = "INSERT INTO user_table (user_no, user_id, password, name ,nickname, user_type) "
				+ "VALUES (user_seq.NEXTVAL, ?, ?, ?, ?, ?)";

		try (Connection conn = DBUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setString(1, user.getUserId());
			pstmt.setString(2, user.getPassword());
			pstmt.setString(3, user.getName());
			pstmt.setString(4, user.getNickname());
			pstmt.setString(5, user.getUserType()); // "U", "C", "A"

			int result = pstmt.executeUpdate();
			return result > 0;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	// 사용자 유형 코드 조회 (U, C, A 반환)
	public String getUserTypeById(String userId) {
		String sql = "SELECT user_type FROM user_table WHERE user_id = ?";

		try (Connection conn = DBUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setString(1, userId);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				return rs.getString("user_type"); // "U", "C", "A"
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return ""; // 없으면 공백 반환
	}

	// 유저 타입 한글 → 코드로 변환 ("개인" → "U")
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

	// 회원 추가 정보 업데이트
	public boolean updateAdditionalInfo(UserVO user) {
		String sql;
		boolean isCompany = "C".equals(user.getUserType());

		if (isCompany) {
			sql = "UPDATE user_table SET company_name = ?, manager_name = ?, business_number = ? WHERE user_id = ?";
		} else {
			sql = "UPDATE user_table SET resume_job_title = ?, resume_location = ?, resume_has_project = ?, "
					+ "resume_project = ?, resume_education = ? WHERE user_id = ?";
		}

		try (Connection conn = DBUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			if (isCompany) {
				pstmt.setString(1, user.getCompanyName());
				pstmt.setString(2, user.getManagerName());
				pstmt.setString(3, user.getBusinessNumber()); // ❗ 수정됨
				pstmt.setString(4, user.getUserId());
			} else {
				pstmt.setString(1, user.getResumeJobTitle());
				pstmt.setString(2, user.getResumeLocation());
				pstmt.setString(3, user.getResumeHasProject());
				pstmt.setString(4, user.getResumeProject());
				pstmt.setString(5, user.getResumeEducation());
				pstmt.setString(6, user.getUserId());
			}

			return pstmt.executeUpdate() > 0;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	public UserVO getUserDetail(String userId) {
		String sql = "SELECT * FROM user_table WHERE user_id = ?";

		try (Connection conn = DBUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setString(1, userId);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				UserVO user = new UserVO();
				user.setUserId(rs.getString("user_id"));
				user.setPassword(rs.getString("password"));
				user.setName(rs.getString("name"));
				user.setNickname(rs.getString("nickname"));
				user.setUserType(rs.getString("user_type"));

				user.setCompanyName(rs.getString("company_name"));
				user.setManagerName(rs.getString("manager_name"));
				user.setBusinessNumber(rs.getString("business_number"));

				user.setResumeJobTitle(rs.getString("resume_job_title"));
				user.setResumeLocation(rs.getString("resume_location"));
				user.setResumeHasProject(rs.getString("resume_has_project"));
				user.setResumeProject(rs.getString("resume_project"));
				user.setResumeEducation(rs.getString("resume_education"));

				return user;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public boolean deleteUser(String userId) {
		String sql = "DELETE FROM user_table WHERE user_id = ?";
		try (Connection conn = DBUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setString(1, userId);
			return pstmt.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public List<ApplyVO> findApplicantsByCompany(String companyId) {
		List<ApplyVO> list = new ArrayList<>();

		String sql = """
				    SELECT a.apply_id, a.user_no, a.post_id, a.status, a.apply_date, p.title AS post_title
				    FROM apply a
				    JOIN job_post p ON a.post_id = p.post_id
				    WHERE p.user_id = ?
				    ORDER BY a.apply_date DESC
				""";

		try (Connection conn = DBUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setString(1, companyId);
			ResultSet rs = pstmt.executeQuery();

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

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}
	

    /**
     * user_no(PK) 기준으로 회원 상세 정보 조회
     * @param userNo 회원 고유번호 (정수형 PK)
     * @return UserVO 객체 (없으면 null)
     */
    public UserVO getUserDetailByNo(int userNo) {
        String sql = "SELECT user_no, user_id, name, nickname, resume_job_title, resume_has_project, resume_education " +
                     "FROM user_table WHERE user_no = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userNo);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    UserVO vo = new UserVO();
                    vo.setUserNo(rs.getInt("user_no"));
                    vo.setUserId(rs.getString("user_id"));
                    vo.setName(rs.getString("name"));
                    vo.setNickname(rs.getString("nickname"));
                    vo.setResumeJobTitle(rs.getString("resume_job_title"));
                    vo.setResumeHasProject(rs.getString("resume_has_project"));
                    vo.setResumeEducation(rs.getString("resume_education"));
                    return vo;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null; // 조회 실패 시
    }
}
