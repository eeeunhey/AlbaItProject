package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import boardVO.BoardVO;
import boardVO.PostVO;
import db.DBUtil;

public class PostDAO {

	private List<PostVO> postList;

	public PostDAO() {
		postList = new ArrayList<>();
	}

	PostVO post = null;

	public boolean insertJobPostList(List<PostVO> postList) {
		String sql = "INSERT INTO job_post " + "(post_id, user_id, title, content, location, pay, work_time, deadline) "
				+ "VALUES (job_post_seq.NEXTVAL, ?, ?, ?, ?, ?, ?, ?)";

		try (Connection conn = DBUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			for (PostVO p : postList) {
				post = p;
				pstmt.setString(1, post.getUserId());
				pstmt.setString(2, post.getTitle());
				pstmt.setString(3, post.getContent());
				pstmt.setString(4, post.getLocation());
				pstmt.setString(5, post.getPay());
				pstmt.setString(6, post.getWorkTime());

				if (post.getDeadline() != null && !post.getDeadline().isEmpty()) {
					pstmt.setDate(7, java.sql.Date.valueOf(post.getDeadline()));
				} else {
					pstmt.setNull(7, java.sql.Types.DATE);
				}

				pstmt.addBatch();
			}

			int[] results = pstmt.executeBatch();
			for (int result : results) {
				if (result == PreparedStatement.EXECUTE_FAILED) {
					return false;
				}
			}
			return true;

		} catch (SQLException e) {
			System.err.println("SQL Error with post: " + (post != null ? post.toString() : "unknown"));
			e.printStackTrace();
		}
		return false;
	}

	// 전체 공고 조회
	public List<PostVO> selectAllPosts() {
		List<PostVO> list = new ArrayList<>();
		String sql = "SELECT * FROM job_post ORDER BY post_id DESC";

		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()) {

			while (rs.next()) {
				PostVO post = new PostVO();
				post.setPostId(rs.getInt("post_id"));
				post.setUserId(rs.getString("user_id"));
				post.setTitle(rs.getString("title"));
				post.setContent(rs.getString("content"));
				post.setLocation(rs.getString("location"));
				post.setPay(rs.getString("pay"));
				post.setWorkTime(rs.getString("work_time"));
				post.setDeadline(rs.getString("deadline"));
				post.setCreatedAt(rs.getString("reg_date"));

				list.add(post);
			}

		} catch (SQLException e) {
			System.out.println("❌ 게시글 조회 중 오류 발생");
			e.printStackTrace();
		}

		return list;
	}

	public PostVO selectByNo(int boardNo) {
		String sql = "SELECT * FROM job_post WHERE post_id = ?";
		PostVO post = null;

		try (Connection conn = DBUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, boardNo);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					post = new PostVO();
					post.setPostId(rs.getInt("post_id"));
					post.setUserId(rs.getString("user_id")); // 작성자 ID
					post.setTitle(rs.getString("title"));
					post.setContent(rs.getString("content"));
					post.setLocation(rs.getString("location"));
					post.setPay(rs.getString("pay"));
					post.setWorkTime(rs.getString("work_time"));
					post.setDeadline(rs.getString("deadline"));
					post.setCreatedAt(rs.getString("reg_date"));

				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return post;
	}

	public boolean deleteByNo(int boardNo) {
		String sql = "DELETE FROM job_post WHERE post_id = ?";
		try (Connection conn = DBUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, boardNo);
			int result = pstmt.executeUpdate();
			return result > 0; // 1건 이상 삭제되면 true

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean applyToJob(int postId, String userId) {
		String sql = "INSERT INTO apply (apply_id, post_id, user_id, status) "
				+ "VALUES (apply_seq.NEXTVAL, ?, ?, '대기')";
		try (Connection conn = DBUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, postId);
			pstmt.setString(2, userId);

			return pstmt.executeUpdate() > 0;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}


}
