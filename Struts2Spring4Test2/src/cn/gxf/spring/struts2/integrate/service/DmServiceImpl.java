package cn.gxf.spring.struts2.integrate.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import cn.gxf.spring.struts2.integrate.dao.AccountBookDao;
import cn.gxf.spring.struts2.integrate.dao.DmUtilDao;
import cn.gxf.spring.struts2.integrate.model.AccountBook;
import cn.gxf.spring.struts2.integrate.model.DmPaymentDl;
import cn.gxf.spring.struts2.integrate.model.DmPaymentXl;

@Service
public class DmServiceImpl implements DmService {
	@Autowired
	private DmUtilDao dmUtilDao;
	
	@Autowired
	private AccountBookDao accountBookDao;

	@Cacheable(value="dmCache" , key="#root.method.name")
	/*@Cacheable(value="dmCache")*/
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

	// Cacheable不能放到Dao的getZhInfo上，因为在这个Service里对结果做了修改，
	// 如果放在Dao.getZhInfo上每次属性页面会向books缓存中增加"新增..."的选项，连续刷新几次就增加几个
	@Cacheable(value="dmCache", key="{#user_id, #root.method.name}")
	@Override
	public List<AccountBook> getZhInfo(int user_id) {
		
		List<AccountBook> books = accountBookDao.getZhInfo(user_id);
		
		for(AccountBook book : books){
			book.setZh_mc(book.getZh_mc() + " | " + String.format("%.2f",book.getYe()) + "元");
		}
		
		AccountBook b = new AccountBook();
		b.setZh_dm("z999");
		b.setZh_mc("新增...");
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
			book.setZh_mc(book.getZh_mc() + " | " + String.format("%.2f",book.getYe()) + "元");
			String bookKey = zhlxdm.get(book.getZhlx_dm()); // 以账户类别的名称作为key
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

	
	
	@Cacheable(value="dmCache", key="{#user_id, #root.method.name}")
	@Override
	public List<AccountBook> getZhInfoSimple(int user_id) {
		
		List<AccountBook> books = accountBookDao.getZhInfo(user_id);		
		return books;
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

	@Cacheable(value="dmCache")
	@Override
	public Map<String, String> getOutgoCategory(Integer user_id) {
		
		return dmUtilDao.getOutgoCategory(user_id);
	}

	@Cacheable(value="dmCache")
	@Override
	public Map<String, String> getTransferType(Integer user_id) {
		
		return dmUtilDao.getTransferType(user_id);
	}
	
	@Cacheable(value="dmCache")
	@Override
	public Map<String, String> getFundType() {
		
		return dmUtilDao.getFundType();
	}
	
}
