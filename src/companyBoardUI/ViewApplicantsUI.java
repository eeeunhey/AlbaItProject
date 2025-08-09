package companyBoardUI;

import java.text.SimpleDateFormat;
import java.util.List;

import boardVO.ApplyVO;
import boardVO.UserVO;
import dao.ApplyDAO;
import dao.UserDAO;
import ui.BaseUI;

public class ViewApplicantsUI extends BaseUI {

    @Override
    public void execute() throws Exception {
        printHeader();
        System.out.println("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
        System.out.println("                 📄 지원자 목록 조회               ");
        System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");

        String companyId = BaseUI.loginUserId; // 현재 로그인 기업 ID
        if (companyId == null || companyId.isEmpty()) {
            System.out.println("⚠️ 로그인 정보가 없습니다. 먼저 로그인해 주세요.");
            return;
        }

        ApplyDAO applyDAO = new ApplyDAO();
        UserDAO userDAO = new UserDAO();

        List<ApplyVO> applicantList = applyDAO.findApplicantsByCompany(companyId);
        if (applicantList == null || applicantList.isEmpty()) {
            System.out.println("⚠️ 현재까지 지원한 인원이 없습니다.");
            return;
        }

        System.out.println("지원자 수: " + applicantList.size());
        System.out.println();

        // 표 헤더
        System.out.println(
            "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
        System.out.printf("  %-6s %-10s %-12s %-16s %-8s %-10s %-18s\n",
                "NO", "이름", "닉네임", "지원 일시", "상태", "희망직무", "프로젝트경험/교육");
        System.out.println(
            "┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        for (ApplyVO apply : applicantList) {
            int applicantNo = apply.getUserNo();

            UserVO applicant = null;
            try {
                applicant = userDAO.getUserDetailByNo(applicantNo);
            } catch (Exception e) {
                // 개별 조회 실패해도 전체 출력은 계속
            }

            String name       = safe(applicant != null ? applicant.getName() : null);
            String nickname   = safe(applicant != null ? applicant.getNickname() : null);
            String jobTitle   = safe(applicant != null ? applicant.getResumeJobTitle() : null);
            String hasProject = safe(applicant != null ? applicant.getResumeHasProject() : null);
            String edu        = safe(applicant != null ? applicant.getResumeEducation() : null);

            String appliedAt  = apply.getApplyDate() != null ? df.format(apply.getApplyDate()) : "-";
            String status     = safe(apply.getStatus());

            // 한 줄 요약 + 상세 정보(프로젝트/교육은 합쳐서 표시)
            String projEdu = (hasProject.equals("-") && edu.equals("-"))
                    ? "-"
                    : (hasProject + "/" + edu);

            System.out.printf("  %-6d %-10s %-12s %-16s %-8s %-10s %-18s\n",
                    applicantNo, name, nickname, appliedAt, status, jobTitle, projEdu);
        }

        System.out.println(
            "──────────────────────────────────────────────────────────────────────────────────────────────────────");
        System.out.println("ℹ️  상세 프로필이 더 필요하면: '개별 지원자 상세보기' 메뉴를 따로 만들어 user_no로 조회하세요.");
    }

    private String safe(String s) {
        return (s == null || s.trim().isEmpty()) ? "-" : s.trim();
    }
}
