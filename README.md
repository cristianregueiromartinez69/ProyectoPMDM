# PROYECTO BLUETOOTH

**Índice**
- Plantilla escogida y sus características
- Como funciona la conexión bluetooth
- Transferencia de archivos mediante bluetooth

# 1. Plantilla escogida y sus características :smile:

### 1.1 Nombre de la plantilla y en que consiste
El nombre de esta plantilla es Buttons Navigation Views Activity.

**¿En que consiste?**:confused:
Es una plantilla pre configurada que implementa un sistema de navegación usando BottomNavigationView y navController, consiste en lo siguiente:
```bash
#BottomNavigationView
Un menú inferior con pestañas para navegar entre secciones principales de la app como "Home", "Dashboard" y "Notifications".
```

```bash
#NavController
Gestiona la navegación entre fragmentos asociados a estas pestañas
```

```bash
#Fragmentos
Componentes reutilizables que representan las diferentes pantallas de la app
```

```bash
#Data Binding
Facilita la conexión entre los elementos visuales (XML) y el código Kotlin
```


**Propósito de esta plantilla**:smile:
Proporciona una base lista para crear aplicaciones con múltiples pantallas, navegación fluida y estructura modular, ideal para apps con secciones principales claramente definidas


### Ejemplo de app:
![imagenInstagram](https://github.com/user-attachments/assets/e5054c7c-d6e6-4316-8d61-c4927b9f5d79)



```bash
#La imagen anterior muestra una app (Instagram) que usa una barra de navegación para ir entre secciones principales de la app
```

### 1.2 Cómo funciona :confused:

1. MainActivity
Es la clase principal de la aplicación, se encarga de hacer lo siguiente:
- Configurar la barra de navegación inferior
- vincular la barra de navegación con el componente de navegación
- Establecer una barra de acción que cambia su título de manera dinámica según la sección visible

```bash
#Ejemplo de MainActivity
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }
}
```
El código anterior es un ejemplo de MainActivity con la plantilla de Buttons Navigation Views Activity, vamos a explicarlo.:grin:

```bash
#class MainActivity : AppCompatActivity() {
Esta clase es la actividad principal de la aplicación. Las actividades en Android son las pantallas de la aplicación,
y MainActivity es la primera pantalla que verá el usuario al abrir la app.
```

```bash
#private lateinit var binding: ActivityMainBinding
binding se usa para enlazar los componentes del XML de la interfaz (como botones, vistas, etc.) con el código de Kotlin.
Este binding va a permitir acceder a las vistas definidas en el archivo de diseño activity_main.xml
```

```bash
#override fun onCreate(savedInstanceState: Bundle?) {
    #super.onCreate(savedInstanceState)
    #binding = ActivityMainBinding.inflate(layoutInflater)
    #setContentView(binding.root)

En el método onCreate, que es el primero que se llama cuando se crea la actividad,
se está inicializando el binding con el diseño de la actividad (activity_main.xml).
setContentView(binding.root) carga la vista definida en el XML y la establece como la interfaz de la actividad.

```

```bash
#val navView: BottomNavigationView = binding.navView
Aquí se está obteniendo una referencia a la barra de navegación en la parte inferior de la
pantalla (BottomNavigationView), que permite al usuario navegar entre diferentes secciones de la aplicación.
```

```bash
#val navController = findNavController(R.id.nav_host_fragment_activity_main)
NavController es el componente que se encarga de gestionar la navegación entre los fragmentos.
Aquí se está obteniendo el NavController asociado con un NavHostFragment que se encuentra en el layout
de la actividad (activity_main.xml). Este fragmento es el que contiene los fragmentos que se van a mostrar cuando el usuario navegue.
```

```bash
#val appBarConfiguration = AppBarConfiguration(
    #setOf(R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
#)
AppBarConfiguration define qué opciones de la barra de acción (como los botones de retroceso o el menú)
deben estar disponibles dependiendo del fragmento actual.
```

```bash
#setupActionBarWithNavController(navController, appBarConfiguration)
Este método se encarga de configurar la barra de acción para que responda a la navegación del NavController.
Cuando el usuario navega entre fragmentos, esta barra de acción se actualiza automáticamente.
```

```bash
#navView.setupWithNavController(navController)
Finalmente, este método vincula la barra de navegación inferior (BottomNavigationView) con el NavController.
Esto significa que, cuando el usuario hace clic en un ítem de la barra de navegación, el NavController cambia al fragmento correspondiente.
```

### VAMOS A VER AHORA UN FRAGMENTO CON SU VIEWMODEL :open_mouth:

```bash
#ejemplo de fragmento
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
```

### VAMOS A EXPLICARLO: :confused:
El Fragmento es una parte de la interfaz de usuario que puede ser reutilizada dentro de una actividad. En este caso, HomeFragment representa una pantalla o una sección de la aplicación
```bash
#private var _binding: FragmentHomeBinding? = null
Aquí se está declarando una variable privada que será usada para enlazar el fragmento con la vista XML (a través de binding).
binding es nullable (?), lo que significa que puede ser null en algunos momentos.
```

```bash
#private val binding get() = _binding!!
binding es una propiedad pública que hace referencia al objeto _binding.
El !! indica que aseguramos que _binding no sea null en este momento
```

```bash
#onCreateView
Este método se llama cuando el fragmento necesita mostrar su vista en la pantalla
```

Después pasa lo siguiente:
- Obtenemos a partir del xml donde están las vistas, el objeto binding
- Hacemos una variable igual a la vista del xml y la devolvemos al final del metodo

### Ejemplo de viewModel :smile:

```bash
#Ejemplo de viewmodel asociado al fragmento
class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "App de conexión a través de bluetooth"
    }
    val text: LiveData<String> = _text
}
```

Resumen de como funciona el fragmento y el viewModel:
```bash
El HomeFragment es responsable de mostrar una vista con un TextView, cuyo contenido se obtiene del HomeViewModel.
El HomeViewModel gestiona los datos que el Fragment necesita (en este caso, el texto), y garantiza que esos datos sobrevivan a los cambios de configuración.
Gracias a LiveData, cualquier cambio en el ViewModel se refleja automáticamente en el fragmento.
```
