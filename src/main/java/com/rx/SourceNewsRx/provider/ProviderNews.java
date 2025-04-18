package com.rx.SourceNewsRx.provider;

import com.rx.SourceNewsRx.model.News;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
public class ProviderNews {
    public List<News> sourceANews()  {
        List<News> newsList = new ArrayList<>();
        try {

        for(int i=0;i<=2;i++){
            newsList.add(new News("vaina"+i, LocalDate.now(),"RX"+i));
            Thread.sleep(300);

        }
        }catch (InterruptedException e){
            System.out.println(e.getMessage());
        }
        return newsList;


    }
    public List<News> sourceBNews()  {
        List<News> newsList = new ArrayList<>();
        try {

            for(int i=0;i<=2;i++){
                newsList.add(new News("Tomato"+i, LocalDate.now(),"RX"+i));
                Thread.sleep(500);

            }
        }catch (InterruptedException e){
            System.out.println(e.getMessage());
        }
        return newsList;
    }
    public List<News> sourceCNews()  {
        List<News> newsList = new ArrayList<>();
        try {

            for(int i=0;i<=2;i++){
                newsList.add(new News("Tomeito"+i, LocalDate.now(),"RX"+i));
                Thread.sleep(250);

            }
        }catch (InterruptedException e){
            System.out.println(e.getMessage());
        }
        return newsList;
    }


    public CompletableFuture<List<News>> sourceANewsAsync(){
       CompletableFuture<List<News>> newsCompletableFuture = new CompletableFuture<>();
       newsCompletableFuture = CompletableFuture.supplyAsync(this::sourceANews);
       return newsCompletableFuture;
    }


    public CompletableFuture<List<News>> sourceBNewsAsync(){
        CompletableFuture<List<News>> newsCompletableFuture = new CompletableFuture<>();
        newsCompletableFuture = CompletableFuture.supplyAsync(this::sourceBNews);
        return newsCompletableFuture;
    }


    public CompletableFuture<List<News>> sourceCNewsAsync(){
        CompletableFuture<List<News>> newsCompletableFuture = new CompletableFuture<>();
        newsCompletableFuture = CompletableFuture.supplyAsync(this::sourceCNews);
        return newsCompletableFuture;
    }




}
