package cn.gxf.spring.quartz.job.dao;

import java.util.Map;


public interface AccountStatisticDao {
	
	public int updateUsersToProcess(); // ������Ҫͳ�Ƶ�users����ʱ������������
	public Map<String, String> getUsersIdNames(Integer start, Integer limit); // ֻ��ȡ����ҵ�����ݵ��û�
	// ��users�����ܴ�ʱ����ȡ�ᳬʱ
	//public Map<String, String> getUsersIdNamesAll();
}
