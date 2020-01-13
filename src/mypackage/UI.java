package mypackage;

import java.awt.BorderLayout;
import java.awt.Dialog.ModalityType;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class UI extends JFrame {

   private JPanel panel;
   private JMenuBar menuBar;
   private JMenu menu;
   private JMenuItem addProcess;
   private JMenuItem unBlock;
   private JMenuItem quitSim;
   private JMenuItem terminate;
   private JButton CS;
   private JTextArea info;
   private JScrollPane scroll;
   private PQueue dis;
   private final String contextSwitch = "CONTEXT SWITCH: ";
   private int count = 1;

   public UI(PQueue dispatcher) {
       dis = dispatcher;
       initComponents();
   }
   
   

   private void addNewBox() {
       final JDialog addDialog = new JDialog();

       JPanel miniPanel = new JPanel(new BorderLayout());
       miniPanel.setPreferredSize(new Dimension(175, 100));

       ButtonGroup radioGroup = new ButtonGroup();
       final JRadioButton blocked = new JRadioButton("Blocked List");
       final JRadioButton ready = new JRadioButton("Ready Queue");
       ready.setSelected(true);
       radioGroup.add(ready);
       radioGroup.add(blocked);
       JPanel radios = new JPanel();
       radios.setPreferredSize(new Dimension(50, 60));
       radios.add(ready);
       radios.add(blocked);
       final JTextField text = new JTextField(10);

       JButton addButton = new JButton("Add");
       addButton.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               try {
                   int temp = Integer.parseInt(text.getText());
                   if (blocked.isSelected()) {
                       dis.makeBlocked(temp);
                       info.setText(info.getText()
                               + "\nAdded Process to the Blocked List\n"
                               + dis.getStatus());

                   } else {
                	   dis.makeReady(temp);
                       info.setText(info.getText()
                               + "\nAdded Process to the Ready Queue\n"
                               + dis.getStatus());

                   }
                   addDialog.dispose();
               } catch (NumberFormatException n) {
                   UI.errorMessage("Please Enter a Number");
               }
           }
       });

       JLabel title = new JLabel(" Process Priority:");

       miniPanel.add(title, BorderLayout.NORTH);
       miniPanel.add(addButton, BorderLayout.WEST);
       miniPanel.add(text, BorderLayout.EAST);
       miniPanel.add(radios, BorderLayout.SOUTH);

       addDialog.setTitle("Add New Process");
       addDialog.add(miniPanel);
       addDialog.setResizable(false);
       addDialog.pack();
       addDialog.setLocationRelativeTo(null);
       addDialog.setModal(true);
       addDialog.setAlwaysOnTop(true);
       addDialog.setModalityType(ModalityType.APPLICATION_MODAL);
       addDialog.setVisible(true);

   }
   
   private void terminationBox() {
       final JDialog addDialog = new JDialog();

       JPanel miniPanel = new JPanel(new BorderLayout());
       miniPanel.setPreferredSize(new Dimension(125, 75));

       JLabel title = new JLabel(" Select Process");
       JButton kill = new JButton("Kill");
       final JComboBox<String> termBox = new JComboBox();
       kill.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               Object[] tempArray = dis.getReadyQueue().toArray();
               for (int i = 0; i < tempArray.length; i++) {
                   if (((Process) tempArray[i]).returnID().equals(
                           (String) termBox.getSelectedItem()))
                	   dis.getReadyQueue().remove(((Process) tempArray[i]));

               }
               for (int i = 0; i < dis.getBlockedList().size(); i++) {
                   if (dis.getBlockedList().get(i).returnID()
                           .equals((String) termBox.getSelectedItem()))
                	   dis.getBlockedList().remove(i);
               }
               info.setText(info.getText() + "\nTerminated Process "
                       + (String) termBox.getSelectedItem() + "\n"
                       + dis.getStatus());
               addDialog.dispose();
           }
       });
       Object[] tempP = dis.getReadyQueue().toArray();
       for (int i = 0; i < tempP.length; i++) {
           termBox.addItem(((Process) tempP[i]).returnID());
       }
       for (int i = 0; i < dis.getBlockedList().size(); i++) {
           termBox.addItem(dis.getBlockedList().get(i).returnID());
       }
       termBox.setEditable(false);

       miniPanel.add(title, BorderLayout.NORTH);
       miniPanel.add(kill, BorderLayout.CENTER);
       miniPanel.add(termBox, BorderLayout.SOUTH);

       addDialog.setTitle("Kill Process");
       addDialog.add(miniPanel);
       addDialog.setResizable(false);
       addDialog.pack();
       addDialog.setLocationRelativeTo(null);
       addDialog.setModal(true);
       addDialog.setAlwaysOnTop(true);
       addDialog.setModalityType(ModalityType.APPLICATION_MODAL);
       addDialog.setVisible(true);

   }
   private void blockBox() {
       final JDialog addDialog = new JDialog();

       JPanel miniPanel = new JPanel(new BorderLayout());
       miniPanel.setPreferredSize(new Dimension(100, 100));

       JLabel title = new JLabel(" Select Process ID");

       JPanel leftPanel = new JPanel();
       leftPanel.setPreferredSize(new Dimension(110, 75));
       JPanel rightPanel = new JPanel();
       

       final JComboBox<String> blockBox = new JComboBox();
       Object[] tempP = dis.getReadyQueue().toArray();
       for (int i = 0; i < tempP.length; i++) {
           blockBox.addItem(((Process) tempP[i]).returnID());
       }
       blockBox.setEditable(false);
       

       final JComboBox<String> unBlockBox = new JComboBox();
       for (int i = 0; i < dis.getBlockedList().size(); i++) {
           unBlockBox.addItem(dis.getBlockedList().get(i).returnID());
       }
       unBlockBox.setEditable(false);
       JButton unBlock = new JButton("UnBlock");
       unBlock.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               for(int i=0; i<dis.getBlockedList().size();i++)
               {
                   if(dis.getBlockedList().get(i).returnID().equals((String)unBlockBox.getSelectedItem()))
                   {
                	   dis.getReadyQueue().add(dis.getBlockedList().get(i));
                	   dis.getBlockedList().remove(i);
                   }  
               }
               info.setText(info.getText() + "\nUnBlocked Process "
                       + (String) unBlockBox.getSelectedItem() + "\n"
                       + dis.getStatus());
               addDialog.dispose();
           }
       });
       leftPanel.add(unBlock, BorderLayout.NORTH);
       leftPanel.add(unBlockBox, BorderLayout.SOUTH);

       miniPanel.add(title, BorderLayout.NORTH);
       miniPanel.add(leftPanel, BorderLayout.WEST);
       miniPanel.add(rightPanel, BorderLayout.EAST);

       addDialog.setTitle("Unblock Process");
       addDialog.add(miniPanel);
       addDialog.setResizable(false);
       addDialog.pack();
       addDialog.setLocationRelativeTo(null);
       addDialog.setModal(true);
       addDialog.setAlwaysOnTop(true);
       addDialog.setModalityType(ModalityType.APPLICATION_MODAL);
       addDialog.setVisible(true);

   }

   
   
   private void initComponents() {
       setTitle("David Berlin Dispatcher");
       setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       setResizable(false);
       panel = new JPanel(new BorderLayout());
       panel.setPreferredSize(new Dimension(400, 380));
       add(panel);

       menuBar = new JMenuBar();
       menu = new JMenu("Options");
       addProcess = new JMenuItem("Add New Process");
       addProcess.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               addNewBox();
           }
       });
       unBlock = new JMenuItem("Unblock Process");
       unBlock.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               blockBox();
           }
       });
       terminate = new JMenuItem("Terminate");
       terminate.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               terminationBox();
           }
       });
       quitSim = new JMenuItem("Quit Simulator");
       quitSim.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               dispose();
               UI.exitMessage("           	Thank You!");
               
           }
       });
       menu.add(addProcess);
       menu.add(unBlock);
       menu.add(terminate);
       menu.add(quitSim);
       menuBar.add(menu);

       CS = new JButton("Context Switch");
       CS.setPreferredSize(new Dimension(10, 40));
       CS.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
        	   dis.contextSwitch();
               info.setText(info.getText() + "\n" + contextSwitch + (count++) + "\n"
                       + dis.getStatus());
           }
       });

       info = new JTextArea();
       info.setEditable(false);
       scroll = new JScrollPane(info, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
               JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
       scroll.setToolTipText("Output");
       info.setText(dis.getStatus());
       scroll.setPreferredSize(new Dimension(400, 300));
       panel.add(scroll, BorderLayout.EAST);
       panel.add(CS, BorderLayout.NORTH);
       setJMenuBar(menuBar);
       setLocation(650, 350);
       setVisible(true);
       pack();
   }

   
   

   public static void errorMessage(String message) {
       JDialog addDialog = new JDialog();

       JPanel miniPanel = new JPanel(new BorderLayout());
       miniPanel.setPreferredSize(new Dimension(175, 100));
       JLabel mess = new JLabel(message);
       miniPanel.add(mess);
      
       addDialog.add(miniPanel);
       addDialog.setTitle("ERROR");
       addDialog.setResizable(false);
       addDialog.pack();
       addDialog.setLocationRelativeTo(null);
       addDialog.setModal(true);
       addDialog.setAlwaysOnTop(true);
       addDialog.setModalityType(ModalityType.APPLICATION_MODAL);
       addDialog.setVisible(true);

   }
   public static void exitMessage(String message){
	   
	   JDialog addDialog = new JDialog();

       JPanel miniPanel = new JPanel(new BorderLayout());
       miniPanel.setPreferredSize(new Dimension(40, 40));
       JLabel mess = new JLabel(message);
       miniPanel.add(mess);
      
       addDialog.add(miniPanel);
       addDialog.setResizable(false);
       addDialog.pack();
       addDialog.setLocationRelativeTo(null);
       addDialog.setModal(true);
       addDialog.setAlwaysOnTop(true);
       addDialog.setModalityType(ModalityType.APPLICATION_MODAL);
       addDialog.setVisible(true);
   }
   }

