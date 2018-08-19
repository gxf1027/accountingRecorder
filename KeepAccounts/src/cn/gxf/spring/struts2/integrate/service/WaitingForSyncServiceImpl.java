package cn.gxf.spring.struts2.integrate.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.gxf.spring.struts.integrate.util.AuxiliaryTools;
import cn.gxf.spring.struts.mybatis.dao.AccountDetailMBDao;
import cn.gxf.spring.struts2.integrate.model.AccountingDetail;

@Service
public class WaitingForSyncServiceImpl implements WaitingForSyncService{

	private final int max_iter_num = 10; 
	private final long dely_mills = AuxiliaryTools.millisec_wait_mysql_sync;
	private final long max_wait_mills = max_iter_num * dely_mills + 100;
	
	@Autowired
	private AccountDetailMBDao accountDetailDao;
	
	
	@Override
	public int queryWaiting4Save(String accuuid) {
		
		int count = 0;
		long start = System.currentTimeMillis();
		
		while(true){
			
			long end = System.currentTimeMillis();
			if( end - start > max_wait_mills){
				return -2;
			}
			
			if ( count > max_iter_num ){
				return -1;
			}
			
			// 等待100ms
			AuxiliaryTools.delay(AuxiliaryTools.millisec_wait_mysql_sync);
			
			AccountingDetail detail = accountDetailDao.getAccountingDetailByUuid(accuuid);
			if (null != detail ){
				break;
			}
			
			count++;
		}
		System.out.println("\n\n snyc cost: " + (System.currentTimeMillis()-start));
		return count;
	}


	@Override
	public int queryWaiting4Del(String accuuid) {
		
		int count = 0;
		long start = System.currentTimeMillis();
		
		while(true){
			
			long end = System.currentTimeMillis();
			if( end - start > max_wait_mills){
				System.out.println("exceed maxium millis.");
				return -2;
			}
			
			if ( count > max_iter_num ){
				System.out.println("exceed maxium iteration.");
				return -1;
			}
			
			// 等待100ms
			AuxiliaryTools.delay(AuxiliaryTools.millisec_wait_mysql_sync);
			
			AccountingDetail detail = accountDetailDao.getAccountingDetailByUuid2(accuuid);
			if (null == detail || detail.getYxbz().equals("N")){
				break;
			}
			
			count++;
		}
		
		System.out.println("\n\n snyc: " + (System.currentTimeMillis()-start) + " count: " + count);
		return count;
		
	}


	@Override
	public int queryWaiting4Update(String accuuid, Date date) {
		
		// 减一秒
		Date date2 = new Date();
		date2.setTime(date.getTime() - 1000);
		
		int count = 0;
		long start = System.currentTimeMillis();
		
		while(true){
			
			long end = System.currentTimeMillis();
			if( end - start > max_wait_mills){
				System.out.println("exceed maxium millis.");
				return -2;
			}
			
			if ( count > max_iter_num ){
				System.out.println("exceed maxium iteration.");
				return -1;
			}
			
			// 等待100ms
			AuxiliaryTools.delay(AuxiliaryTools.millisec_wait_mysql_sync);
			
			AccountingDetail detail = accountDetailDao.getAccountingDetailByUuid3(accuuid, date2);
			if ( null != detail ){
				break;
			}
			
			count++;
		}
		
		System.out.println("\n\n snyc: " + (System.currentTimeMillis()-start) + " count: " + count);
		
		return count;
	}

	
}
