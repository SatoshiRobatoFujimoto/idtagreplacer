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
 * アプリケーションのスタートポイントとなるクラスです。
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
	 * アプリケーションのスタートポイントです。
	 * 
	 * @param args
	 *            コマンドライン引数（利用しない）
	 * @throws IOException
	 *             設定ファイルの読み込み時、あるいはロガー生成時に例外が発生した場合
	 */
	public static void main(String[] args) throws IOException {

		// これは Mac OS X のメニューバーの表示を制御するためのコードです。
		System.setProperty("com.apple.mrj.application.apple.menu.about.name",
				"InDesign Tag Replacer");

		setupLogger();
		Logger.global.info("プログラムを起動しました。");

		Main m = new Main();
		try {
			m.frame = m.showGUI();
			m.tagconfFilePath = m.getTagconfFilePath("tagconf.xml");
			m.readProperties(m.tagconfFilePath);
			m.setDropStop(false);
		} catch (IOException ioe) {
			if (m.frame != null)
				m.frame.showErrorMessage("tagconf.xml が見つからないか、設定内容が不正です。");
			Logger.global.severe("tagconf.xml が見つからないか、設定内容が不正です。\n"
					+ ioe.getMessage());
		} catch (TagconfException tage) {
			if (m.frame != null)
				m.frame.showErrorMessage(tage.getMessage());
			Logger.global.severe(tage.getMessage());
		}
	}

	private String getTagconfFilePath(String defaultPath) {
		File f = new File(defaultPath);
		if (f.exists())
			return defaultPath;
		String p = frame.showFileChooser("タグ設定ファイルを選択してください。");
		return p != null ? p : defaultPath;
	}

	private static void setupLogger() throws SecurityException, IOException {
		File logDir = new File("log");
		if (logDir.exists() == false && logDir.mkdir() == false) {
			throw new IOException();
		}
		Handler handler = new FileHandler("log/log%g.txt", 50000, 10, true);
		handler.setFormatter(new SimpleFormatter());
		Logger.global.addHandler(handler);
	}

	private void readProperties(String filePath) throws IOException,
			TagconfException {
		PropertiesInfo info = new PropertiesLoaderHelper()
				.readProperties(filePath);
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
		frame.setStatusMessage(dropStop ? "" : "編集記号付きテキストファイルをドラッグしてください。");
	}

	/**
	 * 引数で渡された各ファイルに、タグ変換処理を施します。
	 * 
	 * @param args
	 *            処理対象となるファイルのリスト
	 * @throws FileNotFoundException
	 * @throws SourceParserException
	 * @throws Exception
	 */
	public void exec(List<File> args) throws FileNotFoundException,
			SourceParserException, Exception {
		setDropStop(true);
		ListIterator<File> itr = args.listIterator();
		while (itr.hasNext()) {
			File f = itr.next();
			Logger.global.info("'" + f.getPath() + "'をタグ変換処理にかけようとしています。");
			if (f.isDirectory()) {
				frame.showErrorMessage(f.getName() + "はディレクトリです。処理できません。");
				setDropStop(false);
				throw new Exception("ディレクトリを処理しようとしました。");
			} else if (f.isFile()) {
				execEachFile(f);
			}
			App.getInstance().cleanupActiveParagraphTag();
			App.getInstance().getActiveParagraphTag()
					.add(new ParagraphTag(defaultParagraphName));
		}
		frame.showMessage("変換完了", "タグ置換処理が終わりました。");
		Logger.global.info("タグ変換処理が終了しました。");
		setDropStop(false);
	}

	private String getStartTagString() {
		String c = charset.name();
		if (c.equals("UTF-8"))
			return "<UNICODE-MAC>";
		else if (c.equals("Shift_JIS"))
			return "<SJIS-MAC>";
		else
			return null;
	}

	private File getResultFilePath(File sourceFile) {
		String dir = sourceFile.getParent();
		String fn = sourceFile.getName();
		String name = fn.substring(0, fn.lastIndexOf('.'));
		String targetPath = dir + File.separator
				+ String.format(savefileFormat, name);
		return new File(targetPath);
	}

	private void execEachFile(File sourceFile) throws FileNotFoundException,
			SourceParserException, Exception {
		SourceReader sr = new SourceReader(sourceFile, charset);
		String startTagLine = getStartTagString();
		if (startTagLine == null) {
			Logger.global.severe("指定されたエンコード'" + charset.name()
					+ "'では、InDesign開始タグが作れませんでした。");
			throw new Exception(
					"指定された文字コードは、このプログラムがサポートしていません。\nInDesignの開始タグが作れません。");
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
			if (!dtde.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
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
				List<File> ret = (List<File>) t
						.getTransferData(DataFlavor.javaFileListFlavor);
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