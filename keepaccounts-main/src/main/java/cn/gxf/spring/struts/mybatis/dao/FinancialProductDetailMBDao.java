package cn.gxf.spring.struts.mybatis.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import cn.gxf.spring.struts2.integrate.model.FinancialProductDetail;


@MapperScan
public interface FinancialProductDetailMBDao {
	public FinancialProductDetail getFinancialProductDetailByUuid(String uuid);
	public FinancialProductDetail getFinancialProductDetailByTransferUuid(String uuid);
	public FinancialProductDetail getFinancialProductDetailByRedeemUuid(String uuid);
	public FinancialProductDetail getFinancialProductDetailByReturnUuid(String uuid);
	public List<FinancialProductDetail> getFinancialProductDetailByUserId(Integer userId);
	public List<FinancialProductDetail> getFinancialProductDetailUnreturned(Integer userId); // 还没有关联“收入”的理财产品
	public List<FinancialProductDetail> queryFinancialProductDetailByEndDate(@Param("date_from") Date date_from, @Param("date_to") Date date_to); // 查询到期时间在指定时间段之间的理财产品
	
	public void addOne(FinancialProductDetail financialProductDetail);
	public void updateOne(FinancialProductDetail financialProductDetail); // 通过产品的transferUuid更新理财产品
	public void addAmount(@Param("uuid") String uuid, @Param("addAmount") float addAmount); // 追加金额
	public void setRedeem(@Param("uuid") String uuid, @Param("redeemUuid") String redeemUuid); // 设置赎回和关联的“转账”
	public void cancelRedeem(String redeemUuid); // 当“转账”被删除时，根据转账的uuid撤销理财产品的赎回和关联信息
	public void setRealReturn(@Param("uuid") String uuid, @Param("returnUuid") String returnUuid, @Param("realReturn") float realReturn); // 设置收益和关联的“收入”
	public void cancelRealReturn(String returnUuid); // 当“收入”被删除时，根据“收入”的uuid撤销理财产品的收益和关联信息
	public void deleteOne(String transferuuid);
	public void deletePatch(List<String> uuidList);
}
