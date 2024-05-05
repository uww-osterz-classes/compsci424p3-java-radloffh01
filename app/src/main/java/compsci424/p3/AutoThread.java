package compsci424.p3;

import java.util.concurrent.Semaphore;

public class AutoThread implements Runnable{

    private final Semaphore sem;
    private final int[][] max;
    private final int[][] allocate;
    private final int[] available;
    private final int resource;


    public AutoThread(Semaphore sema, int[][] m, int[][] allo, int[] avail, int res){
        this.sem = sema;
        this.max = m;
        this.allocate = allo;
        this.available = avail;
        this.resource = res;
    }
    public void run()
    {
        try {
            // Displaying the thread that is running
            // System.out.println(
            //     "Thread " + Thread.currentThread().getId()
            //     + " is running");
            for(int i = 0; i < 3; i++){
                request();
                release();
            }
            
        }
        catch (Exception e) {
            // Throwing an exception
            System.out.println("Exception is caught");
        }
    }

    public void request() throws InterruptedException{
        while(true){
            //synchronized(buffer){
                sem.tryAcquire();
                int res = resource;
                int pro = (int)(Math.random() * max[1].length) + 1;
                int units = (int)(Math.random() * 5) + 1;

                isSafe("request", units, res, pro, max, available, allocate, max.length, max[0].length);
                
                
                //notify();
                if(sem.availablePermits() == 0){
                    sem.release(1);
                }
                //sem.release();
                Thread.sleep(100);
                break;
            } 
            
        //}
    }

    public void release() throws InterruptedException{
        while(true){
            //synchronized(buffer){
                sem.tryAcquire();
                int res = resource;
                int pro = (int)(Math.random() * max[1].length) + 1;
                int units = (int)(Math.random() * 5) + 1;

                boolean result = isSafe("release", units, res, pro, max, available, allocate, max.length, max[0].length);
                if(result){
                    available[res] -= units;
                    allocate[pro][res] += units;
                }
                
                //notify();
                if(sem.availablePermits() == 0){
                    sem.release(1);
                }
                //sem.release();
                Thread.sleep(100);
                break;
            } 
            
        //}
    }

    public static boolean isSafe(String com, int ele, int res, int pro, int[][] m, int[] a, int[][] allo, int numP, int numR){
        int count=0;
        // if(com.equals("request")){
        //     allo[pro][res] += ele;
        // }else{
        //     allo[pro][res] -= ele;
        // }
     
    //visited array to find the already allocated process
    boolean finish[] = new boolean[numP]; 
    for (int i = 0;i < numP; i++)
    {
        finish[i] = false;
    }
         
    //work array to store the copy of available resources
    int work[] = new int[numR];    
    for (int i = 0;i < numR; i++)
    {
        work[i] = a[i];
    }
    
    int[][] need = new int[numP][numR];
            for(int i = 0; i < need.length; i++){
                for(int j = 0; j < need[i].length; j++){
                    need[i][j] = m[i][j] - allo[i][j];
                }
            }
            if(com.equals("request")){
                allo[pro][res] += ele;
            }else{
                allo[pro][res] -= ele;
            }

            // for(int i = 0; i < allo.length; i++){
            //     for(int j = 0; j < allo[i].length; j++){
            //         System.out.print(allo[i][j] + " ");
            //     }
            //     System.out.println("");
            // }

            // if(a[res] - ele < 0){
            //     System.out.println("System is Unsafe");
            // }else
            // if(allo[pro][res] > m[pro][res]){
            //     System.out.println("System is Unsafe");
            // }else{
            //     System.out.println("System is Safe");
            // }

            while (count<numP)
    {
        boolean flag = false;
        for (int i = 0;i < numP; i++)
        {
            if (finish[i] == false)
             {
            int j;
            for (j = 0;j < numR; j++)
            {        
                if (need[i][j] > work[j]){
                //System.out.println("failed first break");
                break;
                }
            }
            //System.out.println(j);
            if (j == numR)
            {
                //System.out.println("Entered j if");
               count++;
               finish[i]=true;
               flag=true;
                         
                for (j = 0;j < numR; j++)
                {
                work[j] = work[j]+allo[i][j];
                }
            }
             }
        }
        if (flag == false)
        {
            break;
        }
    }
    if (count < numP || allo[pro][res] < need[pro][res])
    {
        //System.out.println("The System is UnSafe!");
        System.out.println("Thread " + Thread.currentThread().getId() + ": Process " + pro + " " + com + "s " + ele + " units of resource " + res + ": denied");
        return false;
    }
    else
    {
        //System.out.println("The given System is Safe");   
        System.out.println("Thread " + Thread.currentThread().getId() + ": Process " + pro + " " + com + "s " + ele + " units of resource " + res + ": granted");   
        return true;   
        
    }
   
    }


}
