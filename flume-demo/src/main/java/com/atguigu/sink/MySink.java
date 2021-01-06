package com.atguigu.sink;

import org.apache.flume.*;
import org.apache.flume.conf.Configurable;
import org.apache.flume.sink.AbstractSink;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class MySink extends AbstractSink implements Configurable {

    private final Logger logger = LoggerFactory.getLogger(MySink.class);
    private String prefix;
    private String suffix;

    public void configure(Context context) {
        prefix = context.getString("prefix");
        suffix = context.getString("suffix", "atguigu");
    }

    /**
     * 1.获取channel
     * 2.从channel获取事务以及数据
     * 3.发送数据
     */
    public Status process() throws EventDeliveryException {
        Status status;

        Channel channel = getChannel();

        Transaction transaction = channel.getTransaction();

        try{
            transaction.begin();

            Event event = channel.take();

            if(event != null){
                logger.info(prefix + new String(event.getBody()) + suffix);
            }

            transaction.commit();

            status = Status.READY;

        }catch (Exception e){
            e.printStackTrace();
            transaction.rollback();
            status = Status.BACKOFF;
        }finally {
            transaction.close();
        }

        return status;
    }
}
