package view;

import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import controller.TabeleSearchActions;
import model.Lek;
import model.table.DrugsTableModel;
import view.dialog.DodajLekDialog;

import javax.swing.JScrollPane;

public class TabelaLekovaPanel extends JPanel {
	private TableModel model;

	public TabelaLekovaPanel() {
		this.setLayout(new BorderLayout());
		JLabel wellcomeMessage = new JLabel();
		wellcomeMessage.setText("Tabela Lekova");
		wellcomeMessage.setFont(new Font("Serif", Font.BOLD, 40));
		wellcomeMessage.setHorizontalAlignment(JLabel.CENTER);
		wellcomeMessage.setVerticalAlignment(JLabel.CENTER);

		this.setBackground(MainFrame.getInstance().getZelenaPozadina());
		this.add(wellcomeMessage);

		this.addTable();
		this.addBottomPanel();
	}

	private void addTable() {
		JPanel panel = new JPanel();

		// Create table
		List<Lek> drugs = MainFrame.getInstance().getLekRepozitorijum().ucitajLekove();

		this.model = new DrugsTableModel(drugs);

		final JTable table = new JTable(this.model);

		final TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(this.model);

		table.setRowSorter(sorter);

		// Create Combo box for search
		final JComboBox<String> searchableColumnsCb = new JComboBox<String>(Lek.getTableHeader());
		searchableColumnsCb.setVisible(true);

		// Create input
		JTextField searchField = new JTextField();
		searchField.setPreferredSize(new Dimension(150, 25));  
		
		// Set actions
		TabeleSearchActions lekoviSearchActions = new TabeleSearchActions(searchField, searchableColumnsCb, sorter);

		searchField.addKeyListener(lekoviSearchActions.processKeyReleased());
		searchableColumnsCb.addActionListener(lekoviSearchActions.processSelection());
		
		// Pack GUI
		panel.add(searchableColumnsCb);
		panel.add(searchField);
		panel.add(new JScrollPane(table));
		this.add(panel);
	}
	
	private void addBottomPanel() {
		JPanel buttonBox = new JPanel();
		buttonBox.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 30));
		buttonBox.setPreferredSize(new Dimension(100, 100));
		buttonBox.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		
		JButton insertujLek = new JButton("Insertuj Lek");
		insertujLek.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				DodajLekDialog dodajLekDialog = new DodajLekDialog(true);
				dodajLekDialog.setVisible(true);
			}
		});
		insertujLek.setPreferredSize(new Dimension(150, 50));
		buttonBox.add(insertujLek);
		
		JButton editujLek = new JButton("Edituj Lek");
		editujLek.setPreferredSize(new Dimension(150, 50));
		buttonBox.add(editujLek);
		
		this.add(buttonBox, BorderLayout.SOUTH);
	}

}

