package com.delivery.mydelivery.database.repositories.news;

import com.delivery.mydelivery.database.entities.news.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface NewsRepository extends JpaRepository<News, Long> {

    News findFirstByOrderByCreatedAtDesc();
}
