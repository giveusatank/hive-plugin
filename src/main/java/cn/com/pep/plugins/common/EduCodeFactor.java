package cn.com.pep.plugins.common;

public class EduCodeFactor {
	private String code_key;
	private int startIndex;
	private int length;
	private int order;
	private String code;

	public EduCodeFactor(String code_key,int startIndex, int length, int order, String code) {
		this.code_key = code_key;
		this.length = length;
		this.order = order;
		this.code = code;
		this.startIndex = startIndex;
	}

	public String getCode_key() {
		return code_key;
	}

	public void setCode_key(String code_key) {
		this.code_key = code_key;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}
}
