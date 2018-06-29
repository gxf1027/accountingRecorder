package cn.gxf.spring.struts.mybatis.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import cn.gxf.spring.struts2.integrate.model.StatByCategory;
import cn.gxf.spring.struts2.integrate.model.StatByMonth;

@MapperScan
public interface StatNdYfMBDao {
	public void procAccStatByNd(@Param("nd") String nd,@Param("user_id") Integer user_id);
	public void procAccStatThisMonth(@Param("user_id") Integer user_id);
	public List<StatByMonth> getNdYfStat(@Param("nd") String nd,@Param("user_id") Integer user_id);
	public List<StatByCategory> getPaymentStatOnDl(@Param("nd") String nd,@Param("user_id") Integer user_id);
	public List<StatByCategory> getIncomeStatOnSrlb(@Param("nd") String nd,@Param("user_id") Integer user_id);
	
	// 更新timedtask_acc_stat表中时间
	public Date getLastProcTime(@Param("user_id") int user_id);
	public void insertProcTime(@Param("proc_time") Date proc_time, @Param("user_id") int user_id);
	public void updateProcTime(@Param("proc_time") Date proc_time, @Param("user_id") int user_id);
}
