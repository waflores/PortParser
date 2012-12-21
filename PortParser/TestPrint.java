import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;

@SuppressWarnings("serial")
public class TestPrint extends JFrame {
    JTextArea uiTxaDummy = new JTextArea(), // Hidden for printing only,
            uiTxaActual = new JTextArea(15, 25) // Show on screen
            {
                // Ensure that same font is used if font change for uiTxaActual
                public void setFont(Font f)
                {
                    super.setFont(f);
                    uiTxaDummy.setFont(f);
                }
            };

    {
        // Set line wrap for dummy because if not, text may be truncated if
        // wordwrap for uiTxaActual is not set.
        // uiTxaActual on screen may or may not be wordwrapped but it doesn’t
        // made sense to print if text truncated, hence dummy will be wordwrap.
        uiTxaDummy.setLineWrap(true);
        uiTxaDummy.setWrapStyleWord(true);

        uiTxaActual.setFont(
                new Font(uiTxaActual.getFont().getName(),
                         Font.BOLD, 15));
    }

    JScrollPane uiScpText = new JScrollPane(uiTxaActual);

    public TestPrint()
    {
        super("TestPrint");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton _uiButPrint = new JButton("Print");
        _uiButPrint.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                print();
            }
        });

        add(uiScpText);
        add(_uiButPrint, BorderLayout.SOUTH);

        pack();
    }

    // Printing text
    public void print()
    {
        // Print all text
        if (uiTxaActual.getSelectedText() == null)
        {
            uiTxaDummy.setText(uiTxaActual.getText());
        }
        else // Print selected text
        {
            uiTxaDummy.setText(uiTxaActual.getSelectedText());
        }
        try
        {
            // This will show the print dialog.
            uiTxaDummy.print();
        }
        catch (PrinterException e)
        {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        // Set focus so that selected text is highlighted.
        uiTxaActual.requestFocusInWindow();
    }

    public static void main(String[] _args)
    {
        TestPrint _fra = new TestPrint();
        _fra.setVisible(true);
    }

}