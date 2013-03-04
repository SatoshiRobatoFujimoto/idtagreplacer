package jp.gihyo.wdp.idtagreplacer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;

/**
 * �ۑ���̃t�@�C�����o�͂��邽�߂̃N���X�ł��B
 */
public class Printer {
	private String lineFeedCode = null;
	private BufferedWriter writer = null;

	/**
	 * �w�肳�ꂽ�t�@�C���ƕ����R�[�h�ŁA�o�͗p�I�u�W�F�N�g�𐶐����܂��B
	 * @param file �ۑ���̃t�@�C��
	 * @param charset �ۑ��t�@�C���̕����R�[�h
	 * @throws FileNotFoundException
	 */
	public Printer(File file, Charset charset) throws FileNotFoundException {
		writer = 
			new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), charset));
		lineFeedCode = System.getProperty("line.separator");
	}

	/**
	 * �w�肳�ꂽ�t�@�C���p�X�ƕ����R�[�h�ŁA�o�͗p�I�u�W�F�N�g�𐶐����܂��B
	 * @param filePath �ۑ���̃t�@�C���p�X
	 * @param charset �ۑ��t�@�C���̕����R�[�h
	 * @throws FileNotFoundException
	 */
	public Printer(String filePath, Charset charset) throws FileNotFoundException {
		this(new File(filePath), charset);
	}
	
	/**
	 * ���s�R�[�h���w�肵�܂��B
	 * �w��ł���l�͌����I�� <code>LF</code>�A<code>CR</code>�A<code>CRLF</code> �̂����ꂩ�ł��B
	 * ����̓��e������ "LF" �̂悤�Ɏw�肵�Ă��A���ڕ����R�[�h�Ŏw�肵�Ă����܂��܂���B
	 * �v���O�����́A��L�̃��e�����ɑ΂��ẮA�Y�����镶���R�[�h�ɕϊ����鏈�����s���܂����A
	 * ����ȊO�̕����񂪓n���ꂽ�ꍇ�́A�m�[�`�F�b�N�ł�������s�R�[�h�ƌ��Ȃ��A�o�̓t�@�C����
	 * �e�s���ɕt�����܂��B
	 * @param lineFeedCode ���s�R�[�h��\��������
	 */
	public void setLineFeedCode(String lineFeedCode) {
		String c = lineFeedCode;
		if (lineFeedCode.equals("LF")) {
			c = (new Character((char)10)).toString();
		} else if (lineFeedCode.equals("CR")) {
			c = (new Character((char)13)).toString();
		} else if (lineFeedCode.equals("CRLF")) {
			c = (new Character((char)13)).toString() + 
				(new Character((char)10)).toString();
		}
		this.lineFeedCode = c;
	}
	
	/**
	 * �I�u�W�F�N�g�ɐݒ肳��Ă�����s�R�[�h��Ԃ��܂��B
	 * ����� "LF" �̂悤�ȉǂ̕�����ł͂Ȃ��A���ۂ̉��s�R�[�h���Ԃ���܂��B
	 * @return ���s�R�[�h
	 */
	public String getLineFeedCode() {
		return lineFeedCode;
	}

	/**
	 * �n���ꂽ�����̕�������o�̓t�@�C���ɏo�͂��܂��B
	 * ���s�R�[�h�͕t������܂���B
	 * @param buf �o�͂��镶����
	 * @throws IOException �o�͎��ɗ�O�����������ꍇ
	 */
	public void print(String buf) throws IOException {
		writer.write(buf);
	}
	
	/**
	 * �n���ꂽ�����̕�������o�̓t�@�C���ɏo�͂��܂��B
	 * <code>print</code> ���\�b�h�ƈႢ�A�o�͎��ɉ��s�R�[�h���t������܂��B
	 * �������A<code>print</code> ���\�b�h�Ƃ̈Ⴂ�́A���s�R�[�h�̂���A�Ȃ��ɗ��܂�܂���B
	 * ���҂͈Ӗ��I�ɑ傫������Ă��܂��B
	 * <code>println</code> ���\�b�h�́A�s���o�͂��ꂽ���Ƃ��v���O�����ɖ������܂��B
	 * �v���O�����͂���ɂ���āA�i���^�O�̐�����s���܂��B
	 * ����ɑ΂��A<code>print</code> ���\�b�h�ɂ���Ē��ډ��s�R�[�h���o�͂����ꍇ�́A
	 * �v���O�����͒i���^�O�̐�����s���܂���B
	 * @param buf �o�͂��镶����
	 * @throws IOException �o�͎��ɗ�O�����������ꍇ
	 * @throws SourceParserException
	 */
	public void println(String buf) throws IOException, SourceParserException {
		ParagraphTag t = null;
		if (App.getInstance().getActiveParagraphTag().size() == 0) {
			throw new SourceParserException("�i���^�O�̊J�n�^�O�ƕ��^�O�̑Ή��ɕs��������܂����B");
		} else {
			t = App.getInstance().getActiveParagraphTag().getLast();
			writer.write(t.getTagString());
		}
		writer.write(buf + lineFeedCode);
	}
	
	/**
	 * �ۑ���̃t�@�C������܂��B
	 * @throws IOException
	 */
	public void close() throws IOException {
		writer.close();
	}
}
