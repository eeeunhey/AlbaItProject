package boardVO;

public class UserVO {

	// 공통 정보
	private int userNo;
	private String userId;
	private String password;
	private String name;
	private String nickname;
	private String userType;   // 'A', 'U', 'C'
	private String status;     // 상태값 (예: '활성', '탈퇴')
	private String createDate; // 가입일

	// 기업회원 전용
	private String companyName;
	private String managerName;
	private String businessNumber;

	// 개인회원 전용 이력서 정보
	private String resumeJobTitle;       // 희망 직무
	private String resumeLocation;       // 희망 근무 지역
	private String resumeHasProject;     // 프로젝트 경험 여부
	private String resumeProject;        // 프로젝트 내용
	private String resumeEducation;      // 이수한 교육

	// 생성자 (기본 생성자 추가)
	public UserVO() {}

	public UserVO(int userNo, String userId, String password, String name, String nickname,
				  String userType, String status, String createDate) {
		this.userNo = userNo;
		this.userId = userId;
		this.password = password;
		this.name = name;
		this.nickname = nickname;
		this.userType = userType;
		this.status = status;
		this.createDate = createDate;
	}

	// -------------------- Getter / Setter --------------------

	public int getUserNo() {
		return userNo;
	}
	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	// 기업회원 전용
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getManagerName() {
		return managerName;
	}
	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public String getBusinessNumber() {
		return businessNumber;
	}
	public void setBusinessNumber(String businessNumber) {
		this.businessNumber = businessNumber;
	}

	// 개인회원 전용
	public String getResumeJobTitle() {
		return resumeJobTitle;
	}
	public void setResumeJobTitle(String resumeJobTitle) {
		this.resumeJobTitle = resumeJobTitle;
	}

	public String getResumeLocation() {
		return resumeLocation;
	}
	public void setResumeLocation(String resumeLocation) {
		this.resumeLocation = resumeLocation;
	}

	public String getResumeHasProject() {
		return resumeHasProject;
	}
	public void setResumeHasProject(String resumeHasProject) {
		this.resumeHasProject = resumeHasProject;
	}

	public String getResumeProject() {
		return resumeProject;
	}
	public void setResumeProject(String resumeProject) {
		this.resumeProject = resumeProject;
	}

	public String getResumeEducation() {
		return resumeEducation;
	}
	public void setResumeEducation(String resumeEducation) {
		this.resumeEducation = resumeEducation;
	}

	// -------------------- toString --------------------

	@Override
	public String toString() {
		return "UserVO [userNo=" + userNo + ", userId=" + userId + ", password=" + password
				+ ", name=" + name + ", nickname=" + nickname + ", userType=" + userType
				+ ", status=" + status + ", createDate=" + createDate
				+ ", companyName=" + companyName + ", managerName=" + managerName
				+ ", businessNumber=" + businessNumber
				+ ", resumeJobTitle=" + resumeJobTitle + ", resumeLocation=" + resumeLocation
				+ ", resumeHasProject=" + resumeHasProject + ", resumeProject=" + resumeProject
				+ ", resumeEducation=" + resumeEducation + "]";
	}
}
