package ui;

import boardVO.PostVO;
import dao.PostDAO;

public class SearchOneUI extends BaseUI {
	// 하나 선택해서 조회하는 UI
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

		// ---------------------- 여기서부터 지원 기능 추가 ----------------------
		// 현재 로그인한 사용자 id (로그인 상태라면 세션/전역 변수로 관리)
		String loginUserId = BaseUI.loginUserId; // 이런 식으로 저장해두었다고 가정

		// 개인회원만 지원 가능
		if (BaseUI.loginUserType.equals("개인")) {
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
		}
	}

}
