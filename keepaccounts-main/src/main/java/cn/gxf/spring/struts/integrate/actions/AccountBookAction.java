package cn.gxf.spring.struts.integrate.actions;

import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import cn.gxf.spring.struts.integrate.security.UserLogin;
import cn.gxf.spring.struts2.integrate.model.AccountBook;
import cn.gxf.spring.struts2.integrate.model.IncomeDetail;
import cn.gxf.spring.struts2.integrate.model.PaymentDetail;
import cn.gxf.spring.struts2.integrate.model.TransferDetail;
import cn.gxf.spring.struts2.integrate.service.DmService;

public class AccountBookAction extends ActionSupport implements RequestAware, SessionAware, ModelDriven<AccountBook>{

	private Map<String, Object> requestMap;
	private Map<String, Object> sessionMap;
	private String bookErrorMessage;
	
	private IncomeDetail incomeDetail;
	private PaymentDetail paymentDetail;
	private TransferDetail transferDetail;
	
	private AccountBook accountBook;
	
	@Autowired
	private DmService dmService;
	

	private static final long serialVersionUID = 3928877422644374734L;
	
	public String inputBook(){
		Map<String, String> zhlx = dmService.getZhLx();
		this.requestMap.put("zhlx", zhlx);
		return "inputBook";
	}
	
	public String inputBookFromIncome(){
		System.out.println("inputBookFromIncome: " + this.incomeDetail);
		Map<String, String> zhlx = dmService.getZhLx();
		this.requestMap.put("zhlx", zhlx);
		this.sessionMap.put("detialRedir", this.incomeDetail);
		return "inputBook";
	}
	
	public String inputBookFromPayment(){
		System.out.println("inputBookFromPayment: " + this.paymentDetail);
		Map<String, String> zhlx = dmService.getZhLx();
		this.requestMap.put("zhlx", zhlx);
		this.sessionMap.put("detialRedir", this.paymentDetail);
		return "inputBook";
	}
	
	public void validateSaveBook(){
		System.out.println("validateSaveBook");
		
		UserLogin user = (UserLogin) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<AccountBook> bookList = this.dmService.getZhInfoSimple(user.getId());
		for (AccountBook item : bookList){
			if ( item.getZh_mc().equals(this.accountBook.getZh_mc())  ){
				System.out.println("name '" + item.getZh_mc() + "' has existed.");
				this.addFieldError("zh_mc", "������˻������Ѵ��ڣ�");
				this.bookErrorMessage= "������˻������Ѵ��ڣ�";
				return;
			}
		}
	}
	
	public String saveBook(){
		System.out.println("saveBook: " + this.accountBook);
		
		
		// save book
		UserLogin user = (UserLogin) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		this.accountBook.setUser_id(user.getId());
		this.accountBook.setKhrmc(user.getUsername());
		this.dmService.saveAccBook(this.accountBook);
		// save end
		
		
		Object obj = this.sessionMap.get("detialRedir");
		System.out.println(obj);
		if (obj instanceof IncomeDetail){
			this.incomeDetail = (IncomeDetail) obj;
			this.incomeDetail.setZh_dm(this.accountBook.getZh_dm());
			sessionMap.remove("detialRedir");
			return "bookSaveOkFromIncome";
		}else if (obj instanceof PaymentDetail){
			this.paymentDetail = (PaymentDetail) obj;
			this.paymentDetail.setZh_dm(this.accountBook.getZh_dm());
			sessionMap.remove("detialRedir");
			return "bookSaveOkFromPayment";
		}else if (obj instanceof TransferDetail){
			this.transferDetail = (TransferDetail) obj;
			sessionMap.remove("detialRedir");
			return "bookSaveOkFromTransfer";
		}
		return null;
		
	}

	@Override
	public void setRequest(Map<String, Object> arg0) {
		this.requestMap = arg0;
	}
	
	@Override
	public void setSession(Map<String, Object> arg0) {
		this.sessionMap = arg0;
	}

	public IncomeDetail getIncomeDetail() {
		return incomeDetail;
	}
	
	public void setIncomeDetail(IncomeDetail incomeDetail) {
		this.incomeDetail = incomeDetail;
	}

	public PaymentDetail getPaymentDetail() {
		return paymentDetail;
	}

	public void setPaymentDetail(PaymentDetail paymentDetail) {
		this.paymentDetail = paymentDetail;
	}

	public TransferDetail getTransferDetail() {
		return transferDetail;
	}

	public void setTransferDetail(TransferDetail transferDetail) {
		this.transferDetail = transferDetail;
	}
	
	public AccountBook getAccountBook() {
		return accountBook;
	}
	
	public void setAccountBook(AccountBook accountBook) {
		this.accountBook = accountBook;
	}
	
	public void setBookErrorMessage(String bookErrorMessage) {
		this.bookErrorMessage = bookErrorMessage;
	}
	
	public String getBookErrorMessage() {
		return bookErrorMessage;
	}

	@Override
	public AccountBook getModel() {
		if (this.accountBook == null){
			this.accountBook = new AccountBook();
		}
		return this.accountBook;
	}
	
}
