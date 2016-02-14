package GUI;

import java.awt.Component;

import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.UIManager;

public class CheckboxListRenderer implements ListCellRenderer {

	@Override
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean selected, boolean focus) {
		JCheckBox checkbox = (JCheckBox) value;
        checkbox.setFocusPainted(false);
        checkbox.setBorderPainted(true);
        checkbox.setEnabled(true);
        return checkbox;
	}

}
