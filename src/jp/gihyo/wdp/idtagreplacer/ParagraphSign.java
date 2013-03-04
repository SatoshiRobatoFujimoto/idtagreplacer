package jp.gihyo.wdp.idtagreplacer;

import java.io.IOException;

/**
 * �i���̕ҏW�L���̏����i�[���邽�߂̃N���X�ɁA��{�ƂȂ������񋟂��钊�ۃN���X�ł��B
 */
public abstract class ParagraphSign {

	protected String startSign = null;
	protected String endSign = null;
	protected ParagraphTag tag = null;

	/**
	 * �J�n�L�����������i���L���I�u�W�F�N�g�𐶐����܂��B
	 * @param sign �J�n�L��
	 * @param tag �i���^�O
	 */
	public ParagraphSign(String sign, ParagraphTag tag) {
		this.startSign = sign;
		this.tag= tag;
	}
	
	/**
	 * �@�J�n�L���ƏI���L�������i���L���I�u�W�F�N�g�𐶐����܂��B
	 * @param startSign �J�n�L��
	 * @param endSign �I���L��
	 * @param tag �i���^�O
	 */
	public ParagraphSign(String startSign, String endSign, ParagraphTag tag) {
		this.startSign = startSign;
		this.endSign = endSign;
		this.tag = tag;
	}

	/**
	 * �I���L���������Ă��邩�ǂ�����Ԃ��܂��B
	 * @return �I���L���������Ă���Ȃ� true
	 */
	protected boolean hasEndSign() {
		return endSign != null;
	}

	/**
	 * �L�����^�O�ɕϊ����đΏۂ̍s���o�͂��܂��B
	 * @param line �����Ώۂ̍s
	 * @return �ϊ����s���ďo�͂����ꍇ�� true
	 * @throws IOException �o�͎��ɃG���[���N�������ꍇ
	 * @throws SourceParserException �^�O�ϊ����ɃG���[���N�������ꍇ
	 */
	public String convertSign(String line) throws IOException, SourceParserException {

		if (line.startsWith(startSign)) {
//			whenStartSignMuches(line);
//			return true;
			return whenStartSignMuches(line);
		}
		
//		if (! hasEndSign()) return false;
		if (! hasEndSign()) return line;
		
		if (line.endsWith(endSign)) {
//			whenEndSignMuches(line);
//			return true;
			return whenEndSignMuches(line);
		}
		
//		return false;
		return line;
	}

	/**
	 * ���̃��\�b�h�́A�����Ώۂ̍s�ɊJ�n�L�������������ꍇ��convertSign���\�b�h����Ăяo����܂��B
	 * �����ŋ�̓I�ȕϊ����������s����܂��B
	 * @param line �����Ώۂ̍s
	 * @throws IOException �o�͎��ɃG���[���N�������ꍇ
	 * @throws SourceParserException �^�O�ϊ����ɃG���[���N�������ꍇ
	 */
	protected abstract String whenStartSignMuches(String line) throws IOException, SourceParserException;
	
	/**
	 * ���̃��\�b�h�́A�����Ώۂ̍s�ɏI���L�������������ꍇ��convertSign���\�b�h����Ăяo����܂��B
	 * �����ŋ�̓I�ȏ��������s����܂��B
	 * @param line �����Ώۂ̍s
	 * @throws IOException �o�͎��ɃG���[���N�������ꍇ
	 * @throws SourceParserException �^�O�ϊ����ɃG���[���N�������ꍇ
	 */
	protected abstract String whenEndSignMuches(String line) throws IOException, SourceParserException;
}