package cn.gxf.spring.struts.integrate.sysctr.log;

import java.io.Serializable;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.core.AbstractLifeCycle;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.appender.AppenderLoggingException;
import org.apache.logging.log4j.core.appender.mom.kafka.KafkaAppender;
import org.apache.logging.log4j.core.appender.mom.kafka.KafkaManager;
import org.apache.logging.log4j.core.appender.mom.kafka.KafkaAppender.Builder;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.Node;
import org.apache.logging.log4j.core.config.Property;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderFactory;
import org.apache.logging.log4j.core.layout.SerializedLayout;

@Plugin(name = "MyKafka", category = Node.CATEGORY, elementType = Appender.ELEMENT_TYPE, printObject = true)
public class MyKafkaAppender extends AbstractAppender{
	/**
     * Builds MyKafkaAppender instances.
     * @param <B> The type to build
     */
    public static class Builder<B extends Builder<B>> extends AbstractAppender.Builder<B>
            implements org.apache.logging.log4j.core.util.Builder<MyKafkaAppender> {

        @PluginAttribute("topic")
        private String topic;

        @PluginAttribute("key")
        private String key;

        @PluginAttribute(value = "syncSend", defaultBoolean = true)
        private boolean syncSend;

        @SuppressWarnings("resource")
        @Override
        public MyKafkaAppender build() {
            final Layout<? extends Serializable> layout = getLayout();
            if (layout == null) {
                AbstractLifeCycle.LOGGER.error("No layout provided for MyKafkaAppender");
                return null;
            }
            //System.out.println(".........xxxxxxxxxxxxxxx..............\n\n\n");
            final KafkaManager kafkaManager = new KafkaManager(getConfiguration().getLoggerContext(), getName(), topic,
                    syncSend, getPropertyArray(), key);
            return new MyKafkaAppender(getName(), layout, getFilter(), isIgnoreExceptions(), kafkaManager,
                    getPropertyArray());
        }

        public String getTopic() {
            return topic;
        }

        public boolean isSyncSend() {
            return syncSend;
        }

        public B setTopic(final String topic) {
            this.topic = topic;
            return asBuilder();
        }

        public B setSyncSend(final boolean syncSend) {
            this.syncSend = syncSend;
            return asBuilder();
        }

    }

    @Deprecated
    public static MyKafkaAppender createAppender(
            final Layout<? extends Serializable> layout,
            final Filter filter,
            final String name,
            final boolean ignoreExceptions,
            final String topic,
            final Property[] properties,
            final Configuration configuration,
            final String key) {

        if (layout == null) {
            AbstractLifeCycle.LOGGER.error("No layout provided for MyKafkaAppender");
            return null;
        }
        final KafkaManager kafkaManager =
                new KafkaManager(configuration.getLoggerContext(), name, topic, true, properties, key);
        return new MyKafkaAppender(name, layout, filter, ignoreExceptions, kafkaManager, null);
    }

    /**
     * Creates a builder for a MyKafkaAppender.
     * @return a builder for a MyKafkaAppender.
     */
    @PluginBuilderFactory
    public static <B extends Builder<B>> B newBuilder() {
        return new Builder<B>().asBuilder();
    }

    private final KafkaManager manager;

    private MyKafkaAppender(final String name, final Layout<? extends Serializable> layout, final Filter filter,
            final boolean ignoreExceptions, final KafkaManager manager, final Property[] properties) {
        super(name, filter, layout, ignoreExceptions, properties);
        this.manager = Objects.requireNonNull(manager, "manager");
    }


    @Override
    public void append(final LogEvent event) {
        if (event.getLoggerName() != null && event.getLoggerName().startsWith("org.apache.kafka")) {
            LOGGER.warn("Recursive logging from [{}] for appender [{}].", event.getLoggerName(), getName());
        } else {
            try {
                tryAppend(event);
            } catch (final Exception e) {
            	throw new AppenderLoggingException("Unable to write to mykafka appender: " + e.getMessage(), e);
            }
        }
    }

    private void tryAppend(final LogEvent event) throws ExecutionException, InterruptedException, TimeoutException {
        final Layout<? extends Serializable> layout = getLayout();
        byte[] data;
        if (layout instanceof SerializedLayout) {
            final byte[] header = layout.getHeader();
            final byte[] body = layout.toByteArray(event);
            data = new byte[header.length + body.length];
            System.arraycopy(header, 0, data, 0, header.length);
            System.arraycopy(body, 0, data, header.length, body.length);
        } else {
            data = layout.toByteArray(event);
        }
        try{
        	manager.send(data);
        }catch (final Exception e) {
        	throw new AppenderLoggingException("A: Unable to write to mykafka appender: " + e.getMessage(), e);
        }
    }

    @Override
    public void start() {
        super.start();
        manager.startup();
    }

    @Override
    public boolean stop(final long timeout, final TimeUnit timeUnit) {
        setStopping();
        boolean stopped = super.stop(timeout, timeUnit, false);
        stopped &= manager.stop(timeout, timeUnit);
        setStopped();
        return stopped;
    }

    @Override
    public String toString() {
        return "MyKafkaAppender{" +
            "name=" + getName() +
            ", state=" + getState() +
            ", topic=" + manager.getTopic() +
            '}';
    }
}
