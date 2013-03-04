package jp.gihyo.wdp.idtagreplacer;

/**
 * <p>�^�O�ݒ�t�@�C������ǂݍ��܂ꂽ�A�i���^�O�̏����i�[���邽�߂̃N���X�ł��B</p>
 * <p>�I�u�W�F�N�g�̓R���X�g���N�^�œn���ꂽ�X�^�C������ێ����A
 * <code>getTagString</code> ���\�b�h���Ă΂ꂽ�ۂɁA�i���^�O�̕������Ԃ��܂��B</p>
 */
public class ParagraphTag {
	private String tagName = null;

	/**
	 * �X�^�C�������w�肵�āA�i���^�O���I�u�W�F�N�g���쐬���܂��B
	 * @param tagName �X�^�C����
	 */
	public ParagraphTag(String tagName) {
		this.tagName  = tagName;
	}
	
	/**
	 * �ێ����Ă���X�^�C������Ԃ��܂��B
	 * @return �X�^�C����
	 */
	public String getTagName() {
		return this.tagName;
	}
	
	/**
	 * �ێ����Ă���X�^�C���������ƂɁA�i���^�O�̕�����𐶐����ĕԂ��܂��B
	 * �Ԃ��l�� <code>&lt;ParaStyle:<i>style name</i>&gt;</code> �ł��B
	 * @return �i���^�O�̕�����
	 */
	public String getTagString() {
		return "<ParaStyle:" + this.tagName + ">";
	}
}
