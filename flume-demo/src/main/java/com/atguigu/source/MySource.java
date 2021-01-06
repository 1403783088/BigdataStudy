package com.atguigu.source;

import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.EventDeliveryException;
import org.apache.flume.PollableSource;
import org.apache.flume.conf.Configurable;
import org.apache.flume.event.SimpleEvent;
import org.apache.flume.source.AbstractSource;

import java.util.HashMap;

public class MySource extends AbstractSource implements Configurable, PollableSource {
    private Long delay;
    private String field;

    public void configure(Context context) {
        delay = context.getLong("delay");
        field = context.getString("field", "Hello!");
    }

    public Status process() throws EventDeliveryException {
        //创建事件头信息
        HashMap<String, String> headerMap = new HashMap<String, String>();

        //创建事件
        Event event = new SimpleEvent();

        try{
            //循环封装事件
            for (int i = 0; i < 5; i++) {
                //给事件设置头信息
                event.setHeaders(headerMap);
                //给事件设置内容
                event.setBody((field+"--"+i).getBytes());
                //给事件写入channel
                getChannelProcessor().processEvent(event);
                Thread.sleep(delay);
            }
        } catch(Exception e){
            e.printStackTrace();
            return Status.BACKOFF;
        }

        return Status.READY;
    }

    public long getBackOffSleepIncrement() {
        return 0;
    }

    public long getMaxBackOffSleepInterval() {
        return 0;
    }


}
