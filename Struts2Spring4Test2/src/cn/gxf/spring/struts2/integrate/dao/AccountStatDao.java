package cn.gxf.spring.struts2.integrate.dao;

import java.util.List;

import cn.gxf.spring.struts2.integrate.model.AccDateStat;

public interface AccountStatDao {
	public List<AccDateStat> getDateStat(int user_id, String nd, String yf);
}
