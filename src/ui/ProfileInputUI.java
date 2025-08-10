package ui;

import boardVO.UserVO;
import dao.UserDAO;

public class ProfileInputUI extends BaseUI {

	@Override
	public void execute() throws Exception {
		System.out.println("\n[회원 정보 수정]");

		UserDAO dao = new UserDAO();
		UserVO user = new UserVO();
		user.setUserId(BaseUI.loginUserId); // 로그인한 사용자 아이디 (String)

		// 현재 이름/닉네임/비밀번호만 수정 가능
		String newName = scanStr("이름 (변경 안하려면 Enter): ");
		String newNickname = scanStr("닉네임 (변경 안하려면 Enter): ");
		String newPassword = scanStr("비밀번호 (변경 안하려면 Enter): ");

		// 입력값이 없으면 null 유지 → DAO에서 NVL로 기존 값 사용
		user.setName(newName.isEmpty() ? null : newName);
		user.setNickname(newNickname.isEmpty() ? null : newNickname);
		user.setPassword(newPassword.isEmpty() ? null : newPassword);

		boolean result = dao.updateUserInfo(user);

		if (result) {
			System.out.println("✅ 회원 정보가 수정되었습니다.");
		} else {
			System.out.println("❌ 수정 실패. 다시 시도해주세요.");
		}
	}
}
