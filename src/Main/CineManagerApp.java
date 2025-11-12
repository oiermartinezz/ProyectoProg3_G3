package Main;

import javax.swing.SwingUtilities;

import gui.VenatanaPrincipal;

public class CineManagerApp {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new VenatanaPrincipal().setVisible(true);
            }

		});
	}

}
