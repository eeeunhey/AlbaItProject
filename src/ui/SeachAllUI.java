package ui;

import java.util.List;

import boardVO.PostVO;
import dao.PostDAO;

public class SeachAllUI extends BaseUI {
	// 전체 글 조회하는 UI 부분
	@Override
	public void execute() throws Exception {
		PostDAO postDao = new PostDAO();
		List<PostVO> posts = postDao.selectAllPosts();

		if (posts.isEmpty()) {
			System.out.println("📭 게시글이 없습니다.");
			return;
		}
		
		System.out.println(
				"┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
		System.out.println("                                       🧾 채용 정보 전체 보기");
		System.out.println(
				"┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
		System.out.printf(" %-4s │ %-12s  %-22s  %-14s  %-12s  %-12s  %-12s  %-12s \n", "ID", "작성자", "제목", "지역", "시급",
				"근무시간", "마감일", "작성일");
		System.out.println(
				"────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────");

		for (PostVO post : posts) {
			System.out.printf(" %-4d│  %-12s  %-22s  %-14s  %-12s  %-12s  %-12s  %-12s \n", post.getPostId(),
					fixWidth(post.getUserId(), 12), fixWidth(post.getTitle(), 22), fixWidth(post.getLocation(), 14),
					fixWidth(post.getPay(), 12), fixWidth(post.getWorkTime(), 12), fixWidth(post.getDeadline(), 10),
					fixWidth(post.getCreatedAt(), 10));
		}

		System.out.println(
				"────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────");
		Thread.sleep(1000);
	}

}
