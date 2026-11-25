package choliem.paba.wodet

import java.text.NumberFormat
import java.util.Locale

data class Kategori(
    var id: Long,
    var nama: String,
    var jenis: String
)

data class Transaksi(
    var id: Long,
    var jenis: String,
    var kategori: String,
    var jumlah: Long,
    var tanggal: String,
    var catatan: String
)

object Jenis {
    const val INCOME = "INCOME"
    const val EXPENSE = "EXPENSE"
    const val SEMUA = "SEMUA"
}

fun rupiah(nominal: Long): String {
    val format = NumberFormat.getInstance(Locale.forLanguageTag("id-ID"))
    return "Rp " + format.format(nominal)
}

fun tanggalTampil(tanggal: String): String {
    val bagian = tanggal.split("-")
    return if (bagian.size == 3) "${bagian[2]}/${bagian[1]}/${bagian[0]}" else tanggal
}