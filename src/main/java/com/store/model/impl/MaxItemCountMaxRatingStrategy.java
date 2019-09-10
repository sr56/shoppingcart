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
package com.store.model.impl;

import com.store.StoreUtil;
import com.store.model.Item;
import com.store.model.ShoppingStrategy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import org.jboss.logging.Logger;
import org.jboss.logging.Logger.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Suraj
 */
@Component
public class MaxItemCountMaxRatingStrategy implements ShoppingStrategy {

    private double shoppingTotalMin = 0.0;
    private double shoppingTotalMax = 0.0;
    private int shoppingRatingMin = 0;
    private int shoppingRatingMax = 0;
    private int shoppingCountMin = 0;
    private int shoppingCountMax = 0;
    
    @Autowired
    StoreUtil storeUtil;
    
    Logger logger = Logger.getLogger(MaxItemCountMaxRatingStrategy.class);

    @Override
    public ArrayList<Item> prioritizeItems(ArrayList<Item> itemList) {
        logger.debug("=================================================");        
        Collections.sort(itemList, getMaxItemCountMaxRatingComparator());
        logger.debug("------------ ");
        logger.debug("PriorityList");
        logger.debug("------------ ");
        storeUtil.print(itemList, true, Level.DEBUG);
        logger.debug("=================================================");
        return itemList;
    }

    @Override
    public void setShoppingAmountLimits(double minAmount, double maxAmount) {
        this.shoppingTotalMin = minAmount;
        this.shoppingTotalMax = maxAmount;
    }

    @Override
    public void setShoppingRatingLimits(int minRating, int maxRating) {
        this.shoppingRatingMin = minRating;
        this.shoppingRatingMax = maxRating;
    }

    @Override
    public void setShoppingNumberLimits(int minCount, int maxCount) {
        this.shoppingCountMin = minCount;
        this.shoppingCountMax = maxCount;
    }

    private Comparator<Item> getMaxItemCountMaxRatingComparator() {
        return Comparator.comparing(Item::getTotalCost)
                .thenComparing(Comparator.comparingInt(Item::getRating).reversed());
    }

    public double getShoppingTotalMin() {
        return shoppingTotalMin;
    }

    public double getShoppingTotalMax() {
        return shoppingTotalMax;
    }

    public int getShoppingRatingMin() {
        return shoppingRatingMin;
    }

    public int getShoppingRatingMax() {
        return shoppingRatingMax;
    }

    public int getShoppingCountMin() {
        return shoppingCountMin;
    }

    public int getShoppingCountMax() {
        return shoppingCountMax;
    }   
    
}
