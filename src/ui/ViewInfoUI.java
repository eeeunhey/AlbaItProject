package ui;

import boardVO.UserVO;
import dao.UserDAO;

public class ViewInfoUI extends BaseUI {

	@Override
	public void execute() throws Exception {
		System.out.println("\n📄 [회원 정보 조회]");

		String userId = BaseUI.loginUserId;
		UserDAO dao = new UserDAO();
		UserVO user = dao.getUserDetail(userId);

		if (user == null) {
			System.out.println("❌ 회원 정보를 불러오지 못했습니다.");
			return;
		}

		System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
		System.out.printf("아이디: %s\n", user.getUserId());
		System.out.printf("이름: %s\n", user.getName());
		System.out.printf("닉네임: %s\n", user.getNickname());
		System.out.printf("회원유형: %s\n", user.getUserType());

		if ("C".equals(user.getUserType())) {
			System.out.printf("회사명: %s\n", user.getCompanyName());
			System.out.printf("담당자: %s\n", user.getManagerName());
			System.out.printf("사업자등록번호: %s\n", user.getBusinessNumber());
		} else if ("U".equals(user.getUserType())) {
			System.out.printf("희망 직무: %s\n", user.getResumeJobTitle());
			System.out.printf("희망 근무 지역: %s\n", user.getResumeLocation());
			System.out.printf("프로젝트 경험: %s\n", user.getResumeHasProject());
			System.out.printf("프로젝트 내용: %s\n", user.getResumeProject());
			System.out.printf("이수한 교육: %s\n", user.getResumeEducation());
		}

		System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
	}
}
