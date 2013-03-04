package jp.gihyo.wdp.idtagreplacer;

/**
 * <p>�^�O�ݒ�t�@�C������ǂݍ��܂ꂽ�A���R�u���ݒ�̒u����̓��e��ێ����邽�߂̃N���X�ł��B</p>
 * <p><code>ParagraphTag</code> �� <code>CharacterTag</code> �ɍ��킹�āA
 * <code>ReplaceSign</code> �Ƒ΂ɂȂ�悤�ɍ���Ă��܂����A�@�\�I�ɂ�
 * <code>ReplaceSign</code> �ƕ�����K�v�͂���܂���B</p>
 * <p>���̃I�u�W�F�N�g�̓���́A�����P���ȃr�[���Ɠ��l�ł��B</p>
 */
public class ReplaceTag {
	private String replacement = null;
	
	/**
	 * �u����̏����Ǘ�����I�u�W�F�N�g�𐶐����܂��B
	 * @param replacement �u����̕�����
	 */
	public ReplaceTag(String replacement) {
		this.replacement = replacement;
	}
	
	/**
	 * �u����̕������Ԃ��܂��B
	 * @return �u����̕�����
	 */
	public String getReplacement() {
		return replacement;
	}
}
