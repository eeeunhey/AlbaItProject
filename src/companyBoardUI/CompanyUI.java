package companyBoardUI;


import exception.ChoiceOutOfBoundException;
import ui.AddUI;
import ui.DeleteUI;
import ui.IBoardUI;
import ui.UpdateUI;
import ui.BaseBoardUI;

public class CompanyUI extends BaseBoardUI {

    @Override
    protected String getMenuTitle() {
        return "기업 회원 메뉴 선택";
    }

    @Override
    protected String[] getSpecificMenuItems() {
        return new String[] {
            "채용공고 등록",   // 3
            "지원자 조회",     // 4
            "게시글 등록",     // 5
            "게시글 수정",     // 6
            "게시글 삭제"      // 7
        };
    }

    @Override
    protected IBoardUI getSpecificUI(String choice) throws ChoiceOutOfBoundException {
        switch (choice) {
            case "3": return new PostAddUI(); // 또는 CompanyAddUI 등
            case "4": return new ViewApplicantsUI();
            case "5": return new AddUI();
            case "6": return new UpdateUI();
            case "7": return new DeleteUI();
            default:
                throw new ChoiceOutOfBoundException("⚠️ 메뉴에서 선택할 수 없는 숫자입니다.");
        }
    }
}
