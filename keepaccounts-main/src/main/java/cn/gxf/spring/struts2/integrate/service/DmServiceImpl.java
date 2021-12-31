package cn.gxf.spring.struts2.integrate.service;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import cn.gxf.spring.struts.integrate.cache.EhCacheUtils;
import cn.gxf.spring.struts.integrate.cache.SpringCacheKeyGenerator;
import cn.gxf.spring.struts2.integrate.dao.AccountBookDao;
import cn.gxf.spring.struts2.integrate.dao.DmUtilDao;
import cn.gxf.spring.struts2.integrate.dao.DmUtilDaoImplJdbc;
import cn.gxf.spring.struts2.integrate.model.AccountBook;
import cn.gxf.spring.struts2.integrate.model.AccountBookVO;
import cn.gxf.spring.struts2.integrate.model.BookTypeSummary;
import cn.gxf.spring.struts2.integrate.model.DmPaymentDl;
import cn.gxf.spring.struts2.integrate.model.DmPaymentXl;

@Service
public class DmServiceImpl implements DmService {	
	
    private Logger logger = LogManager.getLogger();

	@Autowired
	private DmUtilDao dmUtilDao;
	
	@Autowired
	private AccountBookDao accountBookDao;
	
	@Autowired
	private SpringCacheKeyGenerator keyGenerator;
	
	@Autowired
 	private EhCacheUtils cacheUtils;

	/*@Cacheable(value="dmCache" , key="#root.method.name")*/
	@Cacheable(value="dmCache")
	@Override
	public Map<DmPaymentDl, List<DmPaymentXl>> getPaymentDlXlDzb() {
		List<DmPaymentDl> listDl = dmUtilDao.getPaymentDlList();
		List<DmPaymentXl> listXl = dmUtilDao.getPaymentXlList();
		Map<DmPaymentDl, List<DmPaymentXl>> map = new TreeMap<>();
		
		for (DmPaymentDl dl : listDl){
			
			List<DmPaymentXl> dzxl = new ArrayList<>();
			for (DmPaymentXl xl : listXl){
				if (xl.getDl_dm().equals(dl.getDl_dm())){
					dzxl.add(xl);
				}	
			}
			
			map.put(dl, dzxl);
		}
			
		return map;
	}

	// Cacheable���ܷŵ�Dao��getZhInfo�ϣ���Ϊ�����Service��Խ�������޸ģ�
	// �������Dao.getZhInfo��ÿ������ҳ�����books����������"����..."��ѡ�����ˢ�¼��ξ����Ӽ���
	@Cacheable(value="dmCache")
	@Override
	public List<AccountBook> getZhInfo(int user_id) {
		
		List<AccountBook> books = accountBookDao.getZhInfo(user_id);
		
		for(AccountBook book : books){
			book.setZh_mc(book.getZh_mc() + " | " + String.format("%.2f",book.getYe()) + "Ԫ");
		}
		
		AccountBook b = new AccountBook();
		b.setZh_dm("z999");
		b.setZh_mc("����...");
		books.add(b);
		
		return books;
	}
	
	@Cacheable(value="dmCache")
	@Override
	public Map<String, List<AccountBook>> getZhInfoMap(int user_id) {
		
		List<AccountBook> books = accountBookDao.getZhInfo(user_id);
		Map<String, List<AccountBook>> bookMaps = new HashMap<String, List<AccountBook>>();
		Map<String, String> zhlxdm = this.getZhLx();
		
		for(AccountBook book : books){
			book.setZh_mc(book.getZh_mc() + " | " + String.format("%.2f",book.getYe()) + "Ԫ");
			String bookKey = zhlxdm.get(book.getZhlx_dm()); // ���˻�����������Ϊkey
			List<AccountBook> bookList = bookMaps.get(bookKey);
			if (null == bookList){
				bookList = new ArrayList<AccountBook>();
				bookList.add(book);
				bookMaps.put(bookKey, bookList);
			}else{
				bookMaps.get(bookKey).add(book);
			}
			
		}

		return bookMaps;
	}
	
//	@Cacheable(value="dmCache")
//	@Override
//	public Map<String, List<AccountBook>> getZhInfoMapSimple(int user_id) {
//		
//		List<AccountBook> books = accountBookDao.getZhInfo(user_id);
//		Map<String, List<AccountBook>> bookMaps = new HashMap<String, List<AccountBook>>();
//		Map<String, String> zhlxdm = this.getZhLx();
//		
//		for(AccountBook book : books){
//			String bookKey = zhlxdm.get(book.getZhlx_dm()); // ���˻�����������Ϊkey
//			List<AccountBook> bookList = bookMaps.get(bookKey);
//			if (null == bookList){
//				bookList = new ArrayList<AccountBook>();
//				bookList.add(book);
//				bookMaps.put(bookKey, bookList);
//			}else{
//				bookMaps.get(bookKey).add(book);
//			}
//			
//		}
//
//		return bookMaps;
//	}

	@Cacheable(value="dmCache")
	@Override
	public Map<BookTypeSummary, List<AccountBook>> getZhInfoMap4FrontPage(int user_id) {
		
		List<AccountBook> books = accountBookDao.getZhInfo(user_id);
		Map<String, List<AccountBook>> bookMaps = new HashMap<String, List<AccountBook>>();
		Map<String, String> zhlxdm = this.getZhLx();
		
		for(AccountBook book : books){
			String bookKey = book.getZhlx_dm();
			List<AccountBook> bookList = bookMaps.get(bookKey);
			if (null == bookList){
				bookList = new ArrayList<AccountBook>();
				bookList.add(book);
				bookMaps.put(bookKey, bookList);
			}else{
				bookMaps.get(bookKey).add(book);
			}
			
		}
		
		Map<BookTypeSummary, List<AccountBook>> bookSumMap = new TreeMap<BookTypeSummary, List<AccountBook>>();
		for(String typeid : bookMaps.keySet())
		{
			float sum = 0.f;
			for (AccountBook book:bookMaps.get(typeid))
			{
				sum += book.getYe();
			}
			bookSumMap.put(new BookTypeSummary(typeid, zhlxdm.get(typeid), sum), bookMaps.get(typeid));
		}

		return bookSumMap;
	}
	
	@Cacheable(value="dmCache")
	@Override
	public List<AccountBook> getZhInfoSimple(int user_id) {
		
		List<AccountBook> books = accountBookDao.getZhInfo(user_id);		
		return books;
	}
	
	@Cacheable(value="dmCache")
	@Override
	public List<AccountBookVO> getZhInfoVO(int user_id) {

		List<AccountBookVO> books = accountBookDao.getZhInfoVO(user_id);
		return books;
	}
	
	@Override
	public void removeZhInfoCache(int user_id){
				
		try {
 			
 			for (Method m : this.getClass().getDeclaredMethods()){
 				if (!m.getName().startsWith("getZhInfo")){
 					continue;
 				}
 				
 				String key = (String) keyGenerator.generate(this, m, user_id);
 				this.cacheUtils.remove("dmCache", key);
 			}
 			
 		} catch (SecurityException e) {
 			logger.warn("remove 'ZhInfo' cache with exception [{}]", e.getMessage());
 		}
	}
	
	
	@CacheEvict(value="dmCache", key="{#accBook.user_id, 'getZhInfo'}", allEntries=false)
	@Override
	public void saveAccBook(AccountBook accBook) {
		
		accountBookDao.saveOne(accBook);
	}


	@Cacheable(value="dmCache", key="#root.method.name")
	@Override
	public Map<String, String> getIncomeLb() {
		
		return dmUtilDao.getIncomeLb();
	}
	

	@CacheEvict(value="dmCache")
	@Override
	public void doCacheEvict() {
		// TODO Auto-generated method stub
		
	}

	@Cacheable(value="dmCache")
	@Override
	public Map<String, String> getZhLx() {
		
		return dmUtilDao.getZhHuLx();
	}

	@Override
	public Map<String, String> getPaymentDl() {
		
		return dmUtilDao.getPaymentDl();
	}

	@Override
	public Map<String, String> getPaymentXl() {
		
		return dmUtilDao.getPaymentXl();
	}

	// Ϊ�˻��治�ظ�ʹ��getOutgoCategoryCommon+getOutgoCategory(Integer user_id)����������
	// �ֱ��ȡ�������Ĺ��ò��ֺ�ĳ���û����ض�����
	@Cacheable(value="dmCache")
	@Override
	public Map<String, String> getOutgoCategoryCommon(){
		Map<String, Map<String, String>> userCat = dmUtilDao.getOutgoCategory();
		if (userCat == null){
			return new HashMap<String, String>();
		}
		
		return userCat.get(DmUtilDaoImplJdbc.common);
	}

	@Cacheable(value="dmCache")
	@Override
	public Map<String, String> getOutgoCategory(Integer user_id) {
		
		Map<String, Map<String, String>> userCat = dmUtilDao.getOutgoCategory();
		if (userCat == null){
			return new HashMap<String, String>();
		}
		
		return userCat.get(user_id.toString());
	}

	@Cacheable(value="dmCache")
	@Override
	public Map<String, String> getTransferTypeCommon(){
		Map<String, Map<String, String>> userTransType = dmUtilDao.getTransferType();
		if (userTransType == null){
			return new HashMap<String, String>();
		}
		
		return userTransType.get(DmUtilDaoImplJdbc.common);
	}
	
	
	@Cacheable(value="dmCache")
	@Override
	public Map<String, String> getTransferType(Integer user_id) {
		
		Map<String, Map<String, String>> userTransType = dmUtilDao.getTransferType();
		if (userTransType == null){
			return new HashMap<String, String>();
		}
		
		return userTransType.get(user_id.toString());
	}
	
	@Cacheable(value="dmCache")
	@Override
	public Map<String, String> getFundType() {
		
		return dmUtilDao.getFundType();
	}
	
	@Cacheable(value="dmCache")
	@Override
	public Map<String, String> getFinancialProdType(){
		
		return dmUtilDao.getFinancialProdType();
	}
	
	@Cacheable(value="dmCache")
	@Override
	public Map<String, String> getYhInfo() {
		
		return dmUtilDao.getYh();
	}
	
	
}
