package cn.gxf.spring.struts2.integrate.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.gxf.spring.struts.mybatis.dao.AccountVoMBDao;
import cn.gxf.spring.struts2.integrate.dao.AccDetailVoDao;
import cn.gxf.spring.struts2.integrate.model.AccDateStat;
import cn.gxf.spring.struts2.integrate.model.AccountingDetailVO;
import cn.gxf.spring.struts2.integrate.model.IncomeDetailVO;
import cn.gxf.spring.struts2.integrate.model.PaymentDetailVO;
import cn.gxf.spring.struts2.integrate.model.TransferDetailVO;

@Service("accountStatService")
public class AccountStatServiceImpl implements AccountStatService{
	
	@Autowired
	private AccDetailVoDao accDetailVoDao;
	
	@Autowired
	private AccountVoMBDao accountVoMBDao;
	
	@Override
	public List<AccDateStat> getDateStat(int user_id, String nd, String yf)  {
		
		// 获取 比如2017年10月的所有支出、收入、转账
		List<AccountingDetailVO> list = accDetailVoDao.getAccDetailVoAll(user_id, nd, yf);
		
		// 将日期作为key进行统计，同个日期的放入list中
		Map<String, List<AccountingDetailVO>> map = new TreeMap<>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
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
				e.printStackTrace();
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
	public List<AccDateStat> getDateStatMB(int user_id, String nd, String yf) {
		String dd = nd+"-" + yf + "-01";
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("user_id", user_id);
		paramMap.put("dd", dd);
		
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
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
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
				e.printStackTrace();
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
	
	//@Cacheable(value="statCache", key="{#user_id, #date_from, #date_to, #root.method.name}")
	@Cacheable(value="statCache")  // 使用<cache:annotation-driven ... key-generator="userKeyGenerator" />配置的自定义key生成器 
	@Override
	public List<AccDateStat> getDateStatMB(int user_id, Date date_from , Date date_to){
		
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
				e.printStackTrace();
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
	public List<AccDateStat> getDateStatIncome(int user_id, String nd, String yf) {
		String dd = nd+"-" + yf + "-01";
		Map<String, Object> mapParam = new HashMap<>();
		mapParam.put("dd", dd);
		mapParam.put("user_id", user_id);
		List<IncomeDetailVO> list = accountVoMBDao.getIncomeVo(mapParam);
		
		Map<String, List<IncomeDetailVO>> map = new HashMap<>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
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
				e.printStackTrace();
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

	//@Cacheable(value="statCache",  key="{#user_id, #date_from, #date_to, #root.method.name}")
	@Cacheable(value="statCache")  // 使用<cache:annotation-driven ... key-generator="userKeyGenerator" />配置的自定义key生成器 
	@Override
	public List<AccDateStat> getDateStatIncome(int user_id, Date date_from , Date date_to) {
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
				e.printStackTrace();
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
	public List<AccDateStat> getDateStatPayment(int user_id, String nd, String yf) {
		String dd = nd+"-" + yf + "-01";
		Map<String, Object> mapParam = new HashMap<>();
		mapParam.put("dd", dd);
		mapParam.put("user_id", user_id);
		List<PaymentDetailVO> list = accountVoMBDao.getPaymentVo(mapParam);
		
		
		Map<String, List<PaymentDetailVO>> map = new HashMap<>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
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
				// TODO Auto-generated catch block
				e.printStackTrace();
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
	
	//@Cacheable(value="statCache",  key="{#user_id, #date_from, #date_to, #root.method.name}")
	@Cacheable(value="statCache")  // 使用<cache:annotation-driven ... key-generator="userKeyGenerator" />配置的自定义key生成器 
	@Override
	public List<AccDateStat> getDateStatPayment(int user_id, Date date_from, Date date_to){
		System.out.println("getDateStatPayment...........");
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
				// TODO Auto-generated catch block
				e.printStackTrace();
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
	public List<AccDateStat> getDateStatTransfer(int user_id, String nd, String yf) {
		String dd = nd+"-" + yf + "-01";
		Map<String, Object> mapParam = new HashMap<>();
		mapParam.put("dd", dd);
		mapParam.put("user_id", user_id);
		List<TransferDetailVO> list = accountVoMBDao.getTransferVo(mapParam);
		
		
		Map<String, List<TransferDetailVO>> map = new HashMap<>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
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
				// TODO Auto-generated catch block
				e.printStackTrace();
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
	
	
	
	//@Cacheable(value="statCache",  key="{#user_id, #date_from, #date_to, #root.method.name}")
	@Cacheable(value="statCache")  // 使用<cache:annotation-driven ... key-generator="userKeyGenerator" />配置的自定义key生成器 
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
				// TODO Auto-generated catch block
				e.printStackTrace();
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
	
	
	/*
	@Override
	public List<AccDateStat> getDateStat(int user_id, String nd, String yf)  {
		
		// 获取 比如2017年10月的所有支出和收入
		List<AccDetailVO> list = accDetailVoDao.getAccDetailVo(user_id, nd, yf);
		
		// 将日期作为key进行统计，同个日期的放入list中
		Map<String, List<AccDetailVO>> map = new HashMap<>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd");
		for (AccDetailVO accDetailVo : list){
			String d = sdf.format(accDetailVo.getFsrq());
			if (null == map.get(d)){
				List<AccDetailVO> tmp_list = new ArrayList<>();
				tmp_list.add(accDetailVo);
				map.put(d, tmp_list);
			}
			else{
				map.get(d).add(accDetailVo);
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
				e.printStackTrace();
			}
			
			float pay_sum = 0.f;
			float in_sum = 0.f;
			
			for (AccDetailVO acc : map.get(keyd)){
				if (acc.getType() == 1) // 收入
				{
					in_sum += acc.getJe();
				}
				else if (acc.getType() == 2) // 支出
				{
					pay_sum += acc.getJe();
				}
				
				accDateStat.getDetail_list().add(acc);
			}
			
			accDateStat.setIncomesum(in_sum);
			accDateStat.setPaymentsum(pay_sum);
			stat_list.add(accDateStat);
		}
		return stat_list;
	}*/
}


