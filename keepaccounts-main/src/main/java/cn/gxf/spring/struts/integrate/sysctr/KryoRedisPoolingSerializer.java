package cn.gxf.spring.struts.integrate.sysctr;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.util.Pool;

import cn.gxf.spring.struts2.integrate.model.AccDateStat;
import cn.gxf.spring.struts2.integrate.model.AccountingDetailVO;
import cn.gxf.spring.struts2.integrate.model.PaymentDetail;

/*
 * https://blog.csdn.net/rocklee/article/details/81143320
 * 
 */
public class KryoRedisPoolingSerializer implements RedisSerializer<Object> {

	private final static Logger logger = LoggerFactory.getLogger(KryoRedisPoolingSerializer.class);

	private static Pool<Kryo> kryoPool = new Pool<Kryo>(true, false, 8) {
		protected Kryo create() {
			Kryo kryo = new Kryo();
			kryo.setRegistrationRequired(false);
			kryo.setReferences(false);
			// Configure the Kryo instance.
			return kryo;
		}
	};

	private static Pool<Output> outputPool = new Pool<Output>(true, false, 16) {
		protected Output create() {
			return new Output(1024, -1);
		}
	};
	private static Pool<Input> inputPool = new Pool<Input>(true, false, 16) {
		protected Input create() {
			return new Input(1024);
		}
	};

	static {
		//Kryo kryo = kryoPool.obtain();
		//kryo.register(AccDateStat.class);
		//kryo.register(AccountingDetailVO.class);
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
		//System.out.println("free:" + kryoPool.getFree() + " peak:"+kryoPool.getPeak());
		Kryo kryo = kryoPool.obtain();
		Output output = outputPool.obtain();

		try {
			kryo.writeClassAndObject(output, obj);
			return output.toBytes();
		} catch (Exception e) {
			logger.error("serialize error, obj: " + obj, e);
			//throw new SerializationException("Cannot serialize", e);
		} finally {
			kryoPool.free(kryo);
			outputPool.free(output);
		}
		return EMPTY_ARRAY;
	}

	@Override
	public Object deserialize(byte[] bytes) {
		
		if (isEmpty(bytes)) {
			return null;
		}
		
		Kryo kryo = kryoPool.obtain();
		Input input = inputPool.obtain();
		
		try {
			input.setBuffer(bytes);
			return kryo.readClassAndObject(input);
		} catch (Exception e) {
			logger.error("deserialize error", e);
			//throw new SerializationException("Cannot deserialize", e);
		} finally {
			kryoPool.free(kryo);
			inputPool.free(input);
		}
		
		return null;
	}

	

	public static void main1(String[] args) {
		PaymentDetail pd = new PaymentDetail();
		pd.setBz("备注");
		pd.setAccuuid("asdfzxf");
		pd.setDl_dm("1001");
		pd.setUser_name("5");
		pd.setShijian(new Date());

		KryoRedisPoolingSerializer kryoSerializer = new KryoRedisPoolingSerializer();
		byte[] bb = kryoSerializer.serialize(pd);
		System.out.println(bb+"len: " + bb.length);
		PaymentDetail pd_dd = (PaymentDetail) kryoSerializer.deserialize(bb);
		System.out.println(pd_dd);
	}
	
	public static void main(String[] args) {
		ExecutorService pool = Executors.newCachedThreadPool();
		for (int i=0; i<120; i++) {
			
			pool.execute(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					PaymentDetail pd = new PaymentDetail();
					pd.setBz("备注");
					pd.setAccuuid("asdfzxf");
					pd.setDl_dm("1001");
					pd.setUser_name("5");
					pd.setShijian(new Date());

					KryoRedisPoolingSerializer kryoSerializer = new KryoRedisPoolingSerializer();
					byte[] bb = kryoSerializer.serialize(pd);
					System.out.println(bb+"len: " + bb.length);
					PaymentDetail pd_dd = (PaymentDetail) kryoSerializer.deserialize(bb);
					System.out.println(pd_dd);
				}
			});
		}
	}

}
