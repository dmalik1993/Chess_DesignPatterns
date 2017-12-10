package chess;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class SavedGamesListener implements ActionListener{
	
	private ContentLayout contentLayout;
	
	public SavedGamesListener(ContentLayout contLayout) {
		this.contentLayout = contLayout;
	}
	
		@Override
		public void actionPerformed(ActionEvent arg0) {

			String savedGameName = (String) contentLayout.getSavedGameCombo().getSelectedItem();
			contentLayout.createChessBoardPanel();
			contentLayout.setSavedState((BoardState) SavedGame
					.fetchSavedGamesData(savedGameName));

			List<Player> playersList = SavedGame.fetchSavedGamesData(
					savedGameName).getPlayerDetail();
			contentLayout.setPlayersComboBoxData(0, playersList.get(0).getSelectedPlayer());
			contentLayout.setWhitePlayerData(playersList.get(0));
			contentLayout.setPlayersComboBoxData(1, playersList.get(1).getSelectedPlayer());
			contentLayout.setBlackPlayerData(playersList.get(1));

		}
}
