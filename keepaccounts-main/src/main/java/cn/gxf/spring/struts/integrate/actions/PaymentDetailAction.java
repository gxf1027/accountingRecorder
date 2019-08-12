package cn.gxf.spring.struts.integrate.actions;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

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
import cn.gxf.spring.struts.integrate.util.AuxiliaryTools;
import cn.gxf.spring.struts2.integrate.model.AccountingDetail;
import cn.gxf.spring.struts2.integrate.model.DmPaymentDl;
import cn.gxf.spring.struts2.integrate.model.DmPaymentXl;
import cn.gxf.spring.struts2.integrate.model.PaymentDetail;
import cn.gxf.spring.struts2.integrate.service.DetailAccountService;
import cn.gxf.spring.struts2.integrate.service.DetailAccountUnivServiceImpl;
import cn.gxf.spring.struts2.integrate.service.DmService;
import cn.gxf.spring.struts2.integrate.service.WaitingForSyncService;

public class PaymentDetailAction extends ActionSupport implements Preparable, RequestAware, SessionAware, ModelDriven<PaymentDetail>{

	private static final long serialVersionUID = -2307323073133404443L;
	private Map<String, Object> mysession = null;
	private Map<String, Object> myrequest = null;
	private PaymentDetail paymentDetail;
	private List<String> mxuuidList;
	private Date date_from;
	private Date date_to;
	
    private Logger logger = LogManager.getLogger();

	
	@Autowired
	private DmService dmService;
	
	@Autowired
	@Qualifier("DetailAccountService")
	private DetailAccountService detailAccountService;
	
	@Autowired
	private DetailAccountUnivServiceImpl<PaymentDetail> detailAccountUnivServiceImpl;
	
	@Autowired
	private WaitingForSyncService wait4SyncService;

	
	public String inputPayment(){
		//Map<DmPaymentDl, List<DmPaymentXl>> map = daoImplJdbc.getPaymentDlXlDzb();
		Map<DmPaymentDl, List<DmPaymentXl>> map = dmService.getPaymentDlXlDzb();
		System.out.println("payment: " +map.hashCode());
		this.myrequest.put("PAY_DLXL_DZB", map);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		this.myrequest.put("NOW_DATE", sdf.format(new Date()));
		
		UserLogin user = (UserLogin) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		this.myrequest.put("ZH_INFO", dmService.getZhInfo(user.getId()));
		this.myrequest.put("ZH_INFO_MAP", dmService.getZhInfoMap(user.getId()));
		//System.out.println("getZhInfoMap: " + dmService.getZhInfoMap(user.getId()));
		
		Map<String, String> map_cat = dmService.getOutgoCategory(user.getId());
		this.myrequest.put("outgo_category", map_cat);
		Map<String, String> map_cat_com = dmService.getOutgoCategoryCommon();
		this.myrequest.put("outgo_cat_common", map_cat_com);
		Map<String, String> map_cat_all = new TreeMap<>();
		map_cat_all.putAll(map_cat_com);
		map_cat_all.putAll(map_cat);
		this.myrequest.put("outgo_cat_all", map_cat_all);
		
		
		return "PaymentInputOk";
	}
	
	public void prepareEditShow(){
		this.paymentDetail = detailAccountUnivServiceImpl.getPaymentDetailByMxuuid(this.mxuuidList.get(0));
		Map<DmPaymentDl, List<DmPaymentXl>> map = dmService.getPaymentDlXlDzb();
		System.out.println("payment: " +map.hashCode());
		this.myrequest.put("PAY_DLXL_DZB", map);
		
		UserLogin user = (UserLogin) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		this.myrequest.put("ZH_INFO", dmService.getZhInfo(user.getId()));
		this.myrequest.put("ZH_INFO_MAP", dmService.getZhInfoMap(user.getId()));
		
		Map<String, String> map_cat = dmService.getOutgoCategory(user.getId());
		this.myrequest.put("outgo_category", map_cat);
		Map<String, String> map_cat_com = dmService.getOutgoCategoryCommon();
		this.myrequest.put("outgo_cat_common", map_cat_com);
		Map<String, String> map_cat_all = new TreeMap<>();
		map_cat_all.putAll(map_cat_com);
		map_cat_all.putAll(map_cat);
		this.myrequest.put("outgo_cat_all", map_cat_all);
	}
	
	public String editShow(){
		if(this.paymentDetail == null){
			return "PaymentError";
		}
		this.myrequest.put("DETAIL_MODE", "EDIT");
		return "PaymentInputOk";
	}
	
	public String savePayment(){
		UserLogin user = (UserLogin) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		this.paymentDetail.setUser_id(user.getId());
		//this.paymentDetail.setXgrq(new Date());
		//detailAccountService.saveOnePaymentMB(this.paymentDetail);
		String accuuid = detailAccountUnivServiceImpl.saveOne(this.paymentDetail);
		
		if (accuuid.equals("BLOCKED_BY_SENTINEL")){
            logger.info("payment save over flow.");
			return "OVER_FLOW";
		}else{
			
			// 延迟一段时间等待主从同步
			int count = wait4SyncService.queryWaiting4Save(accuuid);
		}
		
		return "saveOk";
	}
	
	public String savePaymentAndRec(){
		this.savePayment();
		return "saveRecOk";
	}
	
	public String updatePayment(){
		System.out.println("updatePayment: " + this.paymentDetail);
		// 再次判断下权限，或者权限是管理员
		PaymentDetail paymentFromDb = detailAccountUnivServiceImpl.getPaymentDetailByMxuuid(this.paymentDetail.getMxuuid());
		UserLogin user = (UserLogin) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		// this.incomeDetail的user_id字段为空，因为表单中无该字段
		if (user.getId() != paymentFromDb.getUser_id() && !user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))){
			return "have-no-authority";
		}
		this.paymentDetail.setUser_id(user.getId());
		this.paymentDetail.setXgrq(new Date());
		AccountingDetail detailUpdated = detailAccountUnivServiceImpl.updateOne(this.paymentDetail);
		
		// 延迟一段时间用于主从同步
		//AuxiliaryTools.delay(AuxiliaryTools.millisec_wait_mysql_sync);
		int count = wait4SyncService.queryWaiting4Update(detailUpdated.getAccuuid(), detailUpdated.getXgrq());
		
		return "saveOk";
	}

	public String delPatch(){
		List<PaymentDetail> list = detailAccountUnivServiceImpl.getPaymentDetailByPatchMxuuid(mxuuidList);
		detailAccountUnivServiceImpl.deletePatch(list);
		
		// 延迟一段时间用于主从同步
		//AuxiliaryTools.delay(AuxiliaryTools.millisec_wait_mysql_sync);
		if (!list.isEmpty()){
			int count = wait4SyncService.queryWaiting4Del(list.get(0).getAccuuid());
		}
				
		return "delOk";
	}
	
	public PaymentDetail getPaymentDetail() {
		return paymentDetail;
	}
	
	public void setPaymentDetail(PaymentDetail paymentDetail) {
		this.paymentDetail = paymentDetail;
	}
	
	@Override
	public PaymentDetail getModel() {
		// TODO Auto-generated method stub
		if(this.paymentDetail == null){
			this.paymentDetail = new PaymentDetail();
		}
		return paymentDetail;
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

	
}
