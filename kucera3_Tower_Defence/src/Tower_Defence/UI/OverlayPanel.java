package Tower_Defence.UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;


public class OverlayPanel extends JComponent {

    private final List<JButton> overlayButtons = new ArrayList<>();
    private boolean dimBackground = true;

    private final ComponentListener resizeListener = new ComponentAdapter() {
        @Override public void componentResized(ComponentEvent e) {
            Component p = (Component) e.getComponent();
            setSize(p.getWidth(), p.getHeight());
            revalidate();
            repaint();
        }
        @Override public void componentShown(ComponentEvent e) {
            Component p = (Component) e.getComponent();
            setSize(p.getWidth(), p.getHeight());
        }
    };

    public OverlayPanel() {
        setLayout(null);        // absolute positioning for buttons
        setOpaque(false);       // we paint our own dim; components remain visible
        setVisible(false);      // start hidden
        setDoubleBuffered(true);


        MouseAdapter eater = new MouseAdapter() {};
        addMouseListener(eater);
        addMouseMotionListener(eater);
        addMouseWheelListener(eater);


        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "overlay.hide");
        getActionMap().put("overlay.hide", new AbstractAction() {
            @Override public void actionPerformed(ActionEvent e) {
                setVisible(false);
                clearOverlay();
            }
        });
    }


    @Override public void addNotify() {
        super.addNotify();
        Container parent = getParent(); // typically the JRootPane (as glass pane)
        if (parent != null) {
            setSize(parent.getSize());        // match parent immediately
            parent.addComponentListener(resizeListener);
        }
    }

    @Override public void removeNotify() {
        Container parent = getParent();
        if (parent != null) {
            parent.removeComponentListener(resizeListener);
        }
        super.removeNotify();
    }

    public void addOverlayButton(JButton button) {
        button.setFocusable(false);
        overlayButtons.add(button);
        add(button);
        revalidate();
        repaint();
    }

    public void clearOverlay() {
        for (JButton b : overlayButtons) remove(b);
        overlayButtons.clear();
        revalidate();
        repaint();
    }

    public void setDimBackground(boolean dim) {
        this.dimBackground = dim;
        repaint();
    }

    public void showOverlay() {
        setVisible(true);
        requestFocusInWindow();
        revalidate();
        repaint();
    }


    public void hideOverlay() {
        clearOverlay();
        setVisible(false);
    }


    @Override protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (!dimBackground) return;

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setComposite(AlphaComposite.SrcOver.derive(0.35f)); // 35% black
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, getWidth(), getHeight());
        g2.dispose();
    }
}

