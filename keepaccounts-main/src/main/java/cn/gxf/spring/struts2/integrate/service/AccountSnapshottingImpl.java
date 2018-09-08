package cn.gxf.spring.struts2.integrate.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.gxf.spring.struts.mybatis.dao.AccountSnapshotMBDao;
import cn.gxf.spring.struts2.integrate.dao.AccountBookDao;
import cn.gxf.spring.struts2.integrate.model.AccountBook;
import cn.gxf.spring.struts2.integrate.model.AccountSnapshot;
import cn.gxf.spring.struts2.integrate.model.AccountingDetail;

@Service
public class AccountSnapshottingImpl implements AccountSnapshotting{

	@Autowired
	private AccountSnapshotMBDao accountSnapshotMBDao;
	
	@Autowired
	private AccountBookDao accountBookDao;
	   
	
	@Transactional(value="dsTransactionManager", propagation=Propagation.REQUIRED, isolation=Isolation.READ_COMMITTED)
	@Override
	public AccountSnapshot shotting(String zh_dm, String accuuid, String type, int user_id, float bdje) {
		AccountBook book = accountBookDao.getZhInfo(zh_dm);
		AccountSnapshot snapshot = new AccountSnapshot();
		snapshot.setAccuuid(accuuid);
		snapshot.setZh_dm(zh_dm);
		snapshot.setBdje(bdje);
		snapshot.setFshje(book.getYe() + bdje);
		snapshot.setUser_id(user_id);
		snapshot.setLrrq(new Date());
		snapshot.setType(type);
		accountSnapshotMBDao.addOne(snapshot); // Ö÷¼ü»ØÌî
		
		return snapshot;
	}

	@Transactional(value="dsTransactionManager", propagation=Propagation.REQUIRED, isolation=Isolation.READ_COMMITTED)
	@Override
	public AccountSnapshot shotting(String zh_dm,  String type, AccountingDetail detail) {
		
		AccountBook book = accountBookDao.getZhInfo(zh_dm);
		
		AccountSnapshot snapshot = new AccountSnapshot();
		snapshot.setAccuuid(detail.getAccuuid());
		snapshot.setZh_dm(zh_dm);
		snapshot.setBdje(detail.getJe());
		snapshot.setFshje(book.getYe() + detail.getJe());
		snapshot.setUser_id(detail.getUser_id());
		snapshot.setLrrq(new Date());
		snapshot.setType(type);
		accountSnapshotMBDao.addOne(snapshot);
		
		return snapshot;
	}

}
