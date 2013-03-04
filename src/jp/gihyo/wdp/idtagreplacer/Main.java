package jp.gihyo.wdp.idtagreplacer;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.ListIterator;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import jp.gihyo.wdp.idtagreplacer.gui.MainFrame;
import jp.gihyo.wdp.idtagreplacer.gui.MainFrameFactory;
import jp.gihyo.wdp.idtagreplacer.gui.MainPanel;
import jp.gihyo.wdp.idtagreplacer.gui.MainPanelFactory;

/**
 * �A�v���P�[�V�����̃X�^�[�g�|�C���g�ƂȂ�N���X�ł��B
 */
public class Main {
	private Charset charset = null;
	private String lineFeedCode = null;
	private boolean dropStop = true;
	private String savefileFormat = null;
	private MainFrame frame = null;
	private String tagconfFilePath = null;
	String defaultParagraphName = null;

	/**
	 * �A�v���P�[�V�����̃X�^�[�g�|�C���g�ł��B
	 * @param args �R�}���h���C�������i���p���Ȃ��j
	 * @throws IOException �ݒ�t�@�C���̓ǂݍ��ݎ��A���邢�̓��K�[�������ɗ�O�����������ꍇ
	 */
	public static void main(String[] args) throws IOException {
		
		// ����� Mac OS X �̃��j���[�o�[�̕\���𐧌䂷�邽�߂̃R�[�h�ł��B
		System.setProperty("com.apple.mrj.application.apple.menu.about.name", "InDesign Tag Replacer");

		setupLogger();
		Logger.global.info("�v���O�������N�����܂����B");
		
		Main m = new Main();
		try {
			m.frame = m.showGUI();
			m.tagconfFilePath = m.getTagconfFilePath("tagconf.xml");
			m.readProperties(m.tagconfFilePath);
			m.setDropStop(false);
		} catch (IOException ioe) {
			if (m.frame != null) m.frame.showErrorMessage("tagconf.xml ��������Ȃ����A�ݒ���e���s���ł��B");
			Logger.global.severe("tagconf.xml ��������Ȃ����A�ݒ���e���s���ł��B\n" + ioe.getMessage());
		} catch (TagconfException tage) {
			if (m.frame != null) m.frame.showErrorMessage(tage.getMessage());
			Logger.global.severe(tage.getMessage());
		}
	}
	
	private String getTagconfFilePath(String defaultPath) {
		File f = new File(defaultPath);
		if (f.exists()) return defaultPath;
		String p = frame.showFileChooser("�^�O�ݒ�t�@�C����I�����Ă��������B");
		return p != null ? p : defaultPath;
	}

	private static void setupLogger() throws SecurityException, IOException {
		Handler handler = new FileHandler("log/log%g.txt", 50000, 10, true);
		handler.setFormatter(new SimpleFormatter());
		Logger.global.addHandler(handler);
	}
	
	private void readProperties(String filePath) throws IOException, TagconfException {
		PropertiesInfo info = 
			new PropertiesLoaderHelper().readProperties(filePath);
		lineFeedCode = info.lineFeedCode;
		savefileFormat = info.savefileFormat;
		defaultParagraphName = info.defaultParagraphName;
		charset = info.charset;
	}

	private MainFrame showGUI() throws IOException {
		MainFrame f = MainFrameFactory.create();
		MainPanel p = MainPanelFactory.create();
		DropTarget t = new DnDDropTarget();
		p.setDropTarget(t);
		f.setContentPane(p);
		f.show();
		return f;
	}
	
	private void setDropStop(boolean stop) {
		dropStop = stop;
		frame.setStatusMessage(dropStop ? "" : "�ҏW�L���t���e�L�X�g�t�@�C�����h���b�O���Ă��������B" );
	}
	
	/**
	 * �����œn���ꂽ�e�t�@�C���ɁA�^�O�ϊ��������{���܂��B
	 * @param args �����ΏۂƂȂ�t�@�C���̃��X�g
	 * @throws FileNotFoundException
	 * @throws SourceParserException
	 * @throws Exception
	 */
	public void exec(List<File> args) throws FileNotFoundException, SourceParserException, Exception {
		setDropStop(true);
		ListIterator<File> itr = args.listIterator();
		while (itr.hasNext()) {
			File f = itr.next();
			Logger.global.info("'" + f.getPath() + "'���^�O�ϊ������ɂ����悤�Ƃ��Ă��܂��B");
			if (f.isDirectory()) {
				frame.showErrorMessage(f.getName() + "�̓f�B���N�g���ł��B�����ł��܂���B");
				setDropStop(false);
				throw new Exception("�f�B���N�g�����������悤�Ƃ��܂����B");
			} else if (f.isFile()) {
				execEachFile(f);
			}
			App.getInstance().cleanupActiveParagraphTag();
			App.getInstance().getActiveParagraphTag().add(new ParagraphTag(defaultParagraphName));
		}
		frame.showMessage("�ϊ�����", "�^�O�u���������I���܂����B");
		Logger.global.info("�^�O�ϊ��������I�����܂����B");
		setDropStop(false);
	}

	private String getStartTagString() {
		String c = charset.name();
		if (c.equals("UTF-8")) return "<UNICODE-MAC>"; 
		else if (c.equals("Shift_JIS")) return "<SJIS-MAC>";
		else return null;
	}

	private File getResultFilePath(File sourceFile) {
		String dir = sourceFile.getParent();
		String fn = sourceFile.getName();
		String name = fn.substring(0, fn.lastIndexOf('.'));
		String targetPath = dir + File.separator + 
			String.format(savefileFormat, name);
		return new File(targetPath);
	}
	
	private void execEachFile(File sourceFile) throws FileNotFoundException, SourceParserException, Exception {
		SourceReader sr = new SourceReader(sourceFile, charset);
		String startTagLine = getStartTagString();
		if (startTagLine == null) {
			Logger.global.severe("�w�肳�ꂽ�G���R�[�h'" + charset.name() + "'�ł́AInDesign�J�n�^�O�����܂���ł����B");
			throw new Exception("�w�肳�ꂽ�����R�[�h�́A���̃v���O�������T�|�[�g���Ă��܂���B\nInDesign�̊J�n�^�O�����܂���B");
		}
		App.out = new Printer(getResultFilePath(sourceFile), charset);
		App.out.setLineFeedCode(lineFeedCode);
		App.out.print(startTagLine);
		App.out.print(App.out.getLineFeedCode());
		
		SourceParser p = new SourceParserImpl();
		sr.readWithParser(p);
		sr.close();
		App.out.close();
	}
	
	class DnDDropTarget extends DropTarget {
		private static final long serialVersionUID = -809232689759788536L;

		@SuppressWarnings("unchecked")
		@Override
		public void drop(DropTargetDropEvent dtde) {
			if ( ! dtde.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
				dtde.rejectDrop();
				return;
			}
			if (dropStop) {
				dtde.rejectDrop();
				return;
			}

			dtde.acceptDrop(DnDConstants.ACTION_COPY);
			Transferable t = dtde.getTransferable();
			try {
				List<File> ret = (List<File>)t.getTransferData(DataFlavor.javaFileListFlavor);
				exec(ret);
				dtde.dropComplete(true);
			} catch (UnsupportedFlavorException e) {
				Logger.global.severe(e.getMessage());
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				Logger.global.severe(e.getMessage());
				e.printStackTrace();
			} catch (IOException e) {
				Logger.global.severe(e.getMessage());
				e.printStackTrace();
			} catch (Exception e) {
				Logger.global.severe(e.getMessage());
				frame.showErrorMessage(e.getMessage());
				e.printStackTrace();
			}
		}
	}
}