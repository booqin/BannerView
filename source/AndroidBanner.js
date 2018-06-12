import React, {Component} from "react";
import {
    StyleSheet, View, ViewPagerAndroid, Dimensions, Image,
    TouchableWithoutFeedback
} from "react-native";
import PropTypes from "prop-types";



const screenWidth = Dimensions.get('window').width;

/**
 * @author leslie.fang
 * @date 2017-03-22
 */
export default class AndroidBanner extends Component {

    static propTypes = {
        imageUrls: PropTypes.arrayOf(PropTypes.string).isRequired,
        /**
         * 循环时间
         */
        autoPlayInterval: PropTypes.number,
        /**
         * 自动播放
         */
        autoPlayEnable: PropTypes.bool,
        clickCallback: PropTypes.func
    };

    static defaultProps={
        autoPlayInterval: 3000,
        autoPlayEnable: false,
    };

    constructor(props) {
        super(props);
        this._renderPagers = this._renderPagers.bind(this);
        this._renderCircles = this._renderCircles.bind(this);
        this._onPageSelected = this._onPageSelected.bind(this);
        this.state = {
            currentPage: 0
        };
    }

    componentDidMount(){
        this._initTimeOut();
    }

    componentWillUnmount(){
        this.timer && clearInterval();
    }




    render() {
        const height = this.props.style.height || 170;
        const width = this.props.style.width || screenWidth;
        return (
            <View style={{height:height, width:width}}>
                <ViewPagerAndroid
                    initialPage={1}
                    style={{height: height}}
                    ref={viewPager => { this.viewPager = viewPager;  }}
                    onPageSelected={this._onPageSelected}
                >
                    {this._renderPagers()}
                </ViewPagerAndroid>
                {this._renderCircles()}
            </View>
        );
    }

    _renderPagers() {
        const imageUrls = this.props.imageUrls;
        if (imageUrls === null || imageUrls.length === 0) {
            return null;
        }

        let len = imageUrls.length;
        let pagers = [];

        if (len === 1) {
            pagers.push(this._renderPagerItem(imageUrls[0], 0, 0));
        } else {
            // 最后一页插入到第 0 个位置
            pagers.push(this._renderPagerItem(imageUrls[len - 1], 0, len - 1));
            for (let i = 0; i < len; i++) {
                pagers.push(this._renderPagerItem(imageUrls[i], i + 1, i));
            }
            // 第 0 页插入到最后一个位置
            pagers.push(this._renderPagerItem(imageUrls[0], len + 1, 0));
        }

        return pagers;
    }

    _renderPagerItem(url, key, position) {
        const height = this.props.style.height || 170;
        const width = this.props.style.width || screenWidth;
        return (
            <View key={key} style={{flex:1,flexDirection:'row', height:height}}>
                <TouchableWithoutFeedback onPress={()=>{
                    this.props.clickCallback && this.props.clickCallback(position);
                }}>
                <Image source={{uri:url}} style={{width:width}}/>
                </TouchableWithoutFeedback>
            </View>
        );
    }

    _renderCircles() {
        const imageUrls = this.props.imageUrls;
        if (imageUrls === null || imageUrls.length <= 1) {
            return null;
        }

        let len = imageUrls.length;
        let circles = [];
        for (let i = 0; i < len; i++) {
            let style = styles.circle;
            if (this.state.currentPage === i) {
                style = [styles.circle, styles.circleSelected];
            }
            circles.push((<View style={style} key={i}/>));
        }

        return (
            <View style={styles.circleContainer}>
                {circles}
            </View>
        );
    }

    _onPageSelected(e) {
        const len = this.props.imageUrls.length;
        if (len === 1) {
            return;
        }

        let position = e.nativeEvent.position;
        let currentPage;
        if (position === 0) {
            // 当到第 0 页时跳转到倒数第二页
            currentPage = len - 1;
            this.viewPager.setPageWithoutAnimation(len);
        } else if (position === len + 1) {
            // 当到最后一页时跳转到第一页
            currentPage = 0;
            this.viewPager.setPageWithoutAnimation(1);
        } else {
            currentPage = position - 1;
        }

        this.setState({
            currentPage: currentPage
        });
    }

    _initTimeOut = ()=>{
        //只有一个循环或不知道播放时
        if(this.props.imageUrls.length === 1 || !this.props.autoPlayEnable){
            return
        }
        this.timer = setInterval(()=>{
            let len = this.props.imageUrls.length;
            let position = (this.state.currentPage+1) % len ;
            let nextPage = position + 1;
            this.setState({
                currentPage: position
            },()=>{
                this.viewPager.setPage(nextPage);
            });
        }, this.props.autoPlayInterval?this.props.autoPlayInterval:3000)
    }
}


const styles = StyleSheet.create({
    circleContainer: {
        position: 'absolute',
        width: '100%',
        bottom: 10,
        flexDirection: 'row',
        justifyContent: 'center',
    },
    circle: {
        width: 10,
        height: 10,
        borderRadius: 5,
        backgroundColor: '#4d4d4d',
        marginHorizontal: 5
    },
    circleSelected: {
        backgroundColor: '#fff'
    }
});