package com.example.shopapplication.detail;

import com.example.shopapplication.ResourceTable;
import com.example.shopapplication.utils.Util;
import ohos.aafwk.ability.AbilitySlice;
import ohos.agp.colors.RgbColor;
import ohos.agp.components.*;
import ohos.agp.components.element.ShapeElement;
import ohos.app.dispatcher.TaskDispatcher;
import ohos.app.dispatcher.task.TaskPriority;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import ohos.multimodalinput.event.TouchEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ShootMainTopBanner {
    private static final HiLogLabel LOG = new HiLogLabel(HiLog.LOG_APP, 0x00101, "ShoppingDetailBanner");
    private TaskDispatcher globalTaskDispatcher;
    private final AbilitySlice mAbilitySlice;
    private PageSlider pageSlider;
    private DependentLayout dependentLayout;
    private final ArrayList<Component> images = new ArrayList<>();
    private ScheduledExecutorService scheduledExecutorService;
    private int dataSize;
    private Integer index = 1;
    private final Integer PAGE_SWITCH_TIME = 500;
    private boolean isAutoSlide = true;
    private static final Integer TIME_INTERVAL = 5000;
    private final AutoSwitchPage autoSwitchPage = new AutoSwitchPage();

    public ShootMainTopBanner(AbilitySlice mAbilitySlice) {
        this.mAbilitySlice = mAbilitySlice;
    }

    public ShootMainTopBanner setImages(Integer ... imageId) {
        List<Image> data = new ArrayList<>();
        for (Integer id : imageId) {
            Image image = new Image(mAbilitySlice.getContext());
            image.setLayoutConfig(new ComponentContainer.LayoutConfig(Util.getDisplayWidthInPx(mAbilitySlice.getContext()), ComponentContainer.LayoutConfig.MATCH_PARENT));
            image.setPixelMap(id);
            data.add(image);
        }
        // 组装数据，顺序为 3 1 2 3 1
        images.add(data.get(data.size() - 1));
        images.addAll(data);
        images.add(data.get(0));
        dataSize = images.size();
        return this;
    }

    public void start() {
        globalTaskDispatcher = mAbilitySlice.getGlobalTaskDispatcher(TaskPriority.DEFAULT);
        pageSlider = (PageSlider) mAbilitySlice.findComponentById(ResourceTable.Id_page_slider);
        pageSlider.setProvider(new PageSliderProvider() {
            @Override
            public int getCount() {
                return dataSize;
            }

            @Override
            public Object createPageInContainer(ComponentContainer componentContainer, int i) {
                componentContainer.addComponent(images.get(i));
                return images.get(i);
            }

            @Override
            public void destroyPageFromContainer(ComponentContainer componentContainer, int i, Object o) {
                componentContainer.removeComponent(images.get(i));
            }

            @Override
            public boolean isPageMatchToObject(Component component, Object o) {
                return component == o;
            }
        });
        pageSlider.setCurrentPage(index, true);
        pageSlider.setPageSwitchTime(PAGE_SWITCH_TIME);
        pageSlider.setReboundEffect(true);
        initPoints();
        pageSlider.addPageChangedListener(new PageSlider.PageChangedListener() {
            @Override
            public void onPageSliding(int i, float v, int i1) {

            }

            @Override
            public void onPageSlideStateChanged(int i) {

            }

            @Override
            public void onPageChosen(int i) {
                index = i;
                if (!isAutoSlide) {
                    if (index == dataSize - 1) {
                        index = 1;
                        pageSlider.setCurrentPage(index, false);
                    }
                    if (index == 0) {
                        index = dataSize - 2;
                        pageSlider.setCurrentPage(index, false);
                    }
                }
                selectedPoint(index);
            }
        });
        startPlaying();
        pageSlider.setTouchEventListener((component, touchEvent) -> {
            int action = touchEvent.getAction();
            // 最后一根手指抬起、取消、没有触摸活动时，启动定时器
            if (action == TouchEvent.PRIMARY_POINT_UP || action == TouchEvent.CANCEL || action == TouchEvent.NONE ) {
                isAutoSlide = true;
                startPlaying();
            } else if (action == TouchEvent.PRIMARY_POINT_DOWN) {
                // 存在触摸时，停止自动切换
                isAutoSlide = false;
                stopPlaying();
            }
            return true;
        });
    }

    private void selectedPoint(Integer index) {
        for (int i = 0; i < dataSize - 1; i++) {
            Button point = (Button) dependentLayout.getComponentAt(i);
            if (i == index) {
                point.setBackground(createOvalShape(new RgbColor(0, 120, 215)));
            } else {
                point.setBackground(createOvalShape(new RgbColor(211, 211, 211)));
            }
        }
    }

    private void initPoints() {
        dependentLayout = (DependentLayout) mAbilitySlice.findComponentById(ResourceTable.Id_points);
        List<Button> points = new ArrayList<>();
        for (int i = 0; i < dataSize - 1; i++) {
            Button point = new Button(mAbilitySlice.getContext());
            DependentLayout.LayoutConfig pointConfig = new DependentLayout.LayoutConfig(Util.toPixes(10, mAbilitySlice.getContext()), Util.toPixes(10, mAbilitySlice.getContext()));
            point.setLayoutConfig(pointConfig);
            point.setMarginRight(Util.toPixes(10, mAbilitySlice.getContext()));
            point.setId(i);
            if (i == 1) {
                point.setBackground(createOvalShape(new RgbColor(0, 120, 215)));
                pointConfig.addRule(DependentLayout.LayoutConfig.RIGHT_OF, points.get(i - 1).getId());
            } else if (i != 0) {
                point.setBackground(createOvalShape(new RgbColor(211, 211, 211)));
                pointConfig.addRule(DependentLayout.LayoutConfig.RIGHT_OF, points.get(i - 1).getId());
            }
            dependentLayout.addComponent(point);
            points.add(point);
        }
    }

    private ShapeElement createOvalShape(RgbColor rgbColor) {
        ShapeElement background = new ShapeElement();
        background.setShape(ShapeElement.OVAL);
        background.setRgbColor(rgbColor);
        return background;
    }

    private void startPlaying() {
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleWithFixedDelay(autoSwitchPage, TIME_INTERVAL, TIME_INTERVAL, TimeUnit.MILLISECONDS);
    }

    private void stopPlaying() {
        scheduledExecutorService.shutdown();
    }

    class AutoSwitchPage implements Runnable {
        @Override
        public void run() {
            mAbilitySlice.getUITaskDispatcher().asyncDispatch(() -> {
                index++;
                pageSlider.setCurrentPage(index, true);
                if (index == dataSize - 1) {
                    // 切换到第一个位置，且立即切换
                    globalTaskDispatcher.asyncDispatch(new SlideShowTaskLast());
                }
            });
        }
    }

    class SlideShowTaskLast implements Runnable {
        @Override
        public void run() {
            try {
                // pageSwitchTime
                Thread.sleep(PAGE_SWITCH_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            mAbilitySlice.getUITaskDispatcher().syncDispatch(() -> {
                index = 1;
                pageSlider.setCurrentPage(index, false);
            });
        }
    }
}
