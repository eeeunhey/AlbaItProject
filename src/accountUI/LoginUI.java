package accountUI;

import companyBoardUI.CompanyUI;
import dao.UserDAO;
import personalBoardUI.PersonalUI;
import ui.BaseUI;
import ui.IBoardUI;
import adminBoardUI.AdminUI;

public class LoginUI extends BaseUI {

    private String userTypeLabel; // "개인", "기업", "관리자"
    private String expectedTypeCode; // "U", "C", "A" (라벨을 코드로 변환해둠)

    public LoginUI(String userTypeLabel) {
        this.userTypeLabel = userTypeLabel;
        this.expectedTypeCode = toTypeCode(userTypeLabel); // 라벨 -> 코드 매핑
    }

    @Override
    public void execute() throws Exception {

        for (int i = 0; i < 15; i++) System.out.println();
        printHeader();
        System.out.println("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
        System.out.printf("               %s 회원 로그인 페이지               \n", userTypeLabel);
        System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛\n");

        UserDAO dao = new UserDAO();

        while (true) {
            // ❗ 재입력 가능하도록 루프 안에서 받기
            String id = scanStr("🆔 아이디      :  ");
            String password = scanStr("🔑 비밀번호     : ");

            int isValid = dao.validateUserDetail(id, password); // 1=성공, 0=아이디 없음, 2=비번오류 등
            if (isValid == 1) {
                // 로그인 성공 시에만 사용자 유형 조회
                String actualUserType = dao.getUserTypeById(id);
                String type = normalizeTypeCode(actualUserType); // null/공백/소문자 방지

                if ("A".equals(type)) {
                    // 관리자는 요청 라벨 상관없이 관리자 페이지로
                    BaseUI.loginUserId = id;
                    BaseUI.loginUserType = "A"; // 전역은 코드로 저장(일관)
                    System.out.println("\n✅ 관리자 로그인 성공! 관리자 페이지로 이동합니다.\n");
                    new AdminUI().execute();
                    return;
                }

                if (type.equals(expectedTypeCode)) {
                    // 사용자 유형 일치
                    BaseUI.loginUserId = id;
                    BaseUI.loginUserType = type; // 코드로 저장: "U"/"C"/"A"
                    if ("U".equals(type)) {
                        System.out.println("\n✅ 로그인 성공! 개인 회원 페이지로 이동합니다.\n");
                        new PersonalUI().execute();
                    } else if ("C".equals(type)) {
                        System.out.println("\n✅ 로그인 성공! 기업 회원 페이지로 이동합니다.\n");
                        new CompanyUI().execute();
                    }
                    return;
                } else {
                    // 유형 불일치
                    System.out.println("\n❌ 로그인 실패! 회원 유형이 다릅니다.");
                    System.out.println("처음으로 돌아갑니다.\n");
                    return;
                }

            } else if (isValid == 0) {
                System.out.println("\n❌ 로그인 실패! 존재하지 않는 아이디입니다.");
                System.out.println("회원가입 페이지로 이동합니다.");
                new SignUpUI().execute();
                return;

            } else if (isValid == 2) {
                System.out.println("\n❌ 로그인 실패! 비밀번호가 틀렸습니다.");
                System.out.println("다시 입력해주세요.\n");
                // 루프 계속 → 다시 입력 받음
                continue;

            } else {
                System.out.println("\n⚠️ 등록되지 않은 회원입니다.");
                System.out.println("1. 회원가입");
                System.out.println("2. 로그인 다시 시도");
                int choice = scanInt("선택 ▶ ");
                if (choice == 1) {
                    new SignUpUI().execute();
                    return;
                } else {
                    // 루프 계속 → 다시 입력 받음
                    System.out.println();
                    continue;
                }
            }
        }
    }

    // ─────────────────────────── 유틸 ───────────────────────────

    /** 화면 라벨 → 코드 매핑 ("개인"→"U", "기업"→"C", "관리자"→"A") */
    private String toTypeCode(String label) {
        if (label == null) return "";
        switch (label.trim()) {
            case "개인": return "U";
            case "기업": return "C";
            case "관리자": return "A";
            default: return "";
        }
    }

    /** DB코드 정규화: null/공백/소문자 → 대문자 코드 */
    private String normalizeTypeCode(String code) {
        if (code == null) return "";
        return code.trim().toUpperCase();
    }
}
