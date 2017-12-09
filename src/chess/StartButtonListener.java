package chess;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class StartButtonListener implements ActionListener{
		Main mainClassRef;

		public StartButtonListener(Main ref) {
			mainClassRef = ref;
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {

			if (mainClassRef.getWhitePlayer() == null || mainClassRef.getBlackPlayer() == null) {
				JOptionPane.showMessageDialog(mainClassRef.getControlPanel(), "Fill in the details");
				return;
			}

			updatePlayersRecord();
			disablePlayerButtons();

			mainClassRef.getSplitPane().remove(mainClassRef.getTemporaryPanel());
			mainClassRef.getSplitPane().add(mainClassRef.getBoardPanel());
			mainClassRef.getPlayerViewPanel().remove(mainClassRef.getTimeSlider());

			mainClassRef.setChessBoardState(new BoardState(mainClassRef.getBoardPanel(), mainClassRef));
			mainClassRef.setGameSound(new SoundClass());
			addNewMoveLabel();
			addNewTurnLabel();

			mainClassRef.getTimeDisplayPanle().remove(mainClassRef.getStartButton());
			mainClassRef.getTimeDisplayPanle().add(mainClassRef.getTimeLabel());
			mainClassRef.getTimer().startTimer();
		}

		private void addNewTurnLabel() {
			mainClassRef.setTurnLabel(new JLabel(mainClassRef.getChessBoardState().getTurnLabel()));
			mainClassRef.getTurnLabel().setFont(new Font("Comic Sans MS", Font.BOLD, 20));
			mainClassRef.getTurnLabel().setForeground(Color.blue);
			mainClassRef.getPlayerViewPanel().add(mainClassRef.getTurnLabel());
		}

		private void addNewMoveLabel() {
			mainClassRef.setMoveLabel(new JLabel("Move:"));
			mainClassRef.getMoveLabel().setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
			mainClassRef.getMoveLabel().setForeground(Color.red);
			mainClassRef.getPlayerViewPanel().add(mainClassRef.getMoveLabel());
		}

		@SuppressWarnings("deprecation")
		private void disablePlayerButtons() {
			mainClassRef.getNewWhitePlayerButton().disable();
			mainClassRef.getNewBlackPlayerButton().disable();

			mainClassRef.getWhitePlayerSelectButton().disable();
			mainClassRef.getBlackPlayerSelectButton().disable();

			mainClassRef.getThemeSelectButton().disable();
		}

		private void updatePlayersRecord() {
			mainClassRef.getWhitePlayer().updateGamesPlayed();
			mainClassRef.getWhitePlayer().updatePlayersData();

			mainClassRef.getBlackPlayer().updateGamesPlayed();
			mainClassRef.getBlackPlayer().updatePlayersData();
		}
}
