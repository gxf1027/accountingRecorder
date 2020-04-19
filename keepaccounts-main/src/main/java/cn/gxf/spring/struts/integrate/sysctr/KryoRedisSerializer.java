package cn.gxf.spring.struts.integrate.sysctr;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import cn.gxf.spring.struts2.integrate.model.AccDateStat;
import cn.gxf.spring.struts2.integrate.model.AccountingDetailVO;
import cn.gxf.spring.struts2.integrate.model.PaymentDetail;

/*
 * ²ÎÕÕ£ºJdkSerializationRedisSerializer
 * 
 */
public class KryoRedisSerializer implements RedisSerializer<Object> {

	private final static Logger logger = LoggerFactory.getLogger(KryoRedisSerializer.class);

	static private final ThreadLocal<Kryo> kryos = new ThreadLocal<Kryo>() {
		protected Kryo initialValue() {
			Kryo kryo = new Kryo();
			// Configure the Kryo instance.
			kryo.setRegistrationRequired(false);
			kryo.setReferences(false);
			return kryo;
		};
	};
	
	static {
		Kryo kryo = kryos.get();
		kryo.register(AccDateStat.class);
		kryo.register(AccountingDetailVO.class);
	}

	public static final byte[] EMPTY_ARRAY = new byte[0];

	public static boolean isEmpty(byte[] data) {
		return (data == null || data.length == 0);
	}

	/*
	 * (non-Javadoc)
	 * https://github.com/zq2599/blog_demos/blob/master/springboot-redis-kyro-
	 * demo/src/main/java/com/bolingcavalry/springbootrediskyrodemo/Serializer/
	 * KryoRedisSerializer.java
	 */
	@Override
	public byte[] serialize(Object obj) {
		if (obj == null) {
			return EMPTY_ARRAY;
		}

		Kryo kryo = kryos.get();
		Output output = new Output(1024, -1);

		try {
			kryo.writeClassAndObject(output, obj);
			return output.toBytes();
		} catch (Exception e) {
			logger.error("serialize error, obj: " + obj, e);
			throw new SerializationException("Cannot serialize", e);
		} 
	}

	@Override
	public Object deserialize(byte[] bytes) {
		
		if (isEmpty(bytes)) {
			return null;
		}
		
		try {
			Input input = null;
			Kryo kryo = kryos.get();
			input = new Input(bytes);
			// System.out.println("kryo deserialize bytes length: " +
			// bytes.length);
			return kryo.readClassAndObject(input);
		} catch (Exception e) {
			logger.error("deserialize error", e);
			throw new SerializationException("Cannot deserialize", e);
		} 
		
	}

	// The Output does not need to be closed because it has not been given an OutputStream
	private void closeOutputStream(OutputStream output) {
		if (output != null) {
			try {
				output.flush();
				output.close();
			} catch (Exception e) {
				logger.error("serialize object close outputStream exception", e);
			}
		}
	}

	// If not reading from an InputStream then it is not necessary to call close
	private void closeInputStream(InputStream input) {
		if (input != null) {
			try {
				input.close();
			} catch (Exception e) {
				logger.error("serialize object close inputStream exception", e);
			}
		}
	}

	public static void main(String[] args) {
		PaymentDetail pd = new PaymentDetail();
		pd.setBz("¹þ¹þ¹þ");
		pd.setAccuuid("asdfzxf");
		pd.setDl_dm("1001");
		pd.setUser_name("5");
		pd.setShijian(new Date());

		KryoRedisSerializer kryoSerializer = new KryoRedisSerializer();
		byte[] bb = kryoSerializer.serialize(pd);
		System.out.println(bb);
		PaymentDetail pd_dd = (PaymentDetail) kryoSerializer.deserialize(bb);
		System.out.println(pd_dd);
	}

}
