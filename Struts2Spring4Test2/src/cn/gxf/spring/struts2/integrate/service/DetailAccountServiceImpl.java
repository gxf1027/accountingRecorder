package cn.gxf.spring.struts2.integrate.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.gxf.spring.struts.mybatis.dao.AccountDetailMBDao;
import cn.gxf.spring.struts.mybatis.dao.IncomeDetailMBDao;
import cn.gxf.spring.struts.mybatis.dao.PaymentDetailMBDao;
import cn.gxf.spring.struts.mybatis.dao.TransferDetailMBDao;
import cn.gxf.spring.struts2.integrate.dao.AccUtil;
import cn.gxf.spring.struts2.integrate.dao.AccountingDetailDao;
import cn.gxf.spring.struts2.integrate.dao.DmUtilDao;
import cn.gxf.spring.struts2.integrate.dao.IncomeDetailDao;
import cn.gxf.spring.struts2.integrate.dao.PaymentDetailDao;
import cn.gxf.spring.struts2.integrate.dao.TransferDetailDao;
import cn.gxf.spring.struts2.integrate.model.AccountingDetail;
import cn.gxf.spring.struts2.integrate.model.IncomeDetail;
import cn.gxf.spring.struts2.integrate.model.PaymentDetail;
import cn.gxf.spring.struts2.integrate.model.TransferDetail;

@Service("DetailAccountService")
public class DetailAccountServiceImpl implements DetailAccountService{
	@Autowired
	@Qualifier("daoImplJdbc")
	private DmUtilDao dmUtilDao;
	
	@Autowired
	private AccUtil accUtil;
	
	@Autowired
	private AccountingDetailDao accountingDetailDao;
	
	@Autowired
	@Qualifier("PaymentDetailDao")
	private PaymentDetailDao paymentDetailDao;
	
	@Autowired
	@Qualifier("IncomeDetailDao")
	private IncomeDetailDao incomeDetailDao;
	
	@Autowired
	@Qualifier("transferDetailDao")
	private TransferDetailDao transferDetailDao;

	/*MyBatis*/
	@Autowired
	private IncomeDetailMBDao incomeDetailMBDao;
	
	@Autowired
	private PaymentDetailMBDao paymentDetailMBDao;
	
	@Autowired
	private TransferDetailMBDao transferDetailMBDao;
	
	@Autowired
	private AccountDetailMBDao accountDetailMBDao;


	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public void saveOnePayment(PaymentDetail paymentDetail) {
		
		AccountingDetail accountingDetail = new AccountingDetail();
		String accuuid = accUtil.generateUuid();
		accountingDetail.setAccuuid(accuuid);
		accountingDetail.setJe(paymentDetail.getJe());
		accountingDetail.setRec_dm(2);
		accountingDetail.setShijian(paymentDetail.getShijian());
		accountingDetail.setUser_id(paymentDetail.getUser_id());
		accountingDetail.setXgrq(paymentDetail.getXgrq());
		accountingDetail.setYxbz("Y");
		
		accountingDetailDao.addOne(accountingDetail);
		
		paymentDetail.setAccuuid(accuuid);
		paymentDetail.setMxuuid(accUtil.generateUuid());
		paymentDetailDao.saveOne(paymentDetail);
	}

	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public void saveOneIncome(IncomeDetail incomeDetail) {
		AccountingDetail accountingDetail = new AccountingDetail();
		String accuuid = accUtil.generateUuid();
		accountingDetail.setAccuuid(accuuid);
		accountingDetail.setJe(incomeDetail.getJe());
		accountingDetail.setRec_dm(1);
		accountingDetail.setShijian(incomeDetail.getShijian());
		accountingDetail.setUser_id(incomeDetail.getUser_id());
		accountingDetail.setXgrq(incomeDetail.getXgrq());
		accountingDetail.setYxbz("Y");
		
		accountingDetailDao.addOne(accountingDetail);
		
		incomeDetail.setAccuuid(accuuid);
		incomeDetail.setMxuuid(accUtil.generateUuid());
		incomeDetailDao.addOne(incomeDetail);
	}
	
	
	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public void saveOneTransfer(TransferDetail transferDetail) {
		AccountingDetail accountingDetail = new AccountingDetail();
		String accuuid = accUtil.generateUuid();
		accountingDetail.setAccuuid(accuuid);
		accountingDetail.setJe(transferDetail.getJe());
		accountingDetail.setRec_dm(2);
		accountingDetail.setShijian(transferDetail.getShijian());
		accountingDetail.setUser_id(transferDetail.getUser_id());
		accountingDetail.setXgrq(transferDetail.getXgrq());
		accountingDetail.setYxbz("Y");
		
		accountingDetailDao.addOne(accountingDetail);
		
		transferDetail.setAccuuid(accuuid);
		transferDetail.setMxuuid(accUtil.generateUuid());
		transferDetailDao.saveOne(transferDetail);
		
	}
	
	/*MyBatis*/
	
	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public void saveOneIncomeMB(IncomeDetail incomeDetail) {
		AccountingDetail accountingDetail = new AccountingDetail();
		String accuuid = accUtil.generateUuid();
		accountingDetail.setAccuuid(accuuid);
		accountingDetail.setJe(incomeDetail.getJe());
		accountingDetail.setRec_dm(1);
		accountingDetail.setShijian(incomeDetail.getShijian());
		accountingDetail.setUser_id(incomeDetail.getUser_id());
		accountingDetail.setXgrq(incomeDetail.getXgrq());
		accountingDetail.setYxbz("Y");
		
		accountDetailMBDao.addOne(accountingDetail);
		
		incomeDetail.setAccuuid(accuuid);
		incomeDetail.setMxuuid(accUtil.generateUuid());
		incomeDetailMBDao.addOne(incomeDetail);
	}

	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public void saveOnePaymentMB(PaymentDetail paymentDetail) {
		AccountingDetail accountingDetail = new AccountingDetail();
		String accuuid = accUtil.generateUuid();
		accountingDetail.setAccuuid(accuuid);
		accountingDetail.setJe(paymentDetail.getJe());
		accountingDetail.setRec_dm(2);
		accountingDetail.setShijian(paymentDetail.getShijian());
		accountingDetail.setUser_id(paymentDetail.getUser_id());
		accountingDetail.setXgrq(paymentDetail.getXgrq());
		accountingDetail.setYxbz("Y");
		
		accountDetailMBDao.addOne(accountingDetail);
		
		paymentDetail.setAccuuid(accuuid);
		paymentDetail.setMxuuid(accUtil.generateUuid());
		paymentDetailMBDao.addOne(paymentDetail);
		
	}

	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public void saveOneTransferMB(TransferDetail transferDetail) {
		AccountingDetail accountingDetail = new AccountingDetail();
		String accuuid = accUtil.generateUuid();
		accountingDetail.setAccuuid(accuuid);
		accountingDetail.setJe(transferDetail.getJe());
		accountingDetail.setRec_dm(3);
		accountingDetail.setShijian(transferDetail.getShijian());
		accountingDetail.setUser_id(transferDetail.getUser_id());
		accountingDetail.setXgrq(transferDetail.getXgrq());
		accountingDetail.setYxbz("Y");
		
		accountDetailMBDao.addOne(accountingDetail);
		
		transferDetail.setAccuuid(accuuid);
		transferDetail.setMxuuid(accUtil.generateUuid());
		transferDetailMBDao.addOne(transferDetail);
		
	}

	
}
