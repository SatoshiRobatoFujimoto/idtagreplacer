package jp.gihyo.wdp.idtagreplacer.gui;

import java.awt.LayoutManager;

import javax.swing.JPanel;

/**
 * �A�v���P�[�V�����̃��C���E�B���h�E�̒��g�ł��B
 */
public abstract class MainPanel extends JPanel {
	MainPanel(LayoutManager layout) {
		super(layout);
	}

	abstract void setStatusMessage(String message);
}