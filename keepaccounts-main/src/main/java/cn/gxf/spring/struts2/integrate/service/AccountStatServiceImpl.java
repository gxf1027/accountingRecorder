package cn.gxf.spring.struts2.integrate.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import cn.gxf.spring.struts.integrate.cache.StatDetailKeyGenerator;
import cn.gxf.spring.struts.mybatis.dao.AccountVoMBDao;
import cn.gxf.spring.struts2.integrate.dao.AccDetailVoDao;
import cn.gxf.spring.struts2.integrate.model.AccDateStat;
import cn.gxf.spring.struts2.integrate.model.AccountingDetailVO;
import cn.gxf.spring.struts2.integrate.model.IncomeDetailVO;
import cn.gxf.spring.struts2.integrate.model.PaymentDetailVO;
import cn.gxf.spring.struts2.integrate.model.TransferDetailVO;

@Service("accountStatService")
public class AccountStatServiceImpl implements AccountStatService{
	
    private Logger logger = LogManager.getLogger();

    
	@Autowired
	private AccDetailVoDao accDetailVoDao;
	
	@Autowired
	private AccountVoMBDao accountVoMBDao;
	
	@Autowired
	private DetailVoService detailVoService;
	
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	
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
		
		for (Map.Entry<String,List<AccountingDetailVO>> entry : map.entrySet()){
			AccDateStat accDateStat = new AccDateStat();
			
			String keyd = entry.getKey();
			List<AccountingDetailVO> accDetailVoList = entry.getValue();
			
			try {
				accDateStat.setDate(sdf.parse(keyd));
			} catch (ParseException e) {
				logger.error(e.getMessage());
			}
			
			float pay_sum = 0.f;
			float in_sum = 0.f;
			
			for (AccountingDetailVO acc : accDetailVoList){
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
		for (Map.Entry<String,List<AccountingDetailVO>> entry : map.entrySet()){
			AccDateStat accDateStat = new AccDateStat();
			
			String keyd = entry.getKey();
			List<AccountingDetailVO> accDetailVoList = entry.getValue();
			
			try {
				accDateStat.setDate(sdf.parse(keyd));
			} catch (ParseException e) {
				logger.error(e.getMessage());
			}
			
			float pay_sum = 0.f;
			float in_sum = 0.f;
			
			for (AccountingDetailVO acc : accDetailVoList){
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
	
	//@Cacheable(value="redisCacheStat", key="{#user_id, #date_from, #date_to, #root.method.name}")
	//@Cacheable(value="redisCacheStat")  // 使用<cache:annotation-driven ... key-generator="userKeyGenerator" />配置的自定义key生成器
//	@Cacheable(value="redisCacheStat", 
//			key="T(cn.gxf.spring.struts.integrate.cache.StatDetailKeyGenerator).detailAllPrefix+"
//					+ "#user_id+'_'"
//					+ "+T(cn.gxf.spring.struts.integrate.util.DateFomatTransfer).date2CompactString(#date_from)+'_'+"
//					+ "T(cn.gxf.spring.struts.integrate.util.DateFomatTransfer).date2CompactString(#date_to)")
	// 不再这里使用缓存，改为在detailVoService中使用
//	@Cacheable(value="redisCacheStat",
//				key="T(cn.gxf.spring.struts.integrate.cache.StatDetailKeyGenerator).generateKey(T(cn.gxf.spring.struts.integrate.cache.StatDetailKeyGenerator).detailAllPrefix, #user_id, #date_from, #date_to)")
	@Override
	public List<AccDateStat> getDateStatMB(int user_id, Date date_from , Date date_to){
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		List<PaymentDetailVO> listPayment = null;
		List<IncomeDetailVO> listIncome = null;
		List<TransferDetailVO> listTransfer = null;
		
		try {
			listPayment = detailVoService.getPaymentVo(user_id, date_from, date_to);
			listIncome = detailVoService.getIncomeVo(user_id, date_from, date_to);
			listTransfer = detailVoService.getTransferVo(user_id, date_from, date_to);
			
		} catch (Exception e) {
			logger.warn("getDateStatMB user [{}] with exception [{}]", user_id, e.getMessage());
			// 返回空
			return new ArrayList<>();
		}
		
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
		for (Map.Entry<String,List<AccountingDetailVO>> entry : map.entrySet()){
			AccDateStat accDateStat = new AccDateStat();
			
			String keyd = entry.getKey();
			List<AccountingDetailVO> accDetailVoList = entry.getValue();
			
			try {
				accDateStat.setDate(sdf.parse(keyd));
			} catch (ParseException e) {
				logger.error(e.getMessage());
			}
			
			float pay_sum = 0.f;
			float in_sum = 0.f;
			
			for (AccountingDetailVO acc : accDetailVoList){
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
	
	// 不再这里使用缓存，改为在detailVoService中使用
//	@CachePut(value="redisCacheStat",
//			key="T(cn.gxf.spring.struts.integrate.cache.StatDetailKeyGenerator).generateKey(T(cn.gxf.spring.struts.integrate.cache.StatDetailKeyGenerator).detailAllPrefix, #user_id, #date_from, #date_to)")
	@SuppressWarnings("unchecked")
	@Override
	public List<AccDateStat> getDateStatMBRefresh(int user_id, Date date_from , Date date_to){
		
		// 删除缓存
		Set<String> keys = new HashSet<String>();
		keys.add(StatDetailKeyGenerator.generateKey(StatDetailKeyGenerator.detailIncomeVoPrefix, user_id, date_from , date_to));
		keys.add(StatDetailKeyGenerator.generateKey(StatDetailKeyGenerator.detailPaymentVoPrefix, user_id, date_from , date_to));
		keys.add(StatDetailKeyGenerator.generateKey(StatDetailKeyGenerator.detailTransferVoPrefix, user_id, date_from , date_to));
		EvictDateStatRedis(keys);
		
		List<AccDateStat> list = this.getDateStatMB(user_id, date_from, date_to);
		
		return list;
	}

	// 对应删除getDateStatMB(int user_id, Date date_from , Date date_to)的缓存
	/*@CacheEvict(value="redisCacheStat", key="'getDateStatMB_'+#user_id+'_'+#date_from+'_'+#date_to")
	@Override
	public void EvictDateStatMB(int user_id, Date date_from , Date date_to){
		
	}*/
	
	
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
		for (Map.Entry<String,List<IncomeDetailVO>> entry : map.entrySet()){
			AccDateStat accDateStat = new AccDateStat();
			
			String keyd = entry.getKey();
			List<IncomeDetailVO> incomeDetailVoList = entry.getValue();
			
			try {
				accDateStat.setDate(sdf.parse(keyd));
			} catch (ParseException e) {
				logger.error(e.getMessage());
			}
			
			float in_sum = 0.f;
			
			for (IncomeDetailVO acc : incomeDetailVoList){

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
	//@Cacheable(value="statCache")  // 使用<cache:annotation-driven ... key-generator="userKeyGenerator" />配置的自定义key生成器
//	@Cacheable(value="redisCacheStat", 
//			key="T(cn.gxf.spring.struts.integrate.cache.StatDetailKeyGenerator).detailIncomePrefix+"
//					+ "#user_id+'_'+"
//					+ "T(cn.gxf.spring.struts.integrate.util.DateFomatTransfer).date2CompactString(#date_from)+'_'+"
//					+ "T(cn.gxf.spring.struts.integrate.util.DateFomatTransfer).date2CompactString(#date_to)")
	// 不再这里使用缓存，改为在detailVoService中使用
//	@Cacheable(value="redisCacheStat",
//			key="T(cn.gxf.spring.struts.integrate.cache.StatDetailKeyGenerator).generateKey(T(cn.gxf.spring.struts.integrate.cache.StatDetailKeyGenerator).detailIncomePrefix, #user_id, #date_from, #date_to)")
	@Override
	public List<AccDateStat> getDateStatIncome(int user_id, Date date_from , Date date_to) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		List<IncomeDetailVO> list = null;
		try {
			list = detailVoService.getIncomeVo(user_id, date_from, date_to);
		} catch (Exception e) {
			logger.error("getDateStatMB user:" + user_id + " getIncomeVo Error.");
			return new ArrayList<>();
		}
		
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
		for (Map.Entry<String,List<IncomeDetailVO>> entry : map.entrySet()){
			AccDateStat accDateStat = new AccDateStat();
			
			String keyd = entry.getKey();
			List<IncomeDetailVO> incomeDetailVoList = entry.getValue();
			
			try {
				accDateStat.setDate(sdf.parse(keyd));
			} catch (ParseException e) {
				logger.error(e.getMessage());
			}
			
			float in_sum = 0.f;
			
			for (IncomeDetailVO acc : incomeDetailVoList){

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
	public List<AccDateStat> getDateStatIncomeRefresh(int user_id, Date date_from, Date date_to){
		// 删除缓存
		Set<String> keys = new HashSet<String>();
		keys.add(StatDetailKeyGenerator.generateKey(StatDetailKeyGenerator.detailIncomeVoPrefix, user_id, date_from , date_to));
		EvictDateStatRedis(keys);
		
		List<AccDateStat> list = this.getDateStatIncome(user_id, date_from, date_to);
		
		return list;
	}

	// 对应删除getDateStatMB(int user_id, Date date_from , Date date_to)的缓存
	/*@CacheEvict(value="redisCacheStat", key="'getDateStatIncome_'+#user_id+'_'+#date_from+'_'+#date_to")
	@Override
	public void EvictDateStatIncome(int user_id, Date date_from , Date date_to){
		
	}*/
	
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
		for (Map.Entry<String,List<PaymentDetailVO>> entry : map.entrySet()){
			AccDateStat accDateStat = new AccDateStat();
			
			String keyd = entry.getKey();
			List<PaymentDetailVO> paymentDetailVoList = entry.getValue();
			
			try {
				accDateStat.setDate(sdf.parse(keyd));
			} catch (ParseException e) {
				logger.error(e.getMessage());
			}
			
			float pay_sum = 0.f;
			
			for (PaymentDetailVO acc : paymentDetailVoList){

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
	//@Cacheable(value="statCache")  // 使用<cache:annotation-driven ... key-generator="userKeyGenerator" />配置的自定义key生成器
//	@Cacheable(value="redisCacheStat", 
//			key="T(cn.gxf.spring.struts.integrate.cache.StatDetailKeyGenerator).detailPaymentPrefix+"
//					+ "#user_id+'_'+"
//					+ "T(cn.gxf.spring.struts.integrate.util.DateFomatTransfer).date2CompactString(#date_from)+'_'+"
//					+ "T(cn.gxf.spring.struts.integrate.util.DateFomatTransfer).date2CompactString(#date_to)")
	// 不再这里使用缓存，改为在detailVoService中使用
//	@Cacheable(value="redisCacheStat",
//			key="T(cn.gxf.spring.struts.integrate.cache.StatDetailKeyGenerator).generateKey(T(cn.gxf.spring.struts.integrate.cache.StatDetailKeyGenerator).detailPaymentPrefix, #user_id, #date_from, #date_to)")
	@Override
	public List<AccDateStat> getDateStatPayment(int user_id, Date date_from, Date date_to){
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		List<PaymentDetailVO> list = null;
		try {
			list = detailVoService.getPaymentVo(user_id, date_from, date_to);
		} catch (Exception e) {
			logger.error("getDateStatMB user:" + user_id + " getPaymentVo Error.");
			return new ArrayList<>();
		}
		
		
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
		for (Map.Entry<String,List<PaymentDetailVO>> entry : map.entrySet()){
			AccDateStat accDateStat = new AccDateStat();
			
			String keyd = entry.getKey();
			List<PaymentDetailVO> paymentDetailVoList = entry.getValue();
			
			try {
				accDateStat.setDate(sdf.parse(keyd));
			} catch (ParseException e) {
				logger.error(e.getMessage());
			}
			
			float pay_sum = 0.f;
			
			for (PaymentDetailVO acc : paymentDetailVoList){

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
	public List<AccDateStat> getDateStatPaymentRefresh(int user_id, Date date_from, Date date_to){
		// 删除缓存
		Set<String> keys = new HashSet<String>();
		keys.add(StatDetailKeyGenerator.generateKey(StatDetailKeyGenerator.detailPaymentVoPrefix, user_id, date_from , date_to));
		EvictDateStatRedis(keys);
		
		List<AccDateStat> list = this.getDateStatPayment(user_id, date_from, date_to);
		
		return list;
	}
	
	/*@CacheEvict(value="redisCacheStat", key="'getDateStatPayment_'+#user_id+'_'+#date_from+'_'+#date_to")
	@Override
	public void EvictDateStatPayment(int user_id, Date date_from , Date date_to){
		
	}*/
	
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
		for (Map.Entry<String,List<TransferDetailVO>> entry : map.entrySet()){
			AccDateStat accDateStat = new AccDateStat();
			
			String keyd = entry.getKey();
			List<TransferDetailVO> transDetailVoList = entry.getValue();
			
			try {
				accDateStat.setDate(sdf.parse(keyd));
			} catch (ParseException e) {
				logger.error(e.getMessage());
			}
			
			float transfersum = 0.f;
			for (TransferDetailVO acc : transDetailVoList){
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
	//@Cacheable(value="statCache")  // 使用<cache:annotation-driven ... key-generator="userKeyGenerator" />配置的自定义key生成器
//	@Cacheable(value="redisCacheStat", 
//			key="T(cn.gxf.spring.struts.integrate.cache.StatDetailKeyGenerator).detailTransferPrefix+"
//					+ "#user_id+'_'+"
//					+ "T(cn.gxf.spring.struts.integrate.util.DateFomatTransfer).date2CompactString(#date_from)+'_'+"
//					+ "T(cn.gxf.spring.struts.integrate.util.DateFomatTransfer).date2CompactString(#date_to)")
	// 不再这里使用缓存，改为在detailVoService中使用
//	@Cacheable(value="redisCacheStat",
//			key="T(cn.gxf.spring.struts.integrate.cache.StatDetailKeyGenerator).generateKey(T(cn.gxf.spring.struts.integrate.cache.StatDetailKeyGenerator).detailTransferPrefix, #user_id, #date_from, #date_to)")
	@Override
	public List<AccDateStat> getDateStatTransfer(int user_id, Date date_from, Date date_to) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		List<TransferDetailVO> list = null;
		try {
			list = detailVoService.getTransferVo(user_id, date_from, date_to);
		} catch (Exception e) {
			logger.error("getDateStatMB user:" + user_id + " getTransferVo Error.");
			return new ArrayList<>();
		}
		
		
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
		for (Map.Entry<String,List<TransferDetailVO>> entry : map.entrySet()){
			AccDateStat accDateStat = new AccDateStat();
			
			String keyd = entry.getKey();
			List<TransferDetailVO> transDetailVoList = entry.getValue();
			
			try {
				accDateStat.setDate(sdf.parse(keyd));
			} catch (ParseException e) {
				logger.error(e.getMessage());
			}
			
			float transfersum = 0.f;
			for (TransferDetailVO acc : transDetailVoList){
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
	
	@Override
	public List<AccDateStat> getDateStatTransferRefresh(int user_id, Date date_from, Date date_to){
		// 删除缓存
		Set<String> keys = new HashSet<String>();
		keys.add(StatDetailKeyGenerator.generateKey(StatDetailKeyGenerator.detailTransferVoPrefix, user_id, date_from , date_to));
		EvictDateStatRedis(keys);
		
		List<AccDateStat> list = this.getDateStatTransfer(user_id, date_from, date_to);
		
		return list;
	}
	
	/*@CacheEvict(value="redisCacheStat", key="'getDateStatTransfer_'+#user_id+'_'+#date_from+'_'+#date_to")
	@Override
	public void EvictDateStatTransfer(int user_id, Date date_from , Date date_to){
		
	}*/
	
	//  stringRedisTemplate.keys方法不能用在生产上！！！有性能问题。
	// 使用redis中方法删除缓存, 由于某些情形下很难根据user_id+date_from+date_to来删除缓存，所以考虑将user_id有关（不论哪个时间段）的缓存一起清除
	/*@Override
	public void EvictByUserId(int user_id){
		
		// 清除“所有明显”中的有关user_id的缓存（不论哪个时间段）
		Set<String> keys = stringRedisTemplate.keys("getDateStatMB_"+user_id+"*");
		stringRedisTemplate.delete(keys);
		// 清除“收入”中的有关user_id的缓存（不论哪个时间段）
		keys = stringRedisTemplate.keys("getDateStatIncome_"+user_id+"*");
		stringRedisTemplate.delete(keys);
		// 清除“支出”中的有关user_id的缓存（不论哪个时间段）
		keys = stringRedisTemplate.keys("getDateStatPayment_"+user_id+"*");
		stringRedisTemplate.delete(keys);
		// 清除“转账”中的有关user_id的缓存（不论哪个时间段）
		keys = stringRedisTemplate.keys("getDateStatTransfer_"+user_id+"*");
		stringRedisTemplate.delete(keys);
	}*/
	
	
	@Override
	public void EvictDateStatRedis(Set<String> keys){
		if (keys == null || keys.size() == 0){
			return;
		}
		
		try {
			this.stringRedisTemplate.delete(keys); // 从源码看使用了pipeline
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
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


