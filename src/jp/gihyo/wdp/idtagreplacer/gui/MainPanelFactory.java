package jp.gihyo.wdp.idtagreplacer.gui;

/**
 * MainPanel �̋�̓I�Ȏ�����Ԃ����߂̃I�u�W�F�N�g�ł��B
 * 
 */
public class MainPanelFactory {
	/**
	 * MainPanel �̋�̓I�Ȏ�����Ԃ��܂��B
	 * @return MainPanel �̋�̓I�Ȏ���
	 */
	public static MainPanel create() {
		return new DnDMainPanel();
	}
	
	private MainPanelFactory() { }
}
