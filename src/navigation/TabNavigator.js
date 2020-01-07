import React, { Component } from 'react';
import { Text, View } from 'react-native';
import { createAppContainer } from 'react-navigation';
import { createBottomTabNavigator } from 'react-navigation-tabs';

class Tab1Screen extends Component {
  render() {
    return (
      <View style={{ flex: 1, justifyContent: 'center', alignItems: 'center' }}>
        <Text>Tab 1</Text>
      </View>
    );
  }
}

class Tab2Screen extends Component {
  render() {
    return (
      <View style={{ flex: 1, justifyContent: 'center', alignItems: 'center' }}>
        <Text>Tab 2</Text>
      </View>
    );
  }
}

const TabNavigator = createBottomTabNavigator({
  Tab1: Tab1Screen,
  Tab2: Tab2Screen,
});

export default createAppContainer(TabNavigator);