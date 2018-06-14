import React, {Component} from "react";
import {
    Dimensions,requireNativeComponent, ViewPropTypes, UIManager
} from "react-native";
import PropTypes from "prop-types";
import ReactNative from "react-native";



const screenWidth = Dimensions.get('window').width;

const BANNER_REF = 'BANNER_REF';
const COMMAND_START_LOOP = 1;
const COMMAND_STOP_LOOP = 2;

/**
 * @author BoQin
 * @date 2018-06-13
 */
export default class AndroidBanner extends Component {

    static propTypes = {
        ...ViewPropTypes,
        imageUrls: PropTypes.arrayOf(PropTypes.string).isRequired,
        /**
         * 循环时间
         */
        autoPlayInterval: PropTypes.number,
        /**
         * 自动播放
         */
        autoPlayEnable: PropTypes.bool,
        clickCallback: PropTypes.func,
        interval : PropTypes.number,

    };

    static defaultProps={
        autoPlayInterval: 3000,
        autoPlayEnable: true,
    };

    constructor(props) {
        super(props);
    }

    componentWillUnmount(){
        this.stopLoop();
    }

    /**
     * 开始循环
     */
    startLoop = ()=>{
        UIManager.dispatchViewManagerCommand(
            this._getHandle(),
            COMMAND_START_LOOP,
            null
        );
    };

    /**
     * 停止循环
     */
    stopLoop = ()=>{
        UIManager.dispatchViewManagerCommand(
            this._getHandle(),
            COMMAND_STOP_LOOP,
            null
        );
    };


    render() {
        const height = (this.props.style && this.props.style.height) || 170;
        const width = (this.props.style && this.props.style.width) || screenWidth;
        let {imageUrls, autoPlayInterval, autoPlayEnable, clickCallback} = this.props;
        return (
            <RCTBanner
                style={{height:height, width:width}}
                interval={autoPlayInterval}
                onPress={(event)=>{
                    clickCallback && clickCallback(event.nativeEvent.index);
                }}
                playEnable={autoPlayEnable}
                dataSet={imageUrls}
                ref={BANNER_REF}
            >
            </RCTBanner>
        );
    }

    _getHandle = ()=>{
        return ReactNative.findNodeHandle(this.refs[BANNER_REF]);
    }

}


const RCTBanner = requireNativeComponent('BannerView', AndroidBanner, {
    nativeOnly: {
        interval: true,
        dataSet: true,
        onPress: true,
        playEnable: true
    }
});