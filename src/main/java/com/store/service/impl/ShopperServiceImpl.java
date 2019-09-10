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
import com.store.model.ShoppingCart;
import com.store.model.ShoppingStrategy;
import com.store.model.Store;
import com.store.service.ShopperService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import org.jboss.logging.Logger;
import org.jboss.logging.Logger.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 *
 * @author Suraj
 */
@Component
public class ShopperServiceImpl implements ShopperService {
    
    @Value("${shopping.min.totalCost}")
    private double shoppingTotalCostMin;
    @Value("${shopping.max.totalCost}")
    private double shoppingTotalCostMax;
    @Value("${shopping.min.rating}")
    private int shoppingRatingMin;
    @Value("${shopping.max.rating}")
    private int shoppingRatingMax;
    @Value("${shopping.min.totalCount}")
    private int shoppingTotalCountMin;
    @Value("${shopping.max.totalCount}")
    private int shoppingTotalCountMax;    
    
    private ShoppingStrategy strategy;    

    @Autowired
    StoreUtil storeUtil;

    @Autowired
    @Qualifier("shoppingcart")
    ShoppingCart shoppingCart;
    
    @Autowired
    @Qualifier("store")
    Store store;
    
    Logger logger = Logger.getLogger(ShopperServiceImpl.class);

    @Override
    public void fillShoppingCart() {
        ArrayList<Item> priorityList = new ArrayList<Item>();
        priorityList.addAll(this.store.getAllItems());
        while((!priorityList.isEmpty()) && (shoppingCart.getTotalAmount() < this.shoppingTotalCostMax) ){
            priorityList = strategy.prioritizeItems(priorityList);
            Item currentItem = priorityList.get(0);
            if(shoppingCart.getTotalAmount() + currentItem.getTotalCost() > this.shoppingTotalCostMax){
                priorityList.remove(currentItem);
                continue;
            }
            shoppingCart.addItem(currentItem);
//            this.printShoppingCart();
            priorityList = this.removeItemsWithCategory(priorityList, currentItem.getCategory());
            continue;
        }
    }

    @Override
    public void setShoppingStrategy(ShoppingStrategy strategy) {
        strategy.setShoppingAmountLimits(shoppingTotalCostMin, shoppingTotalCostMax);
        strategy.setShoppingNumberLimits(shoppingTotalCountMin, shoppingTotalCountMax);
        strategy.setShoppingRatingLimits(shoppingRatingMin, shoppingRatingMax);
        this.strategy = strategy;        
    }

    @Override
    public void printShoppingCart() {
        ArrayList<Item> cartItems = this.shoppingCart.getAllItems();
        Collections.sort(cartItems, getShoppingCartComparator());        
        logger.info("-------------------");
        logger.info("## SHOPPING CART ##");
        logger.info("-------------------");
        storeUtil.print(cartItems, true, Level.INFO);
        logger.info("-----------------");
        logger.info("## R E S U L T ##");
        logger.info("-----------------");
        logger.info("**** There are " + this.shoppingCart.getTotalCountOfItems()
                + " items in the cart costing a total of $" + this.shoppingCart.getTotalAmount()                  
                + " and with a total Rating of " + this.shoppingCart.getTotalRating() + " ****");
        logger.info("-------------------");
    }

    private Comparator<Item> getShoppingCartComparator() {
        return Comparator.comparing(Item::getName);
    }
    
    private ArrayList<Item> removeItemsWithCategory(ArrayList<Item> items, String categoryToRemove){
        for (Iterator<Item> iterator = items.iterator(); iterator.hasNext();) {
            Item item = iterator.next();
            if(item != null){
                if(item.getCategory().equalsIgnoreCase(categoryToRemove)){
                    iterator.remove();
                }
            }
        }
        return items;
    }
}
