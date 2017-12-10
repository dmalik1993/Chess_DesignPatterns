package chess;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

public class PlayerSelectionListener implements ActionListener{

		private int color;
		private ContentLayout contLayout;
		
		PlayerSelectionListener(int i, ContentLayout contLayout) {
			color = i;
			this.contLayout = contLayout;
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			contLayout.setSelectedPlayer(null);

			JComboBox<String> currentPlayerNamesComboBox = (color == 0) ? contLayout.getWhitePlayerCombo() : contLayout.getBlackPlayerCombo();

			contLayout.setPlayersComboBoxData(color, (String) currentPlayerNamesComboBox.getSelectedItem());

			if (color == 0) {
				contLayout.getWhitePlayerData().setSelectedPlayer((String) currentPlayerNamesComboBox.getSelectedItem());
			} else if (color == 1) {
				contLayout.getBlackPlayerData().setSelectedPlayer((String) currentPlayerNamesComboBox.getSelectedItem());
			}
		}
	
		
}
