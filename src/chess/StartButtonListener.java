package chess;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class StartButtonListener implements ActionListener{
		ContentLayout contentLayout;

		public StartButtonListener(ContentLayout ref) {
			contentLayout = ref;
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {

			if (contentLayout.getWhitePlayer() == null || contentLayout.getBlackPlayer() == null) {
				JOptionPane.showMessageDialog(contentLayout.getControlPanel(), "Fill in the details");
				return;
			}

			updatePlayersRecord();
			disablePlayerButtons();

			contentLayout.getSplitPane().remove(contentLayout.getTemporaryPanel());
			contentLayout.getSplitPane().add(contentLayout.getBoardPanel());
			contentLayout.getPlayerViewPanel().remove(contentLayout.getTimeSlider());

			contentLayout.setChessBoardState(new BoardState(contentLayout.getBoardPanel(), contentLayout));
			contentLayout.setGameSound(new SoundClass());
			addNewMoveLabel();
			addNewTurnLabel();

			contentLayout.getTimeDisplayPanle().remove(contentLayout.getStartButton());
			contentLayout.getTimeDisplayPanle().add(contentLayout.getTimeLabel());
			contentLayout.getTimer().startTimer();
		}

		private void addNewTurnLabel() {
			contentLayout.setTurnLabel(new JLabel(contentLayout.getChessBoardState().getTurnLabel()));
			contentLayout.getTurnLabel().setFont(new Font("Comic Sans MS", Font.BOLD, 20));
			contentLayout.getTurnLabel().setForeground(Color.blue);
			contentLayout.getPlayerViewPanel().add(contentLayout.getTurnLabel());
		}

		private void addNewMoveLabel() {
			contentLayout.setMoveLabel(new JLabel("Move:"));
			contentLayout.getMoveLabel().setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
			contentLayout.getMoveLabel().setForeground(Color.red);
			contentLayout.getPlayerViewPanel().add(contentLayout.getMoveLabel());
		}

		@SuppressWarnings("deprecation")
		private void disablePlayerButtons() {
			contentLayout.getNewWhitePlayerButton().disable();
			contentLayout.getNewBlackPlayerButton().disable();

			contentLayout.getWhitePlayerSelectButton().disable();
			contentLayout.getBlackPlayerSelectButton().disable();

			contentLayout.getThemeSelectButton().disable();
		}

		private void updatePlayersRecord() {
			contentLayout.getWhitePlayer().updateGamesPlayed();
			contentLayout.getWhitePlayer().updatePlayersData();

			contentLayout.getBlackPlayer().updateGamesPlayed();
			contentLayout.getBlackPlayer().updatePlayersData();
		}
}
