/*
 * The MIT License
 *
 * Copyright 2019 user.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.store.service.impl;

import com.store.StoreUtil;
import com.store.model.Item;
import com.store.model.Store;
import com.store.service.StoreService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import org.jboss.logging.Logger;
import org.jboss.logging.Logger.Level;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 *
 * @author Suraj
 */
@Component
public class StoreServiceImpl implements StoreService {

    @Value("${store.categories.count}")
    private int categoryCount;
    @Value("${store.itemspercategory.count}")
    private int itemPerCategoryCount;
    @Value("${item.price.min}")
    private double itemMinPrice;
    @Value("${item.price.max}")
    private double itemMaxPrice;
    @Value("${item.shippingcost.min}")
    private double itemMinShippingCost;
    @Value("${item.shippingcost.max}")
    private double itemMaxShippingCost;
    @Value("${item.rating.min}")
    private int itemMinRating;
    @Value("${item.rating.max}")
    private int itemMaxRating;

    @Autowired
    BeanFactory beanFactory;

    @Autowired
    StoreUtil storeUtil;

    @Autowired            
    @Qualifier("store")
    Store store;
    
    Logger logger = Logger.getLogger(StoreServiceImpl.class);

    @Override
    public void initializeStore() {
        store.removeAllItems();
        int catCount = 0;
        while (catCount++ < categoryCount) {
            int itemCount = 0;
            while (itemCount++ < itemPerCategoryCount) {
                Item item = beanFactory.getBean(Item.class);
                item.setName("Category:" + String.format("%02d", catCount) + "_Item:" + String.format("%02d", itemCount));
                item.setCategory("" + catCount);
                item.setPrice(storeUtil.getRandomAmount(this.itemMinPrice, this.itemMaxPrice));
                item.setShippingCost(storeUtil.getRandomAmount(this.itemMinShippingCost, this.itemMaxShippingCost));
                item.setRating(storeUtil.getRandomInt(this.itemMinRating, this.itemMaxRating));
                store.addItem(item);
            }
        }
    }

    @Override
    public void resetStore() {
        store.removeAllItems();
    }

    @Override
    public void setCategoryCount(int categoryCount) {
        this.categoryCount = categoryCount;
    }

    @Override
    public void setItemPerCategoryCount(int itemPerCategoryCount) {
        this.itemPerCategoryCount = itemPerCategoryCount;
    }

    @Override
    public int getCategoryCount() {
        return this.categoryCount;
    }

    @Override
    public int getItemPerCategoryCount() {
        return this.itemPerCategoryCount;
    }

    @Override
    public void printStore() {
        ArrayList<Item> storeList = store.getAllItems();
        Collections.sort(storeList, getStoreComparator());
        logger.info("---------------");
        logger.info("## S T O R E ##");
        logger.info("---------------");
        storeUtil.print(storeList, false, Level.INFO);
    }

    private Comparator<Item> getStoreComparator() {
        return Comparator.comparing(Item::getName);
    }

}
