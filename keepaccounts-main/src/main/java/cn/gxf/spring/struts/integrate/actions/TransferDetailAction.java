package cn.gxf.spring.struts.integrate.actions;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
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
import cn.gxf.spring.struts.integrate.util.AuxiliaryTools;
import cn.gxf.spring.struts2.integrate.dao.DmUtilDaoImplJdbc;
import cn.gxf.spring.struts2.integrate.model.AccountBook;
import cn.gxf.spring.struts2.integrate.model.AccountingDetail;
import cn.gxf.spring.struts2.integrate.model.FinancialProductDetail;
import cn.gxf.spring.struts2.integrate.model.FundDetail;
import cn.gxf.spring.struts2.integrate.model.TransferDetail;
import cn.gxf.spring.struts2.integrate.service.DetailAccountService;
import cn.gxf.spring.struts2.integrate.service.DetailAccountUnivServiceImpl;
import cn.gxf.spring.struts2.integrate.service.DmService;
import cn.gxf.spring.struts2.integrate.service.FinanicalProductService;
import cn.gxf.spring.struts2.integrate.service.WaitingForSyncService;

public class TransferDetailAction  extends ActionSupport implements Preparable, RequestAware, SessionAware, ModelDriven<TransferDetail>{

	private static final long serialVersionUID = 7773125454286834233L;
	private Map<String, Object> mysession = null;
	private Map<String, Object> myrequest = null;
	private TransferDetail transferDetail;
	private String productUnredeemed;
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
	
	@Autowired
	private FinanicalProductService financialProductService;
	
	@Autowired
	private WaitingForSyncService wait4SyncService;
	
	public void prepareInputTransfer(){
		UserLogin user = (UserLogin) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		List<AccountBook> books = dmService.getZhInfo(user.getId());
		Map<String, String> transfer_dm_com = dmService.getTransferTypeCommon();
		Map<String, String> transfer_dm_user = dmService.getTransferType(user.getId());
		Map<String, String> fund_type = dmService.getFundType();
		Map<String, String> yh_dm = dmService.getYhInfo();
		this.myrequest.put("ZH_INFO", books);
		this.myrequest.put("ZH_INFO_MAP", dmService.getZhInfoMap(user.getId()));
		this.myrequest.put("dm_zzlx_user", transfer_dm_user);
		this.myrequest.put("dm_zzlx_com", transfer_dm_com);
		/*Map<String, String> transfer_dm = new HashMap<String, String>();
		transfer_dm.putAll(transfer_dm_com);
		transfer_dm.putAll(transfer_dm_user);
		this.myrequest.put("dm_zzlx", transfer_dm);*/
		this.myrequest.put("fund_type", fund_type);
		this.myrequest.put("yh_dm", yh_dm);
		this.myrequest.put("fin_prod_type", dmService.getFinancialProdType());
		this.myrequest.put("holding_product", financialProductService.getFinancialProductUnredeemedMap(user.getId()));
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
		
		Map<String, String> transfer_dm_user = dmService.getTransferType(user.getId());
		this.myrequest.put("dm_zzlx_user", transfer_dm_user);
		Map<String, String> transfer_dm_com = dmService.getTransferTypeCommon();
		this.myrequest.put("dm_zzlx_com", transfer_dm_com);
		/*Map<String, String> transfer_dm = new HashMap<String, String>();
		transfer_dm.putAll(transfer_dm_com);
		transfer_dm.putAll(transfer_dm_user);
		this.myrequest.put("dm_zzlx", transfer_dm);*/
		
		Map<String, String> fund_type = dmService.getFundType();
		this.myrequest.put("fund_type", fund_type);
		Map<String, String> yh_dm = dmService.getYhInfo();
		this.myrequest.put("yh_dm", yh_dm);
		Map<String, String> fin_prod_type = dmService.getFinancialProdType();
		this.myrequest.put("fin_prod_type", fin_prod_type);
		this.myrequest.put("holding_product", financialProductService.getFinancialProductUnredeemedMap(user.getId()));
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
		if (this.transferDetail.getZzlx_dm().equals(DmService.zzlx_purchase_fund_dm)){
			this.transferDetail.getFundDetail().setConfirmedSum(this.transferDetail.getJe()); // ���ý���Ϊǰ̨û��������Ŀ
		}
		else if (this.transferDetail.getZzlx_dm().equals(DmService.zzlx_purchase_fin_prod_dm)){
			this.transferDetail.getFinancialProductDetail().setJe(this.transferDetail.getJe());
		}else if (this.transferDetail.getZzlx_dm().equals(DmService.zzlx_redeem_fin_prod_dm) || this.transferDetail.getZzlx_dm().equals(DmService.zzlx_add_fin_prod_dm)){ // �����أ����ʱֻ��Ҫ����ʵ������
			// this.transferDetail.financialProductDetail�Ѿ���realReturn���ݣ�����������uuid
			if(this.productUnredeemed == null ){
				this.productUnredeemed = "-1"; // ��Ϊ����������Ʋ�Ʒ��uuid��-1,����ʵ�ʲ���������
			}
			this.transferDetail.getFinancialProductDetail().setUuid(this.productUnredeemed); // ���ù�������Ʋ�Ʒuuid
		}
		System.out.println(this.transferDetail.getFinancialProductDetail().getStartDate());
		String accuuid = detailAccountUnivServiceImpl.saveOne(this.transferDetail);
		
		// �ӳ�һ��ʱ��ȴ�����ͬ��
		//AuxiliaryTools.delay(AuxiliaryTools.millisec_wait_mysql_sync);
		int count = wait4SyncService.queryWaiting4Save(accuuid);
				
		return "saveOk";
	}
	
	public String saveTransferAndRec(){
		UserLogin user = (UserLogin) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		this.transferDetail.setUser_id(user.getId());
		if (this.transferDetail.getZzlx_dm().equals(DmService.zzlx_purchase_fund_dm)){
			this.transferDetail.getFundDetail().setConfirmedSum(this.transferDetail.getJe()); // ���ý���Ϊǰ̨û��������Ŀ
		}else if (this.transferDetail.getZzlx_dm().equals(DmService.zzlx_purchase_fin_prod_dm)){
			this.transferDetail.getFinancialProductDetail().setJe(this.transferDetail.getJe());
		}else if (this.transferDetail.getZzlx_dm().equals(DmService.zzlx_redeem_fin_prod_dm)|| this.transferDetail.getZzlx_dm().equals(DmService.zzlx_add_fin_prod_dm)){ // �����أ����ʱֻ��Ҫ����ʵ������
			// this.transferDetail.financialProductDetail�Ѿ���realReturn���ݣ�����������uuid
			if(this.productUnredeemed == null ){
				this.productUnredeemed = "-1";
			}
			// ��ʱtransferDetail��û��mxuuid,���Բ���������Ʋ�Ʒ�����ת��uuid
			this.transferDetail.getFinancialProductDetail().setUuid(this.productUnredeemed);
		}
		String accuuid = detailAccountUnivServiceImpl.saveOne(this.transferDetail);
		
		// �ӳ�һ��ʱ��ȴ�����ͬ��
		int count = wait4SyncService.queryWaiting4Save(accuuid);
				
		return "saveRecOk";
	}
	
	public String updateTransfer(){
		System.out.println("updateTransfer: " + this.transferDetail);
		// �ٴ��ж���Ȩ�ޣ�����Ȩ���ǹ���Ա
		TransferDetail transferFromDb = detailAccountUnivServiceImpl.getTransferDetailByMxuuid(this.transferDetail.getMxuuid());
		UserLogin user = (UserLogin) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		// this.incomeDetail��user_id�ֶ�Ϊ�գ���Ϊ�����޸��ֶ�
		if (user.getId() != transferFromDb.getUser_id() && !user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))){
			return "have-no-authority";
		}
		this.transferDetail.setUser_id(user.getId());
		this.transferDetail.setXgrq(new Date());
		
		// ����ת�˷�ʽ
		switch (this.transferDetail.getZzlx_dm()) {
		case DmService.zzlx_purchase_fin_prod_dm:
			if (this.transferDetail.getFinancialProductDetail() != null){
				this.transferDetail.getFinancialProductDetail().setXgrq(new Date());
				this.transferDetail.getFinancialProductDetail().setJe(this.transferDetail.getJe());
			}
			break;
		case DmService.zzlx_purchase_fund_dm:
			if ( this.transferDetail.getFundDetail() != null){
				this.transferDetail.getFundDetail().setXgrq(new Date());
				this.transferDetail.getFundDetail().setConfirmedSum(this.transferDetail.getJe());
			}
			break;
		case DmService.zzlx_redeem_fin_prod_dm:
			if(this.productUnredeemed == null ){
				this.productUnredeemed = "-1";
			}
			this.transferDetail.getFinancialProductDetail().setUuid(this.productUnredeemed);
			break;
		default:
			break;
		}
		
		AccountingDetail detailUpdated = detailAccountUnivServiceImpl.updateOne(this.transferDetail);
		
		// �ӳ�һ��ʱ��ȴ�����ͬ��
		//AuxiliaryTools.delay(AuxiliaryTools.millisec_wait_mysql_sync);
		int count = wait4SyncService.queryWaiting4Update(detailUpdated.getAccuuid(), detailUpdated.getXgrq());
		
		return "saveOk";
	}
	
	public String delPatch(){
		List<TransferDetail> list = detailAccountUnivServiceImpl.getTransferDetailByPatchMxuuid(mxuuidList);
		detailAccountUnivServiceImpl.deletePatch(list);
		
		// �ӳ�һ��ʱ��ȴ�����ͬ��
		//AuxiliaryTools.delay(AuxiliaryTools.millisec_wait_mysql_sync);
		if (!list.isEmpty()){
			int count = wait4SyncService.queryWaiting4Del(list.get(0).getAccuuid());
		}
		
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
	
	public String getProductUnredeemed() {
		return productUnredeemed;
	}
	
	public void setProductUnredeemed(String productUnredeemed) {
		this.productUnredeemed = productUnredeemed;
	}

	@Override
	public TransferDetail getModel() {
		// TODO Auto-generated method stub
		if( this.transferDetail == null ){
			this.transferDetail = new TransferDetail();
			this.transferDetail.setFundDetail(new FundDetail());
			this.transferDetail.setFinancialProductDetail(new FinancialProductDetail());
		}
		return this.transferDetail;
	}

}
