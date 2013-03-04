package jp.gihyo.wdp.idtagreplacer;


import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;

public class SimpleTagconfParserTest {
	SimpleTagconfParser parser = null;
	String paraconf = null;
	ParagraphSign pl = null;
	
	@Before public void
	_�Z�b�g�A�b�v
	() {
		parser = new SimpleTagconfParser();
	}
	
	@Test public void
	���ʂ̋L�q���������p�[�X�ł��邱��_�s�̐ݒ� 
	() throws TagconfException {
		paraconf = "start_sign   end_sign:style_name";
		parser.parseParaSetting(paraconf);
		pl = App.getInstance().getParagraphSigns().getLast();
		assertTrue(pl.startSign.equals("start_sign"));
		assertTrue(pl.endSign.equals("end_sign"));
		assertTrue(pl.tag.getTagName().equals("style_name"));
		
		paraconf = "�J�n�L��\t\t�I���L��\t\t:�X�^�C����";
		parser.parseParaSetting(paraconf);
		pl = App.getInstance().getParagraphSigns().getLast();
		assertTrue(pl.startSign.equals("�J�n�L��"));
		assertTrue(pl.endSign.equals("�I���L��"));
		assertTrue(pl.tag.getTagName().equals("�X�^�C����"));
		
		paraconf = "�s���L�� : �X�^�C����";
		parser.parseParaSetting(paraconf);
		pl = App.getInstance().getParagraphSigns().getLast();
		assertTrue(pl.startSign.equals("�s���L��"));
		assertTrue(pl.endSign == null);
		assertTrue(pl.tag.getTagName().equals("�X�^�C����"));
	}
	
	@Test public void
	��s�͖�������邾���ŗ�O�𑗏o���Ȃ�����
	() throws TagconfException {
		paraconf = "";
		parser.parseParaSetting(paraconf);
		
		paraconf = " ";
		parser.parseParaSetting(paraconf);
		
		paraconf = "\n";
		parser.parseParaSetting(paraconf);
	}
	
	@Test public void
	�R�������Ȃ��ꍇ�͗�O�𑗏o���邱��
	() {
		paraconf = "�s���L��  �X�^�C����";
		try {
			parser.parseParaSetting(paraconf);
			fail("�G���[�����o����܂���ł���");
		} catch (TagconfException e) {
			System.out.println(e.getMessage());
		}
	}
}
