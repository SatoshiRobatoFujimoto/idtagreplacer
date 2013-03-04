package jp.gihyo.wdp.idtagreplacer;


import org.junit.Test;
import org.junit.Assert;

public class SimpleCharacterSignTest {
	@Test public void 
	����̃^�O�ϊ������ȏ�s���邱��
	() {
		CharacterTag t = new CharacterTag("Style");
		CharacterSign s = new SimpleCharacterSign("��test��", "��/test��", t);
		String testBuf = 
			"����������������������test��������������/test�������ĂƁ�test���Ȃɂʂ˂́�/test���͂Ђӂւ�";
		String expected =
			"��������������������<CharStyle:Style>����������<CharStyle:>�����Ă�<CharStyle:Style>�Ȃɂʂ˂�<CharStyle:>�͂Ђӂւ�";
		String ret = s.convertSign(testBuf);
		Assert.assertTrue(ret.equals(expected));
		
		s = new SimpleCharacterSign("�y�s", "�t�z", t);
		testBuf = 
			"�@�܂��A���[�U�́A�T�[�r�X���ƂɃA�J�E���g�̍쐬�^�Ǘ�������ς킵������������AOpenID�Ή��T�C�g�ł���΁A���g�́y�yOpenID URL�z�z����͂��邾���Ń��O�C�����邱�Ƃ��\�ɂȂ�܂��B�܂��AOpenID�̎d�l�́AOpenID�R�~���j�e�B��ML�ł̋c�_���x�[�X�ɍ쐬������J����邽�߁AOpenID�v���o�C�_�i�y�sOpenID Provider�t�z�A�ȉ��y�yOP�z�z�j�AOpenID�ɂ��F�؂�񋟂���T�[�r�X�v���o�C�_�i�y�sRelying Party�t�z�A�ȉ��y�yRP�z�z�j�̂ǂ�������B���g�ŗ����グ�鎖���ł��܂��B���̂��߁A���B�̌l��񂪓���̈�ЂɏW������Ƃ����S�z������܂���B";
		expected =
			"�@�܂��A���[�U�́A�T�[�r�X���ƂɃA�J�E���g�̍쐬�^�Ǘ�������ς킵������������AOpenID�Ή��T�C�g�ł���΁A���g�́y�yOpenID URL�z�z����͂��邾���Ń��O�C�����邱�Ƃ��\�ɂȂ�܂��B�܂��AOpenID�̎d�l�́AOpenID�R�~���j�e�B��ML�ł̋c�_���x�[�X�ɍ쐬������J����邽�߁AOpenID�v���o�C�_�i<CharStyle:Style>OpenID Provider<CharStyle:>�A�ȉ��y�yOP�z�z�j�AOpenID�ɂ��F�؂�񋟂���T�[�r�X�v���o�C�_�i<CharStyle:Style>Relying Party<CharStyle:>�A�ȉ��y�yRP�z�z�j�̂ǂ�������B���g�ŗ����グ�鎖���ł��܂��B���̂��߁A���B�̌l��񂪓���̈�ЂɏW������Ƃ����S�z������܂���B";
		ret = s.convertSign(testBuf);
		Assert.assertTrue(ret.equals(expected));
	}
}
