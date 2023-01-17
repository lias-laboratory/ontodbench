/*********************************************************************************
* This file is part of OntoDBench Project.
* Copyright (C) 2012  LIAS - ENSMA
*   Teleport 2 - 1 avenue Clement Ader
*   BP 40109 - 86961 Futuroscope Chasseneuil Cedex - FRANCE
* 
* OntoDBench is free software: you can redistribute it and/or modify
* it under the terms of the GNU Lesser General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* OntoDBench is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU Lesser General Public License for more details.
* 
* You should have received a copy of the GNU Lesser General Public License
* along with OntoDBench.  If not, see <http://www.gnu.org/licenses/>.
**********************************************************************************/
package fr.ensma.lias.ontodbench.ui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Label;
import java.awt.Panel;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.filechooser.FileNameExtensionFilter;

import fr.ensma.lias.ontodbench.metric.Metrics;
import fr.ensma.lias.ontodbench.metricprocess.MetricsTools;
import fr.ensma.lias.ontodbench.model.conversion.ControlThread;
import fr.ensma.lias.ontodbench.model.conversion.NTripleModel;
import fr.ensma.lias.ontodbench.model.segmentation.AOntoTrunk;

/**
 * @author Géraud FOKOU
 */
public class MainFrame {

	private final JFileChooser CHOOSE;
	private final Color ACTIVECOLOR = Color.LIGHT_GRAY;
	private final Color DESACTIVECOLOR = new Color(204, 204, 204);
	private final Color NOTECOLOR = new Color(230, 230, 250);
	private final JRadioButton TYPEXECBUTON;
	private final JRadioButton TYPEXECBUTON1;
	private final JRadioButton SOURCETYPEBUTON;
	private final JRadioButton SOURCETYPEBUTON1;
	private final JRadioButton SEGMENTBUTON;
	private final JRadioButton SEGMENTBUTON1;
	private final ButtonGroup TYPEEXECGROUP;
	private final ButtonGroup SOURCETYPEGROUP;
	private final ButtonGroup SEGMENTGROUP;
	private final JComboBox SIZECOMBOBOX;
	private final JComboBox EXTCOMBOBOX;
	private final JComboBox SGBDCOMBOBOX;
	private final JProgressBar SEGMENT1PROGRESSBAR;
	private final JProgressBar CONVERSIONPROGRESSBAR;
	private final JProgressBar SEGMENT2PROGRESSBAR;
	private final JProgressBar INSERTIONPROGRESSBAR;
	private final JProgressBar COMPUTATIONPROGRESSBAR;
	private final JLabel SEGMENT1DURATIONLABEL;
	private final JLabel CONVERSIONDURATIONLABEL;
	private final JLabel SEGMENT2DURATIONLABEL;
	private final JLabel INSERTIONDURATIONLABEL;
	private final JLabel COMPUTATIONDURATIONLABEL;
	private final JLabel SIZELABEL;
	private final JLabel EXTENSIONLABEL;
	private final JLabel PATHLABEL;
	private final JButton RESTARTBUTTON;
	private final JButton BROWNSEBUTTON;
	private final JTextField PATHVALUEXT;
	private final JTextField BDNAMETEXT;
	private JTextField hostText;
	private JTextField portText;
	private JTextField userText;
	private JPasswordField pwdText;
	private final long SIZEVALUES[] = new long[] { 256 * 1024, 512 * 1024, 1 * 1024 * 1024, 2 * 1024 * 1024,
			3 * 1024 * 1024 };
	private JFrame frmComputeMetric;
	private Panel toolsPanel;
	private Panel executionPanel;
	private Panel warningPanel;
	private JButton computeButton;
	private JButton ResetButton;
	private JLabel typexeclabel;
	private JLabel sgdbLabel;
	private JLabel bdlabel;
	private JLabel labelUser;
	private JLabel labelHost;
	private JLabel labelpwd;
	private JLabel labelPort;
	private Label toolsTitle;
	private JLabel sourcetypaLabel;
	private JLabel lblOperation;
	private JLabel lblProgression;
	private JLabel lblDuration;
	private JLabel segmentLabel;
	private JLabel lblSegmentation;
	private JLabel lblSegmentation_1;
	private JLabel lblConversion;
	private JLabel lblInsertion;
	private JLabel lblComputationMetric;
	private Label labelWarningPan;
	private JLabel warningLabel3;
	private JLabel warningLabel1;
	private JLabel warningLabel2;
	private JLabel warninglabel4;
	private JLabel warningLabel5;
	private Label title;

	private boolean metriqueOnFile = true;
	private boolean sourceTypeFile = true;
	private boolean segmentSource = true;
	private String sourceExt, sourcePath, sgbd, name_DB, user, port, pwd, host;
	private String destExt = "nt";
	private String destPath = "D:\\LUBM\\LUBMSegFile";// D:\\Ntriple";
	private long size;
	private boolean fin;

	private int canal = -1;;
	private long startseg1;
	private long startseg2;
	private long startconv;
	private long startins;
	private long startcomp;

	private MetricsThreads currentMetricsthread;
	private MetricsListener currentListener;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame window = new MainFrame();
					window.frmComputeMetric.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainFrame() {

		frmComputeMetric = new JFrame();
		frmComputeMetric.setTitle("Compute Metric");
		toolsPanel = new Panel();
		executionPanel = new Panel();
		warningPanel = new Panel();
		warningPanel.setLayout(null);

		warningLabel1 = new JLabel(
				"If your dataset file exceeds 4 Mbytes, it is strongly recommended to segment it before any usage.");
		warningLabel1.setBounds(10, 74, 1004, 14);
		warningPanel.add(warningLabel1);

		warningLabel2 = new JLabel(
				"If your dataset folder contents a file with a size greater than 4 Mbytes, it is strongly recommanded to segment the content before any usage.");
		warningLabel2.setBounds(10, 99, 1004, 14);
		warningPanel.add(warningLabel2);

		warningLabel3 = new JLabel(
				"The choice of \"segmentation\" is valid only for the first segmentation, the second is launched by the system when it is necessary.");
		warningLabel3.setBounds(10, 49, 1004, 14);
		warningPanel.add(warningLabel3);

		warninglabel4 = new JLabel(
				"Make sure that the manipulated database does not exist in the systems. If so, it will be destroyed and created again.");
		warninglabel4.setBounds(10, 124, 1004, 14);
		warningPanel.add(warninglabel4);

		warningLabel5 = new JLabel("...");
		warningLabel5.setBounds(10, 149, 1004, 14);
		warningPanel.add(warningLabel5);

		CHOOSE = new JFileChooser(System.getProperty("user.dir"));
		CHOOSE.setMultiSelectionEnabled(false);

		SIZECOMBOBOX = new JComboBox();
		SIZECOMBOBOX.setName("segmentSize");
		SIZECOMBOBOX.setModel(new DefaultComboBoxModel(new String[] { "256 KB", "512 KB", "1 MB", "2 MB", "3 MB" }));
		SIZECOMBOBOX.setSelectedIndex(0);
		SIZECOMBOBOX.setBackground(null);
		SIZECOMBOBOX.setBounds(115, 207, 84, 20);

		EXTCOMBOBOX = new JComboBox();
		EXTCOMBOBOX.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FileNameExtensionFilter filter = new FileNameExtensionFilter((String) EXTCOMBOBOX.getSelectedItem(),
						(String) EXTCOMBOBOX.getSelectedItem());
				CHOOSE.setFileFilter(filter);
			}
		});
		EXTCOMBOBOX.setName("extcomboBox");
		EXTCOMBOBOX
				.setModel(new DefaultComboBoxModel(new String[] { "RDF", "RDFS", "NT", "N3", "TTL", "DAML", "OWL" }));
		EXTCOMBOBOX.setSelectedIndex(0);
		EXTCOMBOBOX.setBackground(new Color(255, 255, 255));
		EXTCOMBOBOX.setBounds(115, 143, 84, 20);
		EXTCOMBOBOX.setVisible(true);

		SGBDCOMBOBOX = new JComboBox();
		SGBDCOMBOBOX.setModel(new DefaultComboBoxModel(new String[] { "POSTGRES", "MYSQL", "ORACLE", "SQLSERVER" }));
		SGBDCOMBOBOX.setSelectedIndex(0);
		SGBDCOMBOBOX.setBounds(666, 45, 106, 20);

		BDNAMETEXT = new JTextField();
		BDNAMETEXT.setHorizontalAlignment(SwingConstants.CENTER);
		BDNAMETEXT.setBackground(SystemColor.control);
		BDNAMETEXT.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		BDNAMETEXT.setText("ontologymetric");
		BDNAMETEXT.setBounds(862, 45, 90, 20);
		BDNAMETEXT.setColumns(12);

		userText = new JTextField();
		userText.setHorizontalAlignment(SwingConstants.CENTER);
		userText.setText("postgres");
		userText.setBounds(666, 88, 106, 20);
		toolsPanel.add(userText);
		userText.setColumns(10);

		hostText = new JTextField();
		hostText.setHorizontalAlignment(SwingConstants.CENTER);
		hostText.setText("localhost");
		hostText.setBounds(669, 143, 103, 20);
		toolsPanel.add(hostText);
		hostText.setColumns(10);

		portText = new JTextField();
		portText.setText("5433");
		portText.setHorizontalAlignment(SwingConstants.CENTER);
		portText.setBounds(862, 88, 90, 20);
		toolsPanel.add(portText);
		portText.setColumns(10);

		pwdText = new JPasswordField();
		pwdText.setHorizontalAlignment(SwingConstants.CENTER);
		pwdText.setBounds(860, 143, 92, 20);
		pwdText.setText("!Psql2011");
		toolsPanel.add(pwdText);

		SEGMENT1PROGRESSBAR = new JProgressBar();
		SEGMENT1PROGRESSBAR.setStringPainted(true);
		SEGMENT1PROGRESSBAR.setBounds(245, 95, 523, 14);

		CONVERSIONPROGRESSBAR = new JProgressBar();
		CONVERSIONPROGRESSBAR.setStringPainted(true);
		CONVERSIONPROGRESSBAR.setBounds(245, 132, 523, 14);

		SEGMENT2PROGRESSBAR = new JProgressBar();
		SEGMENT2PROGRESSBAR.setStringPainted(true);
		SEGMENT2PROGRESSBAR.setBounds(245, 175, 523, 14);

		INSERTIONPROGRESSBAR = new JProgressBar();
		INSERTIONPROGRESSBAR.setStringPainted(true);
		INSERTIONPROGRESSBAR.setBounds(245, 224, 523, 14);

		COMPUTATIONPROGRESSBAR = new JProgressBar();
		COMPUTATIONPROGRESSBAR.setStringPainted(true);
		COMPUTATIONPROGRESSBAR.setBounds(245, 267, 523, 14);

		SEGMENT1DURATIONLABEL = new JLabel("00:00:00:00");
		SEGMENT1DURATIONLABEL.setHorizontalAlignment(SwingConstants.CENTER);
		SEGMENT1DURATIONLABEL.setBounds(878, 93, 86, 14);

		CONVERSIONDURATIONLABEL = new JLabel("00:00:00:00");
		CONVERSIONDURATIONLABEL.setHorizontalAlignment(SwingConstants.CENTER);
		CONVERSIONDURATIONLABEL.setBounds(878, 130, 86, 14);

		SEGMENT2DURATIONLABEL = new JLabel("00:00:00:00");
		SEGMENT2DURATIONLABEL.setHorizontalAlignment(SwingConstants.CENTER);
		SEGMENT2DURATIONLABEL.setBounds(878, 173, 86, 14);

		INSERTIONDURATIONLABEL = new JLabel("00:00:00:00");
		INSERTIONDURATIONLABEL.setHorizontalAlignment(SwingConstants.CENTER);
		INSERTIONDURATIONLABEL.setBounds(878, 222, 86, 14);

		COMPUTATIONDURATIONLABEL = new JLabel("00:00:00:00");
		COMPUTATIONDURATIONLABEL.setHorizontalAlignment(SwingConstants.CENTER);
		COMPUTATIONDURATIONLABEL.setBounds(878, 265, 86, 14);

		RESTARTBUTTON = new JButton("Restart");
		RESTARTBUTTON.setBounds(463, 303, 89, 23);

		SIZELABEL = new JLabel("Segment's Size");
		SIZELABEL.setFont(new Font("Tahoma", Font.BOLD, 10));
		SIZELABEL.setLabelFor(SIZECOMBOBOX);
		SIZELABEL.setBounds(10, 210, 78, 14);

		EXTENSIONLABEL = new JLabel("Extension");
		EXTENSIONLABEL.setFont(new Font("Tahoma", Font.BOLD, 10));
		EXTENSIONLABEL.setName("extlabel");
		EXTENSIONLABEL.setBounds(10, 147, 78, 14);
		EXTENSIONLABEL.setLabelFor(EXTCOMBOBOX);
		EXTENSIONLABEL.setVisible(true);

		PATHLABEL = new JLabel("Path");
		PATHLABEL.setName("pathlabel");
		PATHLABEL.setBounds(10, 122, 28, 14);

		PATHVALUEXT = new JTextField();
		PATHLABEL.setLabelFor(PATHVALUEXT);
		PATHVALUEXT.setName("pathvalueText");
		PATHVALUEXT.setEditable(false);
		PATHVALUEXT.setBounds(115, 112, 286, 20);
		PATHVALUEXT.setColumns(10);

		BROWNSEBUTTON = new JButton("Brownse");
		BROWNSEBUTTON.setBounds(411, 111, 89, 23);
		BROWNSEBUTTON.setVisible(true);
		BROWNSEBUTTON.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CHOOSE.showDialog(frmComputeMetric, "Selectionner");
				if (CHOOSE.getSelectedFile() != null) {
					PATHVALUEXT.setText(CHOOSE.getSelectedFile().getPath());
				}
			}
		});

		TYPEEXECGROUP = new ButtonGroup();

		TYPEXECBUTON = new JRadioButton("Metric On Database");
		TYPEXECBUTON.setBackground(null);
		TYPEXECBUTON.setName("BmetricOnDB");
		TYPEXECBUTON.setBounds(258, 45, 175, 23);
		TYPEEXECGROUP.add(TYPEXECBUTON);

		TYPEXECBUTON1 = new JRadioButton("Metric On File");
		TYPEXECBUTON1.setSelected(true);
		TYPEXECBUTON1.setBackground(null);
		TYPEXECBUTON1.setName("BmetricOnFile");
		TYPEXECBUTON1.setBounds(115, 45, 106, 23);
		TYPEEXECGROUP.add(TYPEXECBUTON1);

		SOURCETYPEGROUP = new ButtonGroup();

		SOURCETYPEBUTON = new JRadioButton("Folder");
		SOURCETYPEBUTON.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (((JRadioButton) e.getSource()).isSelected()) {
					PATHVALUEXT.setVisible(true);
					PATHLABEL.setVisible(true);
					CHOOSE.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				}
			}
		});
		SOURCETYPEBUTON.setName("BsourceOnFolder");
		SOURCETYPEBUTON.setBackground(null);
		SOURCETYPEBUTON.setBounds(258, 87, 84, 23);
		SOURCETYPEGROUP.add(SOURCETYPEBUTON);

		SOURCETYPEBUTON1 = new JRadioButton("File");
		SOURCETYPEBUTON1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (((JRadioButton) e.getSource()).isSelected()) {
					PATHVALUEXT.setVisible(true);
					PATHLABEL.setVisible(true);
					CHOOSE.setFileSelectionMode(JFileChooser.FILES_ONLY);
					FileNameExtensionFilter filter = new FileNameExtensionFilter((String) EXTCOMBOBOX.getSelectedItem(),
							(String) EXTCOMBOBOX.getSelectedItem());
					CHOOSE.setFileFilter(filter);
				}
			}
		});
		SOURCETYPEBUTON1.setName("BsourceOnFile");
		SOURCETYPEBUTON1.setSelected(true);
		SOURCETYPEBUTON1.setBackground(null);
		SOURCETYPEBUTON1.setBounds(115, 87, 68, 23);
		SOURCETYPEGROUP.add(SOURCETYPEBUTON1);

		SEGMENTGROUP = new ButtonGroup();

		SEGMENTBUTON = new JRadioButton("Yes");
		SEGMENTBUTON.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (((JRadioButton) e.getSource()).isSelected()) {
					SIZECOMBOBOX.setVisible(true);
					SIZELABEL.setVisible(true);
				}
			}
		});
		SEGMENTBUTON.setBackground(null);
		SEGMENTBUTON.setSelected(true);
		SEGMENTBUTON.setName("segmentYes");
		SEGMENTBUTON.setBounds(115, 177, 109, 23);
		SEGMENTGROUP.add(SEGMENTBUTON);

		SEGMENTBUTON1 = new JRadioButton("No");
		SEGMENTBUTON1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (((JRadioButton) e.getSource()).isSelected()) {
					SIZECOMBOBOX.setVisible(false);
					SIZELABEL.setVisible(false);
				}
			}
		});
		SEGMENTBUTON1.setBackground(null);
		SEGMENTBUTON1.setName("segmentNo");
		SEGMENTBUTON1.setBounds(263, 177, 109, 23);
		SEGMENTGROUP.add(SEGMENTBUTON1);

		initialize();

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		frmComputeMetric.getContentPane().setBackground(Color.WHITE);
		frmComputeMetric.setBounds(200, 50, 1020, 786);
		frmComputeMetric.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmComputeMetric.getContentPane().setLayout(null);
		frmComputeMetric.setResizable(false);

		toolsPanel.setName("tools");
		toolsPanel.setBackground(this.ACTIVECOLOR);
		toolsPanel.setBounds(0, 0, 1014, 253);
		frmComputeMetric.getContentPane().add(toolsPanel);
		toolsPanel.setLayout(null);

		toolsTitle = new Label("TOOLS\r\n");
		toolsTitle.setName("Title");
		toolsTitle.setFont(new Font("Castellar", Font.BOLD, 14));
		toolsTitle.setForeground(new Color(0, 0, 205));
		toolsTitle.setAlignment(Label.CENTER);
		toolsTitle.setBounds(463, 10, 92, 22);
		toolsPanel.add(toolsTitle);

		toolsPanel.add(SIZECOMBOBOX);
		toolsPanel.add(EXTCOMBOBOX);
		toolsPanel.add(SIZELABEL);
		toolsPanel.add(EXTENSIONLABEL);
		toolsPanel.add(PATHLABEL);
		toolsPanel.add(PATHVALUEXT);
		toolsPanel.add(BROWNSEBUTTON);
		toolsPanel.add(SGBDCOMBOBOX);
		toolsPanel.add(BDNAMETEXT);
		toolsPanel.add(TYPEXECBUTON);
		toolsPanel.add(TYPEXECBUTON1);
		toolsPanel.add(SOURCETYPEBUTON);
		toolsPanel.add(SOURCETYPEBUTON1);
		toolsPanel.add(SEGMENTBUTON);
		toolsPanel.add(SEGMENTBUTON1);
		frmComputeMetric.getContentPane().add(CHOOSE);

		typexeclabel = new JLabel("Execution Type");
		typexeclabel.setBounds(10, 49, 99, 14);
		typexeclabel.setLabelFor(TYPEXECBUTON);
		toolsPanel.add(typexeclabel);

		sourcetypaLabel = new JLabel("Source Type");
		sourcetypaLabel.setBounds(10, 89, 78, 19);
		sourcetypaLabel.setLabelFor(SOURCETYPEBUTON);
		toolsPanel.add(sourcetypaLabel);

		segmentLabel = new JLabel("Segmentation");
		segmentLabel.setName("segmentLabel");
		segmentLabel.setBounds(10, 181, 99, 14);
		segmentLabel.setLabelFor(SEGMENTBUTON);
		toolsPanel.add(segmentLabel);

		sgdbLabel = new JLabel("DBMS");
		sgdbLabel.setLabelFor(SGBDCOMBOBOX);
		sgdbLabel.setBackground(new Color(240, 240, 240));
		sgdbLabel.setBounds(628, 48, 46, 14);
		toolsPanel.add(sgdbLabel);

		bdlabel = new JLabel("Data Base");
		bdlabel.setBounds(798, 48, 68, 14);
		toolsPanel.add(bdlabel);
		bdlabel.setLabelFor(BDNAMETEXT);

		labelUser = new JLabel("User");
		labelUser.setBounds(628, 91, 46, 14);
		toolsPanel.add(labelUser);
		labelUser.setLabelFor(userText);

		labelHost = new JLabel("Host");
		labelHost.setBounds(628, 146, 46, 14);
		toolsPanel.add(labelHost);

		labelPort = new JLabel("Port");
		labelPort.setBounds(798, 91, 46, 14);
		labelPort.setLabelFor(portText);
		toolsPanel.add(labelPort);

		labelpwd = new JLabel("Password");
		labelpwd.setLabelFor(pwdText);
		labelpwd.setBounds(798, 146, 68, 14);
		toolsPanel.add(labelpwd);

		computeButton = new JButton("Compute");
		computeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				metriqueOnFile = TYPEXECBUTON1.isSelected();
				sourceTypeFile = SOURCETYPEBUTON1.isSelected();
				segmentSource = SEGMENTBUTON.isSelected();
				sourceExt = (String) EXTCOMBOBOX.getSelectedItem();
				size = SIZEVALUES[SIZECOMBOBOX.getSelectedIndex()];
				sgbd = (String) SGBDCOMBOBOX.getSelectedItem();
				name_DB = BDNAMETEXT.getText();
				user = userText.getText();
				host = hostText.getText();
				port = portText.getText();
				pwd = String.copyValueOf(pwdText.getPassword());
				sourcePath = PATHVALUEXT.getText();
				executionPanel.setEnabled(true);
				toolsPanel.setBackground(DESACTIVECOLOR);
				executionPanel.setBackground(ACTIVECOLOR);

				currentMetricsthread = new MetricsThreads();
				currentMetricsthread.execute();
				currentListener = new MetricsListener();
				currentListener.execute();
			}
		});
		computeButton.setBounds(376, 219, 89, 23);
		toolsPanel.add(computeButton);

		ResetButton = new JButton("Reset");
		ResetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				SOURCETYPEBUTON.setEnabled(true);
				SOURCETYPEBUTON1.setEnabled(true);
				EXTCOMBOBOX.setEnabled(true);
				SEGMENTBUTON.setEnabled(true);
				SEGMENTBUTON1.setEnabled(true);
				SIZECOMBOBOX.setEnabled(true);
				PATHVALUEXT.setEnabled(true);
				BROWNSEBUTTON.setEnabled(true);
				TYPEXECBUTON1.setSelected(true);
				SOURCETYPEBUTON1.setSelected(true);
				SEGMENTBUTON.setSelected(true);
				SIZELABEL.setVisible(true);
				SIZECOMBOBOX.setVisible(true);
				SIZECOMBOBOX.setSelectedIndex(0);
				SGBDCOMBOBOX.setSelectedIndex(0);
				PATHVALUEXT.setText("");
				BDNAMETEXT.setText("OntologyMetric");
				userText.setText("postgres");
				hostText.setText("localhost");
				portText.setText("5432");
				pwdText.setText("");
				EXTCOMBOBOX.setSelectedIndex(0);
			}
		});
		ResetButton.setBounds(573, 219, 89, 23);
		toolsPanel.add(ResetButton);

		executionPanel.setBackground(this.DESACTIVECOLOR);
		executionPanel.setBounds(0, 253, 1014, 337);
		frmComputeMetric.getContentPane().add(executionPanel);
		executionPanel.setLayout(null);

		title = new Label("EXECUTION");
		title.setBounds(448, 10, 109, 24);
		title.setName("Title");
		title.setForeground(new Color(0, 0, 205));
		title.setFont(new Font("Castellar", Font.BOLD, 14));
		title.setAlignment(Label.CENTER);
		executionPanel.add(title);

		executionPanel.add(SEGMENT1PROGRESSBAR);
		executionPanel.add(CONVERSIONPROGRESSBAR);
		executionPanel.add(SEGMENT2PROGRESSBAR);
		executionPanel.add(INSERTIONPROGRESSBAR);
		executionPanel.add(COMPUTATIONPROGRESSBAR);
		executionPanel.add(SEGMENT1DURATIONLABEL);
		executionPanel.add(CONVERSIONDURATIONLABEL);
		executionPanel.add(SEGMENT2DURATIONLABEL);
		executionPanel.add(INSERTIONDURATIONLABEL);
		executionPanel.add(COMPUTATIONDURATIONLABEL);
		executionPanel.add(RESTARTBUTTON);

		lblOperation = new JLabel("Operation");
		lblOperation.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblOperation.setBounds(10, 55, 80, 15);
		executionPanel.add(lblOperation);

		lblProgression = new JLabel("Progression");
		lblProgression.setHorizontalAlignment(SwingConstants.CENTER);
		lblProgression.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblProgression.setBounds(448, 56, 99, 14);
		executionPanel.add(lblProgression);

		lblDuration = new JLabel("Duration");
		lblDuration.setHorizontalAlignment(SwingConstants.CENTER);
		lblDuration.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblDuration.setBounds(878, 56, 99, 14);
		executionPanel.add(lblDuration);

		lblSegmentation = new JLabel("Segmentation Source");
		lblSegmentation.setBounds(10, 93, 132, 14);
		executionPanel.add(lblSegmentation);

		lblConversion = new JLabel("Conversion");
		lblConversion.setBounds(10, 130, 80, 14);
		executionPanel.add(lblConversion);

		lblSegmentation_1 = new JLabel("Segmentation Ntriples");
		lblSegmentation_1.setBounds(10, 173, 132, 14);
		executionPanel.add(lblSegmentation_1);

		lblInsertion = new JLabel("Insertion");
		lblInsertion.setBounds(10, 222, 80, 14);
		executionPanel.add(lblInsertion);

		lblComputationMetric = new JLabel("Computation Metric");
		lblComputationMetric.setBounds(10, 265, 160, 14);
		executionPanel.add(lblComputationMetric);

		warningPanel.setBackground(this.NOTECOLOR);
		warningPanel.setBounds(0, 592, 1014, 166);
		labelWarningPan = new Label("INSTRUCTIONS FOR USE");
		labelWarningPan.setName("Title");
		labelWarningPan.setForeground(new Color(0, 0, 205));
		labelWarningPan.setFont(new Font("Castellar", Font.BOLD, 14));
		labelWarningPan.setAlignment(Label.CENTER);
		labelWarningPan.setBounds(400, 10, 209, 24);
		warningPanel.add(labelWarningPan);
		frmComputeMetric.getContentPane().add(warningPanel);

		TYPEXECBUTON.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				SOURCETYPEBUTON.setEnabled(false);
				SOURCETYPEBUTON1.setEnabled(false);
				EXTCOMBOBOX.setEnabled(false);
				SEGMENTBUTON.setEnabled(false);
				SEGMENTBUTON1.setEnabled(false);
				SIZECOMBOBOX.setEnabled(false);
				PATHVALUEXT.setEnabled(false);
				BROWNSEBUTTON.setEnabled(false);
			}
		});

		TYPEXECBUTON1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				SOURCETYPEBUTON.setEnabled(true);
				SOURCETYPEBUTON1.setEnabled(true);
				EXTCOMBOBOX.setEnabled(true);
				SEGMENTBUTON.setEnabled(true);
				SEGMENTBUTON1.setEnabled(true);
				SIZECOMBOBOX.setEnabled(true);
				PATHVALUEXT.setEnabled(true);
				BROWNSEBUTTON.setEnabled(true);
			}
		});

		RESTARTBUTTON.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				fin = true;
				SEGMENT1DURATIONLABEL.setText("00:00:00:00");
				CONVERSIONDURATIONLABEL.setText("00:00:00:00");
				SEGMENT2DURATIONLABEL.setText("00:00:00:00");
				INSERTIONDURATIONLABEL.setText("00:00:00:00");
				COMPUTATIONDURATIONLABEL.setText("00:00:00:00");
				SEGMENT1PROGRESSBAR.setValue(0);
				CONVERSIONPROGRESSBAR.setValue(0);
				SEGMENT2PROGRESSBAR.setValue(0);
				INSERTIONPROGRESSBAR.setValue(0);
				COMPUTATIONPROGRESSBAR.setValue(0);
				toolsPanel.setEnabled(true);
				executionPanel.setEnabled(false);
				toolsPanel.setBackground(ACTIVECOLOR);
				executionPanel.setBackground(DESACTIVECOLOR);
				SOURCETYPEBUTON.setEnabled(true);
				SOURCETYPEBUTON1.setEnabled(true);
				EXTCOMBOBOX.setEnabled(true);
				SEGMENTBUTON.setEnabled(true);
				SEGMENTBUTON1.setEnabled(true);
				SIZECOMBOBOX.setEnabled(true);
				PATHVALUEXT.setEnabled(true);
				BROWNSEBUTTON.setEnabled(true);
				TYPEXECBUTON1.setSelected(true);
				SOURCETYPEBUTON1.setSelected(true);
				SEGMENTBUTON.setSelected(true);
				SIZELABEL.setVisible(true);
				SIZECOMBOBOX.setVisible(true);
				SIZECOMBOBOX.setSelectedIndex(0);
				SGBDCOMBOBOX.setSelectedIndex(0);
				PATHVALUEXT.setText("");
				BDNAMETEXT.setText("OntologyMetric");
				userText.setText("postgres");
				hostText.setText("localhost");
				portText.setText("5432");
				pwdText.setText("");
				EXTCOMBOBOX.setSelectedIndex(0);

				if (!currentMetricsthread.isDone()) {
					currentMetricsthread.cancel(true);
					currentListener.cancel(true);
				}

			}
		});

		this.toolsPanel.setEnabled(true);
		this.executionPanel.setEnabled(false);
		this.executionPanel.setEnabled(false);
	}

	private String convertTime(long hour) {

		long nbhr = 0;
		long nbmin = 0;
		long nbsec = 0;
		long nbjr = 0;
		String day;
		String hr;
		String min;
		String sec;
		long temp;

		temp = hour / 1000;
		nbhr = temp / 3600;
		temp = temp % 3600;
		nbmin = temp / 60;
		nbsec = temp % 60;
		nbjr = nbhr / 24;
		nbhr = nbhr % 24;

		if (Long.toString(nbjr).length() < 2) {
			day = "0" + Long.toString(nbjr);
		} else {
			day = Long.toString(nbjr);
		}
		if (Long.toString(nbhr).length() < 2) {
			hr = "0" + Long.toString(nbhr);
		} else {
			hr = Long.toString(nbhr);
		}
		if (Long.toString(nbmin).length() < 2) {
			min = "0" + Long.toString(nbmin);
		} else {
			min = Long.toString(nbmin);
		}
		if (Long.toString(nbsec).length() < 2) {
			sec = "0" + Long.toString(nbsec);
		} else {
			sec = Long.toString(nbsec);
		}
		return (day + ":" + hr + ":" + min + ":" + sec);

	}

	class MetricsThreads extends SwingWorker<List<Integer>, Integer> {
		@Override
		public List<Integer> doInBackground() {
			fin = false;
			toolsPanel.setEnabled(false);
			if (!metriqueOnFile) {
				metricOnDB();
				return null;
			}
			if (sourceTypeFile) {
				if (segmentSource) {
					segmentAndconvertFile();
					return null;
				} else {
					convertAndSegmentFile();
					return null;
				}
			} else {
				if (segmentSource) {
					segmentAndconvertFolder();
					return null;
				} else {
					convertFolder();
					return null;
				}
			}
		}

		@Override
		protected void process(List<Integer> chunk) {
			for (int number : chunk) {

				canal = number;
				switch (number) {

				case 1:
					startcomp = System.currentTimeMillis();
					break;

				case 2:
					startseg1 = System.currentTimeMillis();
					break;

				case 3:
					startconv = System.currentTimeMillis();
					startins = System.currentTimeMillis();
					break;

				case 4:
					startcomp = System.currentTimeMillis();
					break;

				case 5:
					startconv = System.currentTimeMillis();
					break;

				case 6:
					startseg2 = System.currentTimeMillis();
					break;

				case 7:
					startins = System.currentTimeMillis();
					break;

				case 8:
					startcomp = System.currentTimeMillis();
					break;

				case 9:
					startseg1 = System.currentTimeMillis();
					break;

				case 10:
					startconv = System.currentTimeMillis();
					startins = System.currentTimeMillis();
					break;

				case 11:
					startcomp = System.currentTimeMillis();
					break;

				case 12:
					startconv = System.currentTimeMillis();
					startins = System.currentTimeMillis();
					break;

				case 13:
					startcomp = System.currentTimeMillis();
					break;
				}
			}
		}

		@Override
		protected void done() {
			fin = true;
		}

		private void metricOnDB() {
			MetricsTools tools = new MetricsTools(sgbd, name_DB, user, host, port, pwd);

			publish(1);
			tools.processMetric(destPath);

			return;
		}

		private void segmentAndconvertFile() {
			MetricsTools tools = new MetricsTools(sgbd, name_DB, user, host, port, pwd);
			String newSource;

			publish(2);
			newSource = tools.processSegmentFile(sourcePath, sourceExt, size);

			publish(3);
			tools.processConvertAndInsert(newSource, sourceExt, destPath, destExt);

			publish(4);
			tools.processMetric(destPath);
		}

		private void convertAndSegmentFile() {
			MetricsTools tools = new MetricsTools(sgbd, name_DB, user, host, port, pwd);
			String newSource;

			publish(5);
			newSource = tools.processConvertFile(sourcePath, sourceExt, destPath, destExt);

			publish(6);
			newSource = tools.processSegmentFile(newSource, destExt, 1 * 1024 * 1024);

			publish(7);
			tools.processInsertion(newSource);

			publish(8);
			tools.processMetric(destPath);

		}

		private void segmentAndconvertFolder() {
			MetricsTools tools = new MetricsTools(sgbd, name_DB, user, host, port, pwd);
			String newSource;

			publish(9);
			newSource = tools.processSegmentFolder(sourcePath, sourceExt, size);

			publish(10);
			tools.processConvertAndInsert(newSource, sourceExt, destPath, destExt);

			publish(11);
			tools.processMetric(destPath);
		}

		private void convertFolder() {
			MetricsTools tools = new MetricsTools(sgbd, name_DB, user, host, port, pwd);

			publish(12);
			tools.processConvertAndInsert(sourcePath, sourceExt, destPath, destExt);

			publish(13);
			tools.processMetric(destPath);
		}
	}

	class MetricsListener extends SwingWorker<List<Integer>, Integer> {

		@Override
		protected List<Integer> doInBackground() throws Exception {
			while (!fin) {
				publish(1);
				Thread.sleep(1000);
			}
			publish(1);
			return null;
		}

		@Override
		protected void process(List<Integer> chunk) {
			for (int number : chunk) {
				switch (canal) {
				case 1:
					COMPUTATIONDURATIONLABEL.setText(convertTime(System.currentTimeMillis() - startcomp));
					COMPUTATIONPROGRESSBAR.setValue((int) Metrics.evolutionmetric);
					break;

				case 2:
					SEGMENT1DURATIONLABEL.setText(convertTime(System.currentTimeMillis() - startseg1));
					SEGMENT1PROGRESSBAR.setValue((int) AOntoTrunk.evolutionfile);
					break;

				case 3:
					CONVERSIONPROGRESSBAR.setValue((int) (ControlThread.evolutionconv));
					if (CONVERSIONPROGRESSBAR.getValue() < CONVERSIONPROGRESSBAR.getMaximum()) {
						CONVERSIONDURATIONLABEL.setText(convertTime(System.currentTimeMillis() - startconv));
					}
					INSERTIONPROGRESSBAR.setValue((int) (ControlThread.evolutioninsert));
					INSERTIONDURATIONLABEL.setText(convertTime(System.currentTimeMillis() - startins));
					break;

				case 4:
					COMPUTATIONDURATIONLABEL.setText(convertTime(System.currentTimeMillis() - startcomp));
					COMPUTATIONPROGRESSBAR.setValue((int) Metrics.evolutionmetric);
					break;

				case 5:
					CONVERSIONDURATIONLABEL.setText(convertTime(System.currentTimeMillis() - startconv));
					CONVERSIONPROGRESSBAR.setValue((int) (NTripleModel.evolutionfile));
					break;

				case 6:
					CONVERSIONPROGRESSBAR.setValue((int) (NTripleModel.evolutionfile));
					SEGMENT2DURATIONLABEL.setText(convertTime(System.currentTimeMillis() - startseg2));
					SEGMENT2PROGRESSBAR.setValue((int) AOntoTrunk.evolutionfile);
					break;

				case 7:
					INSERTIONDURATIONLABEL.setText(convertTime(System.currentTimeMillis() - startins));
					INSERTIONPROGRESSBAR.setValue((int) (ControlThread.evolutioninsert));
					break;

				case 8:
					COMPUTATIONDURATIONLABEL.setText(convertTime(System.currentTimeMillis() - startcomp));
					COMPUTATIONPROGRESSBAR.setValue((int) Metrics.evolutionmetric);
					break;

				case 9:
					SEGMENT1DURATIONLABEL.setText(convertTime(System.currentTimeMillis() - startseg1));
					SEGMENT1PROGRESSBAR.setValue((int) AOntoTrunk.evolutionfolder);
					break;

				case 10:
					CONVERSIONPROGRESSBAR.setValue((int) (ControlThread.evolutionconv));
					if (CONVERSIONPROGRESSBAR.getValue() < CONVERSIONPROGRESSBAR.getMaximum()) {
						CONVERSIONDURATIONLABEL.setText(convertTime(System.currentTimeMillis() - startconv));
					}
					INSERTIONDURATIONLABEL.setText(convertTime(System.currentTimeMillis() - startins));
					INSERTIONPROGRESSBAR.setValue((int) (ControlThread.evolutioninsert));
					break;

				case 11:
					COMPUTATIONDURATIONLABEL.setText(convertTime(System.currentTimeMillis() - startcomp));
					COMPUTATIONPROGRESSBAR.setValue((int) Metrics.evolutionmetric);
					break;

				case 12:
					CONVERSIONPROGRESSBAR.setValue((int) (ControlThread.evolutionconv));
					if (CONVERSIONPROGRESSBAR.getValue() < CONVERSIONPROGRESSBAR.getMaximum()) {
						CONVERSIONDURATIONLABEL.setText(convertTime(System.currentTimeMillis() - startconv));
					}
					INSERTIONPROGRESSBAR.setValue((int) (ControlThread.evolutioninsert));
					INSERTIONDURATIONLABEL.setText(convertTime(System.currentTimeMillis() - startins));
					break;

				case 13:
					COMPUTATIONDURATIONLABEL.setText(convertTime(System.currentTimeMillis() - startcomp));
					COMPUTATIONPROGRESSBAR.setValue((int) Metrics.evolutionmetric);
					break;
				}
			}

		}

		@Override
		protected void done() {

			if (!currentMetricsthread.isDone()) {
				currentMetricsthread.cancel(true);
			}
			canal = -1;
			currentMetricsthread = null;
		}

	}
}
