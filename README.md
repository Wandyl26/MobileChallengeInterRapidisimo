# MobileChallengeInterRapidisimo

# InterCommerce App

Aplicación Android nativa de comercio electrónico construida con Kotlin y Jetpack Compose, siguiendo los principios de Clean Architecture y un enfoque Offline-First para garantizar resiliencia ante conectividad inestable.

## Descripción del Producto

InterCommerce App es un MVP que consume la API de [DummyJSON](https://dummyjson.com) y permite:

- Explorar un catálogo de productos con scroll infinito y búsqueda.
- Ver detalles de cada producto.
- Agregar productos a un carrito de compras persistente.
- Funcionar sin conexión mostrando datos cacheados localmente.

## Arquitectura

La aplicación implementa Clean Architecture con tres capas bien definidas:

- Data: Contiene repositorios, fuentes de datos remotas (Retrofit) y locales (Room), mapeadores y el mediador de paginación.
- Domain: Entidades puras de negocio (data classes inmutables), casos de uso y repositorios (interfaces).
- Presentation: UI declarativa con Jetpack Compose y ViewModels que gestionan el estado mediante StateFlow.

La inyección de dependencias se realiza con Hilt, desacoplando las capas y facilitando el testing.

## Funcionalidades Implementadas

### Módulo A: Catálogo (Discovery)
- Visualización en `LazyVerticalGrid` con 2 columnas fijas y altura dinámica por tarjeta, mostrando exactamente 6 productos visibles sin importar el tamaño de pantalla.
- Carga progresiva con Shimmer/Skeleton animado durante la carga inicial.
- Se omitieron campos que para los requisitos de la prueba no se vieron necesarios
- Scroll infinito mediante Paging 3 con `RemoteMediator` y `PagingSource` personalizada para evitar invalidaciones.
- Búsqueda con debounce (400 ms) que consulta tanto a la API como a la base de datos local.
- Modo offline: si no hay conexión, se muestran los productos almacenados en Room.

### Módulo B: Detalle de Producto
- Navegación type-safe con Compose Navigation.
- Carga de imágenes con Coil y placeholders.
- Feedback háptico (vibración) al agregar al carrito.

### Módulo C: Carrito de Compras
- Persistencia local en Room (sobrevive a cierres del sistema).
- Lógica de negocio (cálculo de totales, impuestos) en la capa de dominio.
- Actualización de cantidades sin duplicados (métodos separados `setQuantity` e `incrementQuantity`).

## Estrategia Offline-First y Persistencia

Se utiliza Room Database como motor de persistencia. Los productos se cachean automáticamente al cargar desde la red. Si la API falla o el dispositivo pierde conexión, la capa de datos sirve los productos desde Room de forma transparente.

- RemoteMediator: decide cuándo consultar la API y cuándo terminar la paginación. Si no hay red (`NetworkMonitor.isOnline == false`), retorna `Success` sin llamar a la API, usando solo la fuente local.
- Búsqueda offline: se ejecuta una consulta SQL con `LIKE` sobre los campos `title`, `category` y `brand`.
- Persistencia del carrito: las operaciones se realizan localmente y se reflejan inmediatamente en la UI.

### Manejo del Carrito sin Conexión

El carrito se almacena en Room en la tabla `cart_items`. Las operaciones de agregar, actualizar o eliminar elementos se realizan localmente y se reflejan inmediatamente en la UI. No se requiere sincronización con el servidor en este MVP.

##  Networking y Resiliencia

- Cliente Retrofit configurado con OkHttp y logging de peticiones.
- Manejo centralizado de errores: se capturan `IOException`, `SocketTimeoutException` y `HttpException`; en caso de fallo se devuelve `MediatorResult.Success` con fin de paginación para no bloquear la UI y pueda buscar en la base de datos local, se deja un objeto Error Handler por si en el futuro se necesitan personalizar los mensajes de error.
- NetworkMonitor: monitorea el estado de conectividad mediante `ConnectivityManager.NetworkCallback` y expone un `Flow<Boolean>`. Se aplica `distinctUntilChanged()` para evitar emisiones duplicadas y reinicios de paginación.
- Serialización con Kotlinx Serialization.

##  Dependencias Clave

- Jetpack Compose BOM 2024.06.00
- Navigation Compose 2.8.5 (type-safe)
- Room 2.6.1
- Paging 3.3.0
- Retrofit 2.11.0 + OkHttp 4.12.0
- Kotlinx Serialization 1.6.3
- Coil 2.6.0
- Hilt 2.51.1
- JUnit 4.13.2, MockK 1.13.10, Turbine 1.1.0, Truth 1.4.2

## Configuración del Proyecto

1. Clona el repositorio:
   ```bash
   git clone https://github.com/Wandyl26/MobileChallengeInterRapidisimo.git


## Pruebas Unitarias
Se incluyen pruebas para:

- Cálculo de totales del carrito (CalculateCartTotalsUseCaseTest).
- Agregar al carrito (AddToCartUseCaseTest).
- Obtener carrito (GetCartUseCaseTest).
- Actualizar cantidad (UpdateCartItemQuantityUseCaseTest).
- Eliminar del carrito (RemoveFromCartUseCaseTest).
- ViewModel del carrito (CartViewModelTest).
- Repositorio de productos (opcional, ProductRepositoryImplTest).
- Ejecutar pruebas unitarias en la ventana de comandos de android:
   ```bash
   ./gradlew test
   

## Entrega APK

- En la raiz del proyecto se encuentra la la carpeta Entregable donde esta la ultima apk generada
- El proyecto se puede correr en un dispositivo virtual o en uno fisico:

## Guia para correr la app desde Android Studio

Requisitos previos
•	Tener Android Studio instalado en tu computadora.
•	Tener un proyecto creado con una plantilla básica (como Empty Activity).

Cómo correr la app en un Dispositivo Virtual (Emulador)
1.	Abrir el Administrador de Dispositivos:
      o	Ve al menú superior derecho y haz clic en el ícono de Device Manager (un teléfono pequeño con un monitor), o ve a Tools > Device Manager.
2.	Crear un emulador (si no tienes uno):
      o	Haz clic en el botón + (Create Device).
      o	Elige una categoría (por ejemplo, Phone) y un modelo de pantalla (como Pixel 6). Haz clic en Next.
      o	Selecciona una versión de Android (imagen del sistema). Si dice Download, descárgala. Haz clic en Next y luego en Finish.
3.	Ejecutar la aplicación:
      o	En el menú desplegable superior central de Android Studio (al lado del botón de Play), selecciona el emulador que acabas de crear.
      o	Haz clic en el botón verde de Run (o presiona Shift + F10 en Windows/Linux o Control + R en Mac).
      o	Espera a que se abra la ventana del emulador y cargue tu aplicación.

Cómo correr la app en un Dispositivo Físico (Teléfono real)
1.	Preparar tu teléfono Android:
      o	Ve a los Ajustes (Configuración) de tu teléfono.
      o	Busca Información del teléfono (o Acerca del teléfono).
      o	Busca Número de compilación y presiona sobre él 7 veces seguidas hasta que aparezca un mensaje que dice que ya eres desarrollador.
2.	Activar la Depuración USB:
      o	Regresa al menú principal de Ajustes y entra a Opciones de desarrollador (suele estar en Sistema o Ajustes adicionales).
      o	Activa el interruptor de Depuración USB (USB Debugging).
3.	Conectar el teléfono:
      o	Conecta tu teléfono a la computadora usando un cable USB.
      o	En la pantalla de tu teléfono aparecerá un aviso pidiendo permiso para Permitir la depuración USB. Marca la casilla de confiar en esta computadora y presiona Permitir o Aceptar.
4.	Ejecutar la aplicación:
      o	En Android Studio, despliega la lista de dispositivos arriba al centro. Tu teléfono físico ahora debería aparecer en la lista.
      o	Selecciónalo y haz clic en el botón verde de Run.
      o	La app se instalará y abrirá directamente en la pantalla de tu celular.
