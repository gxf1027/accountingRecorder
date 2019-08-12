package cn.gxf.spring.quartz.job.service;

import java.util.List;

import cn.gxf.spring.quartz.job.dao.CreditCardBillDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BillServiceImpl implements BillService {

	@Autowired
	private CreditCardBillDao creditCardBillDao;

	@Override
	public void SetBillMailed(List<String> uuidList) {
		if (null == uuidList || uuidList.isEmpty()){
			System.out.println("SetBillMailed error parameter");
			return ;
		}
		
		creditCardBillDao.setMailed(uuidList);
	}

}
