import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class MergeSortVisualizer extends JPanel {
    private static final int ARRAY_SIZE = 50;
    private static final int BAR_WIDTH = 15;
    private static final int DELAY = 50; // ms
    private int[] array;
    private int[] aux;
    private int highlightL = -1, highlightR = -1;

    public MergeSortVisualizer() {
        array = new int[ARRAY_SIZE];
        aux = new int[ARRAY_SIZE];
        Random rand = new Random();
        for (int i = 0; i < ARRAY_SIZE; i++) {
            array[i] = rand.nextInt(400) + 50;
        }
        setPreferredSize(new Dimension(ARRAY_SIZE * BAR_WIDTH, 500));
        new Thread(this::mergeSort).start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < array.length; i++) {
            if (i >= highlightL && i <= highlightR) {
                g.setColor(Color.RED);
            } else {
                g.setColor(Color.BLUE);
            }
            g.fillRect(i * BAR_WIDTH, getHeight() - array[i], BAR_WIDTH - 2, array[i]);
        }
    }

    private void mergeSort() {
        mergeSort(0, array.length - 1);
        highlightL = highlightR = -1;
        repaint();
    }

    private void mergeSort(int left, int right) {
        if (left >= right) return;
        int mid = left + (right - left) / 2;
        mergeSort(left, mid);
        mergeSort(mid + 1, right);
        merge(left, mid, right);
    }

    private void merge(int left, int mid, int right) {
        for (int k = left; k <= right; k++) {
            aux[k] = array[k];
        }
        int i = left, j = mid + 1;
        for (int k = left; k <= right; k++) {
            if (i > mid) array[k] = aux[j++];
            else if (j > right) array[k] = aux[i++];
            else if (aux[j] < aux[i]) array[k] = aux[j++];
            else array[k] = aux[i++];
            highlightL = left;
            highlightR = right;
            repaint();
            sleep();
        }
    }

    private void sleep() {
        try {
            Thread.sleep(DELAY);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Merge Sort Visualizer");
        MergeSortVisualizer panel = new MergeSortVisualizer();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
