/**
 * Sample React Native ReactNavigation
 * https://github.com/facebook/react-native
 * @flow
 */

import React, { Component } from 'react';
import {
	TouchableOpacity,
	StyleSheet,
	Text,
	View,
	Image,
	TextInput,
	Modal,
	TouchableHighlight
} from 'react-native';

import styles from './Styles'

import { RESULT_OK } from 'react-native-navigation-hybrid'

export default class ReactResult extends Component {

	static navigationItem = {
		// hideBackButton: true,
		// hideShadow: true,
		titleItem: {
			title: 'RN result',
		},

		rightBarButtonItem: {
			title: '确定',
			// icon: Image.resolveAssetSource(require('./ic_settings.png')),
			insets: {top: -1, left: 8, bottom: 0, right: -8},
			action: 'somthing happen',
			enabled: false,
		}
	}

	constructor(props) {
		super(props);
		this.pushToNative = this.pushToNative.bind(this);
		this.pushToReact = this.pushToReact.bind(this);
		this.sendResult = this.sendResult.bind(this);
		this.onInputTextChanged = this.onInputTextChanged.bind(this);
		this.replaceWithNative = this.replaceWithNative.bind(this);
		this.replaceToRootWithNative = this.replaceToRootWithNative.bind(this);
		this.returnHome = this.returnHome.bind(this);
		this.state = {
			text: '',
			isRoot: false,
			modalVisible: false,
		}
	}

	setModalVisible(visible) {
    this.setState({modalVisible: visible});
  }

	componentWillMount() {
		this.props.navigator.isRoot().then((isRoot) => {
			if(isRoot) {
				this.props.garden.setLeftBarButtonItem({
					title: '取消', 
					// icon: Image.resolveAssetSource(require('./ic_settings.png')), 
					insets: {top: -1, left: -8, bottom: 0, right: 8},
					action: 'cancel'
				});
				this.setState({isRoot});
			}
		})
	}

	onBarButtonItemClick(action) {
		if (action === 'cancel') {
			this.props.navigator.dismiss();
		}
	}

	pushToNative() {
		this.props.navigator.push('NativeResult', {homeId: this.getHomeId()});
	}

	pushToReact() {
		this.props.navigator.push('ReactResult', {homeId: this.getHomeId()});
	}

	returnHome() {
		if(this.props.homeId) {
			this.props.navigator.popTo(this.props.homeId);
		}
	}

	getHomeId() {
		let homeId = this.props.homeId;
		if(!homeId) {
			homeId = this.props.sceneId;
		}
		return homeId;
	}

	replaceWithNative() {
		this.props.navigator.isRoot().then((isRoot => {
			if(isRoot) {
				this.props.navigator.replace('NativeResult');
			} else {
				this.props.navigator.replace('NativeResult', {homeId: this.getHomeId()});
			}
		}));
	}

	replaceToRootWithNative() {
		this.props.navigator.replaceToRoot('NativeResult');
	}

	sendResult() {
    this.props.navigator.setResult(RESULT_OK, {text: this.state.text})
		this.props.navigator.dismiss();
  }

	onInputTextChanged(text) {
		this.setState({text: text})
	}
    
	render() {
		return (
			<View style={styles.container}>
				<Text style={styles.welcome}>
					这是一个 React Native 页面:
        </Text>

				<TextInput style={styles.input} 
					onChangeText={this.onInputTextChanged} 
					value={this.state.text} 
					placeholder={'请输入要返回的结果'} 
					underlineColorAndroid='#00000000' 
					textAlignVertical="center"/>

				<TouchableOpacity onPress={this.sendResult} activeOpacity={0.2} style={styles.button}>
					<Text style={styles.buttonText}>
						返回结果
          </Text>
				</TouchableOpacity>

				<TouchableOpacity onPress={this.pushToReact} activeOpacity={0.2} style={styles.button}>
					<Text style={styles.buttonText}>
						push 到 React Native 页面
          </Text>
				</TouchableOpacity>

				<TouchableOpacity onPress={this.pushToNative} activeOpacity={0.2} style={styles.button}>
					<Text style={styles.buttonText}>
						push 到 native 页面
          </Text>
				</TouchableOpacity>

				<TouchableOpacity onPress={this.returnHome} activeOpacity={0.2} style={styles.button} disabled={this.state.isRoot}>
					<Text style={this.state.isRoot ? styles.buttonTextDisable : styles.buttonText}>
						返回首页
          </Text>
				</TouchableOpacity>
				<TouchableOpacity onPress={this.replaceWithNative} activeOpacity={0.2} style={styles.button}>
					<Text style={styles.buttonText}>
						替换成 native 页面
          </Text>
				</TouchableOpacity>
				<TouchableOpacity onPress={this.replaceToRootWithNative} activeOpacity={0.2} style={styles.button}>
					<Text style={styles.buttonText}>
						替换成 native 页面到根部
          </Text>
				</TouchableOpacity>
			</View>
		);
	}
}