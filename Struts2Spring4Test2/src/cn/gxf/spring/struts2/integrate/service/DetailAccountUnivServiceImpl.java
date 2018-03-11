package cn.gxf.spring.struts2.integrate.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.gxf.spring.struts.mybatis.dao.AccountDetailMBDao;
import cn.gxf.spring.struts.mybatis.dao.IncomeDetailMBDao;
import cn.gxf.spring.struts.mybatis.dao.PaymentDetailMBDao;
import cn.gxf.spring.struts.mybatis.dao.TransferDetailMBDao;
import cn.gxf.spring.struts2.integrate.dao.AccountBookDao;
import cn.gxf.spring.struts2.integrate.model.AccountObject;
import cn.gxf.spring.struts2.integrate.model.AccountingDetail;
import cn.gxf.spring.struts2.integrate.model.IncomeDetail;
import cn.gxf.spring.struts2.integrate.model.PaymentDetail;
import cn.gxf.spring.struts2.integrate.model.TransferDetail;

@Service
public class DetailAccountUnivServiceImpl<T extends AccountObject>{

	
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
	private AccountBookDao accountBookDao;
	
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
	
	public IncomeDetail getIncomeDetailByMxuuid(String mxuuid){
		return this.incomeDetailMBDao.getIncomeDetailByUuid(mxuuid);
	}
	
	public PaymentDetail getPaymentDetailByMxuuid(String mxuuid){
		return this.paymentDetailMBDao.getPaymentDetailByUuid(mxuuid);
	}
	
	public TransferDetail getTransferDetailByMxuuid(String mxuuid){
		return this.transferDetailMBDao.getTransferDetailByUuid(mxuuid);
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
	
	@CacheEvict(value="statCache", allEntries=true)
	@Transactional("dsTransactionManager")
	public void saveOne(T detail) {
		
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
		if ( detail instanceof IncomeDetail ){
			IncomeDetail incomeDetail = (IncomeDetail) detail;
			incomeDetailMBDao.addOne(incomeDetail);
			accountBookDao.updateYe(incomeDetail.getZh_dm(), incomeDetail.getJe());
		}else if (detail instanceof PaymentDetail){
			PaymentDetail paymentDetail = (PaymentDetail)detail;
			paymentDetailMBDao.addOne(paymentDetail);
			accountBookDao.updateYe(paymentDetail.getZh_dm(), -1.0f*paymentDetail.getJe());
		}else if (detail instanceof TransferDetail){
			TransferDetail transferDetail = (TransferDetail)detail; 
			transferDetailMBDao.addOne(transferDetail);
			accountBookDao.updateYe(transferDetail.getSrcZh_dm(), -1.0f*transferDetail.getJe());
			accountBookDao.updateYe(transferDetail.getTgtZh_dm(), transferDetail.getJe());
		}
	}

	@CacheEvict(value="statCache", allEntries=true)
	@Transactional("dsTransactionManager")
	public void updateOne(T detail) {
		
		AccountingDetail accountingDetail = accountDetailMBDao.getAccountingDetailByUuid(detail.getAccuuid());
		accountingDetail.setJe(detail.getJe());
		accountingDetail.setShijian(detail.getShijian());
		accountingDetail.setXgrq(detail.getXgrq());
		
		accountDetailMBDao.updateOne(accountingDetail);
		
		if ( detail instanceof IncomeDetail ){
			IncomeDetail incomeDetail_new = (IncomeDetail) detail;
			IncomeDetail incomeDetail_old = incomeDetailMBDao.getIncomeDetailByUuid(incomeDetail_new.getMxuuid());
			if (incomeDetail_new.getZh_dm().equals(incomeDetail_old.getZh_dm()) ){
				accountBookDao.updateYe(incomeDetail_new.getZh_dm(), -1.0f*incomeDetail_old.getJe() + incomeDetail_new.getJe());
			}else{
				accountBookDao.updateYe(incomeDetail_new.getZh_dm(), incomeDetail_new.getJe());
				accountBookDao.updateYe(incomeDetail_old.getZh_dm(), -1.0f*incomeDetail_old.getJe());
			}
			
			incomeDetailMBDao.updateOne((IncomeDetail)detail);
		
		}else if (detail instanceof PaymentDetail){
			PaymentDetail paymentDetail_new = (PaymentDetail) detail;
			PaymentDetail paymentDetail_old = paymentDetailMBDao.getPaymentDetailByUuid(paymentDetail_new.getMxuuid());
			if(paymentDetail_new.getZh_dm().equals(paymentDetail_old.getZh_dm())){
				accountBookDao.updateYe(paymentDetail_new.getZh_dm(), paymentDetail_old.getJe() - 1.0f*paymentDetail_new.getJe());
			}else{
				accountBookDao.updateYe(paymentDetail_new.getZh_dm(), -1.0f*paymentDetail_new.getJe());
				accountBookDao.updateYe(paymentDetail_old.getZh_dm(), paymentDetail_old.getJe());
			}
			
			paymentDetailMBDao.updateOne((PaymentDetail)detail);
			
		}else if (detail instanceof TransferDetail){
			TransferDetail transferDetail_new = (TransferDetail)detail;
			TransferDetail transferDetail_old = transferDetailMBDao.getTransferDetailByUuid(transferDetail_new.getMxuuid());
			
			// 考虑到修改前后的转出、转入账户都有不同的可能，先做撤销，再重新发起
			// 撤销上次交易
			accountBookDao.updateYe(transferDetail_old.getSrcZh_dm(), transferDetail_old.getJe());
			accountBookDao.updateYe(transferDetail_old.getTgtZh_dm(), -1.0f*transferDetail_old.getJe());
			
			// 进行本次交易
			accountBookDao.updateYe(transferDetail_new.getSrcZh_dm(), -1.0f*transferDetail_new.getJe());
			accountBookDao.updateYe(transferDetail_new.getTgtZh_dm(), transferDetail_new.getJe());
			
			transferDetailMBDao.updateOne(transferDetail_new);
		}
	}
	
	@CacheEvict(value="statCache", allEntries=true)
	@Transactional("dsTransactionManager")
	public void deleteOne(T detail){
		
		Date xgrq = new Date();
		Map<String , Object> paramMap = new HashMap<>();
		paramMap.put("mxuuid", detail.getMxuuid());
		paramMap.put("xgrq", xgrq);

		if ( detail instanceof IncomeDetail ){
			IncomeDetail incomeDetail = (IncomeDetail) detail;
			incomeDetailMBDao.deleteOne(paramMap);
			accountBookDao.updateYe(incomeDetail.getZh_dm(), -1.0f*incomeDetail.getJe());
			
		}else if (detail instanceof PaymentDetail){
			PaymentDetail paymentDetail = (PaymentDetail) detail;
			paymentDetailMBDao.deleteOne(paramMap);
			accountBookDao.updateYe(paymentDetail.getZh_dm(), paymentDetail.getJe());
			
		}else if (detail instanceof TransferDetail){
			TransferDetail transferDetail = (TransferDetail)detail; 
			transferDetailMBDao.deleteOne(paramMap);
			
			accountBookDao.updateYe(transferDetail.getSrcZh_dm(), transferDetail.getJe());
			accountBookDao.updateYe(transferDetail.getTgtZh_dm(), -1.0f*transferDetail.getJe());
		}
		
		paramMap.put("accuuid", detail.getAccuuid());
		accountDetailMBDao.deleteOne(paramMap);
	}
	
	
	@CacheEvict(value="statCache", allEntries=true)
	@Transactional("dsTransactionManager")
	public void deletePatch(List<T> detailObjs){
		if (detailObjs.size() == 0){
			System.out.println("deletePatch size==0");
			return;
		}
		
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
				accountBookDao.updateYe(incomeItem.getZh_dm(), -1.0f*incomeItem.getJe());
			}
			
		}else if (detailObjs.get(0) instanceof PaymentDetail){
			paymentDetailMBDao.deletePatch(mxuuidList);
			for (T item : detailObjs){
				PaymentDetail paymentItem = (PaymentDetail) item; 
				accountBookDao.updateYe(paymentItem.getZh_dm(), paymentItem.getJe());
			}
		}else if (detailObjs.get(0) instanceof TransferDetail){
			transferDetailMBDao.deletePatch(mxuuidList);
			for (T item : detailObjs){
				TransferDetail transferItem = (TransferDetail) item;
				accountBookDao.updateYe(transferItem.getSrcZh_dm(), transferItem.getJe());
				accountBookDao.updateYe(transferItem.getTgtZh_dm(), -1.0f*transferItem.getJe());
			}
		}
		
		accountDetailMBDao.deletePatch(accuuidList);
	}
	
	@CacheEvict(value="statCache", allEntries=true)
	@Transactional("dsTransactionManager")
	public void deletePatchByAccuuid(List<AccountingDetail> list){
		if (list.size() == 0){
			System.out.println("deletePatch size==0");
			return;
		}
		
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
			}
			
			incomeDetailMBDao.deletePatch(mxuuidList);
			
			for (IncomeDetail income : incomeDetailList){
				accountBookDao.updateYe(income.getZh_dm(), -1.0f*income.getJe());
			}
		}
		
		if ( paymentAccList.size()>0 ){
			List<PaymentDetail> paymentDetailList = paymentDetailMBDao.getDetailByPatchAccuuid(paymentAccList);
			List<String> mxuuidList = new ArrayList<>();
			for (PaymentDetail payment : paymentDetailList){
				mxuuidList.add(payment.getMxuuid());
			}
			
			paymentDetailMBDao.deletePatch(mxuuidList);
			
			for (PaymentDetail payment : paymentDetailList){
				accountBookDao.updateYe(payment.getZh_dm(), payment.getJe());
			}
		}
		
		if ( transferAccList.size()>0 ){
			List<TransferDetail> transferDetailList = transferDetailMBDao.getDetailByPatchAccuuid(transferAccList);
			List<String> mxuuidList = new ArrayList<>();
			for (TransferDetail transfer : transferDetailList){
				mxuuidList.add(transfer.getMxuuid());
			}
			
			transferDetailMBDao.deletePatch(mxuuidList);
			
			for (TransferDetail transfer : transferDetailList){
				accountBookDao.updateYe(transfer.getSrcZh_dm(), transfer.getJe());
				accountBookDao.updateYe(transfer.getTgtZh_dm(), -1.0f*transfer.getJe());
			}
		}
		 
	}
}
