package pathfindingsource;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;

public class MyMouseListener extends MouseAdapter {
   private Grid mapGrid;

   public MyMouseListener(Grid theGrid) {
      this.mapGrid = theGrid;
   }

   @Override
   public void mousePressed(MouseEvent e) {
      if (e.getButton() == MouseEvent.BUTTON1) {
         mapGrid.labelPressed((JLabel)e.getSource());
      }
   }
}