/*
 * The MIT License
 *
 * Copyright 2019 Suraj.
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
package com.store;

import com.store.model.Item;
import java.util.ArrayList;
import org.jboss.logging.Logger;
import org.jboss.logging.Logger.Level;
import org.springframework.stereotype.Component;

/**
 *
 * @author Suraj
 */
@Component
public class StoreUtil {
   private static final Logger logger = Logger.getLogger(StoreUtil.class);

    public double getRandomAmount(double minAmount, double maxAmount) {
        double result = (Math.random() * ((maxAmount - minAmount) + 1)) + minAmount;
        return roundToTwoDecimal(result);
    }
    
    public int getRandomInt(int minInt, int maxInt){
        return (int)this.getRandomAmount(minInt, maxInt);
    }
    
    public double roundToTwoDecimal(double unRounded){
        return (Math.round(unRounded * 100.0))/100.0;
    }
    
    public void print(ArrayList<Item> items){
        this.print(items, false, Level.DEBUG);
    }
    
    public void print(ArrayList<Item> items, boolean isCart, Level level){
        if(items.size() <= 0){
            return;
        }
        Item prevItem = items.get(0);
        int count = 0;
        for (Item item : items) {
            if((count == 0 || !prevItem.getCategory().equalsIgnoreCase(item.getCategory())) && !isCart){
                logger.log(level, "-----------------------------"); 
                logger.log(level, "{CATEGORY: " + item.getCategory() + "}");
            }
            logger.log(level, item);
            count++;
            prevItem = item;            
        }    
        logger.log(level, "");
    }   
}
