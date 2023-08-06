package CalcMath;
/*
Ian and Gio
Calculus stuff
*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class calculusInterface extends JPanel {  
   private String limitFunction=""; double bound1=0, bound2=0;
   
    public calculusInterface() {
        initializeUI();
    }

    public static void showFrame() {
        JPanel panel = new calculusInterface();
        panel.setOpaque(true);

        JFrame frame = new JFrame("Calc project");
        frame.setPreferredSize(new Dimension(400,200));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setContentPane(panel);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(calculusInterface::showFrame);
        
    }
    

    private void initializeUI() {
        JTabbedPane tabbedPane = new JTabbedPane();
        
        //Derivatives
        JPanel dPanel = new JPanel();
        dPanel.setLayout(new GridBagLayout());
        GridBagConstraints n = new GridBagConstraints();
            JLabel dFunction = new JLabel("Function: ");
            JLabel dAnswer = new JLabel();
            JLabel dDerivative = new JLabel("Derivative: ");
            JTextField dTextField = new JTextField(20);
               dTextField.addActionListener(new ActionListener(){
                  public void actionPerformed(ActionEvent e){
                     dAnswer.setText(Derivatives.Derivative(dTextField.getText()));
                     dTextField.selectAll();
                     dPanel.revalidate();
                     dPanel.repaint();
                  }
               });
            
            n.gridy=0;
            dPanel.add(dFunction, n); dPanel.add(dTextField, n);
            n.gridy=1; n.insets = new Insets(10, 0, 0, 0);
            dPanel.add(dDerivative, n); dPanel.add(dAnswer, n);

        tabbedPane.addTab("Derivatives", dPanel);
        
        //Limits
        JPanel lPanel = new JPanel();
        lPanel.setLayout(new GridBagLayout());
        GridBagConstraints p = new GridBagConstraints();
            JLabel lFunction = new JLabel("Function: ");
            JLabel lValue = new JLabel("Limit at: ");
            JLabel lLabel = new JLabel("Limit: ");
            JLabel lAnswer = new JLabel();
            JTextField lTextField = new JTextField(20);
               lTextField.addActionListener(new ActionListener(){
                  public void actionPerformed(ActionEvent e){
                     limitFunction=lTextField.getText();
                  }
               });
            JTextField lTextFieldValue = new JTextField(20);
               lTextFieldValue.addActionListener(new ActionListener(){
                  public void actionPerformed(ActionEvent e){
                      System.out.println(limitFunction);
                     lAnswer.setText(Limits.limit(limitFunction, lTextFieldValue.getText()));
                     lTextField.selectAll();
                     lTextFieldValue.selectAll();
                     lPanel.revalidate();
                     lPanel.repaint();
                     
                  }
               });

               
          p.gridy=0;
          lPanel.add(lFunction, p); lPanel.add(lTextField, p);
          p.gridy=1; p.insets = new Insets(10,0,0,0);
          lPanel.add(lValue, p); lPanel.add(lTextFieldValue, p);
          p.gridy=2;
          lPanel.add(lLabel, p); lPanel.add(lAnswer, p);
        
        
        tabbedPane.addTab("Limits", lPanel);
        
        
        
        //Integrals
        JPanel iPanel = new JPanel();
        iPanel.setLayout(new GridBagLayout());
        GridBagConstraints o = new GridBagConstraints();
            JLabel iFunction = new JLabel("Function: ");
            JLabel iAnswer = new JLabel();
            JLabel iBoundsLabel = new JLabel("Bounds: ");
            JTextField iBoundValues = new JTextField(20);
               iBoundValues.addActionListener(new ActionListener(){
                  public void actionPerformed(ActionEvent e){
                     if(iBoundValues.getText().equals("")) {
                         bound1=0; bound2=0;
                         return;
                     }
                     String[] bounds = iBoundValues.getText().split(",");
                     bound1=Double.parseDouble(bounds[0]);
                     bound2=Double.parseDouble(bounds[1]);
                  }
                });
            
            JLabel iIntegral = new JLabel("Integral: ");
            JTextField iTextField = new JTextField(20);
               iTextField.addActionListener(new ActionListener(){
                  public void actionPerformed(ActionEvent e){
                     if(bound1!=0 && bound2!=0) iAnswer.setText("" + Integrals.definiteIntegral(iTextField.getText(), bound1, bound2));
                     else iAnswer.setText(Integrals.indefiniteIntegral(iTextField.getText()));
                     bound1=0; bound2=0;
                     iTextField.selectAll();
                     iPanel.revalidate();
                     iPanel.repaint();
                  }
               });
            
            o.gridy=0;
            iPanel.add(iBoundsLabel, o); iPanel.add(iBoundValues, o);
            o.gridy=1; o.insets = new Insets(10,0,0,0);
            iPanel.add(iFunction, o); iPanel.add(iTextField, o);
            o.gridy=2;
            iPanel.add(iIntegral, o); iPanel.add(iAnswer, o);
        tabbedPane.addTab("Integrals", iPanel);


        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(500, 200));
        this.add(tabbedPane, BorderLayout.CENTER);
    }
}