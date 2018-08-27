package cn.gxf.spring.motan.mbdao;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import cn.gxf.spring.motan.model.RpcRequestInfo;

@MapperScan
public interface RpcRequestLogDao {
	public void saveRequestsInfo(List<RpcRequestInfo> requests);
}
