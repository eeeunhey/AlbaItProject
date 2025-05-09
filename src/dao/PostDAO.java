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
		String sql = "INSERT INTO job_post (post_id, user_id, title, content, location, pay, work_time, deadline) "
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
				pstmt.setDate(7, java.sql.Date.valueOf(post.getDeadline()));

				pstmt.addBatch(); // 일괄 처리
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
	            post.setCreatedAt(rs.getString("created_at"));
	            list.add(post);
			}

		} catch (SQLException e) {
			System.out.println("❌ 게시글 조회 중 오류 발생");
			e.printStackTrace();
		}

		return list;
	}

	public List<PostVO> selectAllBoard() {
		// TODO Auto-generated method stub
		return null;
	}

	public PostVO selectByNo(int boardNo) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean deleteByNo(int boardNo) {
		// TODO Auto-generated method stub
		return false;
	}

}
