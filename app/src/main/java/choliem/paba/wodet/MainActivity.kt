package choliem.paba.wodet

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import choliem.paba.wodet.databinding.ActivityMainBinding
import java.util.Calendar
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var penyimpanan: Penyimpanan

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        penyimpanan = Penyimpanan(this)

        binding.btnKategori.setOnClickListener {
            startActivity(Intent(this, KategoriActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        tampilkanRingkasan()
    }

    private fun tampilkanRingkasan() {
        val semua = penyimpanan.ambilTransaksi()
        val totalIncome = semua.filter { it.jenis == Jenis.INCOME }.sumOf { it.jumlah }
        val totalExpense = semua.filter { it.jenis == Jenis.EXPENSE }.sumOf { it.jumlah }

        binding.tvSaldo.text = rupiah(totalIncome - totalExpense)
        binding.tvTotalIncome.text = rupiah(totalIncome)
        binding.tvTotalExpense.text = rupiah(totalExpense)

        val c = Calendar.getInstance()
        val awalan = String.format(Locale.US, "%04d-%02d", c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1)
        val bulanIni = semua.filter { it.tanggal.startsWith(awalan) }
        val incomeBulanIni = bulanIni.filter { it.jenis == Jenis.INCOME }.sumOf { it.jumlah }
        val expenseBulanIni = bulanIni.filter { it.jenis == Jenis.EXPENSE }.sumOf { it.jumlah }
        binding.tvBulanIni.text =
            "Bulan ini: masuk ${rupiah(incomeBulanIni)}, keluar ${rupiah(expenseBulanIni)}"

        val terakhir = semua
            .sortedWith(compareByDescending<Transaksi> { it.tanggal }.thenByDescending { it.id })
            .take(5)
        binding.tvTerakhir.text = if (terakhir.isEmpty()) {
            "Belum ada transaksi"
        } else {
            terakhir.joinToString("\n") {
                val tanda = if (it.jenis == Jenis.INCOME) "+" else "-"
                "${tanggalTampil(it.tanggal)}  ${it.kategori}  $tanda${rupiah(it.jumlah)}"
            }
        }
    }
}