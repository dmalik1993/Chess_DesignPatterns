package chess;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class AddNewPlayerListener implements ActionListener{
		
		private int color;
		private ContentLayout contLayout;
		AddNewPlayerListener(int i, ContentLayout contLayout) {
			color = i;
			this.contLayout = contLayout;
		}
		

		@Override
		public void actionPerformed(ActionEvent e) {
			addNewPlayer();
		}

		private void addNewPlayer() {
			JPanel playerPanel = (color == 0) ? contLayout.getWhitePlayerPanel()
					: contLayout.getBlackPlayerPanel();
			ArrayList<Player> playersList = Player.fetchPlayersData();
			Iterator<Player> playersListIterator = playersList.iterator();

			String playerName = JOptionPane.showInputDialog(playerPanel,
					"Enter your name");

			if (playerName == null || playerName.isEmpty()) {
				return;
			}

			while (playersListIterator.hasNext()) {
				if (playersListIterator.next().getName().equals(playerName)) {
					JOptionPane.showMessageDialog(playerPanel, "Player exists");
					return;
				}
			}

			Player newPlayer = new Player(playerName);
			newPlayer.updatePlayersData();
			if (color == 0)
				contLayout.setWhitePlayer(newPlayer); 
			else
			 contLayout.setBlackPlayer(newPlayer);	

			updatePlayerPanel(playerPanel, newPlayer);
			contLayout.setSelected(true);
		}

		private void updatePlayerPanel(JPanel playerPanel, Player newPlayer) {
			JPanel panelDetails = (color == 0) ? contLayout.getWhitePlayerDetailsPanel()
					: contLayout.getBlackPlayerDetailsPnale();
			panelDetails.removeAll();
			panelDetails.add(new JLabel(" " + newPlayer.getName()));
			panelDetails.add(new JLabel(" " + newPlayer.gamesPlayed()));
			panelDetails.add(new JLabel(" " + newPlayer.gamesWon()));
			playerPanel.revalidate();
			playerPanel.repaint();
			playerPanel.add(panelDetails);
		}
}
