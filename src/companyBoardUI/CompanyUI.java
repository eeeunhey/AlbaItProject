package companyBoardUI;


import exception.ChoiceOutOfBoundException;
import ui.BaseBoardUI;
import ui.IBoardUI;
import ui.ProfileInputUI;
import ui.ResignUI;
import ui.ViewInfoUI;

public class CompanyUI extends BaseBoardUI {

    @Override
    protected String getMenuTitle() {
        return "기업 회원 메뉴 선택";
    }

    @Override
    protected String[] getSpecificMenuItems() {
        return new String[] {
            "회원 정보 조회",	//3
            "채용공고 등록",   // 4
            "지원자 조회",     // 5
            "게시글 등록",     // 6
            "게시글 수정",     // 7
            "게시글 삭제",      // 8
            "회원 탈퇴"       // 9
        };
    }

    @Override
    protected IBoardUI getSpecificUI(String choice) throws ChoiceOutOfBoundException {
        switch (choice) {
        
        	case "3": return new ViewInfoUI(); 
            case "4": return new PostAddUI(); 
            case "5": return new ViewApplicantsUI();
            case "6": return new ProfileInputUI();
            case "7": return new UpdateUI();
            case "8": return new DeleteUI();
            case "9": return new ResignUI();
            default:
                throw new ChoiceOutOfBoundException("⚠️ 메뉴에서 선택할 수 없는 숫자입니다.");
        }
    }
}
