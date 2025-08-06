package personalBoardUI;


import exception.ChoiceOutOfBoundException;
import ui.AddUI;
import ui.DeleteUI;
import ui.IBoardUI;
import ui.SeachAllUI;
import ui.SearchOneUI;
import ui.UpdateUI;
import ui.BaseBoardUI;


public class PersonalUI extends BaseBoardUI {

    @Override
    protected String getMenuTitle() {
        return "개인 회원 메뉴 선택";
    }

    @Override
    protected String[] getSpecificMenuItems() {
        return new String[] {
            "지원 현황",      // 3
            "회원 정보 입력",  // 4
            "회원 정보 수정",  // 5
            "회원 탈퇴"   // 6
        };
    }

    @Override
    protected IBoardUI getSpecificUI(String choice) throws ChoiceOutOfBoundException {
        switch (choice) {
            case "1": return new SeachAllUI();
            case "2": return new SearchOneUI();
            case "3": return new PersonalApplyJob(); // 지원 현황 보기
            case "4": return new AddUI();            // 회원 정보 입력
            case "5": return new UpdateUI();         // 회원 정보 수정
            case "6": return new DeleteUI();         // 회원 정보 삭제
            default:
                throw new ChoiceOutOfBoundException("⚠️ 메뉴에서 선택할 수 없는 숫자입니다.");
        }
    }
}