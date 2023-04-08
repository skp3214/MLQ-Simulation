
import java.util.Scanner;


class MultiLevelQueueScheduling {
    static class Process {
        int priority;
        int burstTime;
        int ttTime;
        int totalTime = 0;
    }
    
    static class Queue {
        int priorityStart;
        int priority_end;
        int totalTime = 0;
        int length;
        MultiLevelQueueScheduling.Process[] P;
        boolean executed = false;
    }
    public static void main(String[] args) {
        
    
        Queue[] q = new Queue[3];
        q[0] = new Queue();
        q[0].priorityStart = 7;
        q[0].priority_end = 9;
        q[1] = new Queue();
        q[1].priorityStart = 4;
        q[1].priority_end = 6;
        q[2] = new Queue();
        q[2].priorityStart = 1;
        q[2].priority_end = 3;
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the number of process: ");
        int noOfProcess = sc.nextInt();

        Process[] p1 = new Process[noOfProcess];
        for (int i = 0; i < noOfProcess; i++) {
            System.out.print("Enter the Priority of Process: ");
            int priority_of_process = sc.nextInt();
            System.out.print("Enter the BurstTime of the Process: ");
            int burstTime_of_process = sc.nextInt();
            p1[i] = new Process();
            p1[i].priority = priority_of_process;
            p1[i].burstTime = burstTime_of_process;
            p1[i].ttTime = burstTime_of_process;
            for (int j = 0; j < 3; j++) {
                if (q[j].priorityStart <= priority_of_process && priority_of_process <= q[j].priority_end) {
                    q[j].length++;
                }
            }
        }

        for (int i = 0; i < 3; i++) {
            int len = q[i].length;
            q[i].P = new Process[len];
        }

        int a = 0, b = 0, c = 0;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < noOfProcess; j++) {
                if ((q[i].priorityStart <= p1[j].priority) && (p1[j].priority <= q[i].priority_end)) {
                    if (i == 0) {
                        q[i].P[a++] = p1[j];
                    } else if (i == 1) {
                        q[i].P[b++] = p1[j];
                    } else {
                        q[i].P[c++] = p1[j];
                    }
                }
            }
        }

        a--;
        b--;
        c--;
        for (int i = 0; i < 3; i++) {
            System.out.print("Queue " + (i + 1) + " : ");
            for (int j = 0; j < q[i].length; j++) {
                System.out.print(q[i].P[j].priority + "->");
            }
            System.out.println("NULL");
        }
        

        

        

        int timer = 0;
        int l = -1;
        int rr_timer = 4;
        int counter = 0;
        int counterps = 0;
        int counterFcfs = 0;
        while (notComplete(q)) {
            if (timer == 10) {
                timer = 0;

            }
            l += 1;
            if (l >= 3) {
                l = l % 3;
            }

            if (q[l].executed == true) {
                System.out.println("Queue " + (l + 1) + " completed");
                l += 1;
                if (l >= 3) {
                    l = l % 3;
                }
                continue;
            }

            if (l == 0) {
                System.out.println("Queue " + (l + 1) + " in hand");
                if (rr_timer == 0) {
                    rr_timer = 4;
                }

                for (int i = 0; i < q[l].length; i++) {
                    if (q[i].P[i].burstTime == 0) {
                        counter++;
                        continue;
                    }
                    if (counter == q[l].length) {
                        break;
                    }
                    int count=0;
                    while (rr_timer > 0 && q[l].P[i].burstTime != 0 && timer != 10) {
                        count++;
                        
                        q[l].P[i].burstTime--;
                        checkCompleteTimer(q);
                        rr_timer--;
                        timer++;
                    }
                    System.out.println("Executing ROUND ROBIN (Queue 1)  with Process P"+(i+1)+" with Priority "+q[l].P[i].priority+" for Unit Time "+count);
                    
                    if (timer == 10) {
                        break;
                    }
                    if (q[l].P[i].burstTime == 0 && rr_timer == 0) {
                        rr_timer = 4;
                        if (i == (q[i].length - 1)) {
                            i = -1;
                        }
                        continue;
                    }
                    if(q[l].P[i].burstTime==0 && rr_timer > 0){
                        if(i == (q[i].length-1)){
                            i=-1;
                        }
                        continue;
                    }
                    if (rr_timer <= 0) {
                        rr_timer = 4;
                        if (i == (q[i].length - 1)) {
                            i = -1;
                        }
                        continue;
                    }
                }
            }
            
            else if (l == 1) {
                sort_ps(q[l]);
                System.out.println("Queue " + (l + 1) + " in hand ");
                for (int i = 0; i < q[l].length; i++) {
                    if (q[l].P[i].burstTime == 0) {
                        counterps++;
                        continue;
                    }
                    if (counterps == q[l].length) {
                        break;
                    }
                    int count2=0;
                    while (q[l].P[i].burstTime != 0 && timer != 10) {
                        count2++;
                        
                        q[l].P[i].burstTime--;
                        checkCompleteTimer(q);
                        timer++;
                    }
                    System.out.println("Executing PRIORITY SCHEDULING (Queue 2) with Process P"+(i+1)+" with Priority "+q[l].P[i].priority+" for Unit Time "+count2);
                    
                    if (timer == 10) {
                        break;
                    }
                    if (q[l].P[i].burstTime == 0) {
                        continue;
                    }
                }

            } else {
                System.out.println("Queue " + (l + 1) + " in hand ");
                for (int i = 0; i < q[l].length; i++) {
                    if (q[l].P[i].burstTime == 0) {
                        counterFcfs++;
                        continue;
                    }
                    if (counterFcfs == q[l].length) {
                        break;
                    }
                    int count3=0;
                    while (q[l].P[i].burstTime != 0 && timer != 10) {
                        count3++;
                        
                        q[l].P[i].burstTime--;
                        checkCompleteTimer(q);
                        
                        timer++;
                    }
                    System.out.println("Executing FCFS (Queue 3) with Process P"+(i+1)+" with Priority "+q[l].P[i].priority+" for Unit Time "+count3);
                    if (timer == 10) {
                        break;
                    }
                    if (q[l].P[i].burstTime == 0) {
                        continue;
                    }
                }
            }
            System.out.println("Broke from Queue " + (l + 1));
            System.out.println();

        }
        
        for (int i = 0; i < 3; i++) {
            System.out.println("Time taken for queue " + (i + 1) + " to execute " + q[i].totalTime);
           
        }

        int sum_tt = 0;
        int sum_wt = 0;

        for (int i = 0; i < 3; i++) {

            for (int j = 0; j < q[i].length; j++) {

                sum_tt += q[i].P[j].totalTime;
                sum_wt += q[i].P[j].totalTime - q[i].P[j].ttTime;
            }

        }
    
       
        System.out.println();
        System.out.println("The average turnAround time is : " + (sum_tt / noOfProcess));
        System.out.println("The average waiting time is : " + (sum_wt / noOfProcess));

        sc.close();

    }

    public static boolean notComplete(Queue q[]) {
        boolean a = false;
        int countInc = 0;
        for (int i = 0; i < 3; i++) {
            countInc = 0;
            for (int j = 0; j < q[i].length; j++) {
                if (q[i].P[j].burstTime != 0) {
                    a = true;
                } else {
                    countInc += 1;
                }
            }
            if (countInc == q[i].length) {
                q[i].executed = true;
            }
        }
        return a;
    }

    static void checkCompleteTimer(Queue[] q) {
        boolean a=notComplete(q);
        for (int i = 0; i < 3; i++) {
            if (q[i].executed == false) {
                for (int j = 0; j < q[i].length; j++) {
                    if (q[i].P[j].burstTime != 0) {
                        q[i].P[j].totalTime += 1;
                    }
                }
                q[i].totalTime += 1;
            }
        }
    }

    static void sort_ps(Queue q) {
        for (int i = 1; i < q.length; i++) {
            for (int j = 0; j < q.length - 1; j++) {
                if (q.P[j+1].priority < q.P[j].priority) {
                    Process temp = q.P[j];
                    q.P[j] = q.P[j+1];
                    q.P[j+1] = temp;
                }
            }
        }
    }
}