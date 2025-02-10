import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun NavigationWrapper() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "pantallaMenu"
    ) {
        composable("pantallaMenu") { PantallaMenu(navController) }
        composable("pantallaCreacionVenta") { PantallaCreacionVenta(navController) }
        composable("pantallaCreacionCompra") { PantallaCreacionCompra(navController) }
        composable("pantallaActualizar") { PantallaActualizar(navController) }
        composable("pantallaBorrar") { PantallaBorrar(navController) }
        composable("pantallaMostrar") { PantallaMostrar(navController) }
    }
}
