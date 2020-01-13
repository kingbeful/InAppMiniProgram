import React, { Component } from 'react';

import {
  StyleSheet,
  View,
  Text,
  Dimensions
} from 'react-native';
import { createAppContainer } from 'react-navigation'
import { createBottomTabNavigator, BottomTabBar } from 'react-navigation-tabs'
import MaterialCommunityIcons from 'react-native-vector-icons/MaterialCommunityIcons'
import FeatherIcons from 'react-native-vector-icons/Feather'

import RecommendScreen from './RecommendPage'
const { width, height } = Dimensions.get('window')
const TabBarComponent = (props) => (<BottomTabBar {...props} />);
const AppTabNavigator = createBottomTabNavigator (
	  {
      Select: {
        screen: RecommendScreen,
        navigationOptions: {
          
          header: null,
      
          tabBarLabel: '有料',
          tabBarIcon: ({ tintColor, focused }) => (
            <View style={{padding:4, width:48,height:48, borderRadius:24, backgroundColor:'rgb(76, 76, 76)', alignItems:'center', justifyContent:'center'}}>
            <FeatherIcons name={'shopping-cart'} size={24}  color={'white'}/>
            </View>
          ),
          tabBarOnPress: ({ navigation, defaultHandler }) => {
              // do nothing
          },
        }
      },
      ForYou: {
        screen: RecommendScreen,
        navigationOptions: {
          tabBarLabel: '热门',
          tabBarIcon: ({ tintColor, focused }) => {
            return (
              <View style={{padding:4, height:48, alignItems:'center', justifyContent:'flex-end'}}>
                <Text style={{color:tintColor, fontSize:16}}>FOR YOU</Text>
                <View style={{height:2, width:width/4 - 16, marginTop:6, backgroundColor:focused?tintColor:'black'}}/>
              </View>
            )
          }
        }
      },
      Discover: {
        screen: RecommendScreen,
        navigationOptions: {
          tabBarLabel: '热门',
          tabBarIcon: ({ tintColor, focused }) => {
            return (
              <View style={{padding:4, height:48, alignItems:'center', justifyContent:'flex-end'}}>
                <Text style={{color:tintColor, fontSize:16}}>DISCOVER</Text>
                <View style={{height:2, width:width/4 - 16, marginTop:6, backgroundColor:focused?tintColor:'black'}}/>
              </View>
            )
          }
        }
      },
      Me: {
        screen: RecommendScreen,
        navigationOptions: {
          tabBarLabel: '热门',
          tabBarIcon: ({ tintColor, focused }) => {
            return (
              <View style={{padding:4, height:48, alignItems:'center', justifyContent:'flex-end'}}>
                <Text style={{color:tintColor, fontSize:16}}>ME</Text>
                <View style={{height:2, width:width/4 - 16, marginTop:6, backgroundColor:focused?tintColor:'black'}}/>
              </View>
            )
          }
        }
      },
      
      
    },
    {
      tabBarPosition: 'bottom',
      lazy: false,
      removeClippedSubviews: true,
      initialRouteName: 'ForYou',
      backBehavior: null,
      // tabBarComponent: (props) =>
      //   <TabBarComponent
      //     {...props}
      //     style={{ borderTopColor: '#605F60' }}
      //   />,
      tabBarOptions: {
        activeTintColor: 'white',
        inactiveTintColor: 'gray',
        showIcon: true,
        showLabel: false,
        // pressOpacity: 0.8,
        style: {
          // borderTopWidth: 0.5,
          // borderTopColor: '#EEEEEE',
          backgroundColor: 'black'
        },
        // labelStyle: {
        //   fontSize: 11,
        //   paddingVertical: 0,
        //   marginTop: -4
        // },
        // labelStyle: {width: 200},
        tabStyle: {
          backgroundColor: 'black'
        },
        indicatorStyle:{
          backgroundColor: 'red'
        },
        safeAreaInset: {bottom: 'always'}
      }
    }
)

const styles = StyleSheet.create({

});

export default createAppContainer(AppTabNavigator)
