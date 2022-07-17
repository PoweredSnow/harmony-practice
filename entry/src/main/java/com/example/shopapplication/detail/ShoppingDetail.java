package com.example.shopapplication.detail;

import com.example.shopapplication.slice.ShoppingDetailSlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class ShoppingDetail extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(ShoppingDetailSlice.class.getName());
    }
}
