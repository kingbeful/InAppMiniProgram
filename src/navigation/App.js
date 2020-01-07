import React, { Component }  from 'react';
import { View, Text, Button } from 'react-native';
import { createAppContainer } from 'react-navigation';
import { createStackNavigator } from 'react-navigation-stack';
import TabNavigator from './TabNavigator'

class HomeScreen extends Component {
  render() {
    return (
       <View style={{ flex: 1, alignItems: 'center', justifyContent: 'center' }}>
        <Text>Home Screen</Text>
        <Button style = {{marginTop: 24}}
          title="Tab Navigator"
          onPress={() => this.props.navigation.navigate('TabNavigator')}
        />
      </View>
    );
  }
}

const AppNavigator = createStackNavigator({
  Home: {
    screen: HomeScreen,
  },
  TabNavigator: {
    screen: TabNavigator
  }
});

export default createAppContainer(AppNavigator);