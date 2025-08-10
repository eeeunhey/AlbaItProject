package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import boardVO.ApplyVO;
import db.DBUtil;

public class ApplyDAO {

	// --------------------------------------
	// [상태값 관리] 허용되는 상태 목록
	// --------------------------------------
	private static final String[] ALLOWED_STATUS = { "접수", "검토 중", "합격", "불합격" };

	private boolean isValidStatus(String status) {
		for (String s : ALLOWED_STATUS) {
			if (s.equals(status))
				return true;
		}
		return false;
	}

	// ============================================================
	// 1. 지원 등록 관련 메서드 (UI: 공고 상세보기 -> 지원)
	// ============================================================

	/** 지원 내역 등록 (STATUS 기본값: '접수') */
	public boolean insertApply(int userNo, int postId) {
		String sql = "INSERT INTO apply (apply_id, user_no, post_id, apply_date) "
				+ "VALUES (apply_seq.NEXTVAL, ?, ?, SYSDATE)";
		try (Connection conn = DBUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, userNo);
			pstmt.setInt(2, postId);
			return pstmt.executeUpdate() > 0;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/** 지원 내역 등록 (STATUS 직접 지정) */
	public boolean insertApply(int userNo, int postId, String status) {
		if (!isValidStatus(status))
			status = "접수"; // 안전 처리

		String sql = "INSERT INTO apply (apply_id, user_no, post_id, status, apply_date) "
				+ "VALUES (apply_seq.NEXTVAL, ?, ?, ?, SYSDATE)";
		try (Connection conn = DBUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, userNo);
			pstmt.setInt(2, postId);
			pstmt.setString(3, status);
			return pstmt.executeUpdate() > 0;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/** 특정 공고에 이미 지원했는지 확인 */
	public boolean exists(int userNo, int postId) {
		String sql = "SELECT COUNT(*) FROM apply WHERE user_no = ? AND post_id = ?";
		try (Connection conn = DBUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, userNo);
			pstmt.setInt(2, postId);
			try (ResultSet rs = pstmt.executeQuery()) {
				return rs.next() && rs.getInt(1) > 0;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	// ============================================================
	// 2. 개인 회원 기능 (UI: PersonalApplyJob)
	// ============================================================

	/** 개인 회원 - 나의 지원 내역 조회 */
	public List<ApplyVO> getMyApplies(int loginUserNo) {
		List<ApplyVO> list = new ArrayList<>();
		String sql = "SELECT a.apply_id, a.user_no, a.post_id, a.status, a.apply_date, " + "p.title AS post_title "
				+ "FROM apply a " + "JOIN job_post p ON a.post_id = p.post_id " + "WHERE a.user_no = ? "
				+ "ORDER BY a.apply_date DESC";

		try (Connection conn = DBUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, loginUserNo);
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

	// ============================================================
	// 3. 기업 회원 기능 (UI: ViewApplicantsUI)
	// ============================================================

	/** 기업 회원 - 내가 올린 공고에 지원한 사람 목록 조회 */
	public List<ApplyVO> findApplicantsByCompany(String companyId) {
		List<ApplyVO> list = new ArrayList<>();
		String sql = "SELECT a.apply_id, a.user_no, a.post_id, a.status, a.apply_date, " + "p.title AS post_title "
				+ "FROM apply a " + "JOIN job_post p ON a.post_id = p.post_id " + "WHERE p.user_id = ? "
				+ "ORDER BY a.apply_date DESC";

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

	// ============================================================
	// 4. 관리자/기업 - 지원 상태 변경
	// ============================================================

	/** 지원 상태 변경 (접수/검토 중/합격/불합격) */
	public boolean updateStatus(int applyId, String newStatus) {
		if (!isValidStatus(newStatus)) {
			System.out.println("⚠️ 허용되지 않은 상태값입니다: " + newStatus);
			return false;
		}

		String sql = "UPDATE apply SET status = ? WHERE apply_id = ?";
		try (Connection conn = DBUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setString(1, newStatus);
			pstmt.setInt(2, applyId);
			return pstmt.executeUpdate() > 0;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}


}
