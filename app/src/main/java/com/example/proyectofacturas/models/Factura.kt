data class Factura(
    val id: String = "",
    val numeroFactura: String = "",
    val fechaEmision: String = "",
    val empresaEmisora: String = "",
    val nifEmisor: String = "",
    val direccionEmisor: String = "",
    val clienteReceptor: String = "",
    val nifReceptor: String = "",
    val direccionReceptor: String = "",
    val importeBase: String = "",
    val importeIVA: String = "",
    val importeTotal: String = ""
)
