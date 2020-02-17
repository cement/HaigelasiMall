package com.haigelasi.mall.api.controller.shop;

import com.haigelasi.mall.bean.constant.factory.PageFactory;
import com.haigelasi.mall.bean.core.BussinessLog;
import com.haigelasi.mall.bean.dictmap.CommonDict;
import com.haigelasi.mall.bean.entity.shop.AttrKey;
import com.haigelasi.mall.bean.entity.shop.AttrVal;
import com.haigelasi.mall.bean.enumeration.BizExceptionEnum;
import com.haigelasi.mall.bean.exception.ApplicationException;
import com.haigelasi.mall.bean.vo.front.Rets;
import com.haigelasi.mall.bean.vo.query.SearchFilter;
import com.haigelasi.mall.service.shop.AttrKeyService;
import com.haigelasi.mall.service.shop.AttrValService;
import com.haigelasi.mall.utils.Lists;
import com.haigelasi.mall.utils.Maps;
import com.haigelasi.mall.utils.StringUtil;
import com.haigelasi.mall.utils.factory.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shop/attr/val")
public class AttrValController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private AttrValService attrValService;
    @Autowired
    private AttrKeyService attrKeyService;

    @RequestMapping("/getAttrByCategoryAndGoods/{idCategory}")
    public Object getAttrByCategoryAndGoods(@PathVariable("idCategory") Long idCategory) {
        List<AttrKey> keyList = attrKeyService.queryBy(idCategory);
        List<Long> idAttrKeyList = Lists.newArrayList();
        for(AttrKey attrKey:keyList){
            idAttrKeyList.add(attrKey.getId());
        }
        List<AttrVal> valList = Lists.newArrayList();
        if(!idAttrKeyList.isEmpty()) {
            valList = attrValService.queryAll(SearchFilter.build("idAttrKey", SearchFilter.Operator.IN, idAttrKeyList));
        }
        return Rets.success(Maps.newHashMap(
                "keyList", keyList,
                "valList", valList
        ));
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Object list() {
        Page<AttrVal> page = new PageFactory<AttrVal>().defaultPage();
        page = attrValService.queryPage(page);
        return Rets.success(page);
    }

    @RequestMapping(method = RequestMethod.POST)
    @BussinessLog(value = "编辑商品属性值", key = "name", dict = CommonDict.class)
    public Object save(@ModelAttribute AttrVal tShopGoodsAttrVal) {
        if (tShopGoodsAttrVal.getId() == null) {
            attrValService.insert(tShopGoodsAttrVal);
        } else {
            attrValService.update(tShopGoodsAttrVal);
        }
        return Rets.success();
    }

    @RequestMapping(method = RequestMethod.DELETE)
    @BussinessLog(value = "删除商品属性值", key = "id", dict = CommonDict.class)
    public Object remove(Long id) {
        if (StringUtil.isEmpty(id)) {
            throw new ApplicationException(BizExceptionEnum.REQUEST_NULL);
        }
        attrValService.deleteById(id);
        return Rets.success();
    }
}
