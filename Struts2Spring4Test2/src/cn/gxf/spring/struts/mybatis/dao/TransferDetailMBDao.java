package cn.gxf.spring.struts.mybatis.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.annotation.MapperScan;

import cn.gxf.spring.struts2.integrate.model.TransferDetail;

@MapperScan
public interface TransferDetailMBDao {
	public List<TransferDetail> getTransferDetailByUserId(int user_id);
	public List<TransferDetail> getDetailByPatchUuid(List<String> mxuuidList);
	public List<TransferDetail> getDetailByPatchAccuuid(List<String> accuuidList);
	public TransferDetail getTransferDetailByUuid(String mxuuid);
	public void addOne(TransferDetail transferDetail);
	public void updateOne(TransferDetail transferDetail);
	public void deleteOne(Map<String, Object> paramMap);
	public void deletePatch(List<String> mxuuidList);
}
