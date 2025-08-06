package adminBoardUI;

import companyBoardUI.DeleteUI;
import exception.ChoiceOutOfBoundException;
import ui.BaseBoardUI;
import ui.IBoardUI;

public class AdminUI extends BaseBoardUI {

    @Override
    protected String getMenuTitle() {
        return "관리자 메뉴 선택";
    }

    @Override
    protected String[] getSpecificMenuItems() {
        return new String[] {
            "게시글 삭제",       // 3
            "회원 목록 조회",     // 4
            "회원 상세 보기",     // 5
            "회원 상태 변경",     // 6
            "지원 내역 조회",     // 7
            "공지사항 등록/수정", // 8
            "통계 보기"          // 9
        };
    }

    @Override
    protected IBoardUI getSpecificUI(String choice) throws ChoiceOutOfBoundException {
        switch (choice) {
            case "3": return new DeleteUI(); // 게시글 삭제
            case "4": return new AdminUserListUI(); // 회원 목록 조회
            case "5": return new AdminUserDetailUI(); // 회원 상세 보기
            case "6": return new AdminUserStatusUI(); // 회원 상태 변경
            case "7": return new AdminApplyListUI(); // 지원 내역 조회
            case "8": return new AdminNoticeUI(); // 공지사항 등록/수정
            case "9": return new AdminStatsUI(); // 통계 보기
            default:
                throw new ChoiceOutOfBoundException("⚠️ 메뉴에서 선택할 수 없는 숫자입니다.");
        }
    }
}
