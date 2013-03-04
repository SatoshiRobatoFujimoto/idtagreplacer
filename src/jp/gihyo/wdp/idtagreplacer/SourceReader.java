package jp.gihyo.wdp.idtagreplacer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

/**
 * �t�@�C����ǂݍ��ނ��߂̃N���X�ł��B
 * <code>readWithParser</code> ���\�b�h�Ńp�[�T�N���X���Ăэ��݁A
 * �t�@�C������ǂݍ��񂾓��e����s���p�[�T�ɓn���܂��B 
 */
public class SourceReader {
	private BufferedReader reader = null;
	
	public SourceReader(File file, Charset charset) throws FileNotFoundException {
		reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), charset));
	}
	
	public SourceReader(String filePath, Charset charset) throws FileNotFoundException {
		this(new File(filePath), charset);
	}
	
	public void readWithParser(SourceParser parser) throws IOException, SourceParserException {
		String line = null;
		while ((line = reader.readLine()) != null) {
			parser.parse(line);
		}
	}
	
	public void close() {
		try { reader.close(); } catch (Exception e) { }
	}
	
}
