package com.example.shopapplication.detail;

import com.example.shopapplication.ResourceTable;
import com.example.shopapplication.models.DataDetailModel;
import ohos.aafwk.ability.AbilitySlice;
import ohos.agp.components.*;

import java.util.ArrayList;
import java.util.List;

public class DataListContainer extends BaseItemProvider {
    private final List<DataDetailModel> dataDetail = new ArrayList<>();
    private final AbilitySlice abilitySlice;

    public DataListContainer(AbilitySlice abilitySlice) {
        this.abilitySlice = abilitySlice;
        initData();
    }

    private void initData() {
        dataDetail.add(new DataDetailModel("Display Size:", "13.9 inches"));
        dataDetail.add(new DataDetailModel("Memory:", "16GB"));
        dataDetail.add(new DataDetailModel("Marketing Name:", "HUAWEI MateBook X Pro"));
        dataDetail.add(new DataDetailModel("Color Gamut:", "100%sRGB color gamut (Typical)"));
        dataDetail.add(new DataDetailModel("Battery:", "56 Wh (rated capacity)"));
        dataDetail.add(new DataDetailModel("Storage:", "512GB"));
        dataDetail.add(new DataDetailModel("Resolution:", "3000x2000"));
        dataDetail.add(new DataDetailModel("Processor:", "11th Gen Intel CoreTM i7-1165G7 Processor"));
        dataDetail.add(new DataDetailModel("CPU Cores:", "4"));
        dataDetail.add(new DataDetailModel("Launch Time", "January 2021"));
    }

    @Override
    public int getCount() {
        return dataDetail.size();
    }

    @Override
    public Object getItem(int i) {
        return dataDetail.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public Component getComponent(int i, Component component, ComponentContainer componentContainer) {
        ViewHolder viewHolder = null;
        DirectionalLayout layout = (DirectionalLayout) component;
        if (layout == null) {
            layout = (DirectionalLayout) LayoutScatter.getInstance(abilitySlice.getContext()).parse(ResourceTable.Layout_recycle_item, null, false);
        } else {
            viewHolder = (ViewHolder) layout.getTag();
        }
        viewHolder.title.setText(dataDetail.get(i).getTitle());
        viewHolder.content.setText(dataDetail.get(i).getContent());
        return layout;
    }

    static class ViewHolder {
        private final Text title;
        private final Text content;
        public ViewHolder(Component component) {
            title = (Text) component.findComponentById(ResourceTable.Id_item_title);
            content = (Text) component.findComponentById(ResourceTable.Id_item_content);
        }
    }
}
