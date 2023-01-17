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

import java.awt.EventQueue;
import java.awt.Panel;

import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;

import fr.ensma.lias.ontodbench.mapper.mappers.*;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.UIManager;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 * @author Géraud FOKOU
 */
public class MapperUI {

	private JFrame frmMappingRepresentation;
	private JTextField userText;
	private JTextField hostText;
	private JTextField portText;
	private JPasswordField pwdText;
	private Panel paramPanel;
	private Panel evolutionPanel;
	private Panel queryPanel;
	private JComboBox dbmsCombo;
	private JTextField dbText;
	private JButton validParamButon;
	private JButton resetButon;
	private JLabel CreateHschemaLabel;
	private JLabel sgbdLabel;
	private JLabel dbLabel;
	private JLabel hostLabel;
	private JLabel userLabel;
	private JLabel portLabel;
	private JLabel pwdLabel;
	private JLabel operationTitle;
	private JLabel progressTitle;
	private JLabel excTitle;
	private JLabel titleLabel;
	private JLabel lblDuration;
	private JLabel labeldurationHschema;
	private JLabel labelBschema;
	private JLabel labelHupload;
	private JLabel labelBupload;
	private JProgressBar progressBarUploadB;
	private JProgressBar progressBarUploadH;
	private JProgressBar progressBarCreateBschema;
	private JProgressBar progressBarCreateHschema;
	private JLabel CreateBschemaLabel;
	private JLabel UploadHschemaLabel;
	private JLabel UploadBschemaLabel;
	private JLabel titleQueryLabel;
	private JButton queryStart;
	private JButton startMappingButon;
	private BMapperThread bmap;
	private VMapperThread vmap;
	private HMapperThread hmap;
	private JLabel lblIteration;
	private JComboBox iterationcomboBox;
	private long startCreatehShema;
	private long startCreatebschema;
	private long starthupload;
	private long startbupload;
	private int huploadevol;
	private int buploadevol;
	private boolean finUpload;
	private boolean finbmap;
	private boolean finhmap;
	private MappingThreads mapper;
	private MappingListener mapperListener;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MapperUI window = new MapperUI();
					window.frmMappingRepresentation.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MapperUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmMappingRepresentation = new JFrame();
		frmMappingRepresentation.setTitle("Mapping Representation");
		frmMappingRepresentation.setResizable(false);
		frmMappingRepresentation.setBounds(100, 100, 863, 669);
		frmMappingRepresentation.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmMappingRepresentation.getContentPane().setLayout(null);

		paramPanel = new Panel();
		paramPanel.setBackground(Color.GRAY);
		paramPanel.setBounds(0, 0, 857, 141);
		frmMappingRepresentation.getContentPane().add(paramPanel);
		paramPanel.setLayout(null);

		titleLabel = new JLabel("TOOLS");
		titleLabel.setLabelFor(paramPanel);
		titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 12));
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setBounds(337, 0, 61, 23);
		paramPanel.add(titleLabel);

		sgbdLabel = new JLabel("DBMS");
		sgbdLabel.setBounds(10, 31, 110, 14);
		paramPanel.add(sgbdLabel);

		dbLabel = new JLabel("Data Base");
		dbLabel.setBounds(10, 73, 110, 23);
		paramPanel.add(dbLabel);

		userLabel = new JLabel("User");
		userLabel.setBounds(392, 34, 46, 14);
		paramPanel.add(userLabel);

		hostLabel = new JLabel("Host");
		hostLabel.setBounds(392, 77, 46, 14);
		paramPanel.add(hostLabel);

		portLabel = new JLabel("Port");
		portLabel.setBounds(633, 31, 61, 14);
		paramPanel.add(portLabel);

		pwdLabel = new JLabel("Password");
		pwdLabel.setBounds(633, 77, 78, 14);
		paramPanel.add(pwdLabel);

		dbText = new JTextField();
		dbLabel.setLabelFor(dbText);
		dbText.setText("ontologymetric");
		dbText.setBounds(160, 74, 124, 20);
		paramPanel.add(dbText);
		dbText.setColumns(10);

		userText = new JTextField();
		userLabel.setLabelFor(userText);
		userText.setText("postgres");
		userText.setBounds(471, 28, 127, 20);
		paramPanel.add(userText);
		userText.setColumns(10);

		hostText = new JTextField();
		hostLabel.setLabelFor(hostText);
		hostText.setText("localhost");
		hostText.setBounds(471, 74, 127, 20);
		paramPanel.add(hostText);
		hostText.setColumns(10);

		portText = new JTextField();
		portLabel.setLabelFor(portText);
		portText.setText("5433");
		portText.setBounds(751, 29, 96, 17);
		paramPanel.add(portText);
		portText.setColumns(10);

		pwdText = new JPasswordField();
		pwdLabel.setLabelFor(pwdText);
		pwdText.setBounds(751, 74, 96, 20);
		pwdText.setText("!Psql2011");
		paramPanel.add(pwdText);

		validParamButon = new JButton("OK");
		validParamButon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				progressBarCreateHschema.setValue(0);
				progressBarCreateBschema.setValue(0);
				progressBarUploadH.setValue(0);
				progressBarUploadB.setValue(0);
				labeldurationHschema.setText("00:00:00:00");
				labelBschema.setText("00:00:00:00");
				labelHupload.setText("00:00:00:00");
				labelBupload.setText("00:00:00:00");
				evolutionPanel.setEnabled(true);
				queryPanel.setEnabled(true);
				paramPanel.setEnabled(false);
				return;
			}
		});

		validParamButon.setBounds(195, 107, 89, 23);
		paramPanel.add(validParamButon);

		resetButon = new JButton("RESET");
		resetButon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dbmsCombo.setSelectedIndex(0);
				dbText.setText("ontologymetric");
				userText.setText("postgres");
				hostText.setText("localhost");
				portText.setText("5432");
				pwdText.setText("");
				progressBarCreateHschema.setValue(0);
				progressBarCreateBschema.setValue(0);
				progressBarUploadH.setValue(0);
				progressBarUploadB.setValue(0);
				labeldurationHschema.setText("00:00:00:00");
				labelBschema.setText("00:00:00:00");
				labelHupload.setText("00:00:00:00");
				labelBupload.setText("00:00:00:00");
				return;
			}
		});
		resetButon.setBounds(473, 107, 89, 23);
		paramPanel.add(resetButon);

		dbmsCombo = new JComboBox();
		sgbdLabel.setLabelFor(dbmsCombo);
		dbmsCombo.setModel(new DefaultComboBoxModel(new String[] { "POSTGRES", "MYSQL", "ORACLE", "SQL SERVER" }));
		dbmsCombo.setSelectedIndex(0);
		dbmsCombo.setMaximumRowCount(2);
		dbmsCombo.setBounds(156, 28, 128, 20);
		paramPanel.add(dbmsCombo);

		evolutionPanel = new Panel();
		evolutionPanel.setBackground(UIManager.getColor("CheckBox.light"));
		evolutionPanel.setBounds(0, 140, 857, 377);
		frmMappingRepresentation.getContentPane().add(evolutionPanel);
		evolutionPanel.setLayout(null);

		excTitle = new JLabel("Mapping Execution");
		excTitle.setLabelFor(evolutionPanel);
		excTitle.setFont(new Font("Times New Roman", Font.BOLD, 14));
		excTitle.setHorizontalAlignment(SwingConstants.CENTER);
		excTitle.setBounds(376, 11, 130, 20);
		evolutionPanel.add(excTitle);

		operationTitle = new JLabel("OPERATIONS");
		operationTitle.setFont(new Font("Times New Roman", Font.BOLD, 12));
		operationTitle.setBounds(10, 65, 99, 14);
		evolutionPanel.add(operationTitle);

		progressTitle = new JLabel("PROGRESSION");
		progressTitle.setFont(new Font("Times New Roman", Font.BOLD, 12));
		progressTitle.setHorizontalAlignment(SwingConstants.CENTER);
		progressTitle.setBounds(389, 62, 109, 20);
		evolutionPanel.add(progressTitle);

		lblDuration = new JLabel("DURATION");
		lblDuration.setFont(new Font("Times New Roman", Font.BOLD, 12));
		lblDuration.setHorizontalAlignment(SwingConstants.CENTER);
		lblDuration.setBounds(748, 65, 99, 14);
		evolutionPanel.add(lblDuration);

		CreateHschemaLabel = new JLabel("Create H_Schema");
		CreateHschemaLabel.setBounds(20, 95, 147, 14);
		evolutionPanel.add(CreateHschemaLabel);

		CreateBschemaLabel = new JLabel("Create B_Schema");
		CreateBschemaLabel.setBounds(20, 159, 147, 14);
		evolutionPanel.add(CreateBschemaLabel);

		UploadBschemaLabel = new JLabel("Upload B_Schema");
		UploadBschemaLabel.setBounds(20, 291, 147, 14);
		evolutionPanel.add(UploadBschemaLabel);

		UploadHschemaLabel = new JLabel("Upload H_Schema");
		UploadHschemaLabel.setBounds(20, 226, 147, 14);
		evolutionPanel.add(UploadHschemaLabel);

		progressBarCreateHschema = new JProgressBar();
		CreateHschemaLabel.setLabelFor(progressBarCreateHschema);
		progressBarCreateHschema.setStringPainted(true);
		progressBarCreateHschema.setBounds(177, 97, 532, 14);
		evolutionPanel.add(progressBarCreateHschema);

		progressBarCreateBschema = new JProgressBar();
		CreateBschemaLabel.setLabelFor(progressBarCreateBschema);
		progressBarCreateBschema.setStringPainted(true);
		progressBarCreateBschema.setBounds(177, 159, 532, 14);
		evolutionPanel.add(progressBarCreateBschema);

		progressBarUploadH = new JProgressBar();
		UploadHschemaLabel.setLabelFor(progressBarUploadH);
		progressBarUploadH.setStringPainted(true);
		progressBarUploadH.setBounds(177, 226, 532, 14);
		evolutionPanel.add(progressBarUploadH);

		progressBarUploadB = new JProgressBar();
		UploadBschemaLabel.setLabelFor(progressBarUploadB);
		progressBarUploadB.setStringPainted(true);
		progressBarUploadB.setBounds(177, 291, 532, 14);
		evolutionPanel.add(progressBarUploadB);

		labeldurationHschema = new JLabel("00:00:00:00");
		labeldurationHschema.setHorizontalAlignment(SwingConstants.CENTER);
		labeldurationHschema.setBounds(748, 95, 99, 14);
		evolutionPanel.add(labeldurationHschema);

		labelBschema = new JLabel("00:00:00:00");
		labelBschema.setHorizontalAlignment(SwingConstants.CENTER);
		labelBschema.setBounds(748, 159, 99, 14);
		evolutionPanel.add(labelBschema);

		labelHupload = new JLabel("00:00:00:00");
		labelHupload.setHorizontalAlignment(SwingConstants.CENTER);
		labelHupload.setBounds(748, 224, 99, 14);
		evolutionPanel.add(labelHupload);

		labelBupload = new JLabel("00:00:00:00");
		labelBupload.setHorizontalAlignment(SwingConstants.CENTER);
		labelBupload.setBounds(748, 291, 99, 14);
		evolutionPanel.add(labelBupload);

		startMappingButon = new JButton("START");
		startMappingButon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				progressBarCreateHschema.setValue(0);
				progressBarCreateBschema.setValue(0);
				progressBarUploadH.setValue(0);
				progressBarUploadB.setValue(0);
				labeldurationHschema.setText("00:00:00:00");
				labelBschema.setText("00:00:00:00");
				labelHupload.setText("00:00:00:00");
				labelBupload.setText("00:00:00:00");
				mapper = new MappingThreads();
				mapperListener = new MappingListener();
				mapper.mapping = true;
				mapper.execute();
				mapperListener.execute();
			}
		});
		startMappingButon.setBounds(399, 343, 89, 23);
		evolutionPanel.add(startMappingButon);

		queryPanel = new Panel();
		queryPanel.setBackground(Color.LIGHT_GRAY);
		queryPanel.setBounds(0, 519, 857, 122);
		frmMappingRepresentation.getContentPane().add(queryPanel);
		queryPanel.setLayout(null);

		titleQueryLabel = new JLabel("Query Execution");
		titleQueryLabel.setFont(new Font("Times New Roman", Font.BOLD, 14));
		titleQueryLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleQueryLabel.setLabelFor(queryPanel);
		titleQueryLabel.setBounds(392, 11, 115, 14);
		queryPanel.add(titleQueryLabel);

		queryStart = new JButton("START");
		queryStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				mapper = new MappingThreads();
				mapperListener = new MappingListener();
				mapper.mapping = false;
				mapper.execute();
				mapperListener.execute();
			}

		});
		queryStart.setBounds(559, 53, 89, 23);
		queryPanel.add(queryStart);

		lblIteration = new JLabel("Iteration");
		lblIteration.setHorizontalAlignment(SwingConstants.CENTER);
		lblIteration.setFont(new Font("Times New Roman", Font.BOLD, 12));
		lblIteration.setBounds(314, 57, 78, 14);
		queryPanel.add(lblIteration);

		iterationcomboBox = new JComboBox();
		lblIteration.setLabelFor(iterationcomboBox);
		iterationcomboBox
				.setModel(new DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" }));
		iterationcomboBox.setMaximumRowCount(2);
		iterationcomboBox.setBounds(429, 56, 78, 17);
		queryPanel.add(iterationcomboBox);

		paramPanel.setEnabled(true);
		evolutionPanel.setEnabled(false);
		queryPanel.setEnabled(false);
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

	@SuppressWarnings({ "rawtypes" })
	private void drawAverageResult(List arg1, List arg2, List arg3, String title) {

		DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		for (int i = 0; i < arg1.size(); i++) {

			try {
				dataset.addValue((Number) arg1.get(i), "Horizontal", "Query".concat(Integer.toString(i + 1)));
				dataset.addValue((Number) arg2.get(i), "Binary", "Query".concat(Integer.toString(i + 1)));
				dataset.addValue((Number) arg3.get(i), "Vertical", "Query".concat(Integer.toString(i + 1)));
			} catch (Exception e) {
			}
		}
		JFreeChart chart1 = ChartFactory.createBarChart3D(title, "QUERY", "Times(ms)", dataset,
				PlotOrientation.VERTICAL, true, true, false);
		chart1.setBackgroundPaint(Color.white);
		chart1.getTitle().setPaint(Color.BLACK);
		CategoryPlot p = chart1.getCategoryPlot();
		p.getRenderer().setSeriesPaint(0, Color.darkGray);
		p.getRenderer().setSeriesPaint(1, Color.gray);
		p.setRangeGridlinePaint(Color.white);
		ChartFrame frame = new ChartFrame("Comparaison Of Execution on the Different Ontology's Representation",
				chart1);
		frame.setVisible(true);
		frame.setSize(700, 400);
	}

	private void Affichertableau(String titre, String[] titres, Object[][] resultats) {

		// Create and set up the window.
		JFrame frame = new JFrame(titre);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Create and set up the content pane.
		TableOfResult newContentPane = new TableOfResult(titres, resultats);
		newContentPane.setOpaque(true); // content panes must be opaque
		frame.setContentPane(newContentPane);

		// Display the window.
		frame.pack();
		frame.setVisible(true);
	}

	class MappingThreads extends SwingWorker<List<Integer>, Integer> {

		public boolean mapping;

		@Override
		public List<Integer> doInBackground() {

			finUpload = false;
			finbmap = false;
			finhmap = false;

			if (mapping) {
				executeMapping();
			} else {
				executeQuery();
				Affichertableau("Représentation Verticale", vmap.getentete(), vmap.getallResults());
				Affichertableau("Représentation Binaire", bmap.getentete(), bmap.getallResults());
				Affichertableau("Représentation Horizontale", vmap.getentete(), hmap.getallResults());
				drawAverageResult(hmap.getAvResult(), bmap.getAvResult(), vmap.getAvResult(),
						"Average Times of Query'Execution");
				drawAverageResult(hmap.getMxResult(), bmap.getMxResult(), vmap.getMxResult(),
						"Maximal Times of Query'Execution");
				drawAverageResult(hmap.getMnResult(), bmap.getMnResult(), vmap.getMnResult(),
						"Minimal Times of Query'Execution ");
			}
			return null;
		}

		@Override
		protected void process(List<Integer> chunk) {

			huploadevol = (int) hmap.threadevol();
			buploadevol = (int) bmap.threadevol();
		}

		@Override
		protected void done() {
			finUpload = true;
			paramPanel.setEnabled(true);
			evolutionPanel.setEnabled(false);
			queryPanel.setEnabled(false);
		}

		public void executeQuery() {
			bmap = new BMapperThread(dbText.getText(), userText.getText(), String.copyValueOf(pwdText.getPassword()),
					hostText.getText(), portText.getText(), (String) dbmsCombo.getSelectedItem());
			hmap = new HMapperThread(dbText.getText(), userText.getText(), String.copyValueOf(pwdText.getPassword()),
					hostText.getText(), portText.getText(), (String) dbmsCombo.getSelectedItem());
			vmap = new VMapperThread(dbText.getText(), userText.getText(), String.copyValueOf(pwdText.getPassword()),
					hostText.getText(), portText.getText(), (String) dbmsCombo.getSelectedItem());
			bmap.setIterationQuery(Integer.parseInt((String) iterationcomboBox.getSelectedItem()));
			hmap.setIterationQuery(Integer.parseInt((String) iterationcomboBox.getSelectedItem()));
			vmap.setIterationQuery(Integer.parseInt((String) iterationcomboBox.getSelectedItem()));
			bmap.setMapping(false);
			hmap.setMapping(false);
			vmap.setMapping(false);

			bmap.run(); // case of sequential execution
			hmap.run();
			vmap.run();
			/*
			 * bmap.start(); vmap.start(); hmap.start(); while ((bmap.isAlive()) ||
			 * (vmap.isAlive()) || (hmap.isAlive())) { try { Thread.sleep(1000); } catch
			 * (InterruptedException e) { e.printStackTrace(); } }
			 */

			return;
		}

		public void executeMapping() {

			bmap = new BMapperThread(dbText.getText(), userText.getText(), String.copyValueOf(pwdText.getPassword()),
					hostText.getText(), portText.getText(), (String) dbmsCombo.getSelectedItem());
			hmap = new HMapperThread(dbText.getText(), userText.getText(), String.copyValueOf(pwdText.getPassword()),
					hostText.getText(), portText.getText(), (String) dbmsCombo.getSelectedItem());
			bmap.setMapping(true);
			hmap.setMapping(true);
			bmap.start();
			hmap.start();
			while ((bmap.isAlive()) || (hmap.isAlive())) {
				finbmap = !bmap.isAlive();
				finhmap = !hmap.isAlive();
				try {
					publish(1);
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			return;
		}
	}

	class MappingListener extends SwingWorker<List<Integer>, Integer> {

		@Override
		protected List<Integer> doInBackground() throws Exception {

			startCreatebschema = System.currentTimeMillis();
			startbupload = System.currentTimeMillis();
			startCreatehShema = System.currentTimeMillis();
			starthupload = System.currentTimeMillis();
			while (!finUpload) {
				publish(1);
				Thread.sleep(1000);
			}
			return null;
		}

		@Override
		protected void process(List<Integer> chunk) {

			// for (int number : chunk) {

			if (!mapper.mapping) {
				return;
			}
			progressBarCreateHschema.setValue(huploadevol);
			progressBarUploadH.setValue(huploadevol);
			progressBarCreateBschema.setValue(buploadevol);
			progressBarUploadB.setValue(buploadevol);
			if (!finhmap) {
				labeldurationHschema.setText(convertTime(System.currentTimeMillis() - startCreatehShema));
				labelHupload.setText(convertTime(System.currentTimeMillis() - starthupload));
			}
			if (!finbmap) {
				labelBschema.setText(convertTime(System.currentTimeMillis() - startCreatebschema));

				labelBupload.setText(convertTime(System.currentTimeMillis() - startbupload));
			}
			// }

		}

		@Override
		protected void done() {
			if (!mapper.mapping) {
				return;
			}
			progressBarCreateHschema.setValue(100);
			progressBarUploadH.setValue(100);
			progressBarCreateBschema.setValue(100);
			progressBarUploadB.setValue(100);
		}
	}
}
