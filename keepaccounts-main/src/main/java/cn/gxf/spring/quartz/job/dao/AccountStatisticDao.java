package cn.gxf.spring.quartz.job.dao;

import java.util.Map;


public interface AccountStatisticDao {
	
	public int getUsersNumHavingData(); // ��Ҫ�����user(���ϴ�stat����������ݵ�)������
	public Map<String, String> getUsersIdNames(Integer start, Integer limit); // ֻ��ȡ����ҵ�����ݵ��û�
	
	// ��users�����ܴ�ʱ����ȡ�ᳬʱ
	//public Map<String, String> getUsersIdNamesAll();
}
