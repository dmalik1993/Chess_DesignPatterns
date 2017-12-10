package chess;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ThemeSelectionListener implements ActionListener{
	
	private ContentLayout contLayout;
	ThemeSelectionListener( ContentLayout contLayout) {
		this.contLayout = contLayout;
	}
	
	@Override
		public void actionPerformed(ActionEvent arg0) {

			JComboBox<String> currentPlayerNamesComboBox = contLayout.getThemeCombo();

			JPanel playerDetails = contLayout.getThemePanelDetail();
			JPanel playerPanel = contLayout.getThemePanel();

			if (contLayout.isThemeSelected() == true)
				playerDetails.removeAll();
			contLayout.setGameTheme( (String) currentPlayerNamesComboBox.getSelectedItem());


			if (contLayout.getGameTheme().equals("Leipzig")) {
				contLayout.setGameThemeCode("2_");
			} else if (contLayout.getGameTheme().equals("Merida")) {
				contLayout.setGameThemeCode("3_");
			} else if (contLayout.getGameTheme().equals("Cheq")) {
				contLayout.setGameThemeCode("4_");
			} else if (contLayout.getGameTheme().equals("Alpha")) {
				contLayout.setGameThemeCode("5_");
			} else {
				contLayout.setGameThemeCode("");
			}

			playerDetails.add(new JLabel(" " + contLayout.getGameTheme()));

			playerPanel.revalidate();
			playerPanel.repaint();
			playerPanel.add(playerDetails);
			contLayout.setThemeSelected(true);		}
}
