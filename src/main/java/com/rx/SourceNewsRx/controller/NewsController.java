package com.rx.SourceNewsRx.controller;

import com.rx.SourceNewsRx.model.News;
import com.rx.SourceNewsRx.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/news")
public class NewsController {
    private final NewsService newsService;

    @Autowired
    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping("Sync")
    public List<News> getNewsSync(){
        return newsService.getNewsSync();
    }

    @GetMapping("Async")
    public List<News> getNewsSync(@RequestParam(defaultValue = "10") int limit){
        return newsService.getNewsAsync(limit);
    }
}
