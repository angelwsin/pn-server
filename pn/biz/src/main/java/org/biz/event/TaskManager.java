package org.biz.event;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TaskManager {
    
    
     private static ExecutorService executor;
    
    
    public TaskManager() {
        executor = new ThreadPoolExecutor(corePoolSize, corePoolSize, 100, 
                TimeUnit.MILLISECONDS, workQueue, new ThreadFactory() {
                    
                    @Override
                    public Thread newThread(Runnable r) {
                        Thread td  = new Thread(r);
                        td.setName("taskManager-thread");
                        return td;
                    }
                });
       

    }
    
    
    public void addTask(Runnable r){
        executor.submit(r);
    }
    
    
    
    private final BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<Runnable>();
    
    private final int corePoolSize =  Runtime.getRuntime().availableProcessors();
    
    static public class ProccessTask  implements Runnable{
        
        
        private  Object content;
        private  Proccessor processor;
        
        public ProccessTask(Object content, Proccessor processor) {
            super();
            this.content = content;
            this.processor = processor;
        }
        @Override
        public void run() {
            processor.proccess(content);
                
          }
           
           
    }
        
  

}
