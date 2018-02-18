package com.jd.home.framework.monitor.db.core.ump;
import com.jd.home.framework.monitor.db.config.SystemConstans;
import com.jd.ump.profiler.proxy.Profiler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Title : UMP消息存储
 * Description: 通过一个FIFO的队列存储UMP消息,work任务会不断从队列拿消息发送至UMP平台</br>
 * @author <a href=mailto:wangsongpeng@jd.com>王宋鹏</a>
 * @since 2018/1/29
 */
public class UmpStore {

    private static Logger logger = LoggerFactory.getLogger(UmpStore.class);


    public UmpStore(){
         Thread  t1 = new Thread(new SendUmpThread(),SystemConstans.SEND_UMP_THREAD_NAME);
         t1.setDaemon(true);
         t1.setPriority(Thread.NORM_PRIORITY);
         t1.start();
    }


    /*
     * 线程安全的链表队列,高并发场景下的队列,适用于多个线程生成消息.
     */
    private ConcurrentLinkedQueue<Ump> umps = new ConcurrentLinkedQueue<Ump>();

    /**
     * 当前存储UMP消息的总数
     */
    private AtomicInteger counter = new AtomicInteger(0);


    /**
     * 存储ump实体
     * @param ump
     */
    public void storeUmp(Ump ump){
       if(counter.get() < SystemConstans.MAX_STORE_UMP_MSG){
           umps.offer(ump); //放入队列头部
           counter.incrementAndGet(); //加一
       }
    }


    /**
     * 发送至UMP平台
     * @param ump
     */
    private void sendUmp(Ump ump) {
        System.out.println("发送数据,UMP = " + ump);
        Profiler.businessAlarm(ump.getKey(),ump.getDate().getTime(), ump.getInfo());
    }


    /**
     * 发送ump线程
     */
    class SendUmpThread implements Runnable{
        boolean stop = false;
        @Override
        public void run() {
            while (!stop){
                try {
                    Ump ump =  UmpStore.this.umps.poll();//从队列中取出待发送的ump实体数据
                    if(null != ump){
                        sendUmp(ump);
                        UmpStore.this.counter.decrementAndGet();
                    }else{
                        Thread.sleep(1000l);
                    }
                }catch (InterruptedException e1){
                    if(logger.isInfoEnabled()){
                        logger.info("Thread : " + Thread.currentThread().getName() + "InterruptedException!");
                    }
                    stop = true;
                }catch (Exception e2){
                    logger.error("Thread : " + Thread.currentThread().getName() + "Execute Fail! ErrMsg : " + e2,e2);
                }
            }
        }
    }

    public static void main(String[] args) {
        UmpStore umpStore = new UmpStore();
        for(;;){
            umpStore.storeUmp(new Ump("aa.bb.cc","assdasd",new Date()));
        }
    }
}
