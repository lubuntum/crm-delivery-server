package com.delivery.mydelivery.database.services.news;

import com.delivery.mydelivery.database.entities.news.News;
import com.delivery.mydelivery.database.repositories.news.NewsRepository;
import com.delivery.mydelivery.utility.file.FIleUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class NewsService {
    @Autowired
    private NewsRepository newsRepository;
    @Value("${banners.folder}")
    private String bannersFolder;

    public News getRecentNews(){
        return newsRepository.findFirstByOrderByCreatedAtDesc();
    }
    public void createNews(News news, MultipartFile imageBanner) throws IOException {
        news.setImagePath(
                FIleUtil.saveFileToDir(imageBanner, bannersFolder, true));
        newsRepository.save(news);
    }
}
