package com.rx.SourceNewsRx.service;

import com.rx.SourceNewsRx.model.News;
import com.rx.SourceNewsRx.provider.ProviderNews;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;


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

        List<CompletableFuture<List<News>>> futureList = List.of(providerNews.sourceANewsAsync(),
                providerNews.sourceBNewsAsync(),providerNews.sourceBNewsAsync());

       CompletableFuture<List<News>> resul= CompletableFuture.allOf(futureList.toArray(new CompletableFuture[0]))
                .thenApply(v -> futureList.stream().map(CompletableFuture::join).flatMap(List::stream).toList());

         return resul.join();

    }
}
