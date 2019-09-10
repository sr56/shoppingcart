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
import com.store.model.ShoppingCart;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 *
 * @author Suraj
 */
@Component 
@Qualifier("shoppingcart")
public class ShoppingCartImpl extends StoreImpl implements ShoppingCart {
    
    @Autowired
    StoreUtil storeUtil;

    @Override
    public double getTotalItemPrice() {
        ArrayList<Item> items = this.getAllItems();
        if(items == null || items.size() == 0){
            return 0.0;
        }
        double totalItemPrice = items
                .stream()
                .map(Item::getPrice)
                .reduce(0.0d, (total, current) -> total + current);
        return totalItemPrice;
//        return storeUtil.roundToTwoDecimal(totalItemPrice);
    }

    @Override
    public double getTotalShippingCost() {
        ArrayList<Item> items = this.getAllItems();
        if(items == null || items.size() == 0){
            return 0.0;
        }
        double totalShippingCost = items
                .stream()
                .map(Item::getShippingCost)
                .reduce(0.0d, (total, current) -> total + current);
        return storeUtil.roundToTwoDecimal(totalShippingCost);
    }

    @Override
    public double getTotalAmount() {
        ArrayList<Item> items = this.getAllItems();
        if(items == null || items.size() == 0){
            return 0.0;
        }
        double totalAmt = items
                .stream()
                .mapToDouble(item -> item.getPrice() + item.getShippingCost())
                .reduce(0.0d, (total, current) -> total + current);
        return storeUtil.roundToTwoDecimal(totalAmt);
    }

    @Override
    public int getTotalRating() {
        ArrayList<Item> items = this.getAllItems();
        if(items == null || items.size() == 0){
            return 0;
        }
        return items
                .stream()
                .map(Item::getRating)
                .reduce(0, (total, current) -> total + current);
    }

    @Override
    public ArrayList<String> getAllCategoriesInCart() {
        ArrayList<Item> items = this.getAllItems();
        if(items == null || items.size() == 0){
            return new ArrayList<String>();
        }
        List<String> categoryList = items
                .stream()
                .map(Item::getCategory)
                .collect(Collectors.toList());
        return new ArrayList<String>(categoryList);
    }

    @Override
    public int getTotalCountOfItems() {
        ArrayList<Item> items = this.getAllItems();
        if(items == null){
            return 0;
        }
        return items.size();
    }
}
