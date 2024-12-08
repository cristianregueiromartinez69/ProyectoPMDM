# PROYECTO Video-Audio PMDM

**Índice**
- Plantilla escogida y sus características
- Como se pone un video en android Studio
- Como se pone un audio en Android Studio

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
    #
    
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

# 2. Como se pone un video en android Studio :smile:

### Integración con YouTube 🤔

1. **YouTubePlayerView:** Utilizamos `YouTubePlayerView` de la biblioteca `androidyoutubeplayer` para incrustar videos de YouTube sin problemas dentro del diseño de nuestra aplicación.
2. **Gestión del Ciclo de Vida:** Al agregar `YouTubePlayerView` como un observador del ciclo de vida, nos aseguramos de que la reproducción de video se pause y se reanude automáticamente cuando cambie el estado del ciclo de vida de la aplicación.
3. **Carga de Videos:** Aprovechamos la función `loadVideo` de `YouTubePlayer` para cargar y reproducir videos de YouTube utilizando sus ID de video únicos.

### Reproducción de Video Local

1. **Configuración de ExoPlayer:** Inicializamos una instancia de `ExoPlayer` utilizando `ExoPlayer.Builder` y la asociamos con un `PlayerView` en nuestro diseño.
2. **Creación de MediaItem:** Creamos un `MediaItem` a partir de una URI de video local, especificando la ruta al archivo de video almacenado en los recursos de la aplicación.
3. **Control de Reproducción:** Utilizamos las funciones `setMediaItem`, `prepare` y `playWhenReady` de `ExoPlayer` para controlar la reproducción de video, asegurando una experiencia de usuario fluida y atractiva.

## Fragmentos de Código 😎

```bash
#integracion youtube
val youTubePlayerView: YouTubePlayerView = binding.playerView
        lifecycle.addObserver(youTubePlayerView)

        youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                val videoId = "_ttcR7VDouE"
                youTubePlayer.loadVideo(videoId, 0f)
            }
        })
```

```bash
#integracion con exoplayer
private var player: ExoPlayer? = null
private fun initializePlayer(){
        player = ExoPlayer.Builder(requireContext()).build()
        val playerView : PlayerView = binding.playerViewExoPlayer2
        playerView.player = player

        val videoUri = Uri.parse("android.resource://${requireContext().packageName}/raw/doctops")
        val mediaItem = MediaItem.fromUri(videoUri)
        player!!.setMediaItem(mediaItem)
        player!!.prepare()
        player!!.playWhenReady = true
    }
```

## Dependencias

* **ExoPlayer:** `implementation("com.google.android.exoplayer:exoplayer:2.X.X")` (Reemplaza `X.X` con la versión deseada)
* **Android YouTube Player:** `implementation("com.pierfrancescosoffritti.androidyoutubeplayer:core:10.0.5")`

# 3.  Como se pone un audio en Android Studio 😄

1. **Inicialización de MediaPlayer:** Se crea una instancia de `MediaPlayer` utilizando `MediaPlayer.create()` y se le asigna un archivo de audio local desde los recursos de la aplicación (`R.raw.song`).
2. **Control de Reproducción:** Se implementan botones para pausar/reanudar, retroceder y avanzar en la reproducción del audio. Se utiliza `mediaPlayer?.isPlaying`, `mediaPlayer?.pause()`, `mediaPlayer?.start()`, `mediaPlayer?.seekTo()` para controlar el estado y la posición de la reproducción.
3. **Gestión de Recursos:** En `onDestroyView()`, se libera el `MediaPlayer` utilizando `mediaPlayer?.release()` para evitar fugas de memoria y optimizar el uso de recursos.

## Fragmentos de Código 😎

```bash
# Inicialización y Reproducción
mediaPlayer = MediaPlayer.create(requireContext(), R.raw.song)

mediaPlayer?.start()
```

```bash
# Control de Pausa/Reproducción
binding.pauseAudiosId.setOnClickListener {
            if(mediaPlayer?.isPlaying == true){
                mediaPlayer?.pause()
                binding.pauseAudiosId.text = "play"
            }
            else{
                mediaPlayer?.start()
                binding.pauseAudiosId.text = "stop"
            }
        }
```

```bash
# Control de Retroceso
binding.atrasAudiosId.setOnClickListener {
            mediaPlayer?.let {
                val newPosition = it.currentPosition - 10000
                if(newPosition >= 0){
                    it.seekTo(newPosition)
                }
                else{
                    it.seekTo(0)
                }
            }
        }
```

```bash
#control de avance
binding.advanceAudiosId.setOnClickListener {
            mediaPlayer?.let {
                val newPosition = it.currentPosition + 10000
                if(newPosition <= it.duration){
                    it.seekTo(newPosition)
                }
                else{
                    it.seekTo(it.duration)
                }
            }
        }
```


