package com.rx.SourceNewsRx.service;

import com.rx.SourceNewsRx.model.News;
import com.rx.SourceNewsRx.provider.ProviderNews;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;


@Service
public class NewsService {

    private final ProviderNews providerNews;

    @Autowired
    public NewsService(ProviderNews providerNews) {
        this.providerNews = providerNews;
    }

    public List<News> getNewsSync() {
        List<News> news = new ArrayList<>();
        news.addAll(providerNews.sourceANews());
        news.addAll(providerNews.sourceBNews());
        news.addAll(providerNews.sourceCNews());

        return news;
    }

    public List<News> getNewsAsync(int limit) {
        List<CompletableFuture<News>> futureList = new ArrayList<>();
        for( int i =0; i<limit;i=i+3){
            futureList.add(providerNews.sourceANewsAsyncOne());
            futureList.add(providerNews.sourceBNewsAsyncOne());
            futureList.add(providerNews.sourceCNewsAsyncOne());
        }

       CompletableFuture<List<News>> resul= CompletableFuture.allOf(futureList.toArray(new CompletableFuture[0]))
                        .thenApply(v -> futureList.stream().map(CompletableFuture::join)
                                .limit(limit)
                        .toList());
        return resul.join();

    }


    public List<News> getFirstNewsAsync(int limit)  {

        ConcurrentLinkedQueue<News> queue = new ConcurrentLinkedQueue<>();
        AtomicBoolean isDone =  new  AtomicBoolean(false);

        AtomicBoolean isTimeout = new AtomicBoolean(false);

        Supplier<Boolean> closeSubscription = () -> isDone.get() || isTimeout.get() || queue.size() >= limit;

        Consumer<News> consumer = news -> {
            if (!isDone.get() && !isTimeout.get()) {
                queue.add(news);
            }
            if (queue.size() >= limit) {
                isDone.set(true);
            }
        };


        CompletableFuture.runAsync(() ->providerNews.sourceAPublicher( consumer,closeSubscription));
        CompletableFuture.runAsync(() ->providerNews.sourceBPublicher( consumer,closeSubscription));
        CompletableFuture.runAsync(() ->providerNews.sourceCPublicher( consumer,closeSubscription));



        CompletableFuture<Void> monitor = CompletableFuture.runAsync(() -> {
            while (!isDone.get()) {
                try {
                    Thread.sleep(200);
                    System.out.println("Noticias: " + queue.size());
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });

        CompletableFuture<Void> timeout = CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(10000);
                isTimeout.set(true);
                System.out.println("Timeout alcanzado!");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });


        try {
            CompletableFuture.anyOf(monitor,timeout).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            isDone.set(true);
            isTimeout.set(true);
        }
        monitor.join();
       return queue.stream().limit(limit).toList();

    }
}
