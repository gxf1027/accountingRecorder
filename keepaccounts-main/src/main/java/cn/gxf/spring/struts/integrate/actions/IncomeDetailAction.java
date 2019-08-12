package cn.gxf.spring.struts.integrate.actions;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

import cn.gxf.spring.struts.integrate.security.UserLogin;
import cn.gxf.spring.struts2.integrate.dao.AccDetailVoDao;
import cn.gxf.spring.struts2.integrate.model.AccountingDetail;
import cn.gxf.spring.struts2.integrate.model.FinancialProductDetail;
import cn.gxf.spring.struts2.integrate.model.IncomeDetail;
import cn.gxf.spring.struts2.integrate.service.DetailAccountService;
import cn.gxf.spring.struts2.integrate.service.DetailAccountUnivServiceImpl;
import cn.gxf.spring.struts2.integrate.service.DmService;
import cn.gxf.spring.struts2.integrate.service.FinanicalProductService;
import cn.gxf.spring.struts2.integrate.service.WaitingForSyncService;

public class IncomeDetailAction extends ActionSupport implements Preparable, RequestAware, SessionAware, ModelDriven<IncomeDetail>{

	
	private static final long serialVersionUID = -1145464068431323783L;
	
	private Map<String, Object> mysession = null;
	private Map<String, Object> myrequest = null;
	private IncomeDetail incomeDetail;
	private List<String> mxuuidList;
	private Date date_from;
	private Date date_to;
	private String productToReturn; // 理财产品的uuid
	private String realReturn;

	
	@Autowired
	private DmService dmService;
	
//	@Autowired
//	private AccDetailVoDao accDetailVoDao;
//	
//	@Autowired
//	@Qualifier("DetailAccountService")
//	private DetailAccountService detailAccountService;
	 
	@Autowired
	private DetailAccountUnivServiceImpl<IncomeDetail> detailAccountUnivServiceImpl;
	
	@Autowired
	private FinanicalProductService financialProductService;
	
	@Autowired
	private WaitingForSyncService wait4SyncService;
	
	public String inputIncome(){
		Map<String, String> map = dmService.getIncomeLb();
		System.out.println("income: " + map.hashCode());
		this.myrequest.put("SRLB", map);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		this.myrequest.put("NOW_DATE", sdf.format(new Date()));
		
		UserLogin user = (UserLogin) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		this.myrequest.put("ZH_INFO", dmService.getZhInfo(user.getId()));
		this.myrequest.put("ZH_INFO_MAP", dmService.getZhInfoMap(user.getId()));
		this.myrequest.put("holding_product", financialProductService.getFinancialProductUnreturnedMap(user.getId()));
		
//		IncomeDetail income = (IncomeDetail) this.myrequest.get("incomeDetialRedir");
//		System.out.println(this.myrequest.get("incomeDetialRedir"));
//		if (income != null){
//			this.incomeDetail = income;
//			ValueStack vs = ActionContext.getContext().getValueStack();
//			System.out.println(vs);
//			if (vs.peek() instanceof IncomeDetail){
//				System.out.println("vs.peek" + vs.peek());
//				vs.pop();
//				vs.push(income);
//				this.incomeDetail = income;
//			}
//		}
			 
		return "IncomeInputOk";
	}
	
	public void prepareEditShow(){
		if (this.mxuuidList.size() != 1){
			System.out.println("error!");
		}
		this.incomeDetail = this.detailAccountUnivServiceImpl.getIncomeDetailByMxuuid(this.mxuuidList.get(0));
		
		Map<String, String> map = dmService.getIncomeLb();
		System.out.println("income: " + map.hashCode());
		this.myrequest.put("SRLB", map);
		
		UserLogin user = (UserLogin) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		this.myrequest.put("ZH_INFO", dmService.getZhInfo(user.getId()));
		this.myrequest.put("ZH_INFO_MAP", dmService.getZhInfoMap(user.getId()));
		//this.myrequest.put("holding_product", financialProductService.getFinancialProductUnreturnedMap(user.getId()));
		//this.myrequest.put("111", financialProduct);
		
		if (this.incomeDetail.getFinprodUuid() != null){
			// 如果这笔"收入"关联了一笔理财产品
			FinancialProductDetail finprod = financialProductService.getFinancialProductByUuid(this.incomeDetail.getFinprodUuid());
			String finprod_info = finprod.getProductName();
			if (finprod.getDateCount() > 0){
				finprod_info += " "+finprod.getDateCount()+"天";
			}
			this.myrequest.put("finprod_info", finprod_info);
		}
	}
	
	public String editShow(){
		this.myrequest.put("DETAIL_MODE", "EDIT");
		return "IncomeInputOk";
	}
	
	public String saveIncome(){
		UserLogin user = (UserLogin) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		this.incomeDetail.setUser_id(user.getId());
		//this.incomeDetail.setXgrq(new Date());
		//System.out.println(this.incomeDetail);
		String accuuid = detailAccountUnivServiceImpl.saveOne(this.incomeDetail);
		
		// 延迟一段时间等待主从同步
		//AuxiliaryTools.delay(AuxiliaryTools.millisec_wait_mysql_sync);
		int count = wait4SyncService.queryWaiting4Save(accuuid);
		
		return "saveOk";
	}
	
	public String saveIncomeAndRec(){
		saveIncome();
		return "saveRecOk";
	}
	
	public String updateIncome(){
		System.out.println("updateIncome: " + this.incomeDetail);
		// 再次判断下权限，或者权限是管理员
		IncomeDetail incomeFromDb = detailAccountUnivServiceImpl.getIncomeDetailByMxuuid(this.incomeDetail.getMxuuid());
		UserLogin user = (UserLogin) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		// this.incomeDetail的user_id字段为空，因为表单中无该字段
		if (user.getId() != incomeFromDb.getUser_id() && !user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))){
			return "have-no-authority";
		}
		this.incomeDetail.setXgrq(new Date());
		this.incomeDetail.setUser_id(user.getId());
		AccountingDetail detailUpdated = detailAccountUnivServiceImpl.updateOne(this.incomeDetail);
		
		// 延迟一段时间等待主从同步
		//AuxiliaryTools.delay(AuxiliaryTools.millisec_wait_mysql_sync);
		int count = wait4SyncService.queryWaiting4Update(detailUpdated.getAccuuid(), detailUpdated.getXgrq());
		
		return "saveOk";
	}
	
	public String delPatch(){
		List<IncomeDetail> list = detailAccountUnivServiceImpl.getIncomeDetailByPatchMxuuid(mxuuidList);
		detailAccountUnivServiceImpl.deletePatch(list);
		
		// 延迟一段时间等待主从同步
		//AuxiliaryTools.delay(AuxiliaryTools.millisec_wait_mysql_sync);
		if (!list.isEmpty()){
			int count = wait4SyncService.queryWaiting4Del(list.get(0).getAccuuid());
		}
				
		return "delOk";
	}
	
	public IncomeDetail getIncomeDetail() {
		return incomeDetail;
	}
	
	public void setIncomeDetail(IncomeDetail incomeDetail) {
		this.incomeDetail = incomeDetail;
	}
	
	@Override
	public void setSession(Map<String, Object> session) {
		// TODO Auto-generated method stub
		this.mysession = session;
	}

	@Override
	public void setRequest(Map<String, Object> request) {
		// TODO Auto-generated method stub
		this.myrequest = request;
	}

	public List<String> getMxuuidList() {
		return mxuuidList;
	}

	public void setMxuuidList(List<String> mxuuidList) {
		this.mxuuidList = mxuuidList;
	}

	public Date getDate_from() {
		return date_from;
	}

	public void setDate_from(Date date_from) {
		this.date_from = date_from;
	}

	public Date getDate_to() {
		return date_to;
	}

	public void setDate_to(Date date_to) {
		this.date_to = date_to;
	}
	
	public String getProductToReturn() {
		return productToReturn;
	}
	
	public void setProductToReturn(String productToReturn) {
		this.productToReturn = productToReturn;
	}
	
	public String getRealReturn() {
		return realReturn;
	}
	
	public void setRealReturn(String realReturn) {
		this.realReturn = realReturn;
	}

	@Override
	public IncomeDetail getModel() {
		// TODO Auto-generated method stub
		if (this.incomeDetail == null){
			this.incomeDetail = new IncomeDetail();
		}
		return this.incomeDetail;
	}

	@Override
	public void prepare() throws Exception {
		// TODO Auto-generated method stub
		
	}

}
