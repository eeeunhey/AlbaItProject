package boardVO;

import java.util.Date;

public class UserVO {

	// 공통 정보 (USER_TABLE 컬럼과 동일)
	private int userNo; // USER_NO
	private String userId; // USER_ID
	private String password; // PASSWORD
	private String name; // NAME
	private String nickname; // NICKNAME
	private String userType; // USER_TYPE ('A', 'U', 'C')
	private String activeYn; // ACTIVE_YN ('Y', 'N') ← 추가
	private Date regDate; // REG_DATE (가입일)

	// 전체 생성자
	public UserVO(int userNo, String userId, String password, String name, String nickname, String userType,
			String activeYn, Date regDate) {
		this.userNo = userNo;
		this.userId = userId;
		this.password = password;
		this.name = name;
		this.nickname = nickname;
		this.userType = userType;
		this.activeYn = activeYn;
		this.regDate = regDate;
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

	public String getActiveYn() {
		return activeYn;
	}

	public void setActiveYn(String activeYn) {
		this.activeYn = activeYn;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	// -------------------- toString --------------------
	@Override
	public String toString() {
		return "UserVO [userNo=" + userNo + ", userId=" + userId + ", password=" + password + ", name=" + name
				+ ", nickname=" + nickname + ", userType=" + userType + ", activeYn=" + activeYn + ", regDate="
				+ regDate + "]";
	}
}
