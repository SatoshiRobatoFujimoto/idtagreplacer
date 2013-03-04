package jp.gihyo.wdp.idtagreplacer;

/**
 * <p>�^�O�ݒ�t�@�C������ǂݍ��܂ꂽ�A�����X�^�C���̏����i�[���邽�߂̃N���X�ł��B</p>
 * <p>���̃I�u�W�F�N�g�̓���͋ɂ߂ĒP���ł��B
 * �I�u�W�F�N�g�̓R���X�g���N�^�œn���ꂽ�X�^�C������ێ����A
 * <code>getStartTagString</code> ���\�b�h���Ă΂ꂽ�ۂɁA
 * <code>&lt;CharStyle: ... &gt;</code> �Ƃ����^�O��������쐬���ĕԂ��܂��B</p>
 */
public class CharacterTag {
	private String styleName = null;
	
	/**
	 * �X�^�C�������w�肵�ĕ����^�O���I�u�W�F�N�g���쐬���܂��B
	 * @param styleName �X�^�C����
	 */
	public CharacterTag(String styleName) {
		this.styleName = styleName;
	}
	
	/**
	 * �J�n�^�O�̕������Ԃ��܂��B
	 * ��̓I�ɂ́A&lt;CharStyle:<i>style name</i>&gt;�Ƃ����������Ԃ��܂��B
	 * <i>style name</i> �ɂ́A�R���X�g���N�^�œn���ꂽ styleName ������܂��B
	 * @return �J�n�^�O�̕�����
	 */
	public String getStartTagString() {
		return "<CharStyle:" + styleName + ">";
	}
	
	/**
	 * �I���^�O�̕������Ԃ��܂��B
	 * �Ԃ��l�� &lt;CharStyle:&gt; �ł��B
	 * @return �I���^�O�̕������Ԃ��܂��B
	 */
	public String getEndTagString() {
		return "<CharStyle:>";
	}
	
	/**
	 * �I�u�W�F�N�g���ێ����Ă���X�^�C������Ԃ��܂��B
	 * @return �X�^�C����
	 */
	public String getStyleName() {
		return styleName;
	}
}
