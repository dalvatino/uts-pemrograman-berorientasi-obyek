import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Locale;

/**
 * UTS Pemrograman Berorientasi Obyek (JAVA II)
 * Aplikasi Penilaian Mahasiswa - Java Swing
 *
 * Nama : Daniel Valentino Sihombing | NIM : 241011750282
 */
public class UTS_241011750282 extends JFrame {

    // Daftar mata kuliah (bobot 2 dan 3 SKS, minimal 5)
    private static final String[] MATKUL = {
        "REKAYASA WEB",
        "PEMROGRAMAN BERORIENTASI OBYEK",
        "BASIS DATA",
        "JARINGAN KOMPUTER",
        "ANALISIS DAN PERANCANGAN SISTEM",
        "PEMROGRAMAN MOBILE"
    };
    private static final int[] SKS = {2, 3, 3, 2, 3, 2};

    private JTextField txtNim, txtNama, txtSks;
    private JTextField txtPresensi, txtTugas, txtUts, txtUas, txtNilaiAkhir, txtGrade;
    private JRadioButton rbLaki, rbPerempuan;
    private ButtonGroup bgJk;
    private JComboBox<String> cbMatkul;
    private JTextArea taLog;
    private JPanel panelUtama;

    private boolean sudahProses = false;

    public UTS_241011750282() {
        setTitle("Aplikasi Penilaian Mahasiswa");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(900, 580);
        setLocationRelativeTo(null);

        buatMenuBar();
        buatForm();
        pasangEvent();
        resetForm();
    }

    // ===================== MENU BAR =====================
    private void buatMenuBar() {
        JMenuBar bar = new JMenuBar();

        JMenu mFile = new JMenu("File");
        JMenuItem miExit = new JMenuItem("Exit");
        miExit.addActionListener(e -> keluar());
        mFile.add(miExit);

        JMenu mApps = new JMenu("Apps");
        JMenuItem miPenilaian = new JMenuItem("Aplikasi Penilaian Mahasiswa");
        miPenilaian.addActionListener(e -> {
            panelUtama.setVisible(true);
            txtNim.requestFocus();
        });
        mApps.add(miPenilaian);

        JMenu mAbout = new JMenu("About");
        JMenuItem miTentang = new JMenuItem("Tentang");
        miTentang.addActionListener(e -> JOptionPane.showMessageDialog(this,
                "Aplikasi Penilaian Mahasiswa\n"
                + "UTS Pemrograman Berorientasi Obyek (JAVA II)\n\n"
                + "Nama : Daniel Valentino Sihombing\n"
                + "NIM  : 241011750282",
                "Tentang", JOptionPane.INFORMATION_MESSAGE));
        mAbout.add(miTentang);

        bar.add(mFile);
        bar.add(mApps);
        bar.add(mAbout);
        setJMenuBar(bar);
    }

    // ===================== FORM / GUI =====================
    private void buatForm() {
        panelUtama = new JPanel(new BorderLayout(10, 10));
        panelUtama.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        JLabel judul = new JLabel("APLIKASI PENILAIAN MAHASISWA", SwingConstants.CENTER);
        judul.setFont(new Font("SansSerif", Font.BOLD, 18));
        panelUtama.add(judul, BorderLayout.NORTH);

        // --- panel kiri: data mahasiswa
        JPanel kiri = new JPanel(new GridLayout(6, 1, 0, 8));
        txtNim = new JTextField();
        txtNama = new JTextField();
        rbLaki = new JRadioButton("LAKI-LAKI", true);
        rbPerempuan = new JRadioButton("PEREMPUAN");
        bgJk = new ButtonGroup();           // supaya pilihan eksklusif
        bgJk.add(rbLaki);
        bgJk.add(rbPerempuan);
        JPanel pJk = new JPanel(new GridLayout(1, 2, 5, 0));
        pJk.add(rbLaki);
        pJk.add(rbPerempuan);
        cbMatkul = new JComboBox<>(MATKUL);
        txtSks = new JTextField();
        txtSks.setEditable(false);

        kiri.add(baris("NIM", txtNim));
        kiri.add(baris("NAMA", txtNama));
        kiri.add(baris("JENIS KEL", pJk));
        kiri.add(baris("MATA KULIAH", cbMatkul));
        kiri.add(baris("SKS", txtSks));
        kiri.add(new JLabel());

        // --- panel kanan: bobot penilaian
        JPanel kanan = new JPanel(new GridLayout(6, 2, 5, 8));
        kanan.setBorder(BorderFactory.createTitledBorder("Bobot Penilaian"));
        txtPresensi = new JTextField();
        txtTugas = new JTextField();
        txtUts = new JTextField();
        txtUas = new JTextField();
        txtNilaiAkhir = new JTextField();
        txtNilaiAkhir.setEditable(false);
        txtGrade = new JTextField();
        txtGrade.setEditable(false);

        kanan.add(new JLabel("PRESENSI"));    kanan.add(txtPresensi);
        kanan.add(new JLabel("TUGAS"));       kanan.add(txtTugas);
        kanan.add(new JLabel("UTS"));         kanan.add(txtUts);
        kanan.add(new JLabel("UAS"));         kanan.add(txtUas);
        kanan.add(new JLabel("NILAI AKHIR")); kanan.add(txtNilaiAkhir);
        kanan.add(new JLabel("GRADE"));       kanan.add(txtGrade);

        JPanel form = new JPanel(new GridLayout(1, 2, 20, 0));
        form.add(kiri);
        form.add(kanan);

        // --- tombol
        JButton btnAdd = new JButton("Add Data");
        JButton btnClear = new JButton("Clear");
        JButton btnProses = new JButton("Proses");
        btnAdd.setName("add");
        btnClear.setName("clear");
        btnProses.setName("proses");
        JPanel pBtn = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        pBtn.add(btnAdd);
        pBtn.add(btnClear);
        pBtn.add(btnProses);

        btnAdd.addActionListener(e -> addData());
        btnClear.addActionListener(e -> resetForm());
        btnProses.addActionListener(e -> proses());

        JPanel atas = new JPanel(new BorderLayout());
        atas.add(form, BorderLayout.CENTER);
        atas.add(pBtn, BorderLayout.SOUTH);
        panelUtama.add(atas, BorderLayout.CENTER);

        // --- text area di dalam scroll pane
        taLog = new JTextArea(10, 50);
        taLog.setEditable(false);
        JScrollPane sp = new JScrollPane(taLog);
        sp.setPreferredSize(new Dimension(750, 190));
        panelUtama.add(sp, BorderLayout.SOUTH);

        setContentPane(panelUtama);
    }

    // satu baris form: label (lebar tetap) di kiri, komponen di kanan
    private JPanel baris(String teks, JComponent komponen) {
        JPanel p = new JPanel(new BorderLayout(5, 0));
        JLabel lbl = new JLabel(teks);
        lbl.setPreferredSize(new Dimension(105, 25));
        p.add(lbl, BorderLayout.WEST);
        p.add(komponen, BorderLayout.CENTER);
        return p;
    }

    private void pasangEvent() {
        // SKS otomatis mengikuti mata kuliah yang dipilih
        cbMatkul.addActionListener(e -> {
            int i = cbMatkul.getSelectedIndex();
            if (i >= 0) {
                txtSks.setText(String.valueOf(SKS[i]));
            }
            kosongkanHasil();
        });
    }

    // ===================== LOGIKA =====================
    private void proses() {
        // validasi kolom kosong
        if (txtNim.getText().trim().isEmpty() || txtNama.getText().trim().isEmpty()
                || txtPresensi.getText().trim().isEmpty() || txtTugas.getText().trim().isEmpty()
                || txtUts.getText().trim().isEmpty() || txtUas.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Semua data harus diisi!",
                    "Validasi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int sks = Integer.parseInt(txtSks.getText());
        int jmlPertemuan = (sks == 3) ? 21 : 14;

        int presensi;
        double tugas, uts, uas;
        try {
            presensi = Integer.parseInt(txtPresensi.getText().trim());
            tugas = Double.parseDouble(txtTugas.getText().trim());
            uts = Double.parseDouble(txtUts.getText().trim());
            uas = Double.parseDouble(txtUas.getText().trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Presensi, Tugas, UTS dan UAS harus berupa angka!",
                    "Validasi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // validasi presensi melebihi jumlah pertemuan
        if (presensi > jmlPertemuan) {
            JOptionPane.showMessageDialog(this,
                    "Presensi (" + presensi + ") melebihi jumlah pertemuan (" + jmlPertemuan + ")!",
                    "Validasi Presensi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (presensi < 0 || tugas < 0 || uts < 0 || uas < 0
                || tugas > 100 || uts > 100 || uas > 100) {
            JOptionPane.showMessageDialog(this,
                    "Nilai harus antara 0 - 100 dan presensi tidak boleh negatif!",
                    "Validasi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // rumus perhitungan
        double persenHadir = ((double) presensi / jmlPertemuan) * 100;
        double nilaiAbsensi = persenHadir * 0.1;
        double nilaiTugas = tugas * 0.2;
        double nilaiUts = uts * 0.3;
        double nilaiUas = uas * 0.4;
        double nilaiAkhir = nilaiAbsensi + nilaiTugas + nilaiUts + nilaiUas;

        // aturan grade (absensi dicek dulu karena mengalahkan nilai)
        String grade;
        if (persenHadir < 50) {
            grade = "E";
        } else if (persenHadir < 75) {
            grade = "D";
        } else if (nilaiAkhir >= 80) {
            grade = "A";
        } else if (nilaiAkhir >= 60) {
            grade = "B";
        } else if (nilaiAkhir >= 40) {
            grade = "C";
        } else {
            grade = "D";
        }

        txtNilaiAkhir.setText(String.format(Locale.US, "%.2f", nilaiAkhir));
        txtGrade.setText(grade);
        sudahProses = true;
    }

    private void addData() {
        if (!sudahProses) {
            JOptionPane.showMessageDialog(this,
                    "Klik tombol Proses terlebih dahulu!",
                    "Informasi", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        String jk = rbLaki.isSelected() ? "LAKI-LAKI" : "PEREMPUAN";
        taLog.append(txtNim.getText().trim() + " | " + txtNama.getText().trim()
                + " | " + jk
                + " | Matkul : " + cbMatkul.getSelectedItem()
                + " | SKS : " + txtSks.getText()
                + " | Absensi : " + txtPresensi.getText().trim()
                + " | Tugas : " + txtTugas.getText().trim()
                + " | UTS : " + txtUts.getText().trim()
                + " | UAS : " + txtUas.getText().trim()
                + " | Nilai Akhir : " + txtNilaiAkhir.getText()
                + " | Grade : " + txtGrade.getText() + "\n");
    }

    private void kosongkanHasil() {
        txtNilaiAkhir.setText("");
        txtGrade.setText("");
        sudahProses = false;
    }

    private void resetForm() {
        txtNim.setText("");
        txtNama.setText("");
        rbLaki.setSelected(true);
        cbMatkul.setSelectedIndex(0);
        txtSks.setText(String.valueOf(SKS[0]));
        txtPresensi.setText("");
        txtTugas.setText("");
        txtUts.setText("");
        txtUas.setText("");
        kosongkanHasil();
        txtNim.requestFocus();
    }

    private void keluar() {
        int pilih = JOptionPane.showConfirmDialog(this, "Keluar dari aplikasi?",
                "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (pilih == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new UTS_241011750282().setVisible(true));
    }
}
