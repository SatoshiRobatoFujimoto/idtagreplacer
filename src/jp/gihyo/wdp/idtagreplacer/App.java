package jp.gihyo.wdp.idtagreplacer;

import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.logging.Logger;

public class App {
	private static App instance = new App();
	
	/**
	 * �A�v���P�[�V���������p����o�͗p�I�u�W�F�N�g��ێ����܂��B
	 */
	public static Printer out = null;
	
	/**
	 * ���ݗL���Ȓi���^�O��ێ����܂��B
	 * ����̓��X�g�Ƃ��Ď�������Ă��܂����A����A�P���ȃX�^�b�N�̂悤�ɓ��삵�Ă��܂��B
	 * �A�v���P�[�V�����͋N�����A�^�O�ݒ�t�@�C����ǂݍ��񂾍ۂɁA
	 * �܂����̃��X�g�Ɂu�f�t�H���g�̒i���X�^�C���v��o�^���܂��B
	 * ���̌�A�����Ώۂ̃e�L�X�g�t�@�C����ǂݍ��݂Ȃ���A
	 * �i���̊J�n�L�����o�����邽�тɁA���̃��X�g�ɑΉ�����i���X�^�C����push���A
	 * �I���L�����o�����邽�тɁApop���܂��B
	 */
	private LinkedList<ParagraphTag> activeParagraphTag = null;
	
	/**
	 * �i���ҏW�L���̃��X�g��ێ����܂��B
	 * �^�O�ݒ�t�@�C���ɋL�q���ꂽ���e���A�p�[�T��ʂ��Ă����Ɋi�[����܂��B
	 */
	private LinkedList<ParagraphSign> paragraphSigns = null;
	
	/**
	 * �����ҏW�L���̃��X�g��ێ����܂��B
	 * �^�O�ݒ�t�@�C���ɋL�q���ꂽ���e���A�p�[�T��ʂ��Ă����Ɋi�[����܂��B
	 */
	private LinkedList<CharacterSign> characterSigns = null;
	
	/**
	 * ���R�u���ҏW�L���̃��X�g��ێ����܂��B
	 * �^�O�ݒ�t�@�C���ɋL�q���ꂽ���e���A�p�[�T��ʂ��Ă����Ɋi�[����܂��B
	 */
	private LinkedList<ReplaceSign> replaceSigns = null;
	
	private PrintController controller = null;
	
	private App() {
		cleanupActiveParagraphTag();

		paragraphSigns = new LinkedList<ParagraphSign>();
		
		characterSigns = new LinkedList<CharacterSign>();
		
		replaceSigns = new LinkedList<ReplaceSign>();
	}
	
	/**
	 * ���݂̒i���X�^�C���̏������ׂăN���A���܂��B
	 * <br/>
	 * �i���X�^�C���̊���l���܂߁A���ׂăN���A���Ă��܂��܂��B���ӁB
	 */
	public void cleanupActiveParagraphTag() {
		activeParagraphTag = new LinkedList<ParagraphTag>() {
			private static final long serialVersionUID = 1925385660123868542L;

			public boolean add(ParagraphTag o) {
				Logger.global.info("���݂̒i���^�O�Ƃ��āA'" + o.getTagName() + "'���o�^����܂����B");
				return super.add(o);
			}
			
			public ParagraphTag removeLast() throws NoSuchElementException {
				ParagraphTag ret = super.removeLast();
				Logger.global.info("���݂̒i���^�O����'" + ret.getTagName() + "'����菜���܂����B");
				return ret;
			}
		};
	}
	
	/**
	 * ���݂̒i���X�^�C�������X�g�ŕԂ��܂��B
	 * <br/>
	 * ���̃v���O�����ł́A�i���X�^�C�����l�X�g�\�ɂ��Ă��܂��B
	 * ����́A�o�������i���X�^�C�������X�g�i�����X�^�b�N�Ƃ��ė��p����Ă���j�ŕێ����邱�ƂŎ������Ă��܂��B
	 * ���̃��\�b�h�́A���̃��X�g��Ԃ��܂��B
	 * 
	 * @return ���݂̒i���X�^�C��
	 */
	public LinkedList<ParagraphTag> getActiveParagraphTag() {
		return activeParagraphTag;
	}
	
	/**
	 * �ݒ�t�@�C������ǂݍ��񂾁A�i���ҏW�L���̑S�������X�g�ŕԂ��܂��B
	 * @return �i���ҏW�L���̑S���
	 */
	public LinkedList<ParagraphSign> getParagraphSigns() {
		return paragraphSigns;
	}
	
	/**
	 * �ݒ�t�@�C������ǂݍ��񂾁A�����ҏW�L���̑S�������X�g�ŕԂ��܂��B
	 * @return �����ҏW�L���̑S���
	 */
	public LinkedList<CharacterSign> getCharacterSigns() {
		return characterSigns;
	}

	/**
	 * �ݒ�t�@�C������ǂݍ��񂾁A���R�u���ݒ�̑S�������X�g�ŕԂ��܂��B
	 * @return ���R�u���ݒ�̑S���
	 */
	public LinkedList<ReplaceSign> getReplaceSigns() {
		return replaceSigns;
	}
	
	/**
	 * �o�͎��̐���I�u�W�F�N�g��V���ɍ쐬���܂��B
	 */
	public void makeNewPrintController() {
		controller = new PrintController();
	}
	
	/**
	 * ���ݎg���Ă���A�o�͎��̐���I�u�W�F�N�g��Ԃ��܂��B
	 * @return �o�͎��̐���I�u�W�F�N�g
	 */
	public PrintController getPrintController() {
		return controller;
	}
	
	/**
	 * ���̃N���X�̃C���X�^���X��Ԃ��܂��B
	 * @return ���̃N���X�̃C���X�^���X
	 */
	public static App getInstance() {
		if (instance == null) instance = new App();
		return instance;
	}
}
