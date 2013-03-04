package jp.gihyo.wdp.idtagreplacer.gui;

/**
 * �A�v���P�[�V�����̃��C���E�B���h�E�̎�����Ԃ��܂��B
 */
public class MainFrameFactory {
	/**
	 * �A�v���P�[�V�����̃��C���E�B���h�E�̎�����Ԃ��܂��B
	 * @return MainFrame �C���^�[�t�F�C�X�̋�̓I�Ȏ���
	 */
	public static MainFrame create() {
		return new DnDFrame();
	}
	
	private MainFrameFactory() { }
}
