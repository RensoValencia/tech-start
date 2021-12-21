package com.techstart.service;

import com.techstart.model.Article;
import com.techstart.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
/*
 * Author: Renso Valencia Ventura
 * Date: 21/12/2021
 * Version: 1.0
 */
@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    public void create(Article article) {
        articleRepository.save(article);
    }

    public List<Article> findAll()  {
        return articleRepository.findAll();
    }
}