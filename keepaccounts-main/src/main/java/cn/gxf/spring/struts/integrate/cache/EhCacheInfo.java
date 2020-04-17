package cn.gxf.spring.struts.integrate.cache;

import java.io.Serializable;

public class EhCacheInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -814901276180514594L;

	private String keyName;
	private long hitCount;
	private String creationTime;
	private String lastAccessTime;
	private int timeToLive;
	private int timeToIdle;

	public String getKeyName() {
		return keyName;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

	public long getHitCount() {
		return hitCount;
	}

	public void setHitCount(long hitCount) {
		this.hitCount = hitCount;
	}

	public String getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(String creationTime) {
		this.creationTime = creationTime;
	}

	public String getLastAccessTime() {
		return lastAccessTime;
	}

	public void setLastAccessTime(String lastAccessTime) {
		this.lastAccessTime = lastAccessTime;
	}

	public int getTimeToLive() {
		return timeToLive;
	}

	public void setTimeToLive(int timeToLive) {
		this.timeToLive = timeToLive;
	}

	public int getTimeToIdle() {
		return timeToIdle;
	}

	public void setTimeToIdle(int timeToIdle) {
		this.timeToIdle = timeToIdle;
	}

	@Override
	public String toString() {
		return "EhCacheInfo [keyName=" + keyName + ", hitCount=" + hitCount + ", creationTime=" + creationTime
				+ ", lastAccessTime=" + lastAccessTime + ", timeToLive=" + timeToLive + ", timeToIdle=" + timeToIdle
				+ "]";
	}

}
