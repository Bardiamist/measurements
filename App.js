/* eslint-disable react-native/no-inline-styles */
import React, {useState, useCallback, useEffect} from 'react';
import {Dimensions, StatusBar, NativeModules, View, Text} from 'react-native';
import DeviceInfo from 'react-native-device-info';

const screenDimensions = Dimensions.get('screen');
const windowDimensions = Dimensions.get('window');

const {Measurements} = NativeModules;

export default () => {
  const [layoutHeight, setLayoutHeight] = useState(0);
  const [isLandscape, setIsLandscape] = useState(false);
  const [hasSoftKeys, setHasSoftKeys] = useState(false);
  const [hasSoftKeys2, setHasSoftKeys2] = useState(false);
  const [hasSoftKeys3, setHasSoftKeys3] = useState(false);
  const [hasMenuKey, setHasMenuKey] = useState(false);
  const [hasBackKey, setHasBackKey] = useState(false);
  const [hasHomeKey, setHasHomeKey] = useState(false);

  const onLayout = useCallback(({nativeEvent: {layout}}) => {
    setLayoutHeight(layout.height);
  }, []);

  useEffect(() => {
    DeviceInfo.isLandscape().then((nextIsLandscape) => {
      setIsLandscape(nextIsLandscape);
    });

    Measurements.hasSoftKeys().then((nextHasSoftKeys) => {
      setHasSoftKeys(nextHasSoftKeys);
    });
    Measurements.hasSoftKeys2().then((nextHasPermanentMenuKey) => {
      setHasSoftKeys2(nextHasPermanentMenuKey);
    });
    Measurements.hasSoftKeys3().then((nextHasPermanentMenuKey) => {
      setHasSoftKeys3(nextHasPermanentMenuKey);
    });
    Measurements.hasMenuKey().then((nextHasMenuKey) => {
      setHasMenuKey(nextHasMenuKey);
    });
    Measurements.hasBackKey().then((nextHasBackKey) => {
      setHasBackKey(nextHasBackKey);
    });
    Measurements.hasHomeKey().then((nextHasHomeKey) => {
      setHasHomeKey(nextHasHomeKey);
    });
  }, []);

  const infoString = JSON.stringify({
    hasNotch: DeviceInfo.hasNotch(),
    isLandscape,
    layoutHeight,
    screenHeight: screenDimensions.height,
    windowHeight: windowDimensions.height,
    statusBarHeight: StatusBar.currentHeight,
    SOFT_MENU_BAR_ENABLED: Measurements.SOFT_MENU_BAR_ENABLED,
    hasSoftKeys,
    hasSoftKeys2,
    hasSoftKeys3,
    hasMenuKey,
    hasBackKey,
    hasHomeKey,
  });

  return (
    <View style={{flex: 1, backgroundColor: 'blue'}} onLayout={onLayout}>
      <View style={{margin: 8, flex: 1, backgroundColor: 'dodgerblue'}}>
        <Text style={{margin: 32, backgroundColor: 'white'}}>{infoString}</Text>
      </View>
    </View>
  );
};
