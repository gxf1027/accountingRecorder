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
import cn.gxf.spring.struts2.integrate.dao.DmUtilDaoImplJdbc;
import cn.gxf.spring.struts2.integrate.model.AccountBook;
import cn.gxf.spring.struts2.integrate.model.DmPaymentDl;
import cn.gxf.spring.struts2.integrate.model.DmPaymentXl;

@Service
public class DmServiceImpl implements DmService {	
	@Autowired
	private DmUtilDao dmUtilDao;
	
	@Autowired
	private AccountBookDao accountBookDao;

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
	@Cacheable(value="dmCache", key="{#user_id, #root.method.name}")
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
