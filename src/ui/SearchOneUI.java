package ui;

import boardVO.PostVO;
import boardVO.ApplyVO;
import dao.ApplyDAO;

public class SearchOneUI extends BaseUI {

	@Override
	public void execute() throws Exception {
		// 1. 글번호 입력
		int postId = scanInt("조회할 글번호를 입력하세요 : ");
		PostVO post = boardService.searchBoardByNo(postId);

		// 2. 게시글 없을 때
		if (post == null) {
			System.out.printf("❌ [%d]번 게시글이 존재하지 않습니다.\n", postId);
			return;
		}

		// 3. 게시글 정보 출력
		printPostDetail(post);

		// 4. 지원 가능 여부 확인
		if (!isEligibleToApply()) {
			return;
		}

		// 5. 중복 지원 여부 확인
		ApplyDAO applyDao = new ApplyDAO();
		if (applyDao.hasAlreadyApplied(postId, BaseUI.loginUserNo)) {
			System.out.println("⚠️ 이미 지원한 공고입니다.");
			return;
		}

		// 6. 지원 여부 묻기
		String choice = scanStr("이 공고에 지원하시겠습니까? (Y/N) : ");
		if (!"Y".equalsIgnoreCase(choice)) {
			System.out.println("❌ 지원이 취소되었습니다.");
			return;
		}

		// 7. 지원 처리
		ApplyVO apply = new ApplyVO();
		apply.setUserNo(BaseUI.loginUserNo);
		apply.setPostID(postId);

		boolean result = applyDao.insertApply(apply);
		if (result) {
			System.out.println("✅ 지원이 완료되었습니다!");
		} else {
			System.out.println("❌ 지원 중 오류가 발생했습니다.");
		}
	}

	/** 게시글 상세 출력 */
	private void printPostDetail(PostVO post) {
		System.out.println("-------------------------------");
		System.out.println("\t글번호 : " + post.getPostId());
		System.out.println("\t작성자 : " + post.getUserId());
		System.out.println("\t제목 : " + post.getTitle());
		System.out.println("\t내용 : " + post.getContent());
		System.out.println("\t근무지 : " + post.getLocation());
		System.out.println("\t급여 : " + post.getPay());
		System.out.println("\t근무시간 : " + post.getWorkTime());
		System.out.println("\t마감일 : " + post.getDeadline());
		System.out.println("\t등록일 : " + post.getCreatedAt());
		System.out.println("-------------------------------");
	}

	/** 지원 가능한 회원인지 확인 */
	private boolean isEligibleToApply() {
		String userType = BaseUI.loginUserType; // "U", "C", "A"
		if (!"U".equalsIgnoreCase(userType)) {
			System.out.println("⚠️ 개인 회원만 지원 가능합니다.");
			return false;
		}
		return true;
	}
}
