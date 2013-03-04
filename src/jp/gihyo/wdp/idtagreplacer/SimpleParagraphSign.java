package jp.gihyo.wdp.idtagreplacer;

import java.io.IOException;

/**
 * �i���L���̏���ێ����A�^�O�ϊ��������s���N���X�ł��B
 */
public class SimpleParagraphSign extends ParagraphSign {
	/* �^�O�u���̍ۂɁA���̋L�����폜���邩�ǂ��� */
	private boolean removeSign = true;
	
	/**
	 * �J�n�L�����������i���L���I�u�W�F�N�g�𐶐�����B
	 * @param sign �J�n�L��
	 * @param tag �i���^�O
	 */
	public SimpleParagraphSign(String sign, ParagraphTag tag) {
		super(sign, tag);
	}
	
	public SimpleParagraphSign(String startSign, String endSign, ParagraphTag tag) {
		super(startSign, endSign, tag);
	}
	
	/**
	 * �^�O�u���̍ۂɁA���̋L�����폜���邩�ǂ�����ݒ肷��B
	 * @param removeSign false ��n���ƋL�����c���B
	 */
	public void setRemoveSign(boolean removeSign) {
		this.removeSign = removeSign;
	}
	
	@Override
	protected String whenEndSignMuches(String line) throws IOException, SourceParserException {
		if (! endSign.equals(line)) {
			line = removeSign ? 
					line.substring(0, line.length() - endSign.length()) : line;
//			App.out.println(line);
			App.getInstance().getPrintController().addCommand("print");
		}
//		App.getInstance().getActiveParagraphTag().removeLast();
		App.getInstance().getPrintController().addCommand("remove last para");
		return line;
	}

	@Override
	protected String whenStartSignMuches(String line) throws IOException, SourceParserException {
		App.getInstance().getActiveParagraphTag().add(tag);
		line = removeSign ? line.substring(startSign.length()) : line;
		if (hasEndSign()) {
//			if (line.trim().length() != 0) App.out.println(line);
			if (line.trim().length() != 0) App.getInstance().getPrintController().addCommand("print");
		} else {
//			App.out.println(line);
//			App.getInstance().getActiveParagraphTag().removeLast();
			App.getInstance().getPrintController().addCommand("print");
			App.getInstance().getPrintController().addCommand("remove last para");
		}
		return line;
	}
}
