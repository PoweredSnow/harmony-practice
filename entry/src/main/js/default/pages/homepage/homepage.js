import router from '@system.router';
import prompt from '@system.prompt';

export default {
    data: {
        flag: 1,
        zeroFlag: 0,
        oneFlag: 1,
        twoFlag: 2,
        iconImageFlag: 1,
        totalPrice: 0,
        latestList: [],
        addList: [],
        titleList: ['first', 'second'],
        pageWord: {
            searchKeyWord: '寻找宝贝、店铺',
        },
        icon: {
            buys: "/common/images/homepage/buyRed.png",
            buy: "/common/images/homepage/buy.png",
            buyRed: "/common/images/homepage/buyRed.png",
            shoppingCarts: "/common/images/homepage/shoppingCart.png",
            shoppingCart: "/common/images/homepage/shoppingCart.png",
            shoppingCartRed: "/common/images/homepage/shoppingCartRed.png",
        },
        firstList: [
            {
                title: 'HUAWEI MateBook X Pro 2021款',
                content: '商务轻薄笔记本  ',
                price: '9999',
                imgSrc: "/common/images/homepage/HW1.png"
            }, {
                title: 'HUAWEI Mate 30 RS 保时捷设计',
                content: '致敬时代 ',
                price: '12999',
                imgSrc: "/common/images/homepage/HW2.png"
            }, {
                title: '华为智慧屏 55英寸',
                content: '享3期免息',
                price: '4299',
                imgSrc: "/common/images/homepage/HW3.png"
            }
        ],
        secondList: [
            {
                title: '华为畅享20 Pro',
                content: '购机赠耳机 ',
                price: '1999',
                imgSrc: "/common/images/homepage/HW4.png"
            }, {
                title: '华为智能体脂秤 3',
                content: '享3期免息',
                price: '169',
                imgSrc: "/common/images/homepage/HW5.png"
            }
        ],
        allList: [],
    },
    change(e) {
        this.allList = []
        if (e.index === this.zeroFlag) {
            this.allList = this.firstList
        } else if (e.index === this.oneFlag) {
            this.allList = this.secondList
        }
    },
    detailPage() {
      router.push({
         uri: "pages/shoppingDetailPage/shoppingDetailPage"
      });
    },
    checkboxChecked(index) {
        let totalPrice = 0;
        for (var i = 0; i < this.latestList.length; i++) {
            if (this.addList[i]) {
                totalPrice += parseInt(this.latestList[i].price);
            }
        }
        this.totalPrice = totalPrice;
    },
    addShopping(e){
        for (var index = 0; index < this.latestList.length; index++) {
            if (e.target.attr.value == index) {
                this.addList[index] = !this.addList[index];
                this.checkboxChecked(index);
            }
        }
    },
    buy(){
        let buyList = [];
        for (let index = 0; index < this.latestList.length; index++) {
            if (this.addList[index]) {
                buyList.push(this.latestList[index]);
            }
        }
        if (buyList.length > 0) {
            prompt.showToast({
               message: '购买成功，共花费' + this.totalPrice + '元',
            });
        }else{
            prompt.showToast({
                message: "请选择商品",
            });
        }
    },
    clickBuy() {
        this.iconImageFlag = this.oneFlag;
        this.flag = this.oneFlag;
        this.ifFlag();
    },
    clickShoppingCart() {
        this.iconImageFlag = this.twoFlag;
        this.flag = this.twoFlag;
        this.ifFlag();
    },
    ifFlag() {
        if (this.iconImageFlag === this.oneFlag) {
            this.icon.buys = this.icon.buyRed;
            this.icon.shoppingCarts = this.icon.shoppingCart;
        } else if (this.iconImageFlag === this.twoFlag) {
            this.icon.buys = this.icon.buy;
            this.icon.shoppingCarts = this.icon.shoppingCartRed;
        }
    },
    onInit() {
        this.allList = this.firstList;
        for (let index = 0; index < 10; index++) {
            let item = {
                index: index,
                imageSrc: "./common/images/homepage/tgrh.jpg",
                title: "样品" + (index + 1),
                price: Math.floor(Math.random() * 99 + 1),
            }
            this.latestList.push(item);
            this.addList.push(false);
        }
    },
}
