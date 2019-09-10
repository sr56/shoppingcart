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

import com.store.model.Item;
import com.store.model.Store;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 *
 * @author Suraj
 */
@Component
@Qualifier("store")
public class StoreImpl implements Store {

    protected ArrayList<Item> items = new ArrayList<Item>();

    @Override
    public void addItem(Item item) {
        this.items.add(item);
    }

    @Override
    public void removeItem(Item item) {
        this.items.remove(item);
    }

    @Override
    public void removeAllItems() {
        this.items.removeIf(item -> true);
    }

    @Override
    public void addAllItems(List<Item> items) {
        this.items.addAll(items);
    }

    @Override
    public ArrayList<Item> getAllItems() {
        return this.items;
    }
}
