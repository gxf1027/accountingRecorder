package cn.gxf.spring.struts.integrate.sysctr.mq;

public class Constant {
	public interface MsgLogStatus {
        Integer DELIVERING = 0;// ��ϢͶ����
        Integer DELIVER_SUCCESS = 1;// Ͷ�ݳɹ�
        Integer DELIVER_FAIL = 2;// Ͷ��ʧ��
        Integer CONSUMED_SUCCESS = 3;// ������
    }
}
