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

		String companyId = BaseUI.loginUserId;
		if (isEmpty(companyId)) {
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

		System.out.println("지원자 수: " + applicantList.size() + "\n");

		// 표 헤더
		System.out.println(
				"┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
		System.out.printf("  %-6s %-10s %-12s %-16s %-8s\n", "NO", "이름", "닉네임", "지원 일시", "상태");
		System.out.println(
				"┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");

		for (ApplyVO apply : applicantList) {
			UserVO applicant = null;
			try {
				applicant = userDAO.getUserDetailByNo(apply.getUserNo());
			} catch (Exception e) {
				// 로그용으로만 사용하고 화면 오류는 숨김
				e.printStackTrace();
			}

			String name = safe(applicant != null ? applicant.getName() : null);
			String nickname = safe(applicant != null ? applicant.getNickname() : null);
			String appliedAt = apply.getApplyDate() != null ? df.format(apply.getApplyDate()) : "-";
			String status = safe(apply.getStatus());

			System.out.printf("  %-6d %-10s %-12s %-16s %-8s\n", apply.getUserNo(), name, nickname, appliedAt, status);
		}

		System.out
				.println("──────────────────────────────────────────────────────────────────────────────────────────");

		// 상세 조회
		int targetUserNo = scanInt("🔍 상세 정보를 볼 지원자 NO 입력 (0: 취소) : ");
		if (targetUserNo > 0) {
			UserVO detail = userDAO.getUserDetailByNo(targetUserNo);
			if (detail != null) {
				printApplicantDetail(detail, df);
			} else {
				System.out.println("⚠️ 해당 번호의 지원자를 찾을 수 없습니다.");
			}
		}
	}

	/** 지원자 상세 정보 출력 */
	private void printApplicantDetail(UserVO detail, SimpleDateFormat df) {
		System.out.println("\n📌 지원자 상세 정보");
		System.out.println("----------------------------------------");
		System.out.println("회원번호 : " + detail.getUserNo());
		System.out.println("아이디   : " + safe(detail.getUserId()));
		System.out.println("이름     : " + safe(detail.getName()));
		System.out.println("닉네임   : " + safe(detail.getNickname()));
		System.out.println("회원유형 : " + convertUserTypeName(detail.getUserType()));
		System.out.println("가입일   : " + (detail.getRegDate() != null ? df.format(detail.getRegDate()) : "-"));
		System.out.println("----------------------------------------\n");
	}

	/** 코드 → 한글 변환 */
	private String convertUserTypeName(String code) {
		switch (safe(code)) {
		case "U":
			return "개인회원";
		case "C":
			return "기업회원";
		case "A":
			return "관리자";
		default:
			return "알수없음";
		}
	}

	/** null, 빈문자 방지 */
	private String safe(String s) {
		return (s == null || s.trim().isEmpty()) ? "-" : s.trim();
	}

	/** 공백 여부 */
	private boolean isEmpty(String s) {
		return s == null || s.trim().isEmpty();
	}
}
