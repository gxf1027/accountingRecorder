package cn.gxf.spring.struts.integrate.sysctr.dbconfig;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;

public class DataSourceAspect {
	
	private String dbUseSlave;
	
	/**
     * �ڽ���Service����֮ǰִ��
     * 
     * @param point �������
     */
    public void before(JoinPoint point) {
    	
    	if (!dbUseSlave.equals("enabled")){
    		DynamicDataSourceHolder.markMaster();
    		return;
    	}
    	
        // ��ȡ����ǰִ�еķ�����
        String methodName = point.getSignature().getName();
        if (isSlave(methodName)) {
            // ���Ϊ����
            DynamicDataSourceHolder.markSlave();
        } else {
            // ���Ϊд��
            DynamicDataSourceHolder.markMaster();
        }
    }
 
    /**
     * �ж��Ƿ�Ϊ����
     * 
     * @param methodName
     * @return
     */
    private Boolean isSlave(String methodName) {
        // ��������query��find��get��ͷ�ķ������ߴӿ�
        return StringUtils.startsWithAny(methodName, "query", "find", "get");
    }
    
    public String getDbUseSlave() {
		return dbUseSlave;
	}
    
    public void setDbUseSlave(String dbUseSlave) {
		this.dbUseSlave = dbUseSlave;
	}
}
