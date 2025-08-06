package boardVO;

public class AdminUserVO {

	private int userId;
	private String username;
	private String password;
	private String nickname;
	private String userType; // "개인", "기업"
	private String status; // "정상", "정지", "탈퇴"
	private int reportCount;

	// 기본 생성자
	public AdminUserVO() {
	}

	// 전체 생성자
	public AdminUserVO(int userId, String username, String password, String nickname, String userType, String status,
			int reportCount) {
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.nickname = nickname;
		this.userType = userType;
		this.status = status;
		this.reportCount = reportCount;
	}

	// Getter/Setter
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public int getReportCount() {
		return reportCount;
	}

	public void setReportCount(int reportCount) {
		this.reportCount = reportCount;
	}
}
