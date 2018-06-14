/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 * @flow
 */

import React, {Component} from 'react';
import {
    Image,
    Platform,
    StyleSheet,
    Text,
    View
} from 'react-native';
import {BannerView} from "xgrn-baseview";



const instructions = Platform.select({
    ios: 'Press Cmd+R to reload,\n' +
    'Cmd+D or shake for dev menu',
    android: 'Double tap R on your keyboard to reload,\n' +
    'Shake or press menu button for dev menu',
});

const urls = ['https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1528449518226&di=b0534ce7158ca8e51d3a199d934bdce1&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01e8a157f86d8ca84a0d304fcb9943.jpg%402o.jpg',
    'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1528449518225&di=b3a4246dd186af19b17f63bf5c86e486&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F018335598924a2a801215603db0836.png%402o.png',
    'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1528449646201&di=f87bfff31d6c2500e7a312f465d8edfe&imgtype=0&src=http%3A%2F%2Fimage.tianjimedia.com%2FuploadImages%2F2014%2F287%2F32%2F1E2205O0EVSB_1000x500.jpg',
];

type Props = {};
export default class App extends Component<Props> {


    constructor(props) {
        super(props);
        this.state = {
            isShow: false,
        }
    }

    render() {
        return (
            <View style={styles.container}>
                {this._renderAndroid()}
                <Text style={{height:40}} onPress={()=>{
                    this.banner.startLoop()
                }}>开始</Text>
                <Text style={{height:40}} onPress={()=>{
                    this.banner.stopLoop()
                }}>暂停</Text>
            </View>
        );
    }

    _renderAndroid = ()=>{

        // if (Platform.OS === 'ios') {
        //     return UIManager.RCTVirtualText
        //         ? this._renderIOS()
        //         : this._renderIOSLegacy();
        // } else if (Platform.OS === 'android') {
        //     return this._renderAndroid();
        // }

        // return (
        //     this.state.isShow?<LCBannerView style={{width:200, height:100}}
        //
        //     />:null
        // )

        // return (
        //     <BannerView style={{height: 300}}
        //                 autoPlayEnable={true}
        //                 autoPlayInterval={2000}
        //                 clickCallback={(position) => {
        //                     console.warn(position)
        //                 }}
        //                 imageUrls={urls}
        //                 ref={(refs)=>{
        //                     this.banner = refs;
        //                 }}
        //     />
        // )

        return (
            <BannerView
                            style={{width:200, height:200}}
                           autoPlayInterval={2000}
                           clickCallback={(position) => {
                               console.warn(position);
                           }
                           }
                           imageUrls={urls}
                           ref={(refs)=>{
                               this.banner = refs;
                           }}

            />
        )
    };


}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        justifyContent: 'center',
        alignItems: 'center',
        backgroundColor: '#ff1111',
    },
    welcome: {
        fontSize: 20,
        textAlign: 'center',
        margin: 10,
    },
    instructions: {
        textAlign: 'center',
        color: '#333333',
        marginBottom: 5,
    },
});
