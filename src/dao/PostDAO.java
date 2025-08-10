// dao/PostDAO.java
package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import boardVO.PostVO;
import db.DBUtil;

public class PostDAO extends DaoSupport {

    private List<PostVO> postList;

    public PostDAO() {
        postList = new ArrayList<>();
    }

    private PostVO post = null;

    public boolean insertJobPostList(List<PostVO> postList) {
        final String sql =
            "INSERT INTO job_post (post_id, user_id, title, content, location, pay, work_time, deadline) " +
            "VALUES (job_post_seq.NEXTVAL, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            for (PostVO p : postList) {
                post = p;
                pstmt.setString(1, p.getUserId());
                pstmt.setString(2, p.getTitle());
                pstmt.setString(3, p.getContent());
                pstmt.setString(4, p.getLocation());
                pstmt.setString(5, p.getPay());
                pstmt.setString(6, p.getWorkTime());

                if (p.getDeadline() != null && !p.getDeadline().isEmpty()) {
                    pstmt.setDate(7, java.sql.Date.valueOf(p.getDeadline())); // yyyy-MM-dd
                } else {
                    pstmt.setNull(7, Types.DATE);
                }

                pstmt.addBatch();
            }

            int[] results = pstmt.executeBatch();
            for (int r : results) {
                if (r == PreparedStatement.EXECUTE_FAILED) {
                    return false;
                }
            }
            return true;

        } catch (SQLException e) {
            // 어떤 post에서 실패했는지 문맥 포함
            String hint = (post != null) ? (" [title=" + post.getTitle() + ", userId=" + post.getUserId() + "]") : "";
            throw wrap(e, "insertJobPostList Insert 실패" + hint);
        }
    }

    // 전체 공고 조회
    public List<PostVO> selectAllPosts() {
        final String sql = "SELECT * FROM job_post ORDER BY post_id DESC";
        List<PostVO> list = new ArrayList<>();

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
            return list;

        } catch (SQLException e) {
            throw wrap(e, "selectAllPosts 실패");
        }
    }

    public PostVO selectByNo(int boardNo) {
        final String sql = "SELECT * FROM job_post WHERE post_id = ?";
        PostVO post = null;

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, boardNo);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    post = new PostVO();
                    post.setPostId(rs.getInt("post_id"));
                    post.setUserId(rs.getString("user_id"));
                    post.setTitle(rs.getString("title"));
                    post.setContent(rs.getString("content"));
                    post.setLocation(rs.getString("location"));
                    post.setPay(rs.getString("pay"));
                    post.setWorkTime(rs.getString("work_time"));
                    post.setDeadline(rs.getString("deadline"));
                    post.setCreatedAt(rs.getString("reg_date"));
                }
            }
            return post;

        } catch (SQLException e) {
            throw wrap(e, "selectByNo 실패: post_id=" + boardNo);
        }
    }

    public boolean deleteByNo(int boardNo) {
        final String sql = "DELETE FROM job_post WHERE post_id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, boardNo);
            int result = pstmt.executeUpdate();
            return result > 0;

        } catch (SQLException e) {
            throw wrap(e, "deleteByNo 실패: post_id=" + boardNo);
        }
    }

    public boolean applyToJob(int postId, String loginUserId) {
        final String findUserNoSql = "SELECT user_no FROM user_table WHERE user_id = ?";
        final String insertSql =
            "INSERT INTO apply (apply_id, post_id, user_no, status) VALUES (apply_seq.NEXTVAL, ?, ?, '접수')";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt1 = conn.prepareStatement(findUserNoSql)) {

            pstmt1.setString(1, loginUserId);
            try (ResultSet rs = pstmt1.executeQuery()) {
                if (!rs.next()) return false;

                int userNo = rs.getInt("user_no");
                try (PreparedStatement pstmt2 = conn.prepareStatement(insertSql)) {
                    pstmt2.setInt(1, postId);
                    pstmt2.setInt(2, userNo);
                    return pstmt2.executeUpdate() > 0;
                }
            }

        } catch (SQLException e) {
            throw wrap(e, "applyToJob 실패: post_id=" + postId + ", userId=" + loginUserId);
        }
    }
}
