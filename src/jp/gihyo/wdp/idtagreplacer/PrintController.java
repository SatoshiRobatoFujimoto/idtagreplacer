package jp.gihyo.wdp.idtagreplacer;

import java.io.IOException;
import java.util.LinkedList;
import java.util.ListIterator;

/**
 * �o�͎��ɍs���ׂ�����̗\�����t���A���s���邽�߂̃I�u�W�F�N�g�ł��B
 */
public class PrintController {
	private LinkedList<String> cmds = null;
	
	PrintController() {
		cmds = new LinkedList<String>();
	}

	/**
	 * �o�͎��ɍs���ׂ������ǉ����܂��B
	 * <br/>
	 * ����́A����̃R�}���h�𔭍s���ė\�񂵂܂��B
	 * ���p�ł���R�}���h�́A
	 * <ul><li>print</li>
	 * <li>remove last para</li>
	 * <li>���̑�</li></ul>
	 * �� 3 ��ނł��B<br/>
	 * �uprint�v�̓t�@�C���ւ̏o�͂��s���܂��B
	 * ����́A���ݏ������̍s���A�R�}���h���s���̏�Ԃŏo�͂���Ƃ������Ƃł͂���܂���B
	 * �t�@�C���ւ̏o�͂́A���������ׂĊ��������i�K�ōs���܂��B<br/>
	 * �uremove last para�v�́A���݂̒i���X�^�C���̂����A�Ō�̂��̂��폜���܂��B<br/>
	 * ���̑��̃R�}���h�͌����I�Ɏg���܂��񂪁A�Ӑ}�I�ɉ����o�͂��������Ȃ��ꍇ�Ɏg�����Ƃ��ł��܂��B
	 * �R�}���h���^�C�|����ƁA�o�͂���Ȃ��Ȃ邱�Ƃ�����܂��B<br/>
	 * ���̃��\�b�h�́A�^�C�|���̂��߂ɗ�O�𑗏o���邱�Ƃ͂���܂���B���̃R�}���h�͒P���ɖ�������܂��B
	 * 
	 * @param command 
	 */
	public void addCommand(String command) {
		cmds.add(command);
	}

	/**
	 * �o�͎��̑��삪�����\�񂳂�Ă��邩�ǂ�����Ԃ��܂��B
	 * <br/>
	 * ���炩�̑��삪�\�񂳂�Ă���� true ��Ԃ��܂��B
	 * @return ���삪�\�񂳂�Ă��邩�ǂ���
	 */
	public boolean hasCommands() {
		return ! cmds.isEmpty();
	}

	/**
	 * �\�񂳂�Ă��鑀������s���܂��B
	 * @param line �o�͂��镶����iprint �R�}���h�����s����Ă���ꍇ�j
	 * @throws IOException I/O �֌W�̗�O�����������ꍇ
	 * @throws SourceParserException�@���e�t�@�C���̉�͂Ɋ֌W�����O�����������ꍇ 
	 */
	public void execute(String line) throws IOException, SourceParserException {
		ListIterator<String> it = cmds.listIterator();
		while (it.hasNext()) {
			String c = it.next();
			if (c.equals("print")) {
				App.out.println(line);
			} else if (c.equals("remove last para")) {
				App.getInstance().getActiveParagraphTag().removeLast();
			}
		}
	}
}
