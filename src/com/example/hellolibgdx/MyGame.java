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
	//����һ��Stage����
	Stage stage;
	//����һ��Slider����
	Slider slider;
	//����һ��SliderStyle����
	SliderStyle style;
	//����һ��TextureAtlas����
	TextureAtlas atlas;
	//���廬��Ĳٿز�������Ӧ��Region����
	TextureRegion konbRegion;
	//���廬��Ĳٿز�������Ӧ��Drawable����
	TextureRegionDrawable konbDrawable;
	//���廬��ı�����������Ӧ��Region����
	TextureRegion bgRegion;
	//���廬��ı�����������Ӧ��Drawable����
	TextureRegionDrawable bgDrawable;
	//Ŀ��Ԫ�ص�Texture����
	Texture targetTexture;
	//Ŀ��Ԫ�ص�Region����
	TextureRegion targetRegion;
	//ĿǰImage
	Image targetImage;
	//���ڱ�����ԴAtlas�ļ�
	TextureAtlas bgAtlas;
	//����ͼƬ
	Image bbgImage;
	//������������Դ��Region����
	TextureRegion bbgRegion;
	
	
	/**
	 * ��Ҫ��ɳ�ʼ�������ͼ�������ע�����Ա�����
	 */
	@Override
	public void create() {
		//atlas����ĳ�ʼ��
		bgAtlas = new TextureAtlas(Gdx.files.internal("data/bofang.atlas"));
		//���������ı�����Region����ĳ�ʼ��
		bbgRegion = new TextureRegion(bgAtlas.findRegion("bg"));
		//Image����ĳ�ʼ��
		bbgImage = new Image(bbgRegion);
		//����ͼƬ�Ĵ�С
		bbgImage.setSize(480, 800);
		//��������Ӧ��atlas�ļ��ĳ�ʼ��
		atlas = new TextureAtlas(Gdx.files.internal("data/slider.atlas"));
		//��ȡ�������õ��Ĳٿز�������Ӧ��Region����
		konbRegion = atlas.findRegion("knob");
		//��ȡ�������õ��ı�����������Ӧ��Region����
		bgRegion = atlas.findRegion("bg");
		//����Ŀ��Ԫ�ص�Texture����
		targetTexture = new Texture(Gdx.files.internal("data/lengjiao1.png"));
		//����Ŀ��Ԫ�ص�Region����
		targetRegion = new TextureRegion(targetTexture);
		//��ȡRegion����Ŀ��
		float width = targetRegion.getRegionWidth();
		//��ȡĿ��Region�ĸ߶�
		float height = targetRegion.getRegionHeight();
		//����Ŀ��ͼƬ
		targetImage = new Image(targetRegion);
		//����Ŀ��Ԫ�ص�ê��
		targetImage.setOrigin(width/2, height/2);
		//����Ŀ��ͼƬ��λ��
		targetImage.setPosition(240, 400);
		//���õ�����Region�������ɵ�����Drawable����
		bgDrawable = new TextureRegionDrawable(bgRegion);
		//���òٿز��ֵ�Region�������ɵ�����Drawable����
		konbDrawable = new TextureRegionDrawable(konbRegion);
		//��ʼ��Style����
		style = new SliderStyle(bgDrawable, konbDrawable);
		//��ʼ��Slider����
		slider = new Slider(0, 480, 5, false, style);
		//����Slider�Ŀ��
		slider.setWidth(480);
		//��Slider��Ӽ�����
		addListenerOnSliderToHandleTarget();
		//��ʼ����̨
		stage = new Stage(480, 800, false);
		//�������ı�����ӵ���̨
		stage.addActor(bbgImage);
		//��Ŀ��Ԫ����ӵ���̨
		stage.addActor(targetImage);
		//��������ӵ���̨
		stage.addActor(slider);
		//ʹ��Stage��������������¼�
		Gdx.input.setInputProcessor(stage);
	}

	/**
	 * ��������ӵ���¼����ڲٿ���ϷԪ�ؽ�������
	 */
	public void addListenerOnSliderToHandleTarget(){
		//������ע�������
		slider.addListener(new InputListener(){
			/**
			 * ����ָ����ʱִ��
			 */
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				//���㻬��Ŀǰ��ƫ����
				float value = slider.getValue();
				//���㻬������ƫ����
				float maxValue = slider.getMaxValue();
				//����ƫ�Ƶı���
				float percent = value/maxValue;
				//�������Ų���
				targetImage.setScale(1+percent);
				return true;
			}
			/**
			 * ��ָ�����ʱ��ִ��
			 */
			@Override
			public void touchDragged(InputEvent event, float x, float y,
					int pointer) {
				//���㻬��Ŀǰ��ƫ����
				float value = slider.getValue();
				//���㻬������ƫ����
				float maxValue = slider.getMaxValue();
				//����ƫ�Ƶı���
				float percent = value/maxValue;
				//�������Ų���
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
		Gdx.gl.glClearColor(1, 1, 1, 1);// ���ñ���Ϊ��ɫ
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);// ����
		
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
