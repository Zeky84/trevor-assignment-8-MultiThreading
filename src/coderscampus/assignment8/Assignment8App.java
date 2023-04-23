package coderscampus.assignment8;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Assignment8App {
    public static void main(String[] args){
        Assignment8 multiThreadEx = new Assignment8();

        ExecutorService fetchNumExecutor = Executors.newCachedThreadPool();
        ExecutorService addNumExecutor = Executors.newSingleThreadExecutor();

        List<CompletableFuture<Void>> tasks = new ArrayList<>();

        List<Integer> totalOfNums = new ArrayList<>()  ;

        for (int i=0; i<1000; i++) {
            CompletableFuture<Void> task = CompletableFuture.supplyAsync(()->multiThreadEx.getNumbers(),fetchNumExecutor)
                                                            .thenAcceptAsync(numbersK->totalOfNums.addAll(numbersK),addNumExecutor);
            tasks.add(task);
        }

        while(tasks.stream().filter(CompletableFuture::isDone).count()<1000){
        }
        System.out.println("tasks size: " + tasks.size());
        System.out.println("totalOfNums size: " + totalOfNums.size());

        //Counting numbers
        Map<Integer, Integer> numTimesAppear = new HashMap<>();
        for(int number: totalOfNums){

            if(numTimesAppear.containsKey(number)){
                numTimesAppear.put(number,numTimesAppear.get(number)+1);
            }
            else{numTimesAppear.put(number,1);}
        }
        System.out.println("Counting the times a number appear: ");
        System.out.print(numTimesAppear);
    }


}

