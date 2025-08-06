package ui;

import boardVO.UserVO;
import dao.UserDAO;

public class ProfileInputUI extends BaseUI {

    @Override
    public void execute() throws Exception {
        System.out.println("\n[회원 추가 정보 입력]");

        String userType = BaseUI.loginUserType; // U or C

        UserDAO dao = new UserDAO();
        UserVO user = new UserVO();
        user.setUserId(BaseUI.loginUserId);  // 로그인한 사용자
        user.setUserType(userType);

        if ("U".equals(userType)) {
            // 개인회원 입력 항목
            String jobTitle = scanStr("희망 직무: ");
            String location = scanStr("희망 근무 지역: ");
            String hasProject = scanStr("프로젝트 경험 여부 (있음/없음): ");
            String project = hasProject.equals("있음") ? scanStr("진행한 프로젝트 내용 (예: 자바 웹게시판 개발): ") : "없음";
            String education = scanStr("이수한 교육 (예: 자바 부트캠프, 카페 실무 2주 등): ");

            user.setResumeJobTitle(jobTitle);
            user.setResumeLocation(location);
            user.setResumeHasProject(hasProject);
            user.setResumeProject(project);
            user.setResumeEducation(education);

        } else if ("C".equals(userType)) {
            // 기업회원 입력 항목
            String companyName = scanStr("회사명: ");
            String managerName = scanStr("담당자 이름: ");
            String bizNo = scanStr("사업자 등록번호: ");

            user.setCompanyName(companyName);
            user.setManagerName(managerName);
            user.setBusinessNumber(bizNo);
        }

        boolean result = dao.updateAdditionalInfo(user);

        if (result) {
            System.out.println("✅ 추가 정보가 저장되었습니다.");
        } else {
            System.out.println("❌ 저장 실패. 다시 시도해주세요.");
        }
    }
}
