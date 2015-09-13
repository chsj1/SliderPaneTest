package com.example.hellolibgdx;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Slider.SliderStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class MyGame implements ApplicationListener {
	//定义一个Stage对象
	Stage stage;
	//定义一个Slider对象
	Slider slider;
	//定义一个SliderStyle对象
	SliderStyle style;
	//定义一个TextureAtlas对象
	TextureAtlas atlas;
	//定义滑块的操控部分所对应的Region对象
	TextureRegion konbRegion;
	//定义滑块的操控部分所对应的Drawable对象
	TextureRegionDrawable konbDrawable;
	//定义滑块的背景部分所对应的Region对象
	TextureRegion bgRegion;
	//定义滑块的背景部分所对应的Drawable对象
	TextureRegionDrawable bgDrawable;
	//目标元素的Texture对象
	Texture targetTexture;
	//目标元素的Region对象
	TextureRegion targetRegion;
	//目前Image
	Image targetImage;
	//用于背景资源Atlas文件
	TextureAtlas bgAtlas;
	//背景图片
	Image bbgImage;
	//用作背景的资源的Region对象
	TextureRegion bbgRegion;
	
	
	/**
	 * 主要完成初始化操作和监听器的注册和演员的添加
	 */
	@Override
	public void create() {
		//atlas对象的初始化
		bgAtlas = new TextureAtlas(Gdx.files.internal("data/bofang.atlas"));
		//整个场景的背景的Region对象的初始化
		bbgRegion = new TextureRegion(bgAtlas.findRegion("bg"));
		//Image对象的初始化
		bbgImage = new Image(bbgRegion);
		//设置图片的大小
		bbgImage.setSize(480, 800);
		//滑块所对应的atlas文件的初始化
		atlas = new TextureAtlas(Gdx.files.internal("data/slider.atlas"));
		//获取滑块所用到的操控部分所对应的Region对象
		konbRegion = atlas.findRegion("knob");
		//获取滑块所用到的背景部分所对应的Region对象
		bgRegion = atlas.findRegion("bg");
		//生成目标元素的Texture对象
		targetTexture = new Texture(Gdx.files.internal("data/lengjiao1.png"));
		//生成目标元素的Region对象
		targetRegion = new TextureRegion(targetTexture);
		//获取Region对象的宽度
		float width = targetRegion.getRegionWidth();
		//获取目标Region的高度
		float height = targetRegion.getRegionHeight();
		//构造目标图片
		targetImage = new Image(targetRegion);
		//设置目标元素的锚点
		targetImage.setOrigin(width/2, height/2);
		//设置目标图片的位置
		targetImage.setPosition(240, 400);
		//利用底座的Region对象生成底座的Drawable对象
		bgDrawable = new TextureRegionDrawable(bgRegion);
		//利用操控部分的Region对象生成底座的Drawable对象
		konbDrawable = new TextureRegionDrawable(konbRegion);
		//初始化Style对象
		style = new SliderStyle(bgDrawable, konbDrawable);
		//初始化Slider对象
		slider = new Slider(0, 480, 5, false, style);
		//设置Slider的宽度
		slider.setWidth(480);
		//给Slider添加监听器
		addListenerOnSliderToHandleTarget();
		//初始化舞台
		stage = new Stage(480, 800, false);
		//将场景的背景添加到舞台
		stage.addActor(bbgImage);
		//将目标元素添加到舞台
		stage.addActor(targetImage);
		//将滑块添加到舞台
		stage.addActor(slider);
		//使用Stage来处理输入输出事件
		Gdx.input.setInputProcessor(stage);
	}

	/**
	 * 给滑块添加点击事件用于操控游戏元素进行缩放
	 */
	public void addListenerOnSliderToHandleTarget(){
		//给滑块注册监听器
		slider.addListener(new InputListener(){
			/**
			 * 当手指按下时执行
			 */
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				//计算滑块目前的偏移量
				float value = slider.getValue();
				//计算滑块最大的偏移量
				float maxValue = slider.getMaxValue();
				//计算偏移的比例
				float percent = value/maxValue;
				//进行缩放操作
				targetImage.setScale(1+percent);
				return true;
			}
			/**
			 * 手指弹起的时候执行
			 */
			@Override
			public void touchDragged(InputEvent event, float x, float y,
					int pointer) {
				//计算滑块目前的偏移量
				float value = slider.getValue();
				//计算滑块最大的偏移量
				float maxValue = slider.getMaxValue();
				//计算偏移的比例
				float percent = value/maxValue;
				//进行缩放操作
				targetImage.setScale(1+percent);
			}
		});
	}
	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}
	
	
	
	
	@Override
	public void render() {
		Gdx.gl.glClearColor(1, 1, 1, 1);// 设置背景为白色
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);// 清屏
		
		stage.act();
		stage.draw();
	}

	@Override
	public void resize(int arg0, int arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

}
