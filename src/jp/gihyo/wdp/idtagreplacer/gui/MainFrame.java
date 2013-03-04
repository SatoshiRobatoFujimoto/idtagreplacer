package jp.gihyo.wdp.idtagreplacer.gui;

/**
 * �A�v���P�[�V�����̃��C���E�B���h�E�ƂȂ�t���[���ł��B 
 */
public interface MainFrame {

	/**
	 * �t���[����\�����܂��B
	 */
	public void show();

	/**
	 * �w�肳�ꂽ���b�Z�[�W�����A�G���[���b�Z�[�W�{�b�N�X��\�����܂��B
	 * @param message �G���[���b�Z�[�W�{�b�N�X�ɕ\��������������
	 */
	public void showErrorMessage(String message);

	/**
	 * �w�肳�ꂽ�^�C�g���ƃ��b�Z�[�W�����A���b�Z�[�W�{�b�N�X��\�����܂��B
	 * @param title ���b�Z�[�W�{�b�N�X�̃^�C�g���o�[�ɕ\��������������
	 * @param message ���b�Z�[�W�{�b�N�X�ɕ\��������������
	 */
	public void showMessage(String title, String message);

	/**
	 * �t���[���̃X�e�[�^�X�o�[�̗̈�ɕ\�����������b�Z�[�W���w�肵�܂��B
	 * @param string �X�e�[�^�X�o�[�̗̈�ɕ\��������������
	 */
	public void setStatusMessage(String string);

	/**
	 * �t���[���ɏ悹��p�l�����w�肵�܂��B
	 * @param p �t���[���ɏ悹�����p�l��
	 */
	public void setContentPane(MainPanel p);

	/**
	 * �t�@�C���I���_�C�A���O��\������B
	 * @return �I�����ꂽ�t�@�C���̃p�X�B�L�����Z���̏ꍇ�� null�B
	 */
	public String showFileChooser(String title);
}