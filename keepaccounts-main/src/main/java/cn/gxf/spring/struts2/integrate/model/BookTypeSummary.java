package cn.gxf.spring.struts2.integrate.model;

public class BookTypeSummary implements Comparable<BookTypeSummary>{
	private String typeid;
	private String bookType;
	private float bookSum;

	public BookTypeSummary(String typeid, String bookType, float bookSum) {
		super();
		this.typeid = typeid;
		this.bookType = bookType;
		this.bookSum = bookSum;
	}

	public String getTypeid() {
		return typeid;
	}

	public void setTypeid(String typeid) {
		this.typeid = typeid;
	}

	public String getBookType() {
		return bookType;
	}

	public void setBookType(String bookType) {
		this.bookType = bookType;
	}

	public float getBookSum() {
		return bookSum;
	}

	public void setBookSum(float bookSum) {
		this.bookSum = bookSum;
	}

	@Override
	public String toString() {
		return "BookTypeSummary [typeid=" + typeid + ", bookType=" + bookType + ", bookSum=" + bookSum + "]";
	}

	@Override
	public int compareTo(BookTypeSummary b) {
		
		return this.typeid.compareTo(b.getTypeid());
	}

}
