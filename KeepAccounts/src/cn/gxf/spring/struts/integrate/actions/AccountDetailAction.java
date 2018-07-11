package cn.gxf.spring.struts.integrate.actions;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

import cn.gxf.spring.struts2.integrate.model.AccountObject;
import cn.gxf.spring.struts2.integrate.model.AccountingDetail;
import cn.gxf.spring.struts2.integrate.model.IncomeDetail;
import cn.gxf.spring.struts2.integrate.model.PaymentDetail;
import cn.gxf.spring.struts2.integrate.model.TransferDetail;
import cn.gxf.spring.struts2.integrate.service.DetailAccountUnivServiceImpl;

// 2017-11-18增加，用于处理在查询页面批量删除各种类型的记录
public class AccountDetailAction extends ActionSupport implements Preparable{
	private static final long serialVersionUID = -7337058723381136118L;

	private List<String> accuuidList;
	private String mxuuidToEdit;
	private Date date_from;
	private Date date_to;
	
	@Autowired
	private DetailAccountUnivServiceImpl<TransferDetail> detailAccountUnivServiceImpl;
	
	// 用于处理在查询页面批量删除各种类型的记录
	public String delPatch(){
		List<AccountingDetail> list = detailAccountUnivServiceImpl.getAccountDetailByPatchAccuuid(accuuidList);
		detailAccountUnivServiceImpl.deletePatchByAccuuid(list);
		return "delOk";
	}
	
	// 只做分发
	public String editShow(){
		System.out.println(accuuidList.get(0));
		AccountObject item = detailAccountUnivServiceImpl.getOneItem(accuuidList.get(0));
		if (item instanceof IncomeDetail) {
			this.mxuuidToEdit = item.getMxuuid();
			return "editIncome";
		}else if (item instanceof PaymentDetail){
			this.mxuuidToEdit = item.getMxuuid();
			return "editPayment";
		}else if (item instanceof TransferDetail){
			this.mxuuidToEdit = item.getMxuuid();
			return "editTransfer";
		}
		
		return null;
	}
	
	public List<String> getAccuuidList() {
		return accuuidList;
	}
	
	public void setAccuuidList(List<String> accuuidList) {
		this.accuuidList = accuuidList;
	}
	
	public String getMxuuidToEdit() {
		return mxuuidToEdit;
	}


	public void setMxuuidToEdit(String mxuuidToEdit) {
		this.mxuuidToEdit = mxuuidToEdit;
	}


	public Date getDate_from() {
		return date_from;
	}
	
	public void setDate_from(Date date_from) {
		this.date_from = date_from;
	}
	
	public Date getDate_to() {
		return date_to;
	}
	
	public void setDate_to(Date date_to) {
		this.date_to = date_to;
	}

	@Override
	public void prepare() throws Exception {
		// TODO Auto-generated method stub
		
	}
	
}
