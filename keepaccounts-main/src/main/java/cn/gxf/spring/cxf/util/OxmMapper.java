package cn.gxf.spring.cxf.util;

import java.io.StringReader;  
import java.io.StringWriter;  
  
import javax.xml.transform.stream.StreamResult;  
import javax.xml.transform.stream.StreamSource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.oxm.Marshaller;  
import org.springframework.oxm.Unmarshaller;

import cn.gxf.spring.struts2.integrate.model.IncomeDetail;  

public class OxmMapper {
	private Marshaller marshaller;
	private Unmarshaller unmarshaller;

	public void setMarshaller(Marshaller marshaller) {
		this.marshaller = marshaller;
	}

	public void setUnmarshaller(Unmarshaller unmarshaller) {
		this.unmarshaller = unmarshaller;
	}

	// java object --> xml
	public String marshal(Object obj) {
		String xml;
		StringWriter writer = null;
		try {
			writer = new StringWriter();
			this.marshaller.marshal(obj, new StreamResult(writer));
			xml = writer.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return xml;
	}

	// xml --> java object
	public Object unmarshal(String xml) {
		Object obj = null;
		try {
			obj = this.unmarshaller.unmarshal(new StreamSource(new StringReader(xml)));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return obj;
	}
	
	public static void main(String[] args) {
		ApplicationContext appContext = new ClassPathXmlApplicationContext( "applicationContext-oxm.xml");
		OxmMapper mapper = (OxmMapper) appContext.getBean("oxmMapper");
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?> " +
		"<IncomeDetail> " +
		  "<mxuuid>mxuuid123123123</mxuuid> " +
		  "<accuuid>acc1231322</accuuid> " +
		  "<finprodUuid>123123</finprodUuid> " +
		  "<user_name>gxf</user_name> " +
		  "<user_id>5</user_id> " +
		  "<je>5.05</je> " +
		  "<lb_dm>101</lb_dm> " +
		  "<fkfmc>fukuangfang</fkfmc> " +
		  "<zh_dm>10101</zh_dm> " +
		  "<shijian>2013-03-29T09:25:56</shijian> " +
		  "<bz> ’»Î</bz> " +
		  "<yxbz>Y</yxbz> " +
		  "<xgrq></xgrq> " +
		"</IncomeDetail> ";
		IncomeDetail income = (IncomeDetail) mapper.unmarshal(xml);
	}
}
