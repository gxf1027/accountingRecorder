package cn.gxf.spring.struts.integrate.actions;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

import cn.gxf.spring.struts.integrate.security.UserLogin;
import cn.gxf.spring.struts2.integrate.dao.DmUtilDaoImplJdbc;
import cn.gxf.spring.struts2.integrate.model.AccountBook;
import cn.gxf.spring.struts2.integrate.model.FundDetail;
import cn.gxf.spring.struts2.integrate.model.TransferDetail;
import cn.gxf.spring.struts2.integrate.service.DetailAccountService;
import cn.gxf.spring.struts2.integrate.service.DetailAccountUnivServiceImpl;
import cn.gxf.spring.struts2.integrate.service.DmService;

public class TransferDetailAction  extends ActionSupport implements Preparable, RequestAware, SessionAware, ModelDriven<TransferDetail>{

	private static final long serialVersionUID = 7773125454286834233L;
	private Map<String, Object> mysession = null;
	private Map<String, Object> myrequest = null;
	private TransferDetail transferDetail;
	private List<String> mxuuidList;
	private Date date_from;
	private Date date_to;

	
	@Autowired
	private DmService dmService;
	
	@Autowired
	@Qualifier("DetailAccountService")
	private DetailAccountService detailAccountService;
	
	@Autowired
	private DetailAccountUnivServiceImpl<TransferDetail> detailAccountUnivServiceImpl;
	
	public void prepareInputTransfer(){
		UserLogin user = (UserLogin) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		List<AccountBook> books = dmService.getZhInfo(user.getId());
		Map<String, String> transfer_dm = dmService.getTransferType(user.getId());
		Map<String, String> fund_type = dmService.getFundType();
		this.myrequest.put("ZH_INFO", books);
		this.myrequest.put("ZH_INFO_MAP", dmService.getZhInfoMap(user.getId()));
		this.myrequest.put("dm_zzlx", transfer_dm);
		this.myrequest.put("fund_type", fund_type);
	}
	
	public String inputTransfer()
	{	
		return "TransferInputOk";
	}
	
	public void prepareEditShow(){
		this.transferDetail = this.detailAccountUnivServiceImpl.getTransferDetailByMxuuid(this.mxuuidList.get(0));
		UserLogin user = (UserLogin) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<AccountBook> books = dmService.getZhInfo(user.getId());
		this.myrequest.put("ZH_INFO", books);
		this.myrequest.put("ZH_INFO_MAP", dmService.getZhInfoMap(user.getId()));
		
		Map<String, String> transfer_dm = dmService.getTransferType(user.getId());
		this.myrequest.put("dm_zzlx", transfer_dm);
		Map<String, String> fund_type = dmService.getFundType();
		this.myrequest.put("fund_type", fund_type);
	}
	
	public String editShow(){
		this.myrequest.put("DETAIL_MODE", "EDIT");
		return "TransferInputOk";
	}
	
	public String saveTransfer(){
		UserLogin user = (UserLogin) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		this.transferDetail.setUser_id(user.getId());
		//this.transferDetail.setXgrq(new Date());
		//System.out.println(this.transferDetail);
		//detailAccountService.saveOneTransfer(this.transferDetail);
		//detailAccountService.saveOneTransferMB(this.transferDetail);
		if (this.transferDetail.getZzlx_dm().equals("0003")){
			this.transferDetail.getFundDetail().setConfirmedSum(this.transferDetail.getJe()); // 设置金额，因为前台没有输入项目
		}
		detailAccountUnivServiceImpl.saveOne(this.transferDetail);
		return "saveOk";
	}
	
	public String saveTransferAndRec(){
		UserLogin user = (UserLogin) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		this.transferDetail.setUser_id(user.getId());
		if (this.transferDetail.getZzlx_dm().equals("0003")){
			this.transferDetail.getFundDetail().setConfirmedSum(this.transferDetail.getJe()); // 设置金额，因为前台没有输入项目
		}
		detailAccountUnivServiceImpl.saveOne(this.transferDetail);
		return "saveRecOk";
	}
	
	public String updateTransfer(){
		System.out.println("updateTransfer: " + this.transferDetail);
		// 再次判断下权限，或者权限是管理员
		TransferDetail transferFromDb = detailAccountUnivServiceImpl.getTransferDetailByMxuuid(this.transferDetail.getMxuuid());
		UserLogin user = (UserLogin) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		// this.incomeDetail的user_id字段为空，因为表单中无该字段
		if (user.getId() != transferFromDb.getUser_id() && !user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))){
			return "have-no-authority";
		}
		this.transferDetail.setXgrq(new Date());
		if (this.transferDetail.getFundDetail() != null){
			this.transferDetail.getFundDetail().setXgrq(new Date());
		}
		detailAccountUnivServiceImpl.updateOne(this.transferDetail);
		return "saveOk";
	}
	
	public String delPatch(){
		List<TransferDetail> list = detailAccountUnivServiceImpl.getTransferDetailByPatchMxuuid(mxuuidList);
		detailAccountUnivServiceImpl.deletePatch(list);
		return "delOk";
	}
	
	@Override
	public void setSession(Map<String, Object> session) {
		this.mysession = session;
		
	}

	@Override
	public void setRequest(Map<String, Object> request) {
		this.myrequest = request;
		
	}

	@Override
	public void prepare() throws Exception {
		// TODO Auto-generated method stub
		
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

	@Override
	public TransferDetail getModel() {
		// TODO Auto-generated method stub
		if( this.transferDetail == null ){
			this.transferDetail = new TransferDetail();
			this.transferDetail.setFundDetail(new FundDetail());
		}
		return this.transferDetail;
	}

}
