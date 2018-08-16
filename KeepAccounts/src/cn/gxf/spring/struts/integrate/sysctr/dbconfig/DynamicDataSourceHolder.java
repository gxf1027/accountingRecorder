package cn.gxf.spring.struts.integrate.sysctr.dbconfig;

public class DynamicDataSourceHolder {
	//写库对应的数据源key
    private static final String MASTER = "master";
 
    //读库对应的数据源key
    private static final String SLAVE = "slave";
    
    //使用ThreadLocal记录当前线程的数据源key
    private static final ThreadLocal<String> holder = new ThreadLocal<String>();
 
    /**
     * 设置数据源key
     * @param key
     */
    public static void putDataSourceKey(String key) {
        holder.set(key);
    }
 
    public static String getMasterDataSourceKey(){
    	putDataSourceKey(MASTER);
    	return holder.get();
    }
    
    /**
     * 获取数据源key
     * @return
     */
    public static String getDataSourceKey() {
        return holder.get();
    }
    
    /**
     * 标记写库
     */
    public static void markMaster(){
        putDataSourceKey(MASTER);
    }
    
    /**
     * 标记读库
     */
    public static void markSlave(){
        putDataSourceKey(SLAVE);
    }

}
