package jp.gihyo.wdp.idtagreplacer;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContextFactory;
import org.mozilla.javascript.ImporterTopLevel;
import org.mozilla.javascript.Scriptable;

/**
 * <p>Rhino �̃X�N���v�g�����s���邽�߂̃N���X�ł��B</p>
 */
public class RhinoLauncher {
	private StringBuilder preCode = new StringBuilder();
	
	/**
	 * <p>�X�N���v�g�{�̂����s����O�Ƀ��[�h���Ă����ׂ��X�N���v�g���w�肵�܂��B</p>
	 * <p>�X�N���v�g�̎��s�́A<code>launch</code> ���\�b�h�ōs���܂��B
	 * �����Ŏw�肳�ꂽ�X�N�v���g�́A<code>launch</code> ���\�b�h�̒��ŁA
	 * �{�̂̃X�N���v�g�ɐ旧���Ď��s����܂��B</p>
	 * @param code ���炩���߃��[�h����X�N���v�g
	 */
	public void addPreCode(String code) {
		preCode.append(code + "\n");
	}
	
	public void addBasicAPI() {
		String appInstance = "Packages.jp.gihyo.wdp.idtagreplacer.App.getInstance()";
		addPreCode("var App = {");
		addPreCode(String.format("activeParagraphTag: %s.getActiveParagraphTag(),", appInstance));
		addPreCode(String.format("characterSigns: %s.getCharacterSigns(),", appInstance));
		addPreCode(String.format("paragraphSigns: %s.getParagraphSigns(),", appInstance));
		addPreCode(String.format("replaceSigns: %s.getReplaceSigns(),", appInstance));
		addPreCode(String.format("out: %s.out,", appInstance));
		addPreCode(String.format("printController: %s.getPrintController()", appInstance));
		addPreCode("}");
	}

	/**
	 * <p>�����ŗ^����ꂽ�X�N���v�g�� Rhino �̏����n�ɓn���Ď��s���A���̏������ʂ𕶎���Ƃ��ĕԂ��܂��B</p>
	 * @param code ���s����X�N���v�g
	 * @return ��������
	 * @throws Exception �X�N���v�g�̏������ɃG���[�����������ꍇ
	 */
	public String launch(String code) throws Exception {
		Context cx = new ContextFactory().enterContext();
		Object ret = null;
		try {
			Scriptable scope = new ImporterTopLevel(cx);
			cx.evaluateString(scope, preCode.toString(), "", 0, null);
			ret = cx.evaluateString(scope, code, "", 0, null);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			Context.exit();
		}
		return Context.toString(ret);
	}
}
