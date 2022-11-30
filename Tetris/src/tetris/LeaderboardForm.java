package tetris;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Vector;

public class LeaderboardForm extends JFrame implements ActionListener {
    private JButton goBackToMenuButton;
    private JTable scoreboard;
    private JScrollPane scrollPane;
    private DefaultTableModel tableModel;
    private String [] columns;
    private Object[][] data;
    private TableRowSorter<TableModel> sorter;
    private String filename = "Data";
    public LeaderboardForm(){
        this.setBounds(0,0,500,800);
        this.setLayout(null);

        initTableData();
        goBackToMenuButton = new JButton();
        goBackToMenuButton.setBounds(150,50,200,50);
        goBackToMenuButton.setText("Main menu");
        goBackToMenuButton.addActionListener(this);
        this.add(goBackToMenuButton);

        scoreboard = new JTable();
        scoreboard.setModel(tableModel);
        scoreboard.setBounds(100, 150, 300, 500);
        scoreboard.setBorder(BorderFactory.createLineBorder(Color.black));
        scoreboard.setVisible(true);
        scoreboard.setBackground(Color.lightGray);

        scrollPane = new JScrollPane(scoreboard);
        scrollPane.setBounds(100, 150, 300, 500);
        this.add(scrollPane);

        initSorter();

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void actionPerformed(ActionEvent e){
        this.setVisible(false);
        Main.goToMainMenu();
    }
    public void addPlayer(String playername, int score){
        tableModel.addRow(new Object[]{playername, score});
        sorter.sort();
        saveLeaderboard();
        this.setVisible(true);
    }

    private void initTableData(){
        Vector ci = new Vector();
        ci.add("Player");
        ci.add("Score");

        tableModel = new DefaultTableModel();
        tableModel.addColumn("Player");
        tableModel.addColumn("Score");

        try {
            FileInputStream fileInputStream = new FileInputStream(filename);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            tableModel.setDataVector((Vector<Vector>) objectInputStream.readObject(), ci);
            objectInputStream.close();
            fileInputStream.close();
        }catch (Exception e){}
    }

    private void saveLeaderboard(){
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(filename);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            objectOutputStream.writeObject(tableModel.getDataVector());
            objectOutputStream.close();
            fileOutputStream.close();
        } catch(Exception e){}
    }

    private void initSorter(){
        sorter = new TableRowSorter<>(tableModel);
        scoreboard.setRowSorter(sorter);
        ArrayList<RowSorter.SortKey> keys = new ArrayList<>();
        keys.add(new RowSorter.SortKey(1, SortOrder.DESCENDING));
        sorter.setSortKeys(keys);
    }
}
