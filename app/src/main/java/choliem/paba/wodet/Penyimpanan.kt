package choliem.paba.wodet

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Penyimpanan(context: Context) {
    private val sp: SharedPreferences =
        context.getSharedPreferences("dataWoDeT", Context.MODE_PRIVATE)
    private val gson = Gson()

    fun ambilKategori(): ArrayList<Kategori> {
        val isiSP = sp.getString("spKategori", null)
        if (isiSP == null) {
            val kategoriAwal = arrayListOf(
                Kategori(1, "Gaji", Jenis.INCOME),
                Kategori(2, "Uang Saku", Jenis.INCOME),
                Kategori(3, "Bonus", Jenis.INCOME),
                Kategori(4, "Makan", Jenis.EXPENSE),
                Kategori(5, "Transport", Jenis.EXPENSE),
                Kategori(6, "Kos", Jenis.EXPENSE),
                Kategori(7, "Hiburan", Jenis.EXPENSE)
            )
            simpanKategori(kategoriAwal)
            return kategoriAwal
        }
        val type = object : TypeToken<ArrayList<Kategori>>() {}.type
        return gson.fromJson(isiSP, type)
    }

    fun simpanKategori(data: ArrayList<Kategori>) {
        sp.edit {
            putString("spKategori", gson.toJson(data))
        }
    }

    fun ambilTransaksi(): ArrayList<Transaksi> {
        val isiSP = sp.getString("spTransaksi", null) ?: return ArrayList()
        val type = object : TypeToken<ArrayList<Transaksi>>() {}.type
        return gson.fromJson(isiSP, type)
    }

    fun simpanTransaksi(data: ArrayList<Transaksi>) {
        sp.edit {
            putString("spTransaksi", gson.toJson(data))
        }
    }
}