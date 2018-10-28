package cn.gxf.spring.struts.integrate.test;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;

/*
 * 配置server.xml的链接器如下，打开gzip功能，（为测试）将最小响应大小限制改为10
 * 测试时需要启动tomcat，测试地址http://localhost:8080/记为tomcat的首页。
<Connector connectionTimeout="20000" port="8080" protocol="HTTP/1.1" redirectPort="8443"
compression="on"
compressionMinSize="2048" -> "10"!!!!
noCompressionUserAgents="gozilla,traviata"
compressableMimeType="text/html,text/xml,application/javascript,text/css,text/plain" />
*/

/*
 * 如果控制台打印出的是乱码，说明tomcat的gzip生效
 * */
public class TestGzip {
	public static void main(String[] args) throws Exception {
		HttpClient http = new HttpClient();
		GetMethod get = new GetMethod("http://localhost:8080/");
		try {
			get.addRequestHeader("accept-encoding", "gzip,deflate");
			get.addRequestHeader("user-agent",
					"Mozilla/5.0 (compatible; MSIE 6.0; Windows NT 5.0; Alexa Toolbar; Maxthon 2.0)");
			int er = http.executeMethod(get);
			if (er == 200) {
				System.out.println(get.getResponseContentLength());
				String html = get.getResponseBodyAsString();
				System.out.println(html);
				System.out.println(html.getBytes().length);
			}
		} finally {
			get.releaseConnection();

		}
	}

}
