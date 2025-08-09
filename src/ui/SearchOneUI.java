package ui;

import boardVO.PostVO;
import dao.PostDAO;
import dao.ApplyDAO;   // ✅ 추가

public class SearchOneUI extends BaseUI {
	@Override
	public void execute() throws Exception {

		int no = scanInt("조회할 글번호를 입력하세요 : ");
		PostVO postNo = boardService.searchBoardByNo(no);
		System.out.println(no);
		if (postNo == null) {
			System.out.println("입력하신 게시글 [" + no + "]번에 해당하는 게시글이 없습니다");
			return;
		}

		System.out.println("-------------------------------");
		System.out.println("\t글번호 : " + postNo.getPostId());
		System.out.println("\t작성자 : " + postNo.getUserId());
		System.out.println("\t제목 : " + postNo.getTitle());
		System.out.println("\t내용 : " + postNo.getContent());
		System.out.println("\t근무지 : " + postNo.getLocation());
		System.out.println("\t급여 : " + postNo.getPay());
		System.out.println("\t근무시간 : " + postNo.getWorkTime());
		System.out.println("\t마감일 : " + postNo.getDeadline());
		System.out.println("\t등록일 : " + postNo.getCreatedAt());
		System.out.println("-------------------------------");

		// ---------------------- 지원 기능 ----------------------
		String loginUserId = BaseUI.loginUserId; // 로그인한 사용자

		if ("U".equals(BaseUI.loginUserType)) {  // ✅ NPE 예방을 위해 리터럴 먼저
			ApplyDAO applyDao = new ApplyDAO();     // ✅ 추가


			// ✅ 먼저 중복 지원 여부 확인
			if (applyDao.hasAlreadyApplied(no, loginUserId)) {
				System.out.println("⚠️ 이미 지원한 공고입니다.");
				return;
			}

			String choice = scanStr("이 공고에 지원하시겠습니까? (Y/N) : ");
			if (choice.equalsIgnoreCase("Y")) {
				PostDAO postDao = new PostDAO();
				boolean result = postDao.applyToJob(no, loginUserId);
				if (result) {
					System.out.println("✅ 지원이 완료되었습니다!");
				} else {
					System.out.println("❌ 지원 중 오류가 발생했습니다.");
				}
			}
		} else {
			System.out.println("기업 및 관리자 계정은 지원할 수 없습니다.");
			System.out.println("현재 회원 유형: " + BaseUI.loginUserType);

		}
	}
}
