package com.example.shopapplication.slice;

import com.example.shopapplication.ResourceTable;
import com.example.shopapplication.detail.ShootMainTopBanner;
import com.example.shopapplication.utils.Util;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;
import ohos.agp.components.Image;
import ohos.agp.components.Text;
import ohos.agp.utils.LayoutAlignment;
import ohos.agp.window.dialog.ListDialog;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;

import static ohos.agp.components.ComponentContainer.LayoutConfig.MATCH_CONTENT;

public class ShoppingDetailSlice extends AbilitySlice {
    private static final HiLogLabel LOG = new HiLogLabel(HiLog.LOG_APP, 0x00101, "ShoppingDetailSlice");
    private Text location;

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);
        initView();
    }

    private void initView() {
        Image image = (Image) findComponentById(ResourceTable.Id_back_detail);
        image.setClickedListener(cpt -> terminate());
        ShootMainTopBanner shootMainTopBanner = new ShootMainTopBanner(this);
        shootMainTopBanner.setImages(ResourceTable.Media_intro1, ResourceTable.Media_intro2, ResourceTable.Media_intro3)
                .start();
        location = (Text) findComponentById(ResourceTable.Id_location);
        location.setClickedListener(cpt -> getUITaskDispatcher().asyncDispatch(this::showLocationDialog));
        Button btnAddToCart = (Button) findComponentById(ResourceTable.Id_btn_add_to_cart);
        btnAddToCart.setClickedListener(cpt -> getUITaskDispatcher().asyncDispatch(this::showAddToCartDialog));
    }


    @Override
    public void onActive() {
        super.onActive();
    }

    @Override
    protected void onInactive() {
        super.onInactive();
    }

    private void showAddToCartDialog() {
        ListDialog dialog = new ListDialog(this);
        dialog.setTitleText("  Add to cart:");
        String[] styles = new String[]{"    style1", "    style2", "    style3"};
        dialog.setItems(styles);
        dialog.setAlignment(LayoutAlignment.CENTER);
        dialog.setSize(Util.toPixes(300, this), MATCH_CONTENT);
        dialog.setOnSingleSelectListener((iDialog, index) -> {
            Util.showToast(this, "You selected: " + styles[index].trim());
            iDialog.destroy();
        });
        dialog.show();
    }

    private void showLocationDialog() {
        ListDialog listDialog = new ListDialog(this);
        String[] locations = new String[]{"    location0", "    location1", "    location2"};
        listDialog.setItems(locations);
        listDialog.setTitleText("  Choose a location:");
        listDialog.setAlignment(LayoutAlignment.CENTER);
        listDialog.setSize(Util.toPixes(300, this), MATCH_CONTENT);
        listDialog.setOnSingleSelectListener((iDialog, i) -> {
            location.setText("Shipment To:" + locations[i]);
            iDialog.destroy();
        });
        listDialog.show();
    }
}
