import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

@Composable
fun facturaListaViewModel(): List<Factura> {
    val facturas = remember { mutableStateListOf<Factura>() }

    LaunchedEffect(Unit) {
        val db = Firebase.firestore
        db.collection("facturas").get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val factura = document.toObject(Factura::class.java)
                    facturas.add(factura)
                }
            }
    }
    return facturas
}
