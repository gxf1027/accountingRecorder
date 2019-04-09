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
	public List<FinancialProductDetail> getFinancialProductDetailUnreturned(Integer userId); // ��û�й��������롱����Ʋ�Ʒ
	public List<FinancialProductDetail> queryFinancialProductDetailByEndDate(@Param("date_from") Date date_from, @Param("date_to") Date date_to); // ��ѯ����ʱ����ָ��ʱ���֮�����Ʋ�Ʒ
	
	public void addOne(FinancialProductDetail financialProductDetail);
	public void updateOne(FinancialProductDetail financialProductDetail); // ͨ����Ʒ��transferUuid������Ʋ�Ʒ
	public void addAmount(@Param("uuid") String uuid, @Param("addAmount") float addAmount); // ׷�ӽ��
	public void setRedeem(@Param("uuid") String uuid, @Param("redeemUuid") String redeemUuid); // ������غ͹����ġ�ת�ˡ�
	public void cancelRedeem(String redeemUuid); // ����ת�ˡ���ɾ��ʱ������ת�˵�uuid������Ʋ�Ʒ����غ͹�����Ϣ
	public void setRealReturn(@Param("uuid") String uuid, @Param("returnUuid") String returnUuid, @Param("realReturn") float realReturn); // ��������͹����ġ����롱
	public void cancelRealReturn(String returnUuid); // �������롱��ɾ��ʱ�����ݡ����롱��uuid������Ʋ�Ʒ������͹�����Ϣ
	public void deleteOne(String transferuuid);
	public void deletePatch(List<String> uuidList);
}
