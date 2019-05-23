package hxy.inspec.inspector.po;

import lombok.Data;

@Data
public class User {
	
	private String userId;
	private String userName;
	private String userTel;
	private String userPasswd;
	private String userGrade;
	private String province;
	private String city;
	private String district;
	private String address;
	private String email;//质检员邮箱
	private String rmb;//人民币
	private String orders;//订单数
	private String integral;//积分
	private String status;//质检员状态

}
