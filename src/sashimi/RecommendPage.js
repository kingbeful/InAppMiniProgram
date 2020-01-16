'use strict';

import React, { Component } from 'react';

import {
  StyleSheet,
  View,
  StatusBar,
  Text,
  TextInput,
  FlatList,
  Image,
  TouchableOpacity,
  Animated,
  Dimensions
} from 'react-native';
import FeatherIcons from 'react-native-vector-icons/Feather'
import FontAwesome from 'react-native-vector-icons/FontAwesome'
import LinearGradient from 'react-native-linear-gradient'
import { getStatusBarHeight } from './Utils';
const Header_HEIGHT = 64
const { width, height } = Dimensions.get('window')
const STATUSBAR_HEIGHT = getStatusBarHeight(true)

const ITEM_WIDTH = width - 64
const ITEM_GAP = 16
const SNAP_DISTANCE = ITEM_WIDTH + ITEM_GAP

const data = [
  {name:'Salmon Sashimi', jpName:'サーモン刺身', src: require('./images/sashimi/salmon.png'), price:42},
  {name:'Tuna Sashimi', jpName:'まぐろ刺身', src: require('./images/sashimi/tuna.png'), price:40},
  {name:'Red Shrimp Sashimi', jpName:'赤えび刺身', src: require('./images/sashimi/redshrimp.png'), price:32},
  {name:'Egg', jpName:'たまご', src: require('./images/sushi/egg.png'), price:12},
  {name:'Octopus', jpName:'たこ', src: require('./images/sushi/octopus.png'), price:17},
  {name:'Salmon', jpName:'サーモン', src: require('./images/sushi/salmon.png'), price:14},
  {name:'Sweet Shrimp', jpName:'甘えび', src: require('./images/sushi/sweetshrimp.png'), price:19},
  {name:'Tuna', jpName:'まぐろ', src: require('./images/sushi/tuna.png'), price:19}
]

const comments = [
  {name: 'Ablee Morcy', text: 'It\'s delecious', avatar: require('./images/avatar/avatar1.jpg')},
  {name: 'Blue Odden', text: 'I love this sashimi', avatar: require('./images/avatar/avatar2.jpg')},
  {name: 'Cavin Pattric', text: 'Will come next time', avatar: require('./images/avatar/avatar3.jpg')},
  {name: 'David Rich', text: 'This is the best sashimi I ever had', avatar: require('./images/avatar/avatar4.jpg')},
  {name: 'Eric Santos', text: 'It\'s delecious', avatar: require('./images/avatar/avatar5.jpg')},
  {name: 'Fabidal Terna', text: 'I love this sashimi', avatar: require('./images/avatar/avatar6.jpg')},
  {name: 'Galleo\'s Uncle', text: 'Will come next time', avatar: require('./images/avatar/avatar7.jpg')},
  {name: 'Henry Veca', text: 'This is the best sashimi I ever had', avatar: require('./images/avatar/avatar8.jpg')}
]


class RecommendPage extends Component {
  constructor() {
    super()
    this.state = {
      scrollX: new Animated.Value(0)
    }
  }

  renderItem = ({ item, index, separators }) => {

    const scale = this.state.scrollX.interpolate({
        inputRange: [SNAP_DISTANCE*(index-1), SNAP_DISTANCE*index, SNAP_DISTANCE*(index+1)],
        outputRange: [0.3, 1, 0.3],
        extrapolate: 'clamp'
    });
    const rotate = this.state.scrollX.interpolate({
        inputRange: [SNAP_DISTANCE*(index-1), SNAP_DISTANCE*index, SNAP_DISTANCE*(index+1)],
        outputRange: ['-45deg', '0deg', '45deg'],
        extrapolate: 'clamp'
    });
    return (
      <View style={{flex:1, alignItems:'flex-end'}}> 
        <LinearGradient start={{x: 0, y: 0}} end={{x: 1, y: 1}} colors={['#4C4C4C', '#3C3C3C', '#2C2C2C']} style={styles.linearGradient}>
        
          <Image style={{position:'absolute', top:-72, left:-128}} opacity={0.2} source={item.src}/>
            
          <View style={{marginTop: width-240, flex:1, alignItems:'center'}}>
            <View style={{flexDirection:'row', alignItems:'center', marginTop:8}}>
              <Text style={{color:'gray', fontSize:14, marginRight:8}}>4.5</Text>
              <FontAwesome name={'star'} size={16}  color={'#C7A054'}/>
              <FontAwesome name={'star'} size={16}  color={'#C7A054'}/>
              <FontAwesome name={'star'} size={16}  color={'#C7A054'}/>
              <FontAwesome name={'star'} size={16}  color={'#C7A054'}/>
              <FontAwesome name={'star-half-o'} size={16}  color={'#C7A054'}/>
            </View>
            <Text style={{fontSize:32, color:'white', marginTop:16}}>{item.name}</Text>
            <Text style={{fontSize:20, color:'gray', marginTop:8}}>{item.jpName}</Text>
            <View style={{flex:1, paddingBottom:24, justifyContent:'flex-end'}}>
              <TouchableOpacity activeOpacity={0.8} onPress={()=>{}}>
                <LinearGradient start={{x: 0, y: 0}} end={{x: 1, y: 0}} colors={['#DCA56A', '#BF380C']} style={styles.buttonGradient}>
                  <Text style={{fontSize:16, color:'white'}}>
                    {`$${item.price} / Add to Cart`}
                  </Text>
                </LinearGradient>
              </TouchableOpacity>
            </View>
          </View>
        </LinearGradient>
        <Animated.Image style={{position:'absolute', top:-48, left:0, 
            width:ITEM_WIDTH, height:ITEM_WIDTH, 
            transform: [{scaleX: scale}, {scaleY: scale}, {rotateZ:rotate}]
            }} resizeMode={'contain'} source={item.src}/>
      </View>)
    }

  renderCommentItem = ({ item, index, separators }) => {
    return (
      <View style={{height:Header_HEIGHT+16, width:width, paddingHorizontal:32, flexDirection: 'row', alignItems: 'center'}}>
        <View style={{alignItems: 'flex-start', flex:1}}>
          <Text style={{fontSize:24, color:'white'}}> {item.name} </Text>
          <Text style={{fontSize:18, color:'gray', marginTop:8, marginLeft:8}}>{item.text}</Text>
        </View>
        <Image style={{width:48, height:48, borderRadius:24, borderColor: 'white', borderWidth: 2}} source={item.avatar}/>
      </View>)
    }

  getSeparator = () => {
    return <View style={{backgroundColor:'transparent', width:ITEM_GAP}} />
  }

  renderHeader = () => {
    return (
      <View style={{ backgroundColor: 'transparent', width:ITEM_GAP*2}} />
    )
  }

  onViewItemChanged = ({viewableItems, changed}) => {
    console.log(viewableItems, changed)
  }

  render() {
    // const viewOpacity = this.state.scrollX.interpolate({
    //     inputRange: [0, width],
    //     outputRange: [0, 1],
    //     extrapolate: 'clamp'
    // });
    return (
      <View style={{flex:1, backgroundColor:'black'}}>
        <StatusBar barStyle="light-content" />
        <View style={styles.headerContainer}>
          <View style={styles.searchContainer}>
            <FeatherIcons name={'search'} size={20}  color={'white'}/>
            <TextInput
              ref='searchTextInput'
              style={{flex:1, color:'white', marginLeft:8, paddingTop: 0, paddingBottom: 0}}
              placeholder='Salmon'
              placeholderTextColor='#999999'
              underlineColorAndroid='transparent'
              returnKeyType='go'
            />
          </View>
        </View>
        <View style={{height:Header_HEIGHT+16, backgroundColor:'black'}}>
          <FlatList
            ref={ref => this.flatListRef = ref}
            pagingEnabled
            data={comments}
            renderItem={this.renderCommentItem}
            keyExtractor={item => item.name}
            scrollEnabled={false}
          />
        </View>
      	<FlatList
          data={data}
          decelerationRate={'fast'}
          snapToInterval={SNAP_DISTANCE}
          snapToAlignment={"start"}
          onMomentumScrollBegin={()=> {console.log('onMomentumScrollBegin')}}
          onMomentumScrollEnd={(e)=>{
            const x = e.nativeEvent.contentOffset.x
            const index = Math.floor(x/SNAP_DISTANCE)
            console.log(x, index)
            this.flatListRef.scrollToIndex({animated:true, index:index, viewOffset:0, viewPosition:0})
          }}
          onViewableItemsChanged={this.onViewItemChanged}
          ListHeaderComponent={this.renderHeader}
          ListFooterComponent={this.renderHeader}
          ItemSeparatorComponent={this.getSeparator}
          horizontal
          showsHorizontalScrollIndicator={false}
          renderItem={this.renderItem}
          keyExtractor={(item, index) => index.toString()}
          onScroll={Animated.event(
              [{ nativeEvent: {
                   contentOffset: {
                     x: this.state.scrollX
                   }
                 }
              }])}
        />
      </View>
    );
  }
}

const styles = StyleSheet.create({
  headerContainer: {
    height:Header_HEIGHT,
    alignItems: 'center',
    justifyContent:'center',
    marginTop:STATUSBAR_HEIGHT
  },
  searchContainer: {
    width:width-64,
    height:Header_HEIGHT-24,
    borderRadius:Header_HEIGHT/2 - 12,
    flexDirection: 'row',
    alignItems:'center',
    paddingHorizontal:16,
    backgroundColor:'rgb(76, 76, 76)'
  },
  linearGradient:{
    width:ITEM_WIDTH,
    flex:1,
    borderRadius:16,
    marginTop:Header_HEIGHT,
    marginBottom:Header_HEIGHT/2
  },
  buttonGradient:{
    width:width/2,
    height:32,
    borderRadius:16,
    marginTop:32,
    alignItems:'center',
    justifyContent: 'center'
  }
});


export default RecommendPage;