# BannerView
version : 0.0.3

## Android

~~android端使用ViewPagerAndroid实现Banner，通过AndroidBanner.js封装，无需在原生端做额外操作。~~

- 使用原生ViewPager实现，将计时操作在原生中处理

## IOS
TODO

## JS

对应BannerView

暴露开始循环动画和暂停循环动画两个方法：

- startLoop
- stopLoop

主要的4个属性如下：

- imageUrls 字符串数组，图片的url地址
- autoPlayInterval 自动播放间隔，单位为ms
- autoPlayEnable 是否开启自动循环，默认关闭
- clickCallback banner页点击回调，返回点击的index
