package com.example.shopapplication.slice;

import com.example.shopapplication.ResourceTable;
import com.example.shopapplication.models.Product;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.*;

import java.util.ArrayList;
import java.util.List;

public class MainAbilitySlice extends AbilitySlice {
    private List<Product> list;

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main2);
        // 商品列表
        ListContainer listContainer = (ListContainer) findComponentById(ResourceTable.Id_lc_product);
        initData();
        listContainer.setItemProvider(new BaseItemProvider() {
            //列表项数
            @Override
            public int getCount() {
                return list.size();
            }

            //列表数据
            @Override
            public Object getItem(int i) {
                return list.get(i);
            }

            @Override
            public long getItemId(int i) {
                return i;
            }

            @Override
            public Component getComponent(int i, Component component, ComponentContainer componentContainer) {
                DirectionalLayout directionalLayout = (DirectionalLayout) component;
                if(directionalLayout == null){
                    directionalLayout = (DirectionalLayout) LayoutScatter.getInstance(getContext()).parse(ResourceTable.Layout_item_product, null, false);
                }
                Image image = (Image) directionalLayout.findComponentById(ResourceTable.Id_image_photo);
                Text name = (Text) directionalLayout.findComponentById(ResourceTable.Id_text_number_name);
                Text price = (Text) directionalLayout.findComponentById(ResourceTable.Id_text_price);
                Text introduce = (Text) directionalLayout.findComponentById(ResourceTable.Id_text_introduce);
                image.setPixelMap(list.get(i).getImg());
                name.setText(((Product)getItem(i)).getName());
                price.setText("￥:" + ((Product)getItem(i)).getPrice() + "");
                introduce.setText(((Product)getItem(i)).getIntroduceStr());
                return directionalLayout;
            }
        });
        listContainer.setItemClickedListener((container, component, position, id) -> {
            present(new ShoppingDetailSlice(), new Intent());
        });
    }

    private void initData() {
        list = new ArrayList<Product>();
        for(int i = 0; i < 10; i++){
            Product product = new Product(ResourceTable.Media_photo, i , "古风旗袍" , 123.2 , "越是简单基础的版型越考验工艺的精巧，我们的设计师和多年经验的车版师对腰身、衣板多次斟酌调整");
            list.add(product);
        }
    }

    @Override
    public void onActive() {
        super.onActive();
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }
}
