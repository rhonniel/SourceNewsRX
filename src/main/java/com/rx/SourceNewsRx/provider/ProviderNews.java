package com.rx.SourceNewsRx.provider;

import com.rx.SourceNewsRx.model.News;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

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


    public CompletableFuture<News> sourceANewsAsyncOne(){
       return CompletableFuture.supplyAsync(this::sourceANewsOne);
    }

    public CompletableFuture<News> sourceBNewsAsyncOne(){
        return CompletableFuture.supplyAsync(this::sourceBNewsOne);
    }

    public CompletableFuture<News> sourceCNewsAsyncOne(){
        return CompletableFuture.supplyAsync(this::sourceCNewsOne);
    }

    private News sourceANewsOne()  {
        News news = null;
        try {
            LocalDate now =LocalDate.now();
            news=new News("vaina"+now, LocalDate.now(),"RX");
                Thread.sleep(300);
        }catch (InterruptedException e){
            System.out.println(e.getMessage());
        }
        return news;

    }

    private News sourceBNewsOne()  {
        News news = null;
        try {
            LocalDate now =LocalDate.now();
            news=new News("vaina"+now, LocalDate.now(),"RX");
            Thread.sleep(500);
        }catch (InterruptedException e){
            System.out.println(e.getMessage());
        }
        return news;

    }


    private News sourceCNewsOne()  {
        News news = null;
        try {
            LocalDate now =LocalDate.now();
            news=new News("vaina"+now, LocalDate.now(),"RX");
            Thread.sleep(250);
        }catch (InterruptedException e){
            System.out.println(e.getMessage());
        }
        return news;

    }


    public void sourceAPublicher(Consumer<News> consumer, Supplier<Boolean>supplier)  {
        new Thread(() -> {
             while (!supplier.get()) {
                 consumer.accept(sourceANewsOne());
            }
        }).start();

    }



    public void sourceBPublicher(Consumer<News> consumer, Supplier<Boolean>supplier)  {
        new Thread(() -> {
            while (!supplier.get()) {
                consumer.accept(sourceBNewsOne());
            }
        }).start();

    }


    public void sourceCPublicher(Consumer<News> consumer, Supplier<Boolean>supplier)  {
        new Thread(() -> {
            while (!supplier.get()) {
                consumer.accept(sourceCNewsOne());
            }
        }).start();

    }






}
