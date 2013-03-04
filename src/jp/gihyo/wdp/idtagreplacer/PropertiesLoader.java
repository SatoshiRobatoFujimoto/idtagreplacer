package jp.gihyo.wdp.idtagreplacer;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.UnsupportedCharsetException;
import java.util.Properties;
import java.util.logging.Logger;

class PropertiesLoader {
	private static PropertiesLoader instance = null;
	private Properties properties = null;

	/**
	 * PropertiesLoader �I�u�W�F�N�g�����������ĕԂ��܂��B
	 * �K���V�K�̃I�u�W�F�N�g��Ԃ��܂��B�����̃I�u�W�F�N�g��Ԃ��킯�ł͂���܂���B
	 * @param fileName �^�O�ݒ�t�@�C���̃p�X
	 * @return PropertiesLoader �I�u�W�F�N�g
	 * @throws IOException
	 */
	static PropertiesLoader getNewInstance(String fileName) throws IOException {
		instance = new PropertiesLoader(fileName);
		return instance;
	}
	
	/**
	 * ������ PropertiesLoader �I�u�W�F�N�g��Ԃ��܂��B
	 * �����̃I�u�W�F�N�g���Ȃ��ꍇ�i�܂� getNewInstance ���Ă�ł��Ȃ��ꍇ�j��
	 * null ��Ԃ��܂��B
	 */
	static PropertiesLoader getInstance() {
		return instance;
	}
	
	private PropertiesLoader(String fileName) throws IOException {
		Logger.global.info("�^�O�ݒ�t�@�C��'" + fileName + "'��ǂݍ��݂܂��B");
		
		properties = new Properties() {
				private static final long serialVersionUID = 1643376174439610083L;
				public String getProperty(String key) {
					Logger.global.info("�v���p�e�B'" + key + "'�̒l�����o���܂��B");
					return super.getProperty(key);
				}
		};
		
		InputStream s = new FileInputStream(fileName);
		properties.loadFromXML(s);
		s.close();
		Logger.global.info("�^�O�ݒ�t�@�C���̓ǂݍ��݂��I�����܂��B");
	}
	
	Properties getProperties() {
		return properties;
	}
}

class PropertiesInfo {
	String lineFeedCode = null;
	String savefileFormat = null;
	String defaultParagraphName = null;
	Charset charset = null;
	
	String charsetName = null;
	String paraSettings = null;
	String charSettings = null;
	String freeSettings = null;
}

class PropertiesLoaderHelper {
	private void getValues(Properties properties, PropertiesInfo info) {
		info.charsetName = properties.getProperty("�G���R�[�h");
		info.lineFeedCode = properties.getProperty("���s�R�[�h");
		info.savefileFormat = properties.getProperty("�ۑ��t�@�C����");
		info.defaultParagraphName = properties.getProperty("�i���^�O�̊���l");
		info.paraSettings = properties.getProperty("�i���^�O�ݒ�");
		info.charSettings = properties.getProperty("�����^�O�ݒ�");
		info.freeSettings = properties.getProperty("���R�u���ݒ�");
	}
	
	private void readTagsSettings(SimpleTagconfParser parser, PropertiesInfo info) throws TagconfException {
		if (info.paraSettings != null) parser.parseParaSetting(info.paraSettings);
		if (info.charSettings != null) parser.parseCharSetting(info.charSettings);
		if (info.freeSettings != null) parser.parseReplaceSetting(info.freeSettings);
	}
	
	private void readJavaScriptSettings(Properties prop, PropertiesInfo info) throws TagconfException {
		// JavaScript �����s����v���p�e�B�[���̃��X�g
		String[] settings = {
				"���ې�������", "���ې�������", "�A���t�@�x�b�g", 
				"���l�p��������", "���̑����R�ݒ�"
		};

		RhinoLauncher r = new RhinoLauncher();
		r.addBasicAPI();
		r.addPreCode("importClass(Packages.jp.gihyo.wdp.idtagreplacer.ReplaceSign);");
		r.addPreCode("importClass(Packages.jp.gihyo.wdp.idtagreplacer.ReplaceTag);");
		r.addPreCode("var encode = '" + info.charset.name() + "';");
		try {
			for (String n: settings)
				r.launch(prop.getProperty(n, ""));
		} catch (Exception e) {
			throw new TagconfException("tagconf.xml �� JavaScript �����s���ɃG���[���������܂����B\n���b�Z�[�W : " + e.getMessage());
		}
	}
	
	public PropertiesInfo readProperties(String filePath) throws IOException, TagconfException {
		
		PropertiesInfo info = new PropertiesInfo();
		Properties prop = PropertiesLoader.getNewInstance(filePath).getProperties();
		
		getValues(prop, info);
		
		if (info.charsetName == null) {
			Logger.global.severe("�G���R�[�h�̐ݒ肪������܂���B");
			throw new TagconfException("�G���R�[�h�̐ݒ肪������܂���");
		}
		if (info.defaultParagraphName == null) {
			throw new TagconfException("�i���^�O�̊���l���ݒ肳��Ă��܂���");
		}

		try {
			info.charset = Charset.forName(info.charsetName);
		} catch (IllegalCharsetNameException icne) {
			Logger.global.severe("�w�肳�ꂽ�G���R�[�h'" + info.charsetName + "'�͕s�K�؂Ȓl�ł��B");
			throw new TagconfException("�G���R�[�h�̎w�肪�s�K�؂ł�");
		} catch (UnsupportedCharsetException uce) {
			Logger.global.severe("�w�肳�ꂽ�G���R�[�h'" + info.charsetName + "'�̓T�|�[�g����Ă��܂���B");
			throw new TagconfException("�T�|�[�g����Ă��Ȃ��G���R�[�h�ł�");
		}
		
		App.getInstance().getActiveParagraphTag().add(new ParagraphTag(info.defaultParagraphName));
		readTagsSettings(new SimpleTagconfParser(), info);
		readJavaScriptSettings(prop, info);
		
		return info;
	}
	
}
