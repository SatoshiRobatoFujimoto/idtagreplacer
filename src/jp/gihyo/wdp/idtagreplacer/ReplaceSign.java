package jp.gihyo.wdp.idtagreplacer;

/**
 * <p>���R�u���ݒ�̏����i�[���邽�߂̃N���X�ł��B</p>
 * <p>���̃I�u�W�F�N�g�̓���́A��ʓI�ȃr�[���Ɠ��l�ł��B
 * �R���X�g���N�^�Ŏ󂯎�������e��ێ����A�Q�b�^�[�ł����Ԃ��܂��B</p>
 */
public class ReplaceSign {
	private String sign = null;
	private ReplaceTag tag = null;
	
	/**
	 * ���R�u���ݒ���I�u�W�F�N�g�𐶐����܂��B
	 * @param sign �u���O�̕�����
	 * @param tag �u����̏��
	 */
	public ReplaceSign(String sign, ReplaceTag tag) {
		this.sign = sign;
		this.tag = tag;
	}

	/**
	 * �u���O�̕������Ԃ��܂��B
	 * @return �u���O�̕�����
	 */
	public String getSign() {
		return sign;
	}
	
	/**
	 * �u����̏���Ԃ��܂��B
	 * @return �u����̏��
	 */
	public ReplaceTag getTag() {
		return tag;
	}
}
