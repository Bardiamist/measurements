/* eslint-disable react-native/no-inline-styles */
import React, {useState, useCallback, useEffect} from 'react';
import {Dimensions, StatusBar, View, Text} from 'react-native';
import DeviceInfo from 'react-native-device-info';

const screenDimensions = Dimensions.get('screen');
const windowDimensions = Dimensions.get('window');

export default () => {
  const [layoutHeight, setLayoutHeight] = useState(0);
  const [isLandscape, setIsLandscape] = useState(false);

  const onLayout = useCallback(({nativeEvent: {layout}}) => {
    setLayoutHeight(layout.height);
  }, []);

  useEffect(() => {
    DeviceInfo.isLandscape().then((nextIsLandscape) => {
      setIsLandscape(nextIsLandscape);
    });
  }, []);

  return (
    <View style={{flex: 1, backgroundColor: 'blue'}} onLayout={onLayout}>
      <View style={{margin: 8, flex: 1, backgroundColor: 'dodgerblue'}}>
        <Text style={{margin: 32, backgroundColor: 'white'}}>
          {JSON.stringify({
            hasNotch: DeviceInfo.hasNotch(),
            isLandscape,
            layoutHeight,
            screenHeight: screenDimensions.height,
            windowHeight: windowDimensions.height,
            statusBarHeight: StatusBar.currentHeight,
          })}
        </Text>
      </View>
    </View>
  );
};
