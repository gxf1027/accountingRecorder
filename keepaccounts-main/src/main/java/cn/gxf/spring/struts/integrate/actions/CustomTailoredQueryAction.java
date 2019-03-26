package cn.gxf.spring.struts.integrate.actions;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.interceptor.RequestAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import com.github.pagehelper.Page;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

import cn.gxf.spring.struts.integrate.security.UserLogin;
import cn.gxf.spring.struts.mybatis.dao.CustomTailorQueryDao;
import cn.gxf.spring.struts.mybatis.dao.StatNdYfMBDao;
import cn.gxf.spring.struts2.integrate.dao.DmUtilDaoImplJdbc;
import cn.gxf.spring.struts2.integrate.model.DmPaymentDl;
import cn.gxf.spring.struts2.integrate.model.DmPaymentXl;
import cn.gxf.spring.struts2.integrate.model.IncomeDetailVO;
import cn.gxf.spring.struts2.integrate.model.PaymentDetailVO;
import cn.gxf.spring.struts2.integrate.model.TransferDetailVO;
import cn.gxf.spring.struts2.integrate.service.CustomTailorQueryService;
import cn.gxf.spring.struts2.integrate.service.DmService;

public class CustomTailoredQueryAction extends ActionSupport implements Preparable, RequestAware{

	private static final long serialVersionUID = 9031854435604124402L;
	private List<String> paydl_dm;
	private List<String> payxl_dm; // �ɶ�ѡ
	private List<String> srlb_dm;
	private String je_gt;  // ����X��ʹ��String��Ŀ����ʹ��ʼ��ʱinput�ϲ���ʾ0.0
	private String je_lt;
	private Date date_from;
	private Date date_to;
	private List<String> zh_dm;
	private List<String> srcZh_dm;
	private List<String> tgtZh_dm;
	private List<String> zzlx_dm;
	private String seller;
	private String fkfmc;
	
	// ��ҳ��ҳ�룬��1��ʼ
	private Integer pageNumPayment;
	private Integer pageNumIncome;
	private Integer pageNumTransfer;
	
	private Integer totalPagesPayment;
	private Integer totalPagesIncome;
	private Integer totalPagesTransfer;
	
	private Integer pageSize = new Integer(10);
	
	
	/*private UserLogin user;*/
	Map<String, Object> myrequest;
	
	@Autowired
	private DmService dmService;
	
	/*@Autowired
	private CustomTailorQueryDao customTailorQueryDao;*/
	
	@Autowired
	private CustomTailorQueryService customTailorQueryService;
	
	/*@Autowired
	private StatNdYfMBDao statNdYfMBDao;*/
	
	@Override
	public void prepare() throws Exception{
		
	}
	
	public void prepareInputQuery() throws Exception {
		
		pageElement2Request();
		
		// Ĭ��ʱ�䷶ΧΪ�³�����ĩ
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String firstday, lastday;
		Calendar cale = null;
		// ��ȡǰ�µĵ�һ��
        cale = Calendar.getInstance();
        cale.add(Calendar.MONTH, 0);
        cale.set(Calendar.DAY_OF_MONTH, 1);
        firstday = df.format(cale.getTime());
        
        // ��ȡǰ�µ����һ��
        cale.add(Calendar.MONTH, 1);
        cale.set(Calendar.DAY_OF_MONTH, 0);
        lastday = df.format(cale.getTime());
        System.out.println("input: " + this.myrequest.get("date_from")); 
        if (!this.myrequest.containsKey("date_from") && !this.myrequest.containsKey("date_to") ){
        	// request�д���date_from����date_to�������ڣ���ʹ��Ĭ��ֵ
        	this.myrequest.put("date_from", firstday);
        	this.myrequest.put("date_to", lastday);
        }
	}
	
	private void prepareDefaultInputDate(){
		// Ĭ��ʱ�䷶ΧΪ�³�����ĩ
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String firstday, lastday;
		Calendar cale = null;
		// ��ȡǰ�µĵ�һ��
        cale = Calendar.getInstance();
        cale.add(Calendar.MONTH, 0);
        cale.set(Calendar.DAY_OF_MONTH, 1);
        firstday = df.format(cale.getTime());
        
        // ��ȡǰ�µ����һ��
        cale.add(Calendar.MONTH, 1);
        cale.set(Calendar.DAY_OF_MONTH, 0);
        lastday = df.format(cale.getTime());
        System.out.println("input: " + this.myrequest.get("date_from")); 
        if (!this.myrequest.containsKey("date_from") && !this.myrequest.containsKey("date_to") ){
        	// request�д���date_from����date_to�������ڣ���ʹ��Ĭ��ֵ
        	this.myrequest.put("date_from", firstday);
        	this.myrequest.put("date_to", lastday);
        }
	}
	
	public String inputQuery(){
		
		// ֧������-С��
		Map<DmPaymentDl, List<DmPaymentXl>> map_payment = dmService.getPaymentDlXlDzb();
		System.out.println("payment: " +map_payment.hashCode());
		this.myrequest.put("PAY_DLXL_DZB", map_payment);
		
		
		return "inputOk";
	}
	
	public String inputPaymentQuery(){
		prepareDefaultInputDate();
		// ֧������С��
		Map<String, String> dlmap = dmService.getPaymentDl();
		this.myrequest.put("dl_dm", dlmap);
				
		Map<String, String> xlmap = dmService.getPaymentXl();
		this.myrequest.put("xl_dm", xlmap);
				
		// �˻�
		UserLogin user = (UserLogin) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		this.myrequest.put("zh_info", dmService.getZhInfoSimple(user.getId()));
		this.myrequest.put("ZH_INFO_MAP", dmService.getZhInfoMap(user.getId()));
		
		return "inputPaymentOk";
	}
	
	public String inputIncomeQuery(){
		prepareDefaultInputDate();
		// �������
		Map<String, String> srlbmap = dmService.getIncomeLb();
		System.out.println("income: " + srlbmap.hashCode());
		this.myrequest.put("srlb_dm", srlbmap);
				
		// �˻�
		UserLogin user = (UserLogin) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		this.myrequest.put("zh_info", dmService.getZhInfoSimple(user.getId()));
		this.myrequest.put("ZH_INFO_MAP", dmService.getZhInfoMap(user.getId()));
		
		return "inputIncomeOk";
	}
	
	public String inputTransferQuery(){	
		prepareDefaultInputDate();
		// �˻�
		UserLogin user = (UserLogin) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		int user_id = user.getId();
		this.myrequest.put("zh_info", dmService.getZhInfoSimple(user_id));
		this.myrequest.put("ZH_INFO_MAP", dmService.getZhInfoMap(user_id));
		Map<String, String> zzlxDm = new HashMap<String, String>();
		zzlxDm.putAll(dmService.getTransferType(user_id));
		zzlxDm.putAll(dmService.getTransferTypeCommon());
		this.myrequest.put("dm_zzlx", zzlxDm);
				
		return "inputTransferOk";
	}
	
	private int checkDmList(List<String> dmlist){
		if (dmlist == null || dmlist.size() == 0){
			return 0;
		}
		else if (dmlist.size() == 1 && (dmlist.get(0).equals("") || dmlist.get(0).equals("-1")) ){
			return 0;
		}
		
		return 1;
	}
	
	
	public String paymentQuery(){
		
		// ���ò���
		Map<String, Object> params = new HashMap<>();
		if ( 1 == this.checkDmList(this.paydl_dm)){
			params.put("paydl_dm", this.paydl_dm);
		}
		if ( 1 == this.checkDmList(this.payxl_dm)){
			params.put("payxl_dm", this.payxl_dm);
		}
		
		params.put("date_from", this.date_from);
		params.put("date_to", this.date_to);
		if (this.seller != null && !this.seller.equals(""))
		{
			params.put("seller", this.seller);
		}
		if (1 == this.checkDmList(this.zh_dm)){
			params.put("zh_dm", this.zh_dm);
		}
		
		if (this.je_gt != null && !this.je_gt.equals("") ){
			params.put("je_gt", Float.parseFloat(this.je_gt));
		}
		if (this.je_lt != null && !this.je_lt.equals("")){
			params.put("je_lt", Float.parseFloat(this.je_lt));
		}
		
		UserLogin user = (UserLogin) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		params.put("user_id", user.getId());
		
		System.out.println("pageNum: " + pageNumPayment);
		if (this.pageNumPayment == null){
			this.pageNumPayment = new Integer(1);
		}
		this.pageNumPayment = this.pageNumPayment.intValue() < 1 ? 1 : this.pageNumPayment; 
		// ��ѯ
		List<PaymentDetailVO> paymentlist = customTailorQueryService.queryPayment(params,
																				this.pageNumPayment.intValue(),
																				this.pageSize.intValue());
		
		this.totalPagesPayment = ((Page<PaymentDetailVO>)paymentlist).getPages();
		
		System.out.println("list: "+ paymentlist);
		this.myrequest.put("paymentResult", paymentlist);
		
		// ���ڻ��ԣ���ѯ��ҳ����ת����ҳ�棬���ֲ�ѯ�������䣩
		// struts2��result type��Ĭ�ϣ�dispatcher�����ܽ�request�����ݴ����¸�ҳ��
		// ���result type��redirectAction��request���ݻᶪʧ
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		if(this.date_from != null){
			this.myrequest.put("date_from", df.format(this.date_from));
		}
		if (this.date_to != null){
			this.myrequest.put("date_to", df.format(this.date_to));
		}
		myrequest.put("queryType", "payment");
		// ��result type��Ĭ�ϣ�dispatcher����ֱ�ӵ�����jspҳ�棬ҳ����selectԪ����Ҫ��ʼ������
		pageElement2Request();
		
		// test
		/*statNdYfMBDao.procAccStatByNd("2017");
		List<StatByMonth> list = statNdYfMBDao.getNdYfStat("2017");
		System.out.println(list);*/
		
		return "inputPaymentOk";
	}
	
	private float calIncomeSum(List<IncomeDetailVO> incomeDetails){
		float sum = 0.f;
		for (IncomeDetailVO income : incomeDetails){
			sum += income.getJe();
		}
		return sum;
	}
	
	public String incomeQuery(){
		
		Map<String, Object> params = new HashMap<>();
		if (1==this.checkDmList(this.srlb_dm)){
			params.put("srlb_dm", this.srlb_dm);
		}
		params.put("date_from", this.date_from);
		params.put("date_to", this.date_to);
		if (this.fkfmc != null && !this.fkfmc.equals(""))
		{
			params.put("fkfmc", this.fkfmc);
		}
		if (1==this.checkDmList(zh_dm)){
			params.put("zh_dm", this.zh_dm);
		}
		
		if (this.je_gt != null && !this.je_gt.equals("") ){
			params.put("je_gt", Float.parseFloat(this.je_gt));
		}
		if (this.je_lt != null && !this.je_lt.equals("")){
			params.put("je_lt", Float.parseFloat(this.je_lt));
		}
		if (1==this.checkDmList(this.zh_dm)){
			params.put("zh_dm", this.zh_dm);
		}
		
		UserLogin user = (UserLogin) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		params.put("user_id", user.getId());
		
		System.out.println("pageNum: " + pageNumIncome);
		if (this.pageNumIncome == null){
			this.pageNumIncome = new Integer(1);
		}
		
		// ��ѯ
		List<IncomeDetailVO> incomelist = customTailorQueryService.queryIncome(params,
																			this.pageNumIncome.intValue(),
																			this.pageSize.intValue());
		
		this.totalPagesIncome = ((Page<IncomeDetailVO>)incomelist).getPages();		
		//System.out.println("list: "+ incomelist);
		this.myrequest.put("incomeResult", incomelist);
		this.myrequest.put("incomeSum", this.calIncomeSum(incomelist));
		// ���ڻ��ԣ���ѯ��ҳ����ת����ҳ�棬���ֲ�ѯ�������䣩
		// struts2��result type��Ĭ�ϣ�dispatcher�����ܽ�request�����ݴ����¸�ҳ��
		// ���result type��redirectAction��request���ݻᶪʧ
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		if(this.date_from != null){
			this.myrequest.put("date_from", df.format(this.date_from));
		}
		if (this.date_to != null){
			this.myrequest.put("date_to", df.format(this.date_to));
		}
		myrequest.put("queryType", "income");
		/*if (this.srlb_dm != null && this.srlb_dm.size()>0){
			myrequest.put("selected_srlb", StringUtils.join(this.srlb_dm.toArray(), ","));
		}*/
		// ��result type��Ĭ�ϣ�dispatcher����ֱ�ӵ�����jspҳ�棬ҳ����selectԪ����Ҫ��ʼ������
		pageElement2Request();
		
		return "inputIncomeOk";
	}
	
	
	public String transferQuery(){
		
		Map<String, Object> params = new HashMap<>();
		if (1==this.checkDmList(this.srcZh_dm)){
			params.put("srcZh_dm", this.srcZh_dm);
		}
		if (1==this.checkDmList(this.tgtZh_dm)){
			params.put("tgtZh_dm", this.tgtZh_dm);
		}
		if (1==this.checkDmList(this.zzlx_dm)){
			params.put("zzlx_dm", this.zzlx_dm);
		}
		
		params.put("date_from", this.date_from);
		params.put("date_to", this.date_to);
		
		if (this.je_gt != null && !this.je_gt.equals("") ){
			params.put("je_gt", Float.parseFloat(this.je_gt));
		}
		if (this.je_lt != null && !this.je_lt.equals("")){
			params.put("je_lt", Float.parseFloat(this.je_lt));
		}
		
		UserLogin user = (UserLogin) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		params.put("user_id", user.getId());
		
		System.out.println("pageNum: " + pageNumTransfer);
		if (this.pageNumTransfer == null){
			this.pageNumTransfer = new Integer(1);
		}
		
		// ��ѯ
		List<TransferDetailVO> transferlist = customTailorQueryService.queryTransfer(params,
																					this.pageNumTransfer.intValue(),
																					this.pageSize.intValue());
		
		this.totalPagesTransfer = ((Page<TransferDetailVO>)transferlist).getPages();
		System.out.println("list: "+ transferlist);
		this.myrequest.put("transferResult", transferlist);
		
		// ���ڻ��ԣ���ѯ��ҳ����ת����ҳ�棬���ֲ�ѯ�������䣩
		// struts2��result type��Ĭ�ϣ�dispatcher�����ܽ�request�����ݴ����¸�ҳ��
		// ���result type��redirectAction��request���ݻᶪʧ
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		if(this.date_from != null){
			this.myrequest.put("date_from", df.format(this.date_from));
		}
		if (this.date_to != null){
			this.myrequest.put("date_to", df.format(this.date_to));
		}
		this.myrequest.put("queryType", "transfer");
		
		// ��result type��Ĭ�ϣ�dispatcher����ֱ�ӵ�����jspҳ�棬ҳ����selectԪ����Ҫ��ʼ������
		this.myrequest.put("dm_zzlx", dmService.getTransferType(user.getId())); // transferҳ������Ԫ��
		pageElement2Request();
		
		return "inputTransferOk";
	}
	
	// ��result type��Ĭ�ϣ�dispatcher����ֱ�ӵ�����jspҳ�棬ҳ����selectԪ����Ҫ��ʼ������
	private void pageElement2Request(){
		// �������
		Map<String, String> srlbmap = dmService.getIncomeLb();
		System.out.println("income: " + srlbmap.hashCode());
		this.myrequest.put("srlb_dm", srlbmap);
				
		// ֧������С��
		Map<String, String> dlmap = dmService.getPaymentDl();
		this.myrequest.put("dl_dm", dlmap);
				
		Map<String, String> xlmap = dmService.getPaymentXl();
		this.myrequest.put("xl_dm", xlmap);
				
		// �˻�
		UserLogin user = (UserLogin) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		this.myrequest.put("zh_info", dmService.getZhInfoSimple(user.getId()));
		this.myrequest.put("ZH_INFO_MAP", dmService.getZhInfoMap(user.getId()));
	}

	public List<String> getPaydl_dm() {
		return paydl_dm;
	}

	public void setPaydl_dm(List<String> paydl_dm) {
		this.paydl_dm = paydl_dm;
	}

	public List<String> getPayxl_dm() {
		return payxl_dm;
	}

	public void setPayxl_dm(List<String> payxl_dm) {
		this.payxl_dm = payxl_dm;
	}

	public List<String> getSrlb_dm() {
		return srlb_dm;
	}
	
	public void setSrlb_dm(List<String> srlb_dm) {
		this.srlb_dm = srlb_dm;
	}

	public String getJe_gt() {
		return je_gt;
	}

	public void setJe_gt(String je_gt) {
		this.je_gt = je_gt;
	}

	public String getJe_lt() {
		return je_lt;
	}

	public void setJe_lt(String je_lt) {
		this.je_lt = je_lt;
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

	public List<String> getZh_dm() {
		return zh_dm;
	}

	public void setZh_dm(List<String> zh_dm) {
		this.zh_dm = zh_dm;
	}

	public List<String> getSrcZh_dm() {
		return srcZh_dm;
	}

	public void setSrcZh_dm(List<String> srcZh_dm) {
		this.srcZh_dm = srcZh_dm;
	}

	public List<String> getTgtZh_dm() {
		return tgtZh_dm;
	}

	public void setTgtZh_dm(List<String> tgtZh_dm) {
		this.tgtZh_dm = tgtZh_dm;
	}
	
	public List<String> getZzlx_dm() {
		return zzlx_dm;
	}
	
	public void setZzlx_dm(List<String> zzlx_dm) {
		this.zzlx_dm = zzlx_dm;
	}

	public String getSeller() {
		return seller;
	}

	public void setSeller(String seller) {
		this.seller = seller;
	}

	public String getFkfmc() {
		return fkfmc;
	}

	public void setFkfmc(String fkfmc) {
		this.fkfmc = fkfmc;
	}
	
	public Integer getPageNumPayment() {
		return pageNumPayment;
	}

	public void setPageNumPayment(Integer pageNumPayment) {
		this.pageNumPayment = pageNumPayment;
	}

	public Integer getPageNumIncome() {
		return pageNumIncome;
	}

	public void setPageNumIncome(Integer pageNumIncome) {
		this.pageNumIncome = pageNumIncome;
	}

	public Integer getPageNumTransfer() {
		return pageNumTransfer;
	}

	public void setPageNumTransfer(Integer pageNumTransfer) {
		this.pageNumTransfer = pageNumTransfer;
	}

	public Integer getPageSize() {
		return pageSize;
	}
	
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	
	public Integer getTotalPagesPayment() {
		return totalPagesPayment;
	}
	
	public Integer getTotalPagesIncome() {
		return totalPagesIncome;
	}
	
	public Integer getTotalPagesTransfer() {
		return totalPagesTransfer;
	}


	@Override
	public void setRequest(Map<String, Object> request) {
		this.myrequest = request;
	}

	public Map<String, Object> getMyrequest() {
		return myrequest;
	}
	
}
