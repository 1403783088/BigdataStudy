package com.atguigu.interceptor;

import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.interceptor.Interceptor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TypeInterceptor implements Interceptor {

    //声明一个存放时间的集合
    private List<Event> addHeaderEvents;

    public void initialize() {
        //初始化
        addHeaderEvents = new ArrayList<Event>();
    }

    public Event intercept(Event event) {
        //1.获取事件中的头信息
        Map<String, String> eventHeaders = event.getHeaders();

        //2.获取事件中的Body信息
        String eventBody = new String(event.getBody());

        //3.根据body中是否有"hello"来决定添加怎样的头信息
        if(eventBody.contains("hello")){
            //4.添加头信息
            eventHeaders.put("type", "atguigu");
        }else{
            eventHeaders.put("type", "notatguigu");
        }

        return event;
    }

    public List<Event> intercept(List<Event> events) {
        for (Event event : events) {
            intercept(event);
        }
        return events;
    }

    public void close() {

    }

    public static class Builder implements Interceptor.Builder {
        public Interceptor build() {
            return new TypeInterceptor();
        }

        public void configure(Context context) {

        }
    }
}
