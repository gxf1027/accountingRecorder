package cn.gxf.spring.cxf;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;

import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.interceptor.LoggingMessage;
import org.apache.cxf.io.CachedOutputStream;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class SoapMsgLogInInteceptor extends AbstractPhaseInterceptor<Message> {  
	  
	private Logger logger = LogManager.getLogger();
    private int limit = 102400;  
  
    public int getLimit() {  
        return limit;  
    }  
  
    public void setLimit(int limit) {  
        this.limit = limit;  
    }  
  
    public SoapMsgLogInInteceptor() {  
        // �������ڵ��÷���֮ǰ����SOAP��Ϣ  
        super(Phase.RECEIVE);  
    }  
  
    public void handleMessage(Message message) throws Fault {  
    	System.out.println("SoapMsgLogInInteceptor...");
        logging(message);  
    }  
  
    private void logging(Message message) throws Fault {  
        if (message.containsKey(LoggingMessage.ID_KEY)) {  
            return;  
        }  
        String id = (String) message.getExchange().get(LoggingMessage.ID_KEY);  
        if (id == null) {  
            id = LoggingMessage.nextId();  
            message.getExchange().put(LoggingMessage.ID_KEY, id);  
        }  
        message.put(LoggingMessage.ID_KEY, id);  
        LoggingMessage buffer = new LoggingMessage("Inbound Message\n----------------------------", id);  
  
        String encoding = (String) message.get(Message.ENCODING);  
  
        if (encoding != null) {  
            buffer.getEncoding().append(encoding);  
        }  
        String ct = (String) message.get("Content-Type");  
        if (ct != null) {  
            buffer.getContentType().append(ct);  
        }  
        Object headers = message.get(Message.PROTOCOL_HEADERS);  
  
        if (headers != null) {  
            buffer.getHeader().append(headers);  
        }  
        String uri = (String) message.get(Message.REQUEST_URI);  
        if (uri != null) {  
            buffer.getAddress().append(uri);  
        }  
  
        InputStream is = (InputStream) message.getContent(InputStream.class);  
        if (is != null) {  
            CachedOutputStream bos = new CachedOutputStream();  
            try {  
                IOUtils.copy(is, bos);  
  
                bos.flush();  
                is.close();  
  
                message.setContent(InputStream.class, bos.getInputStream());  
                if (bos.getTempFile() != null) {  
                    buffer.getMessage().append("\nMessage (saved to tmp file):\n");  
                    buffer.getMessage().append("Filename: " + bos.getTempFile().getAbsolutePath() + "\n");  
                }  
                if (bos.size() > this.limit) {  
                    buffer.getMessage().append("(message truncated to " + this.limit + " bytes)\n");  
                }  
                bos.writeCacheTo(buffer.getPayload(), this.limit);  
                bos.close();
                
                String payload = buffer.getPayload().toString();
                String payloadXml = null;
                try {
                	payloadXml = this.xmlFormat(payload);
				} catch (Exception e) {
					e.printStackTrace();
				}
               
                buffer.getPayload().delete( 0, buffer.getPayload().length() );
                buffer.getPayload().append(payloadXml);
                
            } catch (IOException e) { 
            	e.printStackTrace();
                throw new Fault(e);  
            } 
        }  
        // ��ӡ��־��������־��������Ϳ��ԣ���д���log4j��¼��־
        //System.out.println(buffer.toString());  
        this.logger.info(buffer.toString());
    }  
  
    // ���ִ������������Ϣ��ջ��Ϣ  
    public void handleFault(Message message) {  
        Exception exeption = message.getContent(Exception.class);  
        System.out.println(exeption.getMessage());  
    }  
  
    private String xmlFormat(String str) throws IOException, DocumentException{
    	SAXReader reader = new SAXReader();
    	// System.out.println(reader);
        // ע�ͣ�����һ�������ַ�������
        StringReader in = new StringReader(str);
        Document doc = reader.read(in);
        // System.out.println(doc.getRootElement());
        // ע�ͣ����������ʽ
        OutputFormat formater = OutputFormat.createPrettyPrint();
        //formater=OutputFormat.createCompactFormat();
        // ע�ͣ�����xml���������
        formater.setEncoding("utf-8");
        // ע�ͣ��������(Ŀ��)
        StringWriter out = new StringWriter();
        // ע�ͣ����������
        XMLWriter writer = new XMLWriter(out, formater);
        // ע�ͣ������ʽ���Ĵ���Ŀ���У�ִ�к󡣸�ʽ����Ĵ�������out�С�
        writer.write(doc);
 
        writer.close();
        //System.out.println(out.toString());
        // ע�ͣ��������Ǹ�ʽ����Ľ��
        return out.toString();
    }
}  
