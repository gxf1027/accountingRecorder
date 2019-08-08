package cn.gxf.spring.cxf.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.jws.WebService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import cn.gxf.spring.struts.mybatis.dao.AccountVoMBDao;
import cn.gxf.spring.struts2.integrate.dao.AccDetailVoDao;
import cn.gxf.spring.struts2.integrate.model.AccDateStat;
import cn.gxf.spring.struts2.integrate.model.AccountingDetailVO;
import cn.gxf.spring.struts2.integrate.model.IncomeDetailVO;
import cn.gxf.spring.struts2.integrate.model.PaymentDetailVO;
import cn.gxf.spring.struts2.integrate.model.TransferDetailVO;

@Service
@WebService(endpointInterface="cn.gxf.spring.cxf.service.AccountStatService")
public class AccountStatServiceImpl implements AccountStatService{
	
    private Logger logger = LogManager.getLogger();

    
	@Autowired
	private AccountVoMBDao accountVoMBDao;
	
	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@Override
	public List<AccDateStat> getDateStat(int user_id, Date date_from, Date date_to) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("date_from", date_from == null ? null : sdf.format(date_from));
		paramMap.put("date_to", date_to == null ? null : sdf.format(date_to));
		paramMap.put("user_id", user_id);
		
		List<PaymentDetailVO> listPayment = accountVoMBDao.getPaymentVo(paramMap);
		List<IncomeDetailVO> listIncome = accountVoMBDao.getIncomeVo(paramMap);
		List<TransferDetailVO> listTransfer = accountVoMBDao.getTransferVo(paramMap);
		
		List<AccountingDetailVO> list = new ArrayList<>();
		
		for (IncomeDetailVO e : listIncome){
			list.add(e);
		}
		
		for (PaymentDetailVO e : listPayment){
			list.add(e);
		}
		
		for (TransferDetailVO e : listTransfer){
			list.add(e);
		}
		
		
		
		// 将日期作为key进行统计，同个日期的放入list中
		Map<String, List<AccountingDetailVO>> map = new TreeMap<>();
		//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		for (AccountingDetailVO e : list){
			String d = sdf.format(e.getFsrq());
			if (null == map.get(d)){
				List<AccountingDetailVO> tmp_list = new ArrayList<>();
				tmp_list.add(e);
				map.put(d, tmp_list);
			}
			else{
				map.get(d).add(e);
			}
		}
		
		//System.out.println(map);
		// 将上面的map整理到AccDateStat类型中
		List<AccDateStat> stat_list = new ArrayList<>();
		for (String keyd : map.keySet()){
			AccDateStat accDateStat = new AccDateStat();
			
			try {
				accDateStat.setDate(sdf.parse(keyd));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				logger.error(e.getMessage());
			}
			
			float pay_sum = 0.f;
			float in_sum = 0.f;
			
			for (AccountingDetailVO acc : map.get(keyd)){
				/*if (acc.getType() == 1)*/ // 收入
				if (acc instanceof IncomeDetailVO)
				{
					in_sum += acc.getJe();
				}
				/*else if (acc.getType() == 2)*/ // 支出
				else if (acc instanceof PaymentDetailVO)
				{
					pay_sum += acc.getJe();
				}
				
				accDateStat.getDetail_list().add(acc);
			}
			
			accDateStat.setIncomesum(in_sum);
			accDateStat.setPaymentsum(pay_sum);
			stat_list.add(accDateStat);
			Collections.sort(accDateStat.getDetail_list());
		}
		
		Collections.sort(stat_list);
		
		return stat_list;
	}

	@Override
	public List<AccDateStat> getDateStatIncome(int user_id, Date date_from, Date date_to) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Map<String, Object> mapParam = new HashMap<>();
		mapParam.put("date_from", date_from == null ? null : sdf.format(date_from));
		mapParam.put("date_to", date_to == null ? null : sdf.format(date_to));
		mapParam.put("user_id", user_id);
		List<IncomeDetailVO> list = accountVoMBDao.getIncomeVo(mapParam);
		
		Map<String, List<IncomeDetailVO>> map = new HashMap<>();
		//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		for (IncomeDetailVO e : list){
			String d = sdf.format(e.getFsrq());
			if (null == map.get(d)){
				List<IncomeDetailVO> tmp_list = new ArrayList<>();
				tmp_list.add(e);
				map.put(d, tmp_list);
			}
			else{
				map.get(d).add(e);
			}
		}
		
		//System.out.println(map);
		List<AccDateStat> stat_list = new ArrayList<>();
		for (String keyd : map.keySet()){
			AccDateStat accDateStat = new AccDateStat();
			
			try {
				accDateStat.setDate(sdf.parse(keyd));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				logger.error(e.getMessage());
			}
			
			float in_sum = 0.f;
			
			for (IncomeDetailVO acc : map.get(keyd)){

				in_sum += acc.getJe();
				
				accDateStat.getDetail_list().add(acc);
			}
			
			accDateStat.setIncomesum(in_sum);
			accDateStat.setPaymentsum(0.f);
			stat_list.add(accDateStat);
			Collections.sort(accDateStat.getDetail_list());
		}
				
		Collections.sort(stat_list);
				
		return stat_list;
	}

	@Override
	public List<AccDateStat> getDateStatPayment(int user_id, Date date_from, Date date_to) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Map<String, Object> mapParam = new HashMap<>();
		mapParam.put("date_from", date_from == null ? null : sdf.format(date_from));
		mapParam.put("date_to", date_to == null ? null : sdf.format(date_to));
		mapParam.put("user_id", user_id);
		List<PaymentDetailVO> list = accountVoMBDao.getPaymentVo(mapParam);
		
		
		Map<String, List<PaymentDetailVO>> map = new HashMap<>();
		for (PaymentDetailVO e : list){
			String d = sdf.format(e.getFsrq());
			if (null == map.get(d)){
				List<PaymentDetailVO> tmp_list = new ArrayList<>();
				tmp_list.add(e);
				map.put(d, tmp_list);
			}
			else{
				map.get(d).add(e);
			}
		}
		
		//System.out.println(map);
		List<AccDateStat> stat_list = new ArrayList<>();
		for (String keyd : map.keySet()){
			AccDateStat accDateStat = new AccDateStat();
			
			try {
				accDateStat.setDate(sdf.parse(keyd));
			} catch (ParseException e) {
				logger.error(e.getMessage());
			}
			
			float pay_sum = 0.f;
			
			for (PaymentDetailVO acc : map.get(keyd)){

				pay_sum += acc.getJe();
				
				accDateStat.getDetail_list().add(acc);
			}
			
			accDateStat.setIncomesum(0.f);
			accDateStat.setPaymentsum(pay_sum);
			stat_list.add(accDateStat);
			Collections.sort(accDateStat.getDetail_list());
		}
				
		Collections.sort(stat_list);
		return stat_list;
	}

	@Override
	public List<AccDateStat> getDateStatTransfer(int user_id, Date date_from, Date date_to) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Map<String, Object> mapParam = new HashMap<>();
		mapParam.put("date_from", date_from == null ? null : sdf.format(date_from));
		mapParam.put("date_to", date_to == null ? null : sdf.format(date_to));
		mapParam.put("user_id", user_id);
		List<TransferDetailVO> list = accountVoMBDao.getTransferVo(mapParam);
		
		Map<String, List<TransferDetailVO>> map = new HashMap<>();
		//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		for (TransferDetailVO e : list){
			String d = sdf.format(e.getFsrq());
			if (null == map.get(d)){
				List<TransferDetailVO> tmp_list = new ArrayList<>();
				tmp_list.add(e);
				map.put(d, tmp_list);
			}
			else{
				map.get(d).add(e);
			}
		}
		
		//System.out.println(map);
		
		List<AccDateStat> stat_list = new ArrayList<>();
		for (String keyd : map.keySet()){
			AccDateStat accDateStat = new AccDateStat();
			
			try {
				accDateStat.setDate(sdf.parse(keyd));
			} catch (ParseException e) {
				logger.error(e.getMessage());
			}
			
			float transfersum = 0.f;
			for (TransferDetailVO acc : map.get(keyd)){
				transfersum += acc.getJe();
				accDateStat.getDetail_list().add(acc);
			}
			
			accDateStat.setIncomesum(0.f);
			accDateStat.setPaymentsum(0.f);
			accDateStat.setTransfersum(transfersum);
			stat_list.add(accDateStat);
			Collections.sort(accDateStat.getDetail_list());
		}
				
		Collections.sort(stat_list);
		
		return stat_list;
	}
}
