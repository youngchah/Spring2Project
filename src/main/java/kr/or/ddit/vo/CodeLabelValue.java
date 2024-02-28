package kr.or.ddit.vo;

import lombok.Data;

@Data
public class CodeLabelValue {
	
	private String label;	// 요소의 label로 설정
	private String value;	// 요소의 값으로 설정

	public CodeLabelValue() {}
	public CodeLabelValue(String value, String label) {
		this.value = value;
		this.label = label;
	}
}
