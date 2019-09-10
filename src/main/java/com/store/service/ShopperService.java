/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.store.service;

import com.store.model.ShoppingStrategy;

/**
 *
 * @author Suraj
 */
public interface ShopperService {
    public void setShoppingStrategy(ShoppingStrategy strategy);
    public void fillShoppingCart();
    public void printShoppingCart();
}
