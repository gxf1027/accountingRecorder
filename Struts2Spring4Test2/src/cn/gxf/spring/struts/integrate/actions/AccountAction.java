package cn.gxf.spring.struts.integrate.actions;


import java.util.Collections;
import java.util.List;
import java.util.Map;


import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

import cn.gxf.spring.struts2.integrate.dao.AccountDao;
import cn.gxf.spring.struts2.integrate.model.AccountInfo;
import cn.gxf.spring.struts2.integrate.model.ExpInfo;
import cn.gxf.spring.struts2.integrate.service.AccountService;

public class AccountAction extends ActionSupport implements SessionAware, RequestAware, Preparable, ModelDriven<AccountInfo> {

	/**
	 * 
	 */
	
	private static final long serialVersionUID = 3590193831500043324L;
	public static final String STR_ACC = "Account";
	public static final String STR_EXP = "Exp";
	private Map<String, Object> mysession = null;
	private Map<String, Object> myrequest = null;
	private AccountInfo accountInfo = null;
	private String nd;
	private String yf;
	private String accuuid;
	
	
	@Autowired
	@Qualifier("accountDao")
	private AccountDao accountDao;
	
	@Autowired
	@Qualifier("accountService")
	private AccountService accountService;
	
	public void prepareCalculateExp() {
		//System.out.println("prepareCalculateExp");
		String ssny = this.nd + this.yf;
		this.accountInfo = new AccountInfo();
		this.accountInfo.setSsny(ssny);
	}
	
	public String calculateExp(){
		
		if (mysession == null){
			return ERROR;
		}
		
		
		// ����������ҲҪͬ���������г���2�Ÿ���ʱ��������ȱʧ������
		// ����ж��Ÿ�������ҲҪ���ƶ��
		ExpInfo expInfo = (ExpInfo) mysession.get(STR_EXP);
		if (null != expInfo){
			this.accountInfo.setExpInfo(expInfo);
		}
		
		mysession.remove(STR_ACC);
		mysession.put(STR_ACC, this.accountInfo);
		
		mysession.put("sid",ServletActionContext.getRequest().getSession().getId());
		
		// test code...
//		AccountInfo accontInfo = new AccountInfo();
//		accontInfo.setSalary(100);
//		accontInfo.setBonus(0);
//		accontInfo.setIncome(100);
//		accontInfo.setExpenditure(10);
//		accontInfo.setNetincome(90);
//		accontInfo.getExpInfo().setExpenditure(10);
//		accontInfo.getExpInfo().setTax(1.2f);
//		accontInfo.getExpInfo().setOthers(1);
//		accontInfo.getExpInfo().setShopping(7.8f);
//		int rv = accountDao.persistOne(accontInfo);
//		System.out.println(accontInfo);
		
		return SUCCESS;
	}
	
	public void prepareAccountSave(){
		String ssny = this.nd + this.yf;
		this.accountInfo = new AccountInfo();
		this.accountInfo.setSsny(ssny);
	}
	
	public String accountSave()
	{
		if (this.mysession == null){
			return "NULL_SESSION_ERROR";
		}
		
		// ����ʱ����ʹ��model(this.accountInfo)�е��������ݣ���Ϊ�ڱ���ǰ���ܻ��ᱻ�޸ġ�session�е����ݿ����Ѳ�������
		// �����е���ֵ��js��֤��ȷ�ԣ����ٴ˴���������У��
		System.out.println("AccountAction::accountSave: " + this.mysession.get(STR_ACC));
		
		ExpInfo expInfo = (ExpInfo) this.mysession.get(STR_EXP);
		if (expInfo != null){
			this.accountInfo.setExpInfo(expInfo);
			this.mysession.put(STR_ACC, this.accountInfo);
		}
		else{
			this.addActionError("����д֧�����ٱ���");
			this.mysession.put(STR_ACC, this.accountInfo);
			return "ROLL_BACK";
		}
		
		
//		AccountInfo accontInfo = (AccountInfo) this.mysession.get("Account");
//		if (accontInfo == null || accountInfo.getExpInfo() == null)
//		{
//			return ERROR;
//		}
//		
//		String nd = accontInfo.getSsny().substring(0, 4);
//		String yf = accontInfo.getSsny().substring(4);
		
		if (mysession.get("Mode") == "add_mode"){
			// ����
			List<AccountInfo> list = accountDao.getByNdYf(this.nd, this.yf);
			if (list.isEmpty() == false){
				System.out.println("�Ѿ�������ͬ�·�(" + nd + yf + ")������" + list);
				this.addActionError("�Ѿ�������ͬ�·�(" + nd + yf + ")������");
				return "ROLL_BACK";
			}else{
				accountService.saveOneAccExp(this.accountInfo);
			}
		}else{
			// �༭
			accountService.updateOneAccExp(this.accountInfo);
		}
		
		mysession.clear();
		
		return "SAVE_OK";
	}
	
	public String resetAcc(){
		
		if (mysession != null){
			mysession.clear();
		}
		
		return "RESET_OK";
			
	}
	
	public String listAllAccInfo(){
		
		System.out.println("Holder: " + SecurityContextHolder.getContext().getAuthentication().getPrincipal());
		
		this.mysession.clear();
		List<AccountInfo> list;
		if (this.nd!=null && this.nd.equals("ȫ��")){
			list = accountService.listAll(null,null);
		}else{
			list = accountService.listAll(this.nd,null);
		}
		Collections.sort(list);
		this.myrequest.put("accountinfo_all", list);
		this.myrequest.put("acc_aggregation", AccountInfo.aggregate(list));
		//System.out.println(ServletActionContext.getRequest().getSession().getValue("SPRING_SECURITY_CONTEXT"));
		System.out.println("listAllAccInfo...");
		return "listAll";
	}
	
	public String deleteOne(){
		System.out.println(this.getModel().getAccuuid());
		System.out.println("deleteOne....");
		
		accountService.deleteAccExpInfo(this.getModel().getAccuuid());
		
		return "deleteOne";
	}
	
	public void prepareInput(){
		// accuuid��Ϊnull, ˵���Ǳ༭
		if (this.accuuid != null){
			this.accountInfo = this.accountService.getAccExpInfo(accuuid);
			mysession.put("Mode", "edit_mode");
			mysession.put(STR_ACC, this.accountInfo);
			mysession.put(STR_EXP, this.accountInfo.getExpInfo());
		}
		else{
			// accuuidΪ��, ˵��������
			if (mysession != null){
				mysession.clear();
				mysession.put("Mode", "add_mode");
			}
		}
	}
	
	public String input(){
		return "input_ok";
	}
	
	@Override
	public void setSession(Map<String, Object> arg0) {
		// TODO Auto-generated method stub
		this.mysession = arg0;
	}

	@Override
	public AccountInfo getModel() {
		// TODO Auto-generated method stub
		if (this.accountInfo == null){
			this.accountInfo = new AccountInfo();
		}
		return this.accountInfo;
	}

	@Override
	public void prepare() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setRequest(Map<String, Object> arg0) {
		// TODO Auto-generated method stub
		myrequest = arg0;
	}
	
	public Map<String, Object> getMyrequest() {
		return myrequest;
	}

	public String getNd() {
		return nd;
	}

	public void setNd(String nd) {
		this.nd = nd;
	}

	public String getYf() {
		return yf;
	}

	public void setYf(String yf) {
		this.yf = yf;
	}

	public String getAccuuid() {
		return accuuid;
	}

	public void setAccuuid(String accuuid) {
		this.accuuid = accuuid;
	}
	
}
