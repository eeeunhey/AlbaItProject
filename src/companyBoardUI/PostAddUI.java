package companyBoardUI;

import java.util.ArrayList;
import java.util.List;

import ui.BaseUI;
import boardVO.PostVO;
import dao.PostDAO;

public class PostAddUI extends BaseUI {

	@Override
	public void execute() throws Exception {
		printHeader();
		System.out.println("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
		System.out.println("                 📝 채용 공고 작성하기               ");
		System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");

		String userID = scanStr("📌 ID :          : ");
		String title = scanStr("📌 제목          : ");
		String content = scanStr("🖊️  내용          : ");
		String location = scanStr("📍 근무 지역     : ");
		String pay = scanStr("💰 시급/급여     : ");
		String workTime = scanStr("⏰ 근무 시간     : ");
		String deadlineStr = scanStr("📅 마감일 (yyyy-MM-dd): ");

		try {
			java.sql.Date deadlineDate = java.sql.Date.valueOf(deadlineStr); // 날짜 유효성 검사

			// VO 객체에 값 설정
			PostVO post = new PostVO();
			post.setUserId(userID);
			post.setTitle(title);
			post.setContent(content);
			post.setLocation(location);
			post.setPay(pay);
			post.setWorkTime(workTime);
			post.setDeadline(deadlineStr); // 여전히 String으로 저장

			List<PostVO> postList = new ArrayList<>();
			postList.add(post);

			PostDAO postDao = new PostDAO();
			boolean success = postDao.insertJobPostList(postList);

			System.out.println();
			System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
			if (success) {
				System.out.println("✅ 게시글이 성공적으로 등록되었습니다!");
			} else {
				System.out.println("❌ 게시글 등록에 실패했습니다.");
			}
			System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

		} catch (IllegalArgumentException e) {
			System.out.println("❌ 날짜 형식이 잘못되었습니다! 예: 2025-05-10");
			return;
		}
	}
}
