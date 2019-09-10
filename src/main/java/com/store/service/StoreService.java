/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.store.service;

/**
 *
 * @author Suraj
 */
public interface StoreService {
    public void setCategoryCount(int categoryCount);
    public int getCategoryCount();
    public void setItemPerCategoryCount(int itemPerCategoryCount);
    public int getItemPerCategoryCount();
    public void initializeStore();
    public void resetStore();
    public void printStore();
}
