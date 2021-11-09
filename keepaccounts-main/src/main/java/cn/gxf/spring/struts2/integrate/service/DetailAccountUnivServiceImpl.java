package cn.gxf.spring.struts2.integrate.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;

import cn.gxf.spring.struts.integrate.cache.StatDetailKeyGenerator;
import cn.gxf.spring.struts.mybatis.dao.AccountDetailMBDao;
import cn.gxf.spring.struts.mybatis.dao.FinancialProductDetailMBDao;
import cn.gxf.spring.struts.mybatis.dao.FinancialProductIncomeDetailMBDao;
import cn.gxf.spring.struts.mybatis.dao.FundDetailMBDao;
import cn.gxf.spring.struts.mybatis.dao.IncomeDetailMBDao;
import cn.gxf.spring.struts.mybatis.dao.PaymentDetailMBDao;
import cn.gxf.spring.struts.mybatis.dao.TransferDetailMBDao;
import cn.gxf.spring.struts2.integrate.dao.AccountBookDao;
import cn.gxf.spring.struts2.integrate.model.AccountObject;
import cn.gxf.spring.struts2.integrate.model.AccountingDetail;
import cn.gxf.spring.struts2.integrate.model.FinancialProductDetail;
import cn.gxf.spring.struts2.integrate.model.FinancialProductIncomeDetail;
import cn.gxf.spring.struts2.integrate.model.FundDetail;
import cn.gxf.spring.struts2.integrate.model.IncomeDetail;
import cn.gxf.spring.struts2.integrate.model.PaymentDetail;
import cn.gxf.spring.struts2.integrate.model.TransferDetail;

@Service
public class DetailAccountUnivServiceImpl<T extends AccountObject>{
	
    private Logger logger = LogManager.getLogger();

	@Autowired
	private AccountDetailMBDao accountDetailMBDao;
	
//	@Autowired
//	@Lazy
//	private BaseAccountRepository<T> repository;
	
	@Autowired
	private IncomeDetailMBDao incomeDetailMBDao;
	
	@Autowired
	private PaymentDetailMBDao paymentDetailMBDao;
	
	@Autowired
	private TransferDetailMBDao transferDetailMBDao;
	
	@Autowired
	private FundDetailMBDao fundDetailMBdao;
	
	@Autowired
	private FinancialProductDetailMBDao financialProductDetailMBDao;
	
	@Autowired
	private FinancialProductIncomeDetailMBDao financialProductIncomeDetailMBDao; 
	
	@Autowired
	private AccountBookDao accountBookDao;
	
	@Autowired
	private AccountStatService accountStatService;
	
	@Autowired
	private AccountSnapshotting accountSnapshotting;

	@Autowired
	private StatDetailKeyGenerator statDetailKeyGenerator;
	
	@Autowired
	private DmService dmService;
	
	@Autowired
	private KeepAccountingDatesService keepAccountingDatesService;
	
	// 通过accuuid获得具体的明细对象
	public AccountObject getOneItem(String accuuid){
		AccountingDetail acc = accountDetailMBDao.getAccountingDetailByUuid(accuuid);
		
		if (null == acc){
			return null;
		}
		
		List<String> accuuidList = new ArrayList<>();
		accuuidList.add(accuuid);
		
		switch (acc.getRec_dm()) {
		case 1:
			return incomeDetailMBDao.getDetailByPatchAccuuid(accuuidList).get(0);
		case 2:
			return paymentDetailMBDao.getDetailByPatchAccuuid(accuuidList).get(0);
		case 3:
			return transferDetailMBDao.getDetailByPatchAccuuid(accuuidList).get(0);
		default:
			return null;
		}
		
	}
	
//	public IncomeDetail getIncomeDetailByMxuuid(String mxuuid){
//		IncomeDetail incomeDetail = this.incomeDetailMBDao.getIncomeDetailByUuid(mxuuid);
//		// 查看是否和理财产品关联
//		FinancialProductDetail financialProductDetail = this.financialProductDetailMBDao.getFinancialProductDetailByReturnUuid(mxuuid);
//		if (financialProductDetail != null){
//			incomeDetail.setFinprodUuid(financialProductDetail.getUuid());
//		}
//		return incomeDetail;
//	}
	
	public IncomeDetail getIncomeDetailByMxuuid(String mxuuid){
		IncomeDetail incomeDetail = this.incomeDetailMBDao.getIncomeDetailByUuid(mxuuid);
		// 查看是否和理财产品关联
		if (incomeDetail.getLb_dm().equals(DmService.srlb_fin_prod_dm))
		{
			FinancialProductIncomeDetail fpincomedetail = this.financialProductIncomeDetailMBDao.getFinancialProductIncomeByIncomeUuid(incomeDetail.getMxuuid());
			if (fpincomedetail != null)
			{
				incomeDetail.setFinprodUuid(fpincomedetail.getFpuuid());
				incomeDetail.setIs_redeem(fpincomedetail.getIs_redeem());
			}
			else{
				incomeDetail.setFinprodUuid(null);
				incomeDetail.setIs_redeem("N");
			}
		}
		
		return incomeDetail;
	}
	
	public PaymentDetail getPaymentDetailByMxuuid(String mxuuid){
		return this.paymentDetailMBDao.getPaymentDetailByUuid(mxuuid);
	}
	
	public TransferDetail getTransferDetailByMxuuid(String mxuuid){
		TransferDetail transferDetail = this.transferDetailMBDao.getTransferDetailByUuid(mxuuid);
		FundDetail fundDetail = this.fundDetailMBdao.getFundDetailByUuid(mxuuid);// 如果转账类型不是“购买基金”,返回为null
		transferDetail.setFundDetail(fundDetail);
		
		FinancialProductDetail financialProductDetail = null;
		if(transferDetail.getZzlx_dm().equals(DmService.zzlx_purchase_fin_prod_dm)){
			// 当前转账类型为购买理财产品，那么找到对应的理财产品信息
			financialProductDetail = this.financialProductDetailMBDao.getFinancialProductDetailByTransferUuid(mxuuid); // 根据购买理财产品的转账uuid查找
		}else if(transferDetail.getZzlx_dm().equals(DmService.zzlx_redeem_fin_prod_dm)){
			// 当前转账类型为赎回理财产品，根据赎回转账交易的uuid字段对应到理财产品
			financialProductDetail = this.financialProductDetailMBDao.getFinancialProductDetailByRedeemUuid(mxuuid); // 根据购买理财产品的转账uuid查找;
		}
		transferDetail.setFinancialProductDetail(financialProductDetail);
		return transferDetail;
	}
	
	public List<IncomeDetail> getIncomeDetailByPatchMxuuid(List<String> mxuuidList){
		return this.incomeDetailMBDao.getDetailByPatchUuid(mxuuidList);
	}
	
	public List<PaymentDetail> getPaymentDetailByPatchMxuuid(List<String> mxuuidList){
		return this.paymentDetailMBDao.getDetailByPatchUuid(mxuuidList);
	}
	
	public List<TransferDetail> getTransferDetailByPatchMxuuid(List<String> mxuuidList){
		return this.transferDetailMBDao.getDetailByPatchUuid(mxuuidList);
	}
	
	public List<AccountingDetail> getAccountDetailByPatchAccuuid(List<String> accuuidList){
		return this.accountDetailMBDao.getAccountingDetailByPatchUuid(accuuidList);
	}
	
	public String detailAccountBlockHandler(T detail, BlockException ex){
		System.out.println("error in saveOneBlockHandler by sentinel.");
		logger.error(ex.getMessage());
		return "BLOCKED_BY_SENTINEL";
	}
	
	
	/*
	 * 
	 * 注意 blockHandler 函数会在原方法被限流/降级/系统保护的时候调用，而 fallback 函数会针对所有类型的异常
	 * blockHandler 对应处理 BlockException 的函数名称，可选项。
	 * blockHandler 函数访问范围需要是 public，返回类型需要与原方法相匹配，参数类型需要和原方法相匹配并且最后加一个额外的参数，类型为 BlockException
	 * 
	 * 特别地，若 blockHandler 和 fallback 都进行了配置，则被限流降级而抛出 BlockException 时只会进入 blockHandler 处理逻辑。若未配置 blockHandler、fallback 和 defaultFallback，则被限流降级时会将 BlockException 直接抛出。
	 */
	@SentinelResource(value="DetailAccountUnivServiceImpl", blockHandler="detailAccountBlockHandler")
	@Transactional(value="dsTransactionManager", isolation=Isolation.READ_COMMITTED)
	public String saveOne(T detail) {
		
		long start = System.currentTimeMillis();
		AccountingDetail accountingDetail = new AccountingDetail();
		//String accuuid = accUtil.generateUuid();
		//accountingDetail.setAccuuid(accuuid);
		accountingDetail.setJe(detail.getJe());
		accountingDetail.setRec_dm(detail.getType());
		accountingDetail.setShijian(detail.getShijian());
		accountingDetail.setUser_id(detail.getUser_id());
		//accountingDetail.setXgrq(detail.getXgrq());
		accountingDetail.setYxbz("Y");
		
		// 使用mybatis 主键回填功能
		accountDetailMBDao.addOne(accountingDetail);
		try {
			//int i  = 1/0;
		} catch (Exception e) {
			// TODO: handle exception
			throw new RuntimeException();
		}
		detail.setAccuuid(accountingDetail.getAccuuid());
		//detail.setMxuuid(accUtil.generateUuid());
		//repository.addOne(detail);
		
		Set<String> keys = new HashSet<String>();
		
		if ( detail instanceof IncomeDetail ){
			IncomeDetail incomeDetail = (IncomeDetail) detail;
			incomeDetailMBDao.addOne(incomeDetail);
			// 账户SNAPSHOT
			this.accountSnapshotting.shotting(incomeDetail.getZh_dm(), AccountSnapshotting.ADD, accountingDetail);
			accountBookDao.updateYe(incomeDetail.getZh_dm(), incomeDetail.getJe());
			if (incomeDetail.getLb_dm().equals(DmService.srlb_fin_prod_dm)){ // 如果类型是“理财收入”，则修改理财信息
				// mxuuid通过mybatis的主键回写获得
				if (incomeDetail.getFinprodUuid()!=null && !incomeDetail.getFinprodUuid().equals("-1"))
				{
					// 必须选择了对应的理财产品，如果不选finproduuid为'-1'
					if (incomeDetail.getIs_redeem()!=null && incomeDetail.getIs_redeem().equals("Y"))
					{
						this.financialProductDetailMBDao.setRealReturn(incomeDetail.getFinprodUuid(), incomeDetail.getMxuuid(), incomeDetail.getJe());
					}
					// 写入理财收入明细信息
					FinancialProductIncomeDetail finprodincome = new FinancialProductIncomeDetail();
					finprodincome.init(null/*uuid*/, incomeDetail.getFinprodUuid(), incomeDetail.getMxuuid(), incomeDetail.getJe(), incomeDetail.getIs_redeem(), new Date()/*lrrq*/, null/*xgrq*/, "Y");
					this.financialProductIncomeDetailMBDao.addOne(finprodincome);
				}
			}
			
			// 清除“收入明细”的缓存(redis)
			keys.add(this.statDetailKeyGenerator.detailIncomeKey(incomeDetail.getUser_id(), incomeDetail.getShijian()));
		}else if (detail instanceof PaymentDetail){
			PaymentDetail paymentDetail = (PaymentDetail)detail;
			paymentDetailMBDao.addOne(paymentDetail);
			// 账户SNAPSHOT
			this.accountSnapshotting.shotting(paymentDetail.getZh_dm(), accountingDetail.getAccuuid(), AccountSnapshotting.ADD, paymentDetail.getUser_id(), -1.0f*accountingDetail.getJe());
			accountBookDao.updateYe(paymentDetail.getZh_dm(), -1.0f*paymentDetail.getJe());
			
			// 清除“支出明细”的缓存(redis)
			keys.add(this.statDetailKeyGenerator.detailPaymentKey(paymentDetail.getUser_id(), paymentDetail.getShijian()));
		}else if (detail instanceof TransferDetail){
			TransferDetail transferDetail = (TransferDetail)detail; 
			transferDetailMBDao.addOne(transferDetail);
			// 账户SNAPSHOT
			float bdje = accountingDetail.getJe(); // 变动金额
			this.accountSnapshotting.shotting(transferDetail.getSrcZh_dm(), accountingDetail.getAccuuid(), AccountSnapshotting.ADD, transferDetail.getUser_id(), -1.0f*bdje); // 转出账户为负
			this.accountSnapshotting.shotting(transferDetail.getTgtZh_dm(), accountingDetail.getAccuuid(), AccountSnapshotting.ADD, transferDetail.getUser_id(),       bdje);
						
			accountBookDao.updateYe(transferDetail.getSrcZh_dm(), -1.0f*transferDetail.getJe());
			accountBookDao.updateYe(transferDetail.getTgtZh_dm(), transferDetail.getJe());
			
			String transferUuid = transferDetail.getMxuuid(); // 通过上面主键回写获得
			if (transferDetail.getZzlx_dm().equals(DmService.zzlx_purchase_fund_dm) && transferDetail.getFundDetail() != null){
				transferDetail.getFundDetail().setTransferUuid(transferUuid);
				transferDetail.getFundDetail().setLrrq(new Date());
				fundDetailMBdao.addOne(transferDetail.getFundDetail());
			}
			
			if (transferDetail.getZzlx_dm().equals(DmService.zzlx_purchase_fin_prod_dm) && transferDetail.getFinancialProductDetail() != null){
				transferDetail.getFinancialProductDetail().setTransferUuid(transferUuid);
				transferDetail.getFinancialProductDetail().setLrrq(new Date());
				financialProductDetailMBDao.addOne(transferDetail.getFinancialProductDetail());
			}
			
			if (transferDetail.getZzlx_dm().equals(DmService.zzlx_redeem_fin_prod_dm) && transferDetail.getFinancialProductDetail() != null){
				// 转账类型为“理财赎回”
				FinancialProductDetail financialProductDetail = transferDetail.getFinancialProductDetail();
				financialProductDetail.setRedeemUuid(transferUuid); // 设置理财产品关联的赎回转账交易uuid
				// 设置对应理财产品赎回标志为Y, 并设置关联的赎回转账交易
				financialProductDetailMBDao.setRedeem(financialProductDetail.getUuid(), financialProductDetail.getRedeemUuid());
			}
			
			// 清除“转账明细”的缓存(redis)
			keys.add(this.statDetailKeyGenerator.detailTransferKey(transferDetail.getUser_id(), transferDetail.getShijian()));
		}
		
	    // 清除缓存
		this.accountStatService.EvictDateStatRedis(keys);
		this.dmService.removeZhInfoCache(detail.getUser_id());
		// 更新记账天数数据（可能不更新）
		this.keepAccountingDatesService.updatekeepAccountingDates(detail.getUser_id(), new Date());
		System.out.println("save consumption: " +(System.currentTimeMillis()-start));
		return accountingDetail.getAccuuid();
	}

	@SentinelResource(value="DetailAccountUnivServiceImpl", blockHandler="detailAccountBlockHandler")
	//@CacheEvict(value="redisCacheStat", allEntries=true)
	@Transactional(value="dsTransactionManager", isolation=Isolation.READ_COMMITTED)
	public AccountingDetail updateOne(T detail) {
		
		AccountingDetail accountingDetail = accountDetailMBDao.getAccountingDetailByUuid(detail.getAccuuid());
		Set<String> keys = new HashSet<String>();
		
		accountingDetail.setJe(detail.getJe());
		accountingDetail.setShijian(detail.getShijian());
		accountingDetail.setXgrq(detail.getXgrq());
		accountDetailMBDao.updateOne(accountingDetail);
		
		if ( detail instanceof IncomeDetail ){
			IncomeDetail incomeDetail_new = (IncomeDetail) detail;
			IncomeDetail incomeDetail_old = incomeDetailMBDao.getIncomeDetailByUuid(incomeDetail_new.getMxuuid());
						
			if (incomeDetail_new.getZh_dm().equals(incomeDetail_old.getZh_dm()) ){
				float ye_delt = -1.0f*incomeDetail_old.getJe() + incomeDetail_new.getJe();
				accountBookDao.updateYe(incomeDetail_new.getZh_dm(), ye_delt);
				
				// 账户SNAPSHOT
				accountSnapshotting.shottingAfterBookUpdated(incomeDetail_old.getZh_dm(), accountingDetail.getAccuuid(), AccountSnapshotting.UPDATE, accountingDetail.getUser_id(), ye_delt);
				
			}else{
				accountBookDao.updateYe(incomeDetail_new.getZh_dm(), incomeDetail_new.getJe());
				accountBookDao.updateYe(incomeDetail_old.getZh_dm(), -1.0f*incomeDetail_old.getJe());
				
				// 账户SNAPSHOT
				accountSnapshotting.shottingAfterBookUpdated(incomeDetail_old.getZh_dm(), accountingDetail.getAccuuid(), AccountSnapshotting.UPDATE, accountingDetail.getUser_id(), -1.0f*incomeDetail_old.getJe());
				accountSnapshotting.shottingAfterBookUpdated(incomeDetail_new.getZh_dm(), accountingDetail.getAccuuid(), AccountSnapshotting.UPDATE, accountingDetail.getUser_id(),       incomeDetail_new.getJe());
			}
			
			incomeDetailMBDao.updateOne((IncomeDetail)detail);
			
			keys.add(this.statDetailKeyGenerator.detailIncomeKey(incomeDetail_new.getUser_id(), incomeDetail_new.getShijian()));
			keys.add(this.statDetailKeyGenerator.detailIncomeKey(incomeDetail_old.getUser_id(), incomeDetail_old.getShijian()));
		}else if (detail instanceof PaymentDetail){
			PaymentDetail paymentDetail_new = (PaymentDetail) detail;
			PaymentDetail paymentDetail_old = paymentDetailMBDao.getPaymentDetailByUuid(paymentDetail_new.getMxuuid());
						
			if(paymentDetail_new.getZh_dm().equals(paymentDetail_old.getZh_dm())){
				float ye_delt = paymentDetail_old.getJe() - 1.0f*paymentDetail_new.getJe();
				accountBookDao.updateYe(paymentDetail_new.getZh_dm(), ye_delt);
				
				// 账户SNAPSHOT
				accountSnapshotting.shottingAfterBookUpdated(paymentDetail_old.getZh_dm(), accountingDetail.getAccuuid(), AccountSnapshotting.UPDATE, accountingDetail.getUser_id(), ye_delt);
			}else{
				accountBookDao.updateYe(paymentDetail_new.getZh_dm(), -1.0f*paymentDetail_new.getJe());
				accountBookDao.updateYe(paymentDetail_old.getZh_dm(), paymentDetail_old.getJe());
				
				// 账户SNAPSHOT
				accountSnapshotting.shottingAfterBookUpdated(paymentDetail_old.getZh_dm(), accountingDetail.getAccuuid(), AccountSnapshotting.UPDATE, accountingDetail.getUser_id(),        paymentDetail_old.getJe());
				accountSnapshotting.shottingAfterBookUpdated(paymentDetail_new.getZh_dm(), accountingDetail.getAccuuid(), AccountSnapshotting.UPDATE, accountingDetail.getUser_id(),  -1.0f*paymentDetail_new.getJe());
			}
						
			paymentDetailMBDao.updateOne((PaymentDetail)detail);
			
			keys.add(this.statDetailKeyGenerator.detailPaymentKey(paymentDetail_new.getUser_id(), paymentDetail_new.getShijian()));
			keys.add(this.statDetailKeyGenerator.detailPaymentKey(paymentDetail_old.getUser_id(), paymentDetail_old.getShijian()));
		}else if (detail instanceof TransferDetail){
			TransferDetail transferDetail_new = (TransferDetail)detail;
			TransferDetail transferDetail_old = this.getTransferDetailByMxuuid(transferDetail_new.getMxuuid()); //transferDetailMBDao.getTransferDetailByUuid(transferDetail_new.getMxuuid());
			FundDetail fundDetail_old = fundDetailMBdao.getFundDetailByUuid(transferDetail_new.getMxuuid());
						
			// 考虑到修改前后的转出、转入账户都有不同的可能，先做撤销，再重新发起
			// 撤销上次交易
			accountBookDao.updateYe(transferDetail_old.getSrcZh_dm(), transferDetail_old.getJe());
			accountBookDao.updateYe(transferDetail_old.getTgtZh_dm(), -1.0f*transferDetail_old.getJe());
			
			// 账户SNAPSHOT
			this.accountSnapshotting.shottingAfterBookUpdated(transferDetail_old.getSrcZh_dm(), accountingDetail.getAccuuid(), AccountSnapshotting.UPDATE, accountingDetail.getUser_id(),        transferDetail_old.getJe());
			this.accountSnapshotting.shottingAfterBookUpdated(transferDetail_old.getTgtZh_dm(), accountingDetail.getAccuuid(), AccountSnapshotting.UPDATE, accountingDetail.getUser_id(),  -1.0f*transferDetail_old.getJe());
						
			// 进行本次交易
			accountBookDao.updateYe(transferDetail_new.getSrcZh_dm(), -1.0f*transferDetail_new.getJe());
			accountBookDao.updateYe(transferDetail_new.getTgtZh_dm(), transferDetail_new.getJe());
			
			// 账户SNAPSHOT
			this.accountSnapshotting.shottingAfterBookUpdated(transferDetail_new.getSrcZh_dm(), accountingDetail.getAccuuid(), AccountSnapshotting.UPDATE, accountingDetail.getUser_id(),  -1.0f*transferDetail_new.getJe());
			this.accountSnapshotting.shottingAfterBookUpdated(transferDetail_new.getTgtZh_dm(), accountingDetail.getAccuuid(), AccountSnapshotting.UPDATE, accountingDetail.getUser_id(),        transferDetail_new.getJe());
			
			transferDetailMBDao.updateOne(transferDetail_new);
			
			
			if (transferDetail_new.getZzlx_dm().equals(DmService.zzlx_purchase_fund_dm) && !transferDetail_old.getZzlx_dm().equals(DmService.zzlx_purchase_fund_dm)){
				// 转账类型从非基金购买修改为基金购买
				FundDetail fundDetail = transferDetail_new.getFundDetail();
				fundDetail.setTransferUuid(transferDetail_new.getMxuuid());
				fundDetail.setLrrq(new Date());
				this.fundDetailMBdao.addOne(fundDetail);
			}else if (transferDetail_new.getZzlx_dm().equals(DmService.zzlx_purchase_fund_dm) && transferDetail_old.getZzlx_dm().equals(DmService.zzlx_purchase_fund_dm)){
				// 修改前后的转账类型都是购买基金
				FundDetail fundDetail = transferDetail_new.getFundDetail();
				fundDetail.setTransferUuid(transferDetail_new.getMxuuid());
				fundDetail.setXgrq(new Date());
				this.fundDetailMBdao.updateOne(fundDetail);
			}else if (!transferDetail_new.getZzlx_dm().equals(DmService.zzlx_purchase_fund_dm) && transferDetail_old.getZzlx_dm().equals(DmService.zzlx_purchase_fund_dm)){
				// 修改前是购买基金，修改后不是购买基金
				this.fundDetailMBdao.deleteOne(transferDetail_new.getMxuuid());
			}
			
			if (transferDetail_new.getZzlx_dm().equals(DmService.zzlx_purchase_fin_prod_dm) && !transferDetail_old.getZzlx_dm().equals(DmService.zzlx_purchase_fin_prod_dm)){
				// 转账类型从非理财产品修改为理财产品购买
				FinancialProductDetail financialProductDetail = transferDetail_new.getFinancialProductDetail();
				financialProductDetail.setTransferUuid(transferDetail_new.getMxuuid());
				financialProductDetail.setLrrq(new Date());
				this.financialProductDetailMBDao.addOne(financialProductDetail);
			}else if (transferDetail_new.getZzlx_dm().equals(DmService.zzlx_purchase_fin_prod_dm) && transferDetail_old.getZzlx_dm().equals(DmService.zzlx_purchase_fin_prod_dm)){
				// 修改前后的转账类型都是购买理财产品
				FinancialProductDetail financialProductDetail = transferDetail_new.getFinancialProductDetail();
				financialProductDetail.setTransferUuid(transferDetail_new.getMxuuid());
				financialProductDetail.setXgrq(new Date());
				this.financialProductDetailMBDao.updateOne(financialProductDetail); // 通过产品的transferUuid更新理财产品
			}else if (!transferDetail_new.getZzlx_dm().equals(DmService.zzlx_purchase_fin_prod_dm) && transferDetail_old.getZzlx_dm().equals(DmService.zzlx_purchase_fin_prod_dm)){
				// 修改后不是理财产品
				this.financialProductDetailMBDao.deleteOne(transferDetail_new.getMxuuid());
			}
			
			if (transferDetail_new.getZzlx_dm().equals(DmService.zzlx_redeem_fin_prod_dm) && transferDetail_old.getZzlx_dm().equals(DmService.zzlx_redeem_fin_prod_dm)){
				// 设置理财产品收益 (20180429在更新时不能修改赎回的理财产品)
				//FinancialProductDetail financialProductDetail = transferDetail_new.getFinancialProductDetail();
				//this.financialProductDetailMBDao.setRedeem(financialProductDetail.getUuid(), transferDetail_old.getMxuuid());
			}
			
			keys.add(this.statDetailKeyGenerator.detailTransferKey(transferDetail_new.getUser_id(), transferDetail_new.getShijian()));
			keys.add(this.statDetailKeyGenerator.detailTransferKey(transferDetail_old.getUser_id(), transferDetail_old.getShijian()));
		}
		
		this.accountStatService.EvictDateStatRedis(keys);
		this.dmService.removeZhInfoCache(detail.getUser_id());
		
		return accountingDetail;
	}
	
	//@CacheEvict(value="redisCacheStat",  allEntries=true)
	@Transactional(value="dsTransactionManager", isolation=Isolation.READ_COMMITTED)
	public void deleteOne(T detail){
		
		Date xgrq = new Date();
		Map<String , Object> paramMap = new HashMap<>();
		paramMap.put("mxuuid", detail.getMxuuid());
		paramMap.put("xgrq", xgrq);
		Set<String> keys = new HashSet<String>();
		
		if ( detail instanceof IncomeDetail ){
			IncomeDetail incomeDetail = (IncomeDetail) detail;
			incomeDetailMBDao.deleteOne(paramMap);
			// Account SNAPSHOT
			accountSnapshotting.shotting(incomeDetail.getZh_dm(), incomeDetail.getAccuuid(), AccountSnapshotting.DELETE, incomeDetail.getUser_id(), -1.0f*incomeDetail.getJe());
			accountBookDao.updateYe(incomeDetail.getZh_dm(), -1.0f*incomeDetail.getJe());
			
			if (incomeDetail.getLb_dm().equals(DmService.srlb_fin_prod_dm))
			{	
				FinancialProductIncomeDetail fpincomedetail = this.financialProductIncomeDetailMBDao.getFinancialProductIncomeByIncomeUuid(incomeDetail.getMxuuid());
				if (fpincomedetail != null)
				{
					incomeDetail.setFinprodUuid(fpincomedetail.getFpuuid());
					incomeDetail.setIs_redeem(fpincomedetail.getIs_redeem());
					
					// 如果收入类型是“理财收益”,那么关联的理财产品信息被清除
					if (incomeDetail.getIs_redeem().equals("Y"))
					{
						this.financialProductDetailMBDao.cancelRealReturn(incomeDetail.getMxuuid());
					}
					this.financialProductIncomeDetailMBDao.deleteOneByIncomeUuid(incomeDetail.getMxuuid());
					
				}
			}
			
			// 清除“收入明细”的缓存(redis)
			keys.add(this.statDetailKeyGenerator.detailIncomeKey(incomeDetail.getUser_id(), incomeDetail.getShijian()));			
		}else if (detail instanceof PaymentDetail){
			PaymentDetail paymentDetail = (PaymentDetail) detail;
			paymentDetailMBDao.deleteOne(paramMap);
			// Account SNAPSHOT
			accountSnapshotting.shotting(paymentDetail.getZh_dm(), paymentDetail.getAccuuid(), AccountSnapshotting.DELETE, paymentDetail.getUser_id(), paymentDetail.getJe());
			accountBookDao.updateYe(paymentDetail.getZh_dm(), paymentDetail.getJe());
			
			// 清除“支出明细”的缓存(redis)
			keys.add(this.statDetailKeyGenerator.detailPaymentKey(paymentDetail.getUser_id(), paymentDetail.getShijian()));			
		}else if (detail instanceof TransferDetail){
			TransferDetail transferDetail = (TransferDetail)detail; 
			transferDetailMBDao.deleteOne(paramMap);
			
			// Account SNAPSHOT
			accountSnapshotting.shotting(transferDetail.getSrcZh_dm(), transferDetail.getAccuuid(), AccountSnapshotting.DELETE, transferDetail.getUser_id(),       transferDetail.getJe());
			accountSnapshotting.shotting(transferDetail.getTgtZh_dm(), transferDetail.getAccuuid(), AccountSnapshotting.DELETE, transferDetail.getUser_id(), -1.0f*transferDetail.getJe());
						
			accountBookDao.updateYe(transferDetail.getSrcZh_dm(),       transferDetail.getJe());
			accountBookDao.updateYe(transferDetail.getTgtZh_dm(), -1.0f*transferDetail.getJe());
			
			if (transferDetail.getZzlx_dm().equals(DmService.zzlx_purchase_fund_dm) && transferDetail.getFundDetail() != null){
				fundDetailMBdao.deleteOne(transferDetail.getFundDetail().getTransferUuid());
			}
			
			if (transferDetail.getZzlx_dm().equals(DmService.zzlx_purchase_fin_prod_dm) && transferDetail.getFinancialProductDetail() != null){
				financialProductDetailMBDao.deleteOne(transferDetail.getFinancialProductDetail().getTransferUuid());
			}
			
			if (transferDetail.getZzlx_dm().equals(DmService.zzlx_redeem_fin_prod_dm)){
				// 如果转账类型为“赎回理财产品”，那么对应的理财产品的赎回信息被清除
				financialProductDetailMBDao.cancelRedeem(transferDetail.getMxuuid());
			}
			
			// 清除“转账明细”的缓存(redis)
			keys.add(this.statDetailKeyGenerator.detailTransferKey(transferDetail.getUser_id(), transferDetail.getShijian()));
		}
		
		paramMap.put("accuuid", detail.getAccuuid());
		accountDetailMBDao.deleteOne(paramMap);
		
		this.accountStatService.EvictDateStatRedis(keys);
		this.dmService.removeZhInfoCache(detail.getUser_id());
	}
	
	
	//@CacheEvict(value="redisCacheStat", allEntries=true)
	@Transactional(value="dsTransactionManager", isolation=Isolation.READ_COMMITTED)
	public void deletePatch(List<T> detailObjs){
		if (detailObjs.size() == 0){
			System.out.println("deletePatch size==0");
			return;
		}
		Set<String> keys = new HashSet<String>();
		List<String> mxuuidList = new ArrayList<>();
		List<String> accuuidList = new ArrayList<>();
		for (T item : detailObjs){
			mxuuidList.add(item.getMxuuid());
			accuuidList.add(item.getAccuuid());
		}
		
		if ( detailObjs.get(0) instanceof IncomeDetail ){
			incomeDetailMBDao.deletePatch(mxuuidList);
			for (T item : detailObjs){
				IncomeDetail incomeItem = (IncomeDetail) item;
				
				// Account SNAPSHOT
				accountSnapshotting.shotting(incomeItem.getZh_dm(), incomeItem.getAccuuid(), AccountSnapshotting.DELETE, incomeItem.getUser_id(), -1.0f*incomeItem.getJe());
				
				accountBookDao.updateYe(incomeItem.getZh_dm(), -1.0f*incomeItem.getJe());
				
				if (incomeItem.getLb_dm().equals(DmService.srlb_fin_prod_dm))
				{	
					FinancialProductIncomeDetail fpincomedetail = this.financialProductIncomeDetailMBDao.getFinancialProductIncomeByIncomeUuid(incomeItem.getMxuuid());
					if (fpincomedetail != null)
					{
						incomeItem.setFinprodUuid(fpincomedetail.getFpuuid());
						incomeItem.setIs_redeem(fpincomedetail.getIs_redeem());
						
						// 如果收入类型是“理财收益”,那么关联的理财产品信息被清除
						if (incomeItem.getIs_redeem().equals("Y"))
						{
							this.financialProductDetailMBDao.cancelRealReturn(incomeItem.getMxuuid());
						}
						this.financialProductIncomeDetailMBDao.deleteOneByIncomeUuid(incomeItem.getMxuuid());
						
					}
				}
				
				// 准备删除缓存
				keys.add(this.statDetailKeyGenerator.detailIncomeKey(incomeItem.getUser_id(), incomeItem.getShijian()));
			}
			
		}else if (detailObjs.get(0) instanceof PaymentDetail){
			paymentDetailMBDao.deletePatch(mxuuidList);
			for (T item : detailObjs){
				PaymentDetail paymentItem = (PaymentDetail) item; 
				
				// Account SNAPSHOT
				accountSnapshotting.shotting(paymentItem.getZh_dm(), paymentItem.getAccuuid(), AccountSnapshotting.DELETE, paymentItem.getUser_id(), paymentItem.getJe());
				
				accountBookDao.updateYe(paymentItem.getZh_dm(), paymentItem.getJe());
				
				// 准备删除缓存
				keys.add(this.statDetailKeyGenerator.detailPaymentKey(paymentItem.getUser_id(), paymentItem.getShijian()));
			}
		}else if (detailObjs.get(0) instanceof TransferDetail){
			transferDetailMBDao.deletePatch(mxuuidList);
			fundDetailMBdao.deletePatch(mxuuidList);// 如果有则删除，fund的uuid和transfer的uuid相同
			financialProductDetailMBDao.deletePatch(mxuuidList);// 如果有则删除，financial product的uuid和transfer的uuid相同
			for (T item : detailObjs){
				TransferDetail transferItem = (TransferDetail) item;
				
				// Account SNAPSHOT
				accountSnapshotting.shotting(transferItem.getSrcZh_dm(), transferItem.getAccuuid(), AccountSnapshotting.DELETE, transferItem.getUser_id(),       transferItem.getJe());
				accountSnapshotting.shotting(transferItem.getTgtZh_dm(), transferItem.getAccuuid(), AccountSnapshotting.DELETE, transferItem.getUser_id(), -1.0f*transferItem.getJe());
				
				accountBookDao.updateYe(transferItem.getSrcZh_dm(),       transferItem.getJe());
				accountBookDao.updateYe(transferItem.getTgtZh_dm(), -1.0f*transferItem.getJe());
				
				if (transferItem.getZzlx_dm().equals(DmService.zzlx_redeem_fin_prod_dm)){
					financialProductDetailMBDao.cancelRedeem(transferItem.getMxuuid());
				}
				
				// 准备删除缓存
				keys.add(this.statDetailKeyGenerator.detailTransferKey(transferItem.getUser_id(), transferItem.getShijian()));
			}
		}
		
		accountDetailMBDao.deletePatch(accuuidList);
		
		// 清除缓存
		this.accountStatService.EvictDateStatRedis(keys);
		this.dmService.removeZhInfoCache(detailObjs.get(0).getUser_id());
	}
	
	//@CacheEvict(value="redisCacheStat", allEntries=true)
	@Transactional(value="dsTransactionManager", isolation=Isolation.READ_COMMITTED)
	public void deletePatchByAccuuid(List<AccountingDetail> list){
		if (list.size() == 0){
			System.out.println("deletePatch size==0");
			return;
		}
		Set<String> keys = new HashSet<String>();
		List<String> incomeAccList = new ArrayList<>();
		List<String> paymentAccList = new ArrayList<>();
		List<String> transferAccList = new ArrayList<>();
		List<String> accuuidList = new ArrayList<>();
		for (AccountingDetail detail : list){
			String accuuid = detail.getAccuuid();
			accuuidList.add(accuuid);
			switch (detail.getRec_dm()) {
			case 1:
				incomeAccList.add(accuuid);
				break;
			case 2:
				paymentAccList.add(accuuid);
				break;
			case 3:
				transferAccList.add(accuuid);
				break;
			default:
				break;
			}
		}
		
		accountDetailMBDao.deletePatch(accuuidList);

		
		if ( incomeAccList.size()>0 ){
			List<IncomeDetail> incomeDetailList = incomeDetailMBDao.getDetailByPatchAccuuid(incomeAccList);
			List<String> mxuuidList = new ArrayList<>();
			for (IncomeDetail income : incomeDetailList){
				mxuuidList.add(income.getMxuuid());
				// 为清除缓存做准备
				keys.add(this.statDetailKeyGenerator.detailIncomeKey(income.getUser_id(), income.getShijian()));
			}
			
			incomeDetailMBDao.deletePatch(mxuuidList);
			
			for (IncomeDetail income : incomeDetailList){
				// Account SNAPSHOT
				accountSnapshotting.shotting(income.getZh_dm(), income.getAccuuid(), AccountSnapshotting.DELETE, income.getUser_id(), -1.0f*income.getJe());
				
				accountBookDao.updateYe(income.getZh_dm(), -1.0f*income.getJe());
				
				if (income.getLb_dm().equals(DmService.srlb_fin_prod_dm)){
					
					FinancialProductIncomeDetail fpincomedetail = this.financialProductIncomeDetailMBDao.getFinancialProductIncomeByIncomeUuid(income.getMxuuid());
					if (fpincomedetail != null)
					{
						income.setFinprodUuid(fpincomedetail.getFpuuid());
						income.setIs_redeem(fpincomedetail.getIs_redeem());
						
						// 如果收入类型是“理财收益”,那么关联的理财产品信息被清除
						if (income.getIs_redeem().equals("Y"))
						{
							this.financialProductDetailMBDao.cancelRealReturn(income.getMxuuid());
						}
						this.financialProductIncomeDetailMBDao.deleteOneByIncomeUuid(income.getMxuuid());
						
					}
					
				}
			}
		}
		
		if ( paymentAccList.size()>0 ){
			List<PaymentDetail> paymentDetailList = paymentDetailMBDao.getDetailByPatchAccuuid(paymentAccList);
			List<String> mxuuidList = new ArrayList<>();
			for (PaymentDetail payment : paymentDetailList){
				mxuuidList.add(payment.getMxuuid());
				// 为清除缓存做准备
				keys.add(this.statDetailKeyGenerator.detailIncomeKey(payment.getUser_id(), payment.getShijian()));
			}
			
			paymentDetailMBDao.deletePatch(mxuuidList);
			
			for (PaymentDetail payment : paymentDetailList){
				// Account SNAPSHOT
				accountSnapshotting.shotting(payment.getZh_dm(), payment.getAccuuid(), AccountSnapshotting.DELETE, payment.getUser_id(), payment.getJe());
				accountBookDao.updateYe(payment.getZh_dm(), payment.getJe());
			}
		}
		
		if ( transferAccList.size()>0 ){
			List<TransferDetail> transferDetailList = transferDetailMBDao.getDetailByPatchAccuuid(transferAccList);
			List<String> mxuuidList = new ArrayList<>();
			for (TransferDetail transfer : transferDetailList){
				mxuuidList.add(transfer.getMxuuid());
				// 为清除缓存做准备
				keys.add(this.statDetailKeyGenerator.detailIncomeKey(transfer.getUser_id(), transfer.getShijian()));
			}
			
			transferDetailMBDao.deletePatch(mxuuidList);
			fundDetailMBdao.deletePatch(mxuuidList); // 如果有则删除
			financialProductDetailMBDao.deletePatch(mxuuidList); // 如果有则删除
			
			for (TransferDetail transfer : transferDetailList){
				// Account SNAPSHOT
				accountSnapshotting.shotting(transfer.getSrcZh_dm(), transfer.getAccuuid(), AccountSnapshotting.DELETE, transfer.getUser_id(),       transfer.getJe());
				accountSnapshotting.shotting(transfer.getTgtZh_dm(), transfer.getAccuuid(), AccountSnapshotting.DELETE, transfer.getUser_id(), -1.0f*transfer.getJe());
				
				accountBookDao.updateYe(transfer.getSrcZh_dm(),       transfer.getJe());
				accountBookDao.updateYe(transfer.getTgtZh_dm(), -1.0f*transfer.getJe());
				
				
				if (transfer.getZzlx_dm().equals(DmService.zzlx_redeem_fin_prod_dm)){
					financialProductDetailMBDao.cancelRedeem(transfer.getMxuuid());
				}
			}
		}
		
		// 清除缓存
		this.accountStatService.EvictDateStatRedis(keys);
		this.dmService.removeZhInfoCache(list.get(0).getUser_id());
	}
}
