import router from '@system.router';
import prompt from '@system.prompt';

export default {
    data: {
        scrollAmount: 10,
        loop: '-1',
        oneFlag: 1,
        marqueeDir: 'right',
        textColor: '#FF3536',
        selectCityString: '',
        swiperList: [1, 2, 3],
        newDate: JSON.stringify(new Date()).substring(1,11),
        cityList: [['中国'], ['河北省'], ['石家庄市', '唐山市', '秦皇岛市', '邯郸市', '邢台市', '保定市', '张家口市', '承德市', '沧州市', '廊坊市', '衡水市']],
        selectCityList: [['中国'], ['河北省'], ['石家庄市']],
        contentList: [
            {
                title: '屏幕尺寸',
                content: '13.9英寸',
            }, {
                title: '运行内存',
                content: '16GB',
            }, {
                title: '传播名',
                content: 'HUAWEI MateBook X Pro',
            }, {
                title: '屏幕色彩',
                content: '100% sRGB（典型值）',
            }, {
                title: '电池容量',
                content: '56Wh（额定容量）',
            }, {
                title: '存储容量',
                content: '512GB',
            }, {
                title: '分辨率',
                content: '3000x2000',
            }, {
                title: 'CPU型号',
                content: '第11代英特尔® 酷睿™ i5-1135G7 处理器',
            }, {
                title: 'CPU核数',
                content: '4核',
            }, {
                title: '上市时间',
                content: '2021年1月',
            }
        ],
        pageInfo: {
            annualPrice: '年货节 ￥9999',
            productTitle: '【新品】HUAWEI MateBook X Pro 2021款',
            productIntroduction: '13.9英寸全新11代酷睿i7 16G 512G 商务轻薄笔记本 3K触控全面屏 锐炬显卡 多屏协同 翡冷翠',
            marqueeCustomData: '此商品活动中，请尽快购买！',
            shipment: '发货',
            nextDayReach: '次日达',
            select: '选择',
            buy: '立即购买',
            selectRewardTime: '选择配送时间：',
            selectRewardCity: '选择配送城市：',
        }
    },
    backPage() {
        router.push({
            uri: "pages/homepage/homepage"
        });
    },
    selectCity() {
        this.$element('simpleDialog').show();
    },
    buy() {
        prompt.showToast({
            message: "购买成功！"
        })
    },
    changeDate(e) {
        this.newDate = e.year + '-' + (e.month + this.oneFlag) + '-' + e.day;
    },
    changeCity(e) {
        this.selectCityList = e.newValue;
        this.selectCityString = e.newValue.join('-');
    }
}
