
import React, {Component} from 'react'

import PropTypes from "prop-types";
import {requireNativeComponent, ViewPropTypes} from "react-native";


export default class LCBannerView extends Component{

    static displayName = "LCBannerView";

    static propTypes = {
        ...ViewPropTypes,
        interval : PropTypes.number
    };

    static defaultProps ={
        interval : 3000
    };

    render(){

        let { ...props} = this.props;

        return(
            <RCTBanner
                {...props}
                interval={1000}
                dataSet={[]}>
            </RCTBanner>
        )
    }
}

var RCTBanner = requireNativeComponent('RCTBannerView', LCBannerView, {
    nativeOnly: {
        dataSet: true,
    }
});