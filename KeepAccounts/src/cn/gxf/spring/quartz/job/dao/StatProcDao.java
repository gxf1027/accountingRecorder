package cn.gxf.spring.quartz.job.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

@MapperScan
public interface StatProcDao {

	public void insertProcTime(@Param("proc_time") Date proc_time, @Param("user_id") int user_id);
	public void updateProcTime(@Param("proc_time") Date proc_time, @Param("user_id") int user_id);
	public Date getLastProcTime(@Param("user_id") int user_id);
	
	public int isNewIncomeDataExists(Map<String, Object> params);
	public int isNewPaymentDataExists(Map<String, Object> params);
	public List<String> queryMonthsHaveNewIncomeData(Map<String, Object> params);
	public List<String> queryMonthsHaveNewPaymentData(Map<String, Object> params);
	public List<String> queryMonthsHaveNewData(Map<String, Object> params);
}
