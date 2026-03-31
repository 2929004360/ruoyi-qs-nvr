package com.ruoyi.dahua.frame.ThermalCamera;

import com.ruoyi.dahua.common.BorderEx;
import com.ruoyi.dahua.common.Res;
import com.ruoyi.dahua.lib.ToolKits;
import com.ruoyi.dahua.lib.structure.NET_OUT_RADIOMETRY_RANDOM_REGION_TEMPER;
import com.ruoyi.dahua.module.ThermalCameraModule;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class RegionQueryDialog extends JDialog {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public RegionQueryDialog() {
        setTitle(Res.string().getShowInfo("REGION_TEMPER"));
        setLayout(new BorderLayout());
        setModal(true);
        pack();
        setSize(350, 500);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        ///////////////////////////////
        queryPanel = new QueryPanel();
        showPanel = new QueryShowPanel();

        add(queryPanel, BorderLayout.NORTH);
        add(showPanel, BorderLayout.CENTER);
    }


    /**
     * 查询界面
     * */
    public class QueryPanel extends JPanel {
        /**
         *
         */
        private static final long serialVersionUID = 1L;

        public QueryPanel() {
            BorderEx.set(this, Res.string().getShowInfo("QUERY_CONDITION"), 1);
            setLayout(new FlowLayout(FlowLayout.CENTER, 30, 15));

            Dimension dimension = new Dimension(180, 200);
            setPreferredSize(dimension);
            JLabel XLabel1 = new JLabel(Res.string().getShowInfo("X"), JLabel.RIGHT);
            XTextField1 = new JTextField("0");
            JLabel YLabel1 = new JLabel(Res.string().getShowInfo("Y"), JLabel.RIGHT);
            YTextField1 = new JTextField("0");
            JLabel XLabel2 = new JLabel(Res.string().getShowInfo("X"), JLabel.RIGHT);
            XTextField2 = new JTextField("8191");
            JLabel YLabel2 = new JLabel(Res.string().getShowInfo("Y"), JLabel.RIGHT);
            YTextField2 = new JTextField("0");
            JLabel XLabel3 = new JLabel(Res.string().getShowInfo("X"), JLabel.RIGHT);
            XTextField3 = new JTextField("0");
            JLabel YLabel3 = new JLabel(Res.string().getShowInfo("Y"), JLabel.RIGHT);
            YTextField3 = new JTextField("8191");
            JLabel XLabel4 = new JLabel(Res.string().getShowInfo("X"), JLabel.RIGHT);
            XTextField4 = new JTextField("8191");
            JLabel YLabel4 = new JLabel(Res.string().getShowInfo("Y"), JLabel.RIGHT);
            YTextField4 = new JTextField("8191");
            queryBtn = new JButton(Res.string().getShowInfo("QUERY"));

            Dimension lableDimension = new Dimension(10, 20);
            Dimension textFieldDimension = new Dimension(80, 20);
            Dimension btnDimension = new Dimension(100, 20);

            XLabel1.setPreferredSize(lableDimension);
            XTextField1.setPreferredSize(textFieldDimension);
            YLabel1.setPreferredSize(lableDimension);
            YTextField1.setPreferredSize(textFieldDimension);

            XLabel2.setPreferredSize(lableDimension);
            XTextField2.setPreferredSize(textFieldDimension);
            YLabel2.setPreferredSize(lableDimension);
            YTextField2.setPreferredSize(textFieldDimension);

            XLabel3.setPreferredSize(lableDimension);
            XTextField3.setPreferredSize(textFieldDimension);
            YLabel3.setPreferredSize(lableDimension);
            YTextField3.setPreferredSize(textFieldDimension);

            XLabel4.setPreferredSize(lableDimension);
            XTextField4.setPreferredSize(textFieldDimension);
            YLabel4.setPreferredSize(lableDimension);
            YTextField4.setPreferredSize(textFieldDimension);
            queryBtn.setPreferredSize(btnDimension);

            add(XLabel1);
            add(XTextField1);
            add(YLabel1);
            add(YTextField1);
            add(XLabel2);
            add(XTextField2);
            add(YLabel2);
            add(YTextField2);
            add(XLabel3);
            add(XTextField3);
            add(YLabel3);
            add(YTextField3);
            add(XLabel4);
            add(XTextField4);
            add(YLabel4);
            add(YTextField4);
            add(queryBtn);

            listener = new NumberKeyListener();
            XTextField1.addKeyListener(listener);
            YTextField1.addKeyListener(listener);
            XTextField2.addKeyListener(listener);
            YTextField2.addKeyListener(listener);
            XTextField3.addKeyListener(listener);
            YTextField3.addKeyListener(listener);
            XTextField4.addKeyListener(listener);
            YTextField4.addKeyListener(listener);
            queryBtn.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    queryPointInfo();
                }
            });
        }

        private void queryPointInfo() {
            try {
                showPanel.clearData();

                short x1 = Short.parseShort(XTextField1.getText());
                short y1 = Short.parseShort(YTextField1.getText());
                short x2 = Short.parseShort(XTextField2.getText());
                short y2 = Short.parseShort(YTextField2.getText());
                short x3 = Short.parseShort(XTextField3.getText());
                short y3 = Short.parseShort(YTextField3.getText());
                short x4 = Short.parseShort(XTextField4.getText());
                short y4 = Short.parseShort(YTextField4.getText());

                short[][] nPoints = new short[4][2];
                nPoints[0][0] = x1;
                nPoints[0][1] = y1;
                nPoints[1][0] = x2;
                nPoints[1][1] = y2;
                nPoints[2][0] = x3;
                nPoints[2][1] = y3;
                nPoints[3][0] = x4;
                nPoints[3][1] = y4;
                NET_OUT_RADIOMETRY_RANDOM_REGION_TEMPER pointInfo =
                        ThermalCameraModule.radiometryGetRandomRegionTemper(ThermalCameraFrame.THERMAL_CHANNEL, 4, nPoints);
                if (pointInfo == null) {
                    JOptionPane.showMessageDialog(null, ToolKits.getErrorCodeShow(), Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
                    return;
                }

                showPanel.updateData(pointInfo);

            }catch(NumberFormatException e) {
                JOptionPane.showMessageDialog(null, Res.string().getShowInfo("COORDINATE_ILLEGAL"), Res.string().getErrorMessage(), JOptionPane.ERROR_MESSAGE);
            }
        }

        private NumberKeyListener listener;

        private JTextField XTextField1;
        private JTextField YTextField1;
        private JTextField XTextField2;
        private JTextField YTextField2;
        private JTextField XTextField3;
        private JTextField YTextField3;
        private JTextField XTextField4;
        private JTextField YTextField4;
        private JButton queryBtn;
    }

    /**
     * 查询显示界面
     * */
    public class QueryShowPanel extends JPanel {
        /**
         *
         */
        private static final long serialVersionUID = 1L;

        public QueryShowPanel() {
            BorderEx.set(this, Res.string().getShowInfo("QUERY_RESULT"), 1);
            setLayout(new FlowLayout(FlowLayout.CENTER, 5, 15));

            JLabel meterTypeLabel = new JLabel(Res.string().getShowInfo("METER_TYPE"), JLabel.RIGHT);
            meterTypeTextField = new JTextField();
            JLabel temperUnitLabel = new JLabel(Res.string().getShowInfo("TEMPER_UNIT"), JLabel.RIGHT);
            temperUnitTextField = new JTextField();
            JLabel averTemperLabel = new JLabel(Res.string().getShowInfo("TEMPER_AVER"), JLabel.RIGHT);
            averTemperTextField = new JTextField();
            JLabel maxTemperLabel = new JLabel(Res.string().getShowInfo("TEMPER_MAX"), JLabel.RIGHT);
            maxTemperTextField = new JTextField();
            JLabel minTemperLabel = new JLabel(Res.string().getShowInfo("TEMPER_MIN"), JLabel.RIGHT);
            minTemperTextField = new JTextField();
            JLabel maxTemperPointLabel = new JLabel(Res.string().getShowInfo("TEMPER_MAX_POINT"), JLabel.RIGHT);
            maxTemperPointTextField = new JTextField();
            JLabel minTemperPointLabel = new JLabel(Res.string().getShowInfo("TEMPER_MIN_POINT"), JLabel.RIGHT);
            minTemperPointTextField = new JTextField();

            Dimension lableDimension = new Dimension(100, 20);
            Dimension textFieldDimension = new Dimension(130, 20);
            meterTypeLabel.setPreferredSize(lableDimension);
            temperUnitLabel.setPreferredSize(lableDimension);
            averTemperLabel.setPreferredSize(lableDimension);
            maxTemperLabel.setPreferredSize(lableDimension);
            minTemperLabel.setPreferredSize(lableDimension);
            maxTemperPointLabel.setPreferredSize(lableDimension);
            minTemperPointLabel.setPreferredSize(lableDimension);
            meterTypeTextField.setPreferredSize(textFieldDimension);
            temperUnitTextField.setPreferredSize(textFieldDimension);
            averTemperTextField.setPreferredSize(textFieldDimension);
            maxTemperTextField.setPreferredSize(textFieldDimension);
            minTemperTextField.setPreferredSize(textFieldDimension);
            maxTemperPointTextField.setPreferredSize(textFieldDimension);
            minTemperPointTextField.setPreferredSize(textFieldDimension);

            meterTypeTextField.setEditable(false);
            temperUnitTextField.setEditable(false);
            averTemperTextField.setEditable(false);
            maxTemperTextField.setEditable(false);
            minTemperTextField.setEditable(false);
            maxTemperPointTextField.setEditable(false);
            minTemperPointTextField.setEditable(false);

            add(meterTypeLabel);
            add(meterTypeTextField);
            add(temperUnitLabel);
            add(temperUnitTextField);
            add(averTemperLabel);
            add(averTemperTextField);
            add(maxTemperLabel);
            add(maxTemperTextField);
            add(minTemperLabel);
            add(minTemperTextField);
            add(maxTemperPointLabel);
            add(maxTemperPointTextField);
            add(minTemperPointLabel);
            add(minTemperPointTextField);
        }

        public void updateData(NET_OUT_RADIOMETRY_RANDOM_REGION_TEMPER stPointInfo) {
            String[] data = new String[7];

            String [] arrMeterType = Res.string().getMeterTypeList();
            data[0] = arrMeterType[0];

            String [] arrTemperUnit = Res.string().getTemperUnitList();
            if (stPointInfo.stuRegionTempInfo.emTemperatureUnit >= 0 &&
                    stPointInfo.stuRegionTempInfo.emTemperatureUnit <= arrTemperUnit.length) {
                data[1] = arrTemperUnit[stPointInfo.stuRegionTempInfo.emTemperatureUnit];
            }else {
                data[1] = Res.string().getShowInfo("UNKNOWN");
            }
            data[2] = String.valueOf(new Float(stPointInfo.stuRegionTempInfo.nTemperAver)/100);
            data[3] = String.valueOf(new Float(stPointInfo.stuRegionTempInfo.nTemperMax)/100);
            data[4] = String.valueOf(new Float(stPointInfo.stuRegionTempInfo.nTemperMin)/100);
            data[5] = stPointInfo.stuRegionTempInfo.stuTemperMaxPoint.nx + ":" + stPointInfo.stuRegionTempInfo.stuTemperMaxPoint.ny;
            data[6] = stPointInfo.stuRegionTempInfo.stuTemperMinPoint.nx + ":" + stPointInfo.stuRegionTempInfo.stuTemperMinPoint.ny;

            setData(data);
        }

        public void clearData() {
            setData(new String[7]);
        }

        private void setData(String[] data) {

            if (data.length != 7) {
                System.err.printf("data length  %d != 5", data.length);
                return;
            }

            meterTypeTextField.setText(data[0]);
            temperUnitTextField.setText(data[1]);
            averTemperTextField.setText(data[2]);
            maxTemperTextField.setText(data[3]);
            minTemperTextField.setText(data[4]);
            maxTemperPointTextField.setText(data[5]);
            minTemperPointTextField.setText(data[6]);
        }

        private JTextField meterTypeTextField;
        private JTextField temperUnitTextField;
        private JTextField averTemperTextField;
        private JTextField maxTemperTextField;
        private JTextField minTemperTextField;
        private JTextField maxTemperPointTextField;
        private JTextField minTemperPointTextField;
    }



    class NumberKeyListener implements KeyListener {

        public void keyTyped(KeyEvent e) {
            int key = e.getKeyChar();
            if (key < 48 || key > 57) {
                e.consume();
            }
        }

        public void keyPressed(KeyEvent e) {}

        public void keyReleased(KeyEvent e) {}
    }

    private QueryPanel queryPanel;
    private QueryShowPanel showPanel;
}
