package com.haigelasi.mall.api.controller.front.officialsite;

import com.haigelasi.mall.web.controller.BaseController;
import com.haigelasi.mall.bean.entity.cms.Article;
import com.haigelasi.mall.bean.vo.front.Rets;
import com.haigelasi.mall.service.cms.ArticleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/offcialsite/article")
public class ArticleController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(ArticleController.class);
    @Autowired
    private ArticleService articleService;
    @RequestMapping(method = RequestMethod.GET)
    public Object get(@Param("id") Long id,@Param("type")String type) {
        logger.info("type:{},id:{}",type,id);
         Article article = articleService.get(id);
        return Rets.success(article);
    }
}
