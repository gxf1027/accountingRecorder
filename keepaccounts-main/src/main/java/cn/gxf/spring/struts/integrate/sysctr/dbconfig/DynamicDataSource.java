package cn.gxf.spring.struts.integrate.sysctr.dbconfig;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicDataSource extends AbstractRoutingDataSource{

	private boolean masterOnly;
	
	@Override
	protected Object determineCurrentLookupKey() {
		
		if (masterOnly){
			System.out.println("determineCurrentLookupKey: " + DynamicDataSourceHolder.getMasterDataSourceKey());
			return DynamicDataSourceHolder.getMasterDataSourceKey();
		}
		
		String dbKey = DynamicDataSourceHolder.getDataSourceKey();
		// ʹ��DynamicDataSourceHolder��֤�̰߳�ȫ�����ҵõ���ǰ�߳��е�����Դkey
		System.out.println("determineCurrentLookupKey: " + dbKey);
		
		if ( null == dbKey){
			return DynamicDataSourceHolder.getMasterDataSourceKey();
		}
		
        return dbKey;
	}

	public boolean isMasterOnly() {
		return masterOnly;
	}

	public void setMasterOnly(boolean masterOnly) {
		this.masterOnly = masterOnly;
	}

}
