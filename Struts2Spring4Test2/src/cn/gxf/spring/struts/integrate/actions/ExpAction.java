package cn.gxf.spring.struts.integrate.actions;

import java.util.List;
import java.util.Map;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.validator.annotations.Validations;

import cn.gxf.spring.struts2.integrate.model.AccountInfo;
import cn.gxf.spring.struts2.integrate.model.ExpInfo;
import cn.loan.ws.service.LoanInfo;
import cn.loan.ws.service.LoanService;


@Validations
public class ExpAction extends ActionSupport implements SessionAware, ApplicationContextAware, ModelDriven<ExpInfo> {
	
	private static final long serialVersionUID = -7091304428749167409L;
	
//	@Autowired
//	@Qualifier("loanServiceProxy")
//	private LoanService loanService;
	
	private ExpInfo expInfo = null;
	private Map<String, Object> mysession = null;
	private ApplicationContext appctx = null;
	
	public String saveExp(){
		
		System.out.println("ExpAction::saveExp");
		System.out.println("ExpAction::saveExp: " + this.expInfo);
		
		AccountInfo accInfo = (AccountInfo) this.mysession.get(AccountAction.STR_ACC);
		if (null == accInfo){
			return ERROR;
		}
		
		this.mysession.remove(AccountAction.STR_EXP);
		this.mysession.put(AccountAction.STR_EXP, this.expInfo);
		
//		this.mysession.remove("sid");
//		this.mysession.put("sid",ServletActionContext.getRequest().getSession().getId());
		
		accInfo.setExpInfo(this.expInfo);
		accInfo.setExpenditure(expInfo.getExpenditure());
		accInfo.setNetincome(accInfo.getIncome()-accInfo.getExpenditure());
		
		return SUCCESS;
	}
	
	public String loanInfo(){
		
		try {
			//以下顺序说明：
			//在获取贷款数据时，可能同时修改了其他数据
			//1. 先保存model到session（其他修改的数据得到保存）
			//2. 获取服务，调用接口，获得贷款
			//3. 如果调用失败，抛出异常；如果成功，设置model的贷款值，再次保存到session
			System.out.println(this.expInfo);
			mysession.put(AccountAction.STR_EXP, this.expInfo);
			
			LoanService loanService = (LoanService) this.appctx.getBean("loanServiceProxy");
			System.out.println("loanInfo: " + loanService);
			List<LoanInfo> list = loanService.getLoanInfo("2017-01", "gxf");
			
			
			this.expInfo.setLoan(list.get(0).getJe());
			this.expInfo.updateExpenditure();
			mysession.put(AccountAction.STR_EXP, this.expInfo);
				
//			ExpInfo expInfo = (ExpInfo) mysession.get(AccountAction.STR_EXP);
//			expInfo.setLoan(list.get(0).getJe());
//			expInfo.updateExpenditure();
//			mysession.put(AccountAction.STR_EXP, expInfo);
			
			return "loanInfo_OK";
			
		} catch (BeanCreationException e) {
			// TODO: handle exception
			this.addFieldError("loan", "WebService服务异常");
			System.out.println("WebService down...");
			e.printStackTrace();
		}
		
		return "loanInfo_OK";
	}
	
	@Override
	public ExpInfo getModel() {
		// TODO Auto-generated method stub
		if (this.expInfo == null){
			this.expInfo = new ExpInfo();
		}
		return this.expInfo;
	}

	@Override
	public void setSession(Map<String, Object> arg0) {
		// TODO Auto-generated method stub
		this.mysession = arg0;
	}

	@Override
	public void setApplicationContext(ApplicationContext arg0) throws BeansException {
		// TODO Auto-generated method stub
		this.appctx = arg0;
	}

	
}
