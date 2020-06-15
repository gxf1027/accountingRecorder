package cn.gxf.spring.struts.integrate.sysctr.audit;

import java.io.Serializable;
import java.util.Date;

public class AuditInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6001001082081513967L;
	
	private String uuid;
	private int event_type;
	private String event_info;
	private int user_id;
	private Date proc_time;
	
	public static final int PAY_ADD = 1;
	public static final int PAY_UPDATE = 2;
	public static final int PAY_DEL = 3;
	public static final int INCOME_ADD = 4;
	public static final int INCOME_UPDATE = 5;
	public static final int INCOME_DEL = 6;
	public static final int TRANS_ADD = 7;
	public static final int TRANS_UPDATE = 8;
	public static final int TRANS_DEL = 9;
	public static final int MULTI_DEL = 10;
	
	public AuditInfo(){
		
	}

	public AuditInfo(String uuid, int type, String event_info, int user_id, Date proc_time) {
		super();
		this.uuid = uuid;
		this.event_type = type;
		this.event_info = event_info;
		this.user_id = user_id;
		this.proc_time = proc_time;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}


	public int getEvent_type() {
		return event_type;
	}

	public void setEvent_type(int event_type) {
		this.event_type = event_type;
	}

	public String getEvent_info() {
		return event_info;
	}

	public void setEvent_info(String event_info) {
		this.event_info = event_info;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public Date getProc_time() {
		return proc_time;
	}

	public void setProc_time(Date proc_time) {
		this.proc_time = proc_time;
	}

	@Override
	public String toString() {
		return "AuditInfo [uuid=" + uuid + ", event_type=" + event_type + ", event_info=" + event_info + ", user_id="
				+ user_id + ", proc_time=" + proc_time + "]";
	}
}
