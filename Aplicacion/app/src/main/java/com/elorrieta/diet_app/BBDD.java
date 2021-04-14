package com.elorrieta.diet_app;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class BBDD extends SQLiteOpenHelper {
    public BBDD(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table dieta(nomDieta varchar(20) primary key)");
        db.execSQL("create table fecha(dia TEXT, nomDieta varchar(20), foreign key(nomDieta) references dieta(nomDieta), primary key(nomDieta, dia))");
        db.execSQL("create table menu(tipoComida varchar(10) primary key)");
        db.execSQL("create table contiene(diaSemana varchar(10), nomDieta varchar(20), dia TEXT, tipoComida varchar(10), id integer, foreign key(nomDieta) references fecha(nomDieta), foreign key(dia) references fecha(dia), foreign key(tipoComida) references menu(tipoComida), foreign key(id) references receta(id), primary key(nomDieta, dia, tipoComida, id))");
        db.execSQL("create table receta(id integer primary key autoincrement, nombre varchar(10), elaboracion varchar(150), foto varchar(50), tiempo integer, dificultad varchar(10), tipo varchar(10), origen varchar(10))");
        db.execSQL("create table nComensales(numComensales integer, id integer, primary key(numComensales, id), foreign key(id) references receta(id))");
        db.execSQL("create table tiene(numComensales integer, nomIngrediente varchar(10), id integer, cantidad integer, foreign key(numComensales,id) references nComensales(numComensales,id), foreign key(nomIngrediente) references ingrediente(nomIngrediente), primary key(nomIngrediente, numComensales, id))");
        db.execSQL("create table ingrediente(nomIngrediente varchar(10) primary key, unidad varchar(10), foto varchar(50), precio float)");
        db.execSQL("create table hay(nomAlmacen varchar(20), nomIngrediente varchar(10), cantidad integer, foreign key(nomIngrediente) references ingrediente(nomIngrediente), foreign key(nomAlmacen) references almacen(nomAlmacen), primary key(nomAlmacen, nomIngrediente))");
        db.execSQL("create table almacen(nomAlmacen varchar(20) primary key)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void rellenar(BBDD admin){
        SQLiteDatabase bd = admin.getWritableDatabase();
        receta(bd);
        Comensales(bd);
        tiene(bd);
        ingrediente(bd);
        dieta(bd);
        menu(bd);
        fecha(bd);
        contiene(bd);
    }

    public void receta(SQLiteDatabase bd){
        bd.execSQL("INSERT INTO receta (id,nombre, elaboracion, foto, tiempo, dificultad, tipo, origen) VALUES (1,'ALCACHOFAS RELLENAS DE PAN Y AJO', 'Exprimir el limón en un bol con agua, y dejar las dos mitades dentro. Sacar las hojas exteriores de las alcachofas, quitarles los tallos -reservar para otra preparación-, cortar la mitad superior de las hojas y ponerlas en el bol con agua y limón para que no se oscurezcan (si se quiere). Pelar y picar el ajo y parte del perejil -reservar un poco para emplatar, si se quiere-, trocear el pan con un cuchillo, salpimentar y mezclar bien. Abrir las alcachofas con los dedos y, si tienen pelillos, retirarlos con ayuda de una puntilla o una cucharilla. Ponerlas en una cazuela, apoyadas en la base, y salpimentar. Rellenar con la farsa de pan, ajo y perejil, añadir a la cazuela el agua y el aceite, un poco más de sal y pimienta y llevar a ebullición a fuego medio-bajo, tapado con una tapa que encaje bien. Cocinar durante unos 30-40 minutos. A la mitad de la cocción, añadir el vino blanco y, si se quiere, el vinagre. Cuando ya estén tiernas y con las puntas bien asadas, subir el fuego a medio y destapar para conseguir que la base quede un poco crujiente y la salsa se reduzca. Servir calientes, si se quiere con perejil picado por encima.', 'Enlace', 60, 'BAJA', 'PRIMERO','VEGANA')");
        bd.execSQL("INSERT INTO receta (nombre, elaboracion, foto, tiempo, dificultad, tipo, origen) VALUES ('PERAS POCHADAS EN AZAFRÁN Y CARDAMOMO', 'Pelar las peras dejando el rabillo y sumergirlas en agua fría con unas rodajas de limón para evitar que se oxiden. Romper ligeramente los granos de cardamomo (yo usé un mortero) y añadirlos junto al resto de las especias (excepto el azafrán) y las pieles de cítricos a una olla. Tostar a fuego bajo-medio sin parar de mover hasta que huela. Añadir 250 g de azúcar blanco y suficiente agua para disolverlo. Triturar con ¼ de cucharadita de sal las hebras de azafrán. Añadir el azafrán, las peras y suficiente agua para cubrirlas. Llevar a ebullición y cocinar a fuego bajo, con tapa, aproximadamente unos 45 minutos, o hasta que las peras se vuelvan tiernas y melosas pero no se deshagan (vigilar a partir de los 30 minutos de cocción). Retirar las peras con cuidado y reducir el almíbar de azafrán, sin tapar, 10 minutos más con el fuego alto. Conservar en un tarro con el propio almíbar en el que las hemos cocinado. Se pueden consumir directamente, pero tras dos días estarán mejor.', 'Enlace', 60, 'BAJA', 'POSTRE', 'VEGANA')");
        bd.execSQL("INSERT INTO receta (nombre, elaboracion, foto, tiempo, dificultad, tipo, origen) VALUES ('LECHE FRITA CLÁSICA, CRUJIENTE Y AL HORNO', 'Untar una fuente cuadrada de unos 20x20cm (o rectangular de tamaño similar) con aceite de girasol. Mezclar la harina, la maicena, el azúcar y entre 200 y 250 mililitros de leche con una batidora. Llevar a hervor el resto de la leche con la canela, y el cardamomo, la vainilla y el anís estrellado si se tienen. Mantenerlo a fuego muy suave durante cinco minutos. Colar la leche con las especias sobre la mezcla de leche y harina. Devolver todo a la cazuela y cocer a fuego suave removiendo con una espátula unos 10 minutos, hasta que espese. Dejar que la mezcla se temple y verterla sobre la fuente. Cubrirla con plástico transparente en contacto con la crema. Cuando la crema se haya enfriado, ya se puede manipular, pero para que sea más fácil es conveniente dejarla en la nevera toda la noche. Pintar con un poco de aceite una tabla. Dar la vuelta a la fuente sobre la tabla y cortar la crema en cuadrados. Pasar los trozos de crema primero por harina y después por huevo batido, y freírlos a fuego medio en una cazuela con aceite abundante. Ponerlos en un plato con papel de cocina para que pierdan el exceso de grasa, y servir.', 'Enlace', 25, 'MEDIA', 'POSTRE', 'CASERO')");
        bd.execSQL("INSERT INTO receta (nombre, elaboracion, foto, tiempo, dificultad, tipo, origen) VALUES ('ENSALADILLA ROSA', 'Si se usan remolachas frescas, envolverlas en papel de aluminio, ponerlas en una fuente de horno y asarlas a 200 grados unos 45 minutos o hasta que estén tiernas (que se puedan atravesar fácilmente con un cuchillo). Dejar que se enfríen y reservar. Poner agua abundante a hervir con un puñado de sal. Lavar bien las patatas. Cocerlas enteras y sin pelar. A los 25 minutos, pincharlas. Si el pincho o cuchillo entra con facilidad, es que están. Si no dejarlas 5 minutos más. Usar el mismo agua para cocer dos huevos durante 10 minutos. Pasarlos por agua fría y pelarlos. Dejar que todo se enfríe. Hacer medio litro aproximadamente de mayonesa: poner las anchoas, un huevo, un chorro de vinagre y otro de aceite de oliva en un recipiente alto. Batir con la batidora hasta que se mezcle y emulsione. Ir añadiendo aceite de girasol a hilo mientras se sigue batiendo y moviendo la batidora arriba y abajo con suavidad. Corregir de vinagre y sal, y si está demasiado espesa, añadir un par de cucharadas de agua y remover bien. Pelar las patatas y las remolachas cortarlas en dados pequeños de 1 cm. aproximadamente. Juntarlas en un bol con las habitas, las aceitunas cortadas en dos mitades y el atún escurrido y desmigado. Rociar la ensaladilla con un chorrito de aceite de oliva y mezclar todo bien. Sumar la mitad de la mayonesa y mezclar. Ir añadiendo el resto de la mayonesa hasta dar con la densidad justa: ni muy pastosa ni demasiado cremosa o líquida. Mezclar bien, corregir de sal, meter en la nevera y servir fría con más aceitunas cortadas por encima si se quiere.', 'Enlace', 65, 'BAJA', 'ENTRANTE','VEGANA')");
        bd.execSQL("INSERT INTO receta (nombre, elaboracion, foto, tiempo, dificultad, tipo, origen) VALUES ('MANTEQUILLA DE HIERBAS', 'Lavar y picar las hierbas. Mezclar con la mantequilla en pomada, la piel de limón (solo la parte amarilla) sal, pimienta y, si se quiere, el ajo. Poner la mezcla en un trozo de plástico transparente, doblarlo y formar un rulo. Dejarlo en la nevera un par de horas. Consejos de uso: esta mantequilla será –con pan o tostadas– la mejor amiga de cualquier pescado ahumado, y sola la base para una salsatipomeunièrecon una vuelta o el aderezo de unosescargotsa la francesa.', 'Enlace', 140, 'MEDIA', 'ENTRANTE','CASERO')");
        bd.execSQL("INSERT INTO receta (nombre, elaboracion, foto, tiempo, dificultad, tipo, origen) VALUES ('GALLETAS DE QUESO, TOMATE SECO Y ORÉGANO', 'Remojar en agua tibia los tomates durante media hora, secarlos bien y picarlos finos con un cuchillo bien afilado. Poner la mantequilla con la harina tamizada, la levadura, el tomate y el queso en un bol y amasar hasta que se integren y quede una masa elástica y que no se pega en las manos (si tenéis un robot de cocina, podéis dejarle esta parte del trabajo). Estirar la masa entre dos papeles de horno hasta que tenga un grosor de unos 3 mm aproximadamente. Ponerla en la nevera unos 30 minutos para que sea más fácil de cortar. Con un cortapastas –o un cuchillo, o un vaso– cortarlas con la forma deseada y disponer en una bandeja para hornear, que meteremos en el horno calentado a 160 grados centígrados con calor arriba y abajo. Hornear las galletas unos 25 minutos o hasta que estén doradas y apetitosas, dándole la vuelta a la bandeja –lo de delante, atrás– más o menos a los 15 minutos para una cocción más uniforme. Lo ideal sería vigilarlas a partir de los 20 minutos, por aquello de que cada horno es un mundo. Cuando estén hechas, sacarlas del horno, dejar enfriar y comer o guardar en un recipiente hermético.', 'Enlace', 130, 'ALTA', 'ENTRANTE','CASERO')");
        bd.execSQL("INSERT INTO receta (nombre, elaboracion, foto, tiempo, dificultad, tipo, origen) VALUES ('LIBRITOS DE BOQUERONES', 'Cortar láminas de queso con un cuchillo o pelador. Extender la mitad de las anchoas con la piel abajo. Repartir el jamón y el queso sobre ellas, tratando de que el tamaño de ambos no exceda el de los pescados. Tapar cada librito con e resto de las anchoas con la piel arriba. Preparar un plato hondo con harina, media cucharadita rasa de levadura Royal y sal. Mezclar bien. Preparar otro plato hondo con los huevos batidos, y uno llano con papel de cocina. Calentar aceite de oliva abundante en una sartén a fuego medio-alto. Cuando esté bien caliente, ir pasando los libritos de anchoa con cuidado por la harina primero, después por el huevo, y por último, por la sartén. Freír lo mínimo para que cojan un color dorado claro: con 30 segundos por lado suele ser suficiente. Sacar al plato con papel, dejar que escurran un poco el exceso de grasa y servir de inmediato con ensalada verde.','Enlace', 20, 'MEDIA', 'SEGUNDO','MEDITERRANEA')");
        bd.execSQL("INSERT INTO receta (nombre, elaboracion, foto, tiempo, dificultad, tipo, origen) VALUES ('ALBÓNDIGAS VEGETALES DE OTOÑO', 'Hornear la calabaza, la manzana y el boniato a 180 grados durante una hora o hasta que estén blandos y la calabaza y la manzana bastante doradas (el tiempo concreto depende del tamaño del boniato y la variación de temperatura de cada horno). Cocer el arroz en agua salada unos 25 minutos –tiene que quedar bastante pasado– y escurrirlo en un colador fino hasta que haya eliminado gran parte de su humedad. Reservar. Lavar durante un rato la quinua debajo del grifo en un colador y hervirla unos 15 minutos o hasta que haya doblado el tamaño y soltado el germen blanco. Colar, dejar escurrir por completo y reservar. Pelar la cebolla, picarla y pocharla en una sartén a fuego medio-bajo hasta que esté bien melosa. Cuando esté, sacar la cebolla, escurriendo bien el aceite y, en la misma sartén, poner los champiñones y las setas picadas y dorarlas bien a fuego medio con un poco de aceite. Mezclar en un bol la carne del boniato y la calabaza, el arroz, las setas, los champiñones, la quinua, el arroz, el perejil, el sésamo y el ras el hanout. Mezclar bien todo hasta que se amalgame y quede una masa blanda, pero manejable. Con el horno a 180 grados, ir poniendo en una bandeja con papel de horno bolitas un poco más grandes que una nuez. Manipular la masa con las manos húmedas o untadas en aceite ayudará. Hornear las bolas durante unos 35-40 minutos o hasta que estén doradas y tengan un punto crujiente por fuera. Cuando casi estén, preparar un all i oli con la misma técnica que si hiciéramos una mayonesa, pero con el diente de ajo (o medio, al gusto). Cuando esté listo, añadir la carne de la manzana asada y rectificar de sal. Servir las albóndigas calientes, acompañadas del all i oli para mojar.', 'Enlace', 100, 'MEDIA', 'SEGUNDO','VEGETAL')");
        bd.execSQL("INSERT INTO receta (nombre, elaboracion, foto, tiempo, dificultad, tipo, origen) VALUES ('COSTILLAS ASADAS CON MEMBRILLO', 'Majar el ajo. Mezclarlo con la mermelada, el tomillo, el romero, el orégano, el pimentón, el zumo del limón, un par de cucharadas de aceite, sal y pimienta. Quitar la membrana que tienen pegada las costillas: levantar una pequeña parte con un cuchillo, y luego tirar de ella. Embadurnar la costilla con la mezcla, meterla en una bolsa de plástico hermética o envolverla en film. Dejarla marinando toda la noche (este proceso se puede reducir o saltar, pero conviene para que la carne quede más sabrosa). Retirar las costillas de la marinada reservándola. Poner la carne en una fuente de horno y dejar que se temple unos 30 minutos. Precalentar el horno a 160 grados. Mojar las costillas con el brandy y 100 ml de agua. Meter al horno durante una hora tapada con papel de aluminio o una tapa. Cortar el membrillo en ocho gajos y descorazonarlos. Salpimentarlos e incorporarlos al asado añadiendo más agua y brandy si se ha quedado seco. Pintar la costilla con marinada y volver a meter tapado al horno durante una hora más, dándole la vuelta y volviéndola a pintar a la mitad. Mover también los trozos de membrillo. Destapar la costilla, pintarla de nuevo y dejar que se haga 30 minutos más añadiendo un poco de agua si es necesario. Poner el grill en el horno y subir la temperatura a 230. Sacar la costilla y dejar que repose 10 minutos. Cortarla en trozos individuales y devolverla a la fuente. Untar otra vez con marinada y meter de nuevo en el horno 5-10 minutos hasta que se doren. Servir inmediatamente.', 'Enlace', 120, 'BAJA', 'SEGUNDO','MEDITERRANEA')");
        bd.execSQL("INSERT INTO receta (nombre, elaboracion, foto, tiempo, dificultad, tipo, origen) VALUES ('SARDINAS ASADAS CON LIMÓN Y GUINDILLA', 'Picar las guindillas y los ajos en trozos no demasiado pequeños para que se puedan retirar. Mezclarlos con unas 10 cucharadas de aceite y dejar madurando un mínimo de media hora. Antes de que se vayan a tomar las sardinas, picar fino perejil abundante y añadirlo al aceite junto al zumo del limón y sal. Remover bien, aplastando un poco el ajo y la guindilla para que suelten aún más sabor. Probar y corregir de sal. Asar las sardinas y servirlas con el aliño por encima o en una salsera aparte para que cada uno se ponga lo que quiera.', 'Enlace', 40, 'BAJA', 'SEGUNDO','MEDITERRANEA')");
        bd.execSQL("INSERT INTO receta (nombre, elaboracion, foto, tiempo, dificultad, tipo, origen) VALUES ('ESTOFADO DE HIERBAS DE YODA', 'Calentar 3 cucharadas de aceite en una cazuela y dorar los trozos de carne previamente salpimentados. Reservar. Mezclar las especias y molerlas juntas en un molinillo o con la ayuda de un mortero. Añadir a la cazuela el resto del aceite y sofreír la cebolleta y el ajo cortados finamente. Agregar el jengibre rallado, la hoja de laurel y las especias, cuidando de que no se quemen. Cortar el boniato y el nabo en trozos de tamaño similar al de la carne y dorar ligeramente en la misma cazuela. Volver a meter dentro la carne dorada y cubrir con agua. Calentar hasta que el líquido hierva y bajar el fuego para mantener un borboteo suave durante al menos una hora o hasta que el cordero esté tierno. Cuando la carne esté casi a punto preparar los champiñones o setas, bien lavados y cortados en cuartos en una sartén aparte con un poco de aceite. Lavar y cortar las espinacas, añadir a la cazuela y dejar cocinar todo junto cinco minutos. A la hora de servir, poner los champiñones por encima y espolvorear con el perejil picado.', 'Enlace', 40, 'BAJA', 'SEGUNDO','MEDITERRANEA')");
        bd.execSQL("INSERT INTO receta (nombre, elaboracion, foto, tiempo, dificultad, tipo, origen) VALUES ('COLIFLOR ASADA PICANTE', 'En un bol mezclar el yogur con la cúrcuma, un poco de pimienta negra molida, el tabasco, el queso rallado, el aceite de sésamo, el aceite de oliva y la soja. Batir con una cuchara y meter en una bolsa de congelado la coliflor y la marinada, agitando la bolsa para que se impregne todo. Marinar durante 2 horas. Sacar de la bolsa y disponer en una fuente de horno, desechando la marinada. Hornear a 180º (horno precalentado) durante 25 minutos. El tiempo dependerá del tamaño de los trozos, pero hay que evitar que se cocine demasiado. Prueba la coliflor y aumenta la cocción hasta que tenga el punto al dente deseado. Tras este tiempo, encender el grill y dorar durante otros 3-5 minutos. Emplatar y añadir cebollino picado por encima.', 'Enlace', 40, 'BAJA', 'SEGUNDO','VEGANA')");
        bd.execSQL("INSERT INTO receta (nombre, elaboracion, foto, tiempo, dificultad, tipo, origen) VALUES ('TOSTADA FRANCESA CON FRUTAS', 'Limpiar y cortar las fresas, el kiwi o las frutas que se quieran y repartirlas en los platos, poniéndolas en un lado de los mismos. Batir el huevo en un bol. Mezclarlo con la leche, el zumo, el azúcar, la canela y el comino. Cortar 8 rebanadas de pan de unos 2 cm. de grosor, haciéndolo en diagonal para que salgan más grandes. Poner a calentar en una sartén a fuego medio una de las cucharadas de mantequilla. Empapar bien en la mezcla de huevo y leche cuatro rebanadas de pan por los dos lados y ponerlas en la sartén cuando la mantequilla esté bien caliente. Cuando estén doradas, darles la vuelta y esperar a que se hagan por el otro lado. Repetir la operación con las otras cuatro rebanadas, poniendo antes la segunda cucharada de mantequilla en la sartén. Poner dos tostadas en cada plato. Rociar todo con sirope de arce y, si se quiere, decorar con más azúcar glas. Servir de inmediato.', 'Enlace', 20, 'MEDIA', 'POSTRE','CASERO')");
        bd.execSQL("INSERT INTO receta (nombre, elaboracion, foto, tiempo, dificultad, tipo, origen) VALUES ('FLAN DE YEMAS CON VAINILLA', ' Precalentar el horno a 150º. Poner a calentar la leche con la nata. Cortar la vaina de vainilla transversalmente, sacarle las semillas con la punta de un cuchillo e ir echándolas sobre la leche. Poner también en ella la vaina para que dé más sabor. Cuando hierva, retirar del fuego y dejar que se temple. Mezclar las yemas con los huevos y el azúcar. Incorporar la leche con la nata pasándola por un colador o quitando la vaina de vainilla antes. Mezclar. Llenar 3/4 partes de los moldes o ramequines con la mezcla. Ponerlos en una bandeja alta con agua caliente para cocerlos al baño maría. Si los moldes son metálicos, conviene cubrir el fondo de la bandeja con papel de aluminio para que no la toquen. Hornear unos 20-25 minutos y sacar cuando el borde se vean algo cuajados pero el centro tiemble. Dejar enfriar en la misma bandeja con el agua y servir en el mismo molde a temperatura ambiente.', 'Enlace', 30, 'BAJA', 'POSTRE','CASERO')");
        bd.execSQL("INSERT INTO receta (nombre, elaboracion, foto, tiempo, dificultad, tipo, origen) VALUES ('LA CIENCIA DE LAS MAGDALENAS', 'Precalentar el horno a 200 ºC con calor arriba y abajo. Batir los huevos y el azúcar con unas varillas eléctricas durante 3 o 4 minutos, hasta que la mezcla espume y coja un color claro. Tamizar la harina junto con la levadura química y la sal. Añadirla a los huevos en tres veces, batiendo bien después de cada adición. Agregar el aceite o la mantequilla (derretida y templada) y la leche, y batir hasta conseguir una masa homogénea. Repartir las cápsulas de papel en los huecos del molde o las flaneras. Llenar con la masa cada cápsula hasta tres cuartas partes de su capacidad con la ayuda de una cuchara de helado o dos cucharas grandes. Poner encima y en el centro de cada magdalena un poco de azúcar para conseguir la costra crujiente. Meter los moldes al horno y cocer unos 15 minutos. No abrir la puerta del horno durante la cocción. Sacar las magdalenas, enfriar y comer.', 'Enlace', 25, 'MEDIA', 'POSTRE','CASERO')");
        bd.execSQL("INSERT INTO receta (nombre, elaboracion, foto, tiempo, dificultad, tipo, origen) VALUES ('HELADO DE FRESA SIN HELADERA', 'Lavar y secar las fresas. Quitar hojas, rabitos y partes estropeadas. Cortar las fresas en láminas finas y colocar éstas sin amontonar en una bandeja que quepa en el congelador. Otra opción es ponerlas en un táper con film plástico entre capa y capa para que no se peguen entre sí. Tapar y congelar al menos 6 horas. Montar la nata. Sacar las láminas de fresa del congelador y triturarlas en un procesador o picadora junto a la leche condensada hasta conseguir una crema espesa con textura parecida al helado. Añadir la nata montada a la mezcla de fresas, en varias tandas y con movimientos envolventes para que no pierda volumen. Agregar el licor (opcional). Verter la mezcla en un recipiente hermético limpio y seco. Colocar film plástico al contacto sobre la superficie para que no se forme escarcha, colocar la tapa y meter en el congelador. Idealmente, y para conseguir más cremosidad, se puede sacar a las 3 horas para volver a batirlo. Congelar de nuevo. Para los tropezones: calentar en un cazo las fresas cortadas finamente, el zumo de limón y el azúcar. Reducir a fuego lento hasta conseguir una mermelada espesa. Dejar enfriar por completo y echarla por encima del helado, repartiéndola en una capa fina. Volver a tapar y a guardar en el congelador. Sacar el helado a temperatura ambiente 10 minutos antes de consumirlo.', 'Enlace', 340, 'BAJA', 'POSTRE','CASERO')");
        bd.execSQL("INSERT INTO receta (nombre, elaboracion, foto, tiempo, dificultad, tipo, origen) VALUES ('GRATINADO DE BRÉCOL Y BONIATO', 'Poner a hervir agua abundante con sal en una cazuela. Pelar y cortar los boniatos en rodajas finas y cocerlos de 3 a 5 minutos, lo justo para que se ablanden. Picar la cebolla y el ajo y rehogarlos en una sartén grande con un poco de aceite unos 10 minutos a fuego suave. Separar las flores del brécol del tronco con un cuchillo. Cortar en rodajas finas los trozos de éste. Saltearlos en la sartén de la cebolla y el ajo unos tres minutos a fuego medio. Añadir las flores y saltear un par de minutos más (así el brécol quedará al dente; si gusta más pasado, alargar el tiempo de salteado). Sazonar con la cayena si se quiere un punto picante, salar ligeramente y reservar. Precalentar el horno a 220 grados. Rallar los quesos. Poner en una cazuela la mantequilla con la harina a fuego suave. Dejar cocer unos 10 minutos removiendo de vez en cuando. Calentar la leche en otra cazuela e ir añadiéndola a la mezcla de harina y mantequilla poco a poco, removiendo para que no se formen grumos. Añadir la nuez moscada, retirar del fuego e incorporar 2/3 partes de los quesos. Mezclar bien y salpimentar. Repartir la mitad del boniato en una fuente de horno. Extender el brócoli y otra capa de boniato por encima. Bañar con la bechamel y terminar con el resto del queso espolvoreado por encima. Hornear unos 20 minutos hasta que esté dorado -si la parte de arriba se tuesta muy rápido, cubrir con papel de aluminio- y servir.', 'Enlace', 40, 'MEDIA', 'PRIMERO','MEDITERRANEA')");
        bd.execSQL("INSERT INTO receta (nombre, elaboracion, foto, tiempo, dificultad, tipo, origen) VALUES ('CALABACINES ASADOS CON SALSA DE PIMIENTO Y ALMENDRAS', 'Precalentar el horno a 200 grados.Cortar los calabacines en rodajas finas de 1/2 centímetro aproximadamente. Embadurnarlas con un chorrito de aceite en un bol y salpimentarlas. Extenderlas en una bandeja de horno, espolvorear con el orégano y el romero, añadir el ajo sin pelar y asar 20 minutos. Dar la vuelta a las rodajas y asarlas otros 10 o hasta que estén doradas y crujientes. Mientras, preparar la salsa triturando los pimientos, las almendras, la miga del pan de molde, el zumo del limón, la cayena y el azúcar, sin pasarnos mucho para que se noten los trocitos de almendra. Ir añadiendo aceite mientras removemos hasta que quede una salsa cremosa. Puede que se necesite añadir un poco de agua también. Salpimentar. Servir los calabacines calientes con la salsa por encima y algunas almendras tostadas si se quiere.', 'Enlace', 30, 'BAJA', 'PRIMERO','MEDITERRANEA')");
        bd.execSQL("INSERT INTO receta (nombre, elaboracion, foto, tiempo, dificultad, tipo, origen) VALUES ('ENSALADA TROPICAL', 'Poner en una picadora el trozo de jengibre, 4 hojas de hierbabuena, el zumo de una lima y aceite como para aliñar una ensalada. Batir hasta que quede todo bien triturado. Cortar toda la fruta en trozos pequeños y disponerla en un bol. Sazonar con la vinagreta y un poco de sal –como si fuese una ensalada tradicional– y remover. Dejar reposar un rato en la nevera tapado con film de cocina. Volver a mezclar un poco, decorar con unas láminas de coco deshidratado y unas hojas de hierbabuena y servir inmediatamente.', 'Enlace', 14, 'BAJA', 'PRIMERO','VEGANA')");
        bd.execSQL("INSERT INTO receta (nombre, elaboracion, foto, tiempo, dificultad, tipo, origen) VALUES ('MELÓN CON JAMÓN 2.0', 'Cortar 3 o 4 rajas normales del melón y quitarles las pipas. Desde la parte interior (donde estaban las pipas), ir sacando láminas en sentido longitudinal con la ayuda de un pelador. Cortar las lonchas de jamón por el medio a lo largo o de modo que sean de tamaño similar a las láminas de melón. Colocar en cada plato el doble de melón que de jamón. Espolvorear con pimienta negra recién molida y añadir un hilito de aceite de oliva por encima.', 'Enlace', 10, 'BAJA', 'ENTRNTE','MEDITERRANEA')");
    }
    public void Comensales(SQLiteDatabase bd){
        //numComensales y id = el menu al que pertenece
        bd.execSQL("INSERT INTO nComensales(numComensales, id) VALUES (4, 1)");
        bd.execSQL("INSERT INTO nComensales(numComensales, id) VALUES (4, 2)");
        bd.execSQL("INSERT INTO nComensales(numComensales, id) VALUES (4, 3)");
        bd.execSQL("INSERT INTO nComensales(numComensales, id) VALUES (4, 4)");
        bd.execSQL("INSERT INTO nComensales(numComensales, id) VALUES (2, 5)");
        bd.execSQL("INSERT INTO nComensales(numComensales, id) VALUES (4, 6)");
        bd.execSQL("INSERT INTO nComensales(numComensales, id) VALUES (4, 7)");
        bd.execSQL("INSERT INTO nComensales(numComensales, id) VALUES (4, 8)");
        bd.execSQL("INSERT INTO nComensales(numComensales, id) VALUES (4, 9)");
        bd.execSQL("INSERT INTO nComensales(numComensales, id) VALUES (4, 10)");
        bd.execSQL("INSERT INTO nComensales(numComensales, id) VALUES (4, 11)");
        bd.execSQL("INSERT INTO nComensales(numComensales, id) VALUES (2, 12)");
        bd.execSQL("INSERT INTO nComensales(numComensales, id) VALUES (4, 13)");
        bd.execSQL("INSERT INTO nComensales(numComensales, id) VALUES (6, 14)");
        bd.execSQL("INSERT INTO nComensales(numComensales, id) VALUES (6, 15)");
        bd.execSQL("INSERT INTO nComensales(numComensales, id) VALUES (4, 16)");
        bd.execSQL("INSERT INTO nComensales(numComensales, id) VALUES (4, 17)");
        bd.execSQL("INSERT INTO nComensales(numComensales, id) VALUES (4, 18)");
        bd.execSQL("INSERT INTO nComensales(numComensales, id) VALUES (2, 19)");
        bd.execSQL("INSERT INTO nComensales(numComensales, id) VALUES (6, 20)");
    }
    public void tiene(SQLiteDatabase bd){
        //numComensales= el ingrediente - cantidad que corresponde al num de comensales que hay.
        //ejem: 10 alcachofas para 4 comensales = numComensal=4, 5 alcachofas para 2 comensales = numComensal=2

        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Alcachofas', 1, 10)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Pan duro', 1, 100)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Dientes de ajo', 1, 5)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Hojas de perejil', 1, 20)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Aceite Oliva virgen', 1, 75)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Agua', 1, 75)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Vinagre Suave', 1, 50)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Vino Blanco', 1, 75)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Sal', 1, 20)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Pimienta', 1, 20)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Limon', 1, 1)");

        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Peras', 2, 1)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Azucar blanco', 2, 250)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Granos de cardamomo verde', 2, 2)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Semillas de cilantro', 2, 1)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Anís estrellado', 2, 3)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Canela', 2, 1)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Azafrán en hebras', 2, 0.3)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Piel naranja', 2, 20)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Piel Limon', 2, 20)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Agua', 2, 2500)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Sal', 2, 20)");

        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Leche entera', 3, 1)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Canela', 3, 1)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Anís estrellado', 3, 2)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Vainas de cardamomo', 3, 2)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Vaina de vainilla', 3, 0.2)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Azucar blanco', 3, 150)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Maicena', 3, 50)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Harina', 3, 50)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Aceite de girasol', 3, 0.15)");

        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Patatas', 4, 2)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Remolachas', 4, 2)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Habas cocidas', 4, 100)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Bonito en aceite', 4, 150)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Aceitunas sin hueso', 4, 100)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Huevos', 4, 3)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Anchoas', 4, 8)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Aceite Oliva virgen', 4, 30)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Aceite de girasol', 4, 30)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Vinagre', 4, 0.30)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Sal', 4, 20)");

        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (2, 'Mantequilla', 5, 200)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (2, 'Perejil picado', 5, 40)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (2, 'Hojas de tomillo', 5, 20)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (2, 'Cebollino picado', 5, 40)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (2, 'Hojas de romero picadas', 5, 10)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (2, 'Piel Limon', 5, 5)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (2, 'Dientes de ajo', 5, 1)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (2, 'Sal', 5, 20)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (2, 'Pimienta', 5, 20)");

        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Parmesano', 6, 80)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Harina', 6, 200)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Mantequilla', 6, 120)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Levadura', 6, 15)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Orégano', 6, 15)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Tomates secos', 6, 50)");

        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Boquerones', 7, 12)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Jamón serrano', 7, 150)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Queso poco curado', 7, 150)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Huevos', 7, 2)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Harina', 7, 200)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Levadura', 7, 16)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Aceite Oliva virgen', 7, 100)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Sal', 7, 20)");

        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Calabaza', 8, 300)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Boniato', 8, 1)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Champiñones', 8, 150)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Setas', 8, 150)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Arroz', 8, 100)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Quinua', 8, 100)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Cebolla', 8, 1)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Sésamo', 8, 15)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Ras al hanut', 8, 15)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Perejil picado', 8, 40)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Sal', 8, 40)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Huevos', 8, 1)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Dientes de ajo', 8, 1)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Manzana asada', 8, 1)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Aceite Oliva virgen', 8, 150)");

        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Costilla de cerdo', 9, 1.5)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Membrillos', 9, 2)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Mermelada de frutos rojos', 9, 60)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Dientes de ajo', 9, 1)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Brandy', 9, 100)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Tomillo', 9, 10)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Romero', 9, 10)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Orégano', 9, 10)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Pimentón picante', 9, 10)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Limon', 9, 1)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Aceite Oliva virgen', 9, 1000)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Sal', 9, 20)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Pimienta', 9, 20)");

        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Sardinas', 10, 1)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Dientes de ajo', 10, 2)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Guindilla roja', 10, 2)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Perejil', 10, 300)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Limon', 10, 1)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Aceite Oliva virgen', 10, 1000)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Sal', 10, 20)");

        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Carne de cordero', 11, 0.7)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Aceite Oliva virgen', 11, 120)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Cebolleta', 11, 1)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Dientes de ajo', 11, 1)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Boniato', 11, 0.5)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Nabo', 11, 1)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Jengibre', 11, 20)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Semillas de cilantro', 11, 1)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Comino', 11, 10)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Cúrcuma', 11, 10)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Guindilla picada', 11, 10)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Vainas de cardamomo', 11, 1)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Canela molida', 11, 10)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Clavos de olor', 11, 4)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Hojas de laurel', 11, 1)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Perejil picado', 11, 50)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Espinacas', 11, 100)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Champiñones', 11, 100)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Sal', 11, 20)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Pimienta', 11, 20)");

        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (2, 'Coliflor', 12, 0.5)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (2, 'Yogur natural', 12, 1)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (2, 'Queso curado', 12, 30)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (2, 'Cúrcuma', 12, 20)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (2, 'Tabasco', 12, 10)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (2, 'Pimienta', 12, 20)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (2, 'Aceite de sésamo', 12, 20)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (2, 'Salsa de soja', 12, 20)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (2, 'Aceite Oliva virgen', 12, 20)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (2, 'Cebollino picado', 12, 50)");

        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Barra Pan blanco', 13, 1)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Huevos', 13, 1)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Leche entera', 13, 0.150)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Zumo de naranja', 13, 50)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Azúcar glas', 13, 40)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Canela molida', 13, 15)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Comino', 13, 10)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Mantequilla', 13, 50)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Sirope de arce', 13, 100)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Fresas', 13, 100)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Kiwis', 13, 1)");

        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (6, 'Yemas', 14, 6)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (6, 'Huevos', 14, 3)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (6, 'Azucar blanco', 14, 150)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (6, 'Leche entera', 14, 0.500)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (6, 'Nata líquida', 14, 125)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (6, 'Vaina de vainilla', 14, 1)");

        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (6, 'Huevos', 15, 3)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (6, 'Azucar blanco', 15, 110)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (6, 'Harina', 15, 180)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (6, 'Levadura', 15, 9)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (6, 'Sal', 15, 10)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (6, 'Aceite de girasol', 15, 100)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (6, 'Leche entera', 15, 50)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (6, 'Azúcar glas', 15, 5)");

        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Fresas', 16, 500)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Nata para montar', 16, 200)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Leche condensada', 16, 150)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Licor de cereza', 16, 15)");

        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Boniato', 17, 3)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Brécol', 17, 500)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Queso de oveja', 17, 50)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Queso suizo', 17, 50)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Cebolla', 17, 1)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Dientes de ajo', 17, 1)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Cayena', 17, 10)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Nuez moscada', 17, 10)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Leche entera', 17, 500)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Harina', 17, 500)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Mantequilla', 17, 30)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Aceite Oliva virgen', 17, 500)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Sal', 17, 10)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Pimienta', 17, 10)");

        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Calabacines', 18, 3)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Pimientos del Piquillo', 18, 3)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Almendras crudas', 18, 100)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Dientes de ajo', 18, 1)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Rebanada de pan de molde', 18, 0.5)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Limon', 18, 1)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Cayena', 18, 10)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Romero', 18, 10)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Orégano', 18, 10)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Azúcar moreno', 18, 20)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Aceite Oliva virgen', 18, 300)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Sal', 18, 10)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Pimienta', 18, 10)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (4, 'Almendras tostadas', 18, 100)");

        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (2, 'Mango', 19, 0.5)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (2, 'Aguacate', 19, 0.5)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (2, 'Papaya', 19, 0.25)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (2, 'Plátano', 19, 1)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (2, 'Kiwis', 19, 1)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (2, 'Jengibre', 19, 1)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (2, 'Lima', 19, 1)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (2, 'Aceite Oliva virgen', 19, 100)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (2, 'Hojas de hierbabuena', 19, 4)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (2, 'Coco deshidratado', 19, 10)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (2, 'Sal', 19, 10)");

        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (6, 'Melón', 20, 0.5)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (6, 'Jamón serrano', 20, 200)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (6, 'Pimienta', 20, 10)");
        bd.execSQL("INSERT INTO tiene(numComensales, nomIngrediente, id, cantidad) VALUES (6, 'Aceite Oliva virgen', 20, 100)");

    }
    public void ingrediente(SQLiteDatabase bd){
        bd.execSQL("INSERT INTO ingrediente(nomIngrediente, unidad, foto, precio) VALUES ('Alcachofas', 'ud', 'Falcachofa', 0.00)");
        bd.execSQL("INSERT INTO ingrediente(nomIngrediente, unidad, foto, precio) VALUES ('Pan duro', 'gr', 'F_PanDuro', 0)");
        bd.execSQL("INSERT INTO ingrediente(nomIngrediente, unidad, foto, precio) VALUES ('Dientes de ajo', 'ud', 'F_DientesAjo', 0)");
        bd.execSQL("INSERT INTO ingrediente(nomIngrediente, unidad, foto, precio) VALUES ('Hojas de perejil', 'gr', 'F_Perejil', 0)");
        bd.execSQL("INSERT INTO ingrediente(nomIngrediente, unidad, foto, precio) VALUES ('Aceite Oliva virgen', 'ml', 'F_Aceite_Virgen', 0)");
        bd.execSQL("INSERT INTO ingrediente(nomIngrediente, unidad, foto, precio) VALUES ('Agua', 'ml', 'F_Agua', 0)");
        bd.execSQL("INSERT INTO ingrediente(nomIngrediente, unidad, foto, precio) VALUES ('Vinagre Suave', 'ml', 'F_VinagreSuave', 0)");
        bd.execSQL("INSERT INTO ingrediente(nomIngrediente, unidad, foto, precio) VALUES ('Vino Blanco', 'ml', 'F_VinoB', 0)");
        bd.execSQL("INSERT INTO ingrediente(nomIngrediente, unidad, foto, precio) VALUES ('Sal', 'gr', 'F_Sal', 0)");
        bd.execSQL("INSERT INTO ingrediente(nomIngrediente, unidad, foto, precio) VALUES ('Pimienta', 'gr', 'F_Pimienta', 0)");
        bd.execSQL("INSERT INTO ingrediente(nomIngrediente, unidad, foto, precio) VALUES ('Limon', 'ud', 'F_Limon', 0)");

        bd.execSQL("INSERT INTO ingrediente(nomIngrediente, unidad, foto, precio) VALUES ('Peras', 'Kl', 'F_Pera', 0)");
        bd.execSQL("INSERT INTO ingrediente(nomIngrediente, unidad, foto, precio) VALUES ('Azucar blanco', 'gr', 'F_azucar', 0)");
        bd.execSQL("INSERT INTO ingrediente(nomIngrediente, unidad, foto, precio) VALUES ('Granos de cardamomo verde', 'cucharada', 'F_cardamomo', 0)");
        bd.execSQL("INSERT INTO ingrediente(nomIngrediente, unidad, foto, precio) VALUES ('Semillas de cilantro', 'cucharada', 'F_cilantro', 0)");
        bd.execSQL("INSERT INTO ingrediente(nomIngrediente, unidad, foto, precio) VALUES ('Anís estrellado', 'piezas', 'F_anís', 0)");
        bd.execSQL("INSERT INTO ingrediente(nomIngrediente, unidad, foto, precio) VALUES ('Canela', 'ramita', 'F_canela', 0)");
        bd.execSQL("INSERT INTO ingrediente(nomIngrediente, unidad, foto, precio) VALUES ('Azafrán en hebras', 'gr', 'F_azafrán', 0)");
        bd.execSQL("INSERT INTO ingrediente(nomIngrediente, unidad, foto, precio) VALUES ('Piel naranja', 'gr', 'F_naranja', 0)");
        bd.execSQL("INSERT INTO ingrediente(nomIngrediente, unidad, foto, precio) VALUES ('Piel Limon', 'gr', 'F_Limon', 0)");

        bd.execSQL("INSERT INTO ingrediente(nomIngrediente, unidad, foto, precio) VALUES ('Leche entera', 'L', 'F_LecheEn', 0)");
        bd.execSQL("INSERT INTO ingrediente(nomIngrediente, unidad, foto, precio) VALUES ('Vainas de cardamomo', 'ud', 'F_VainaC', 0)");
        bd.execSQL("INSERT INTO ingrediente(nomIngrediente, unidad, foto, precio) VALUES ('Vaina de vainilla', 'ud', 'F_VainaV', 0)");
        bd.execSQL("INSERT INTO ingrediente(nomIngrediente, unidad, foto, precio) VALUES ('Maicena', 'gr', 'F_maicena', 0)");
        bd.execSQL("INSERT INTO ingrediente(nomIngrediente, unidad, foto, precio) VALUES ('Harina', 'gr', 'F_Harina', 0)");
        bd.execSQL("INSERT INTO ingrediente(nomIngrediente, unidad, foto, precio) VALUES ('Aceite de girasol', 'ml', 'F_AceiteGirasol', 0)");

        bd.execSQL("INSERT INTO ingrediente(nomIngrediente, unidad, foto, precio) VALUES ('Patatas', 'ud', 'F_Patatas', 0)");
        bd.execSQL("INSERT INTO ingrediente(nomIngrediente, unidad, foto, precio) VALUES ('Remolachas', 'ud', 'F_Remolachas', 0)");
        bd.execSQL("INSERT INTO ingrediente(nomIngrediente, unidad, foto, precio) VALUES ('Habas cocidas', 'gr', 'F_Habas', 0)");
        bd.execSQL("INSERT INTO ingrediente(nomIngrediente, unidad, foto, precio) VALUES ('Bonito en aceite', 'gr', 'F_Bonito', 0)");
        bd.execSQL("INSERT INTO ingrediente(nomIngrediente, unidad, foto, precio) VALUES ('Aceitunas sin hueso', 'gr', 'F_Aceitunas', 0)");
        bd.execSQL("INSERT INTO ingrediente(nomIngrediente, unidad, foto, precio) VALUES ('Huevos', 'ud', 'F_Huevos', 0)");
        bd.execSQL("INSERT INTO ingrediente(nomIngrediente, unidad, foto, precio) VALUES ('Anchoas', 'ud', 'F_Anchoas', 0)");
        bd.execSQL("INSERT INTO ingrediente(nomIngrediente, unidad, foto, precio) VALUES ('Vinagre', 'L', 'F_Vinagre', 0)");

        bd.execSQL("INSERT INTO ingrediente(nomIngrediente, unidad, foto, precio) VALUES ('Mantequilla', 'gr', 'F_Mantequilla', 0)");
        bd.execSQL("INSERT INTO ingrediente(nomIngrediente, unidad, foto, precio) VALUES ('Perejil picado', 'gr', 'F_PerejilP', 0)");
        bd.execSQL("INSERT INTO ingrediente(nomIngrediente, unidad, foto, precio) VALUES ('Hojas de tomillo', 'gr', 'F_Htomillo', 0)");
        bd.execSQL("INSERT INTO ingrediente(nomIngrediente, unidad, foto, precio) VALUES ('Cebollino picado', 'gr', 'F_CebollinoP', 0)");
        bd.execSQL("INSERT INTO ingrediente(nomIngrediente, unidad, foto, precio) VALUES ('Hojas de Perejil', 'gr', 'F_HPerejil', 0)");
        bd.execSQL("INSERT INTO ingrediente(nomIngrediente, unidad, foto, precio) VALUES ('Hojas de romero picadas', 'L', 'F_HRomeroP', 0)");

        bd.execSQL("INSERT INTO ingrediente(nomIngrediente, unidad, foto, precio) VALUES ('Parmesano', 'gr', 'F_Parmesano', 0)");
        bd.execSQL("INSERT INTO ingrediente(nomIngrediente, unidad, foto, precio) VALUES ('Levadura', 'gr', 'F_Levadura', 0)");
        bd.execSQL("INSERT INTO ingrediente(nomIngrediente, unidad, foto, precio) VALUES ('Orégano', 'gr', 'F_Orégano', 0)");
        bd.execSQL("INSERT INTO ingrediente(nomIngrediente, unidad, foto, precio) VALUES ('Tomates secos', 'gr', 'F_TomatesSeco', 0)");

        bd.execSQL("INSERT INTO ingrediente(nomIngrediente, unidad, foto, precio) VALUES ('Boquerones', 'ud', 'F_Boquerones', 0)");
        bd.execSQL("INSERT INTO ingrediente(nomIngrediente, unidad, foto, precio) VALUES ('Jamón serrano', 'gr', 'F_JamonSerrano', 0)");
        bd.execSQL("INSERT INTO ingrediente(nomIngrediente, unidad, foto, precio) VALUES ('Queso poco curado', 'gr', 'F_QuesoPC', 0)");

        bd.execSQL("INSERT INTO ingrediente(nomIngrediente, unidad, foto, precio) VALUES ('Calabaza', 'gr', 'F_Calabaza', 0)");
        bd.execSQL("INSERT INTO ingrediente(nomIngrediente, unidad, foto, precio) VALUES ('Boniato', 'ud', 'F_Boniato', 0)");
        bd.execSQL("INSERT INTO ingrediente(nomIngrediente, unidad, foto, precio) VALUES ('Champiñones', 'gr', 'F_Champiñones', 0)");
        bd.execSQL("INSERT INTO ingrediente(nomIngrediente, unidad, foto, precio) VALUES ('Setas', 'gr', 'F_Setas', 0)");
        bd.execSQL("INSERT INTO ingrediente(nomIngrediente, unidad, foto, precio) VALUES ('Arroz', 'gr', 'F_Arroz', 0)");
        bd.execSQL("INSERT INTO ingrediente(nomIngrediente, unidad, foto, precio) VALUES ('Quinua', 'gr', 'F_Quinua', 0)");
        bd.execSQL("INSERT INTO ingrediente(nomIngrediente, unidad, foto, precio) VALUES ('Cebolla', 'ud', 'F_Cebolla', 0)");
        bd.execSQL("INSERT INTO ingrediente(nomIngrediente, unidad, foto, precio) VALUES ('Sésamo', 'gr', 'F_Sésamo', 0)");
        bd.execSQL("INSERT INTO ingrediente(nomIngrediente, unidad, foto, precio) VALUES ('Ras al hanut', 'gr', 'F_Ras_al_hanut', 0)");
        bd.execSQL("INSERT INTO ingrediente(nomIngrediente, unidad, foto, precio) VALUES ('Manzana asada', 'ud', 'F_ManzanaAsada', 0)");

        bd.execSQL("INSERT INTO ingrediente(nomIngrediente, unidad, foto, precio) VALUES ('Costilla de cerdo', 'Kg', 'F_Costilla', 0)");
        bd.execSQL("INSERT INTO ingrediente(nomIngrediente, unidad, foto, precio) VALUES ('Membrillos', 'ud', 'F_Membrillos', 0)");
        bd.execSQL("INSERT INTO ingrediente(nomIngrediente, unidad, foto, precio) VALUES ('Mermelada de frutos rojos', 'gr', 'F_Mermelada', 0)");
        bd.execSQL("INSERT INTO ingrediente(nomIngrediente, unidad, foto, precio) VALUES ('Tomillo', 'gr', 'F_Tomillo', 0)");
        bd.execSQL("INSERT INTO ingrediente(nomIngrediente, unidad, foto, precio) VALUES ('Romero', 'gr', 'F_Romero', 0)");
        bd.execSQL("INSERT INTO ingrediente(nomIngrediente, unidad, foto, precio) VALUES ('Pimentón picante', 'gr', 'F_Pimentón', 0)");
        bd.execSQL("INSERT INTO ingrediente(nomIngrediente, unidad, foto, precio) VALUES ('Brandy', 'ml', 'F_Brandy', 0)");

        bd.execSQL("INSERT INTO ingrediente(nomIngrediente, unidad, foto, precio) VALUES ('Sardinas', 'kg', 'F_Sardinas', 0)");
        bd.execSQL("INSERT INTO ingrediente(nomIngrediente, unidad, foto, precio) VALUES ('Guindilla roja', 'ud', 'F_Guindilla', 0)");
        bd.execSQL("INSERT INTO ingrediente(nomIngrediente, unidad, foto, precio) VALUES ('Perejil', 'gr', 'F_Perejil', 0)");

        bd.execSQL("INSERT INTO ingrediente(nomIngrediente, unidad, foto, precio) VALUES ('Carne de cordero', 'Kl', 'F_cordero', 0)");
        bd.execSQL("INSERT INTO ingrediente(nomIngrediente, unidad, foto, precio) VALUES ('Cebolleta', 'ud', 'F_Cebolleta', 0)");
        bd.execSQL("INSERT INTO ingrediente(nomIngrediente, unidad, foto, precio) VALUES ('Nabo', 'ud', 'F_Nabo', 0)");
        bd.execSQL("INSERT INTO ingrediente(nomIngrediente, unidad, foto, precio) VALUES ('Comino', 'gr', 'F_Comino', 0)");
        bd.execSQL("INSERT INTO ingrediente(nomIngrediente, unidad, foto, precio) VALUES ('Cúrcuma', 'gr', 'F_Cúrcuma', 0)");
        bd.execSQL("INSERT INTO ingrediente(nomIngrediente, unidad, foto, precio) VALUES ('Guindilla picada', 'gr', 'F_Guindilla', 0)");
        bd.execSQL("INSERT INTO ingrediente(nomIngrediente, unidad, foto, precio) VALUES ('Canela molida', 'gr', 'F_Canela', 0)");
        bd.execSQL("INSERT INTO ingrediente(nomIngrediente, unidad, foto, precio) VALUES ('Clavos de olor', 'ud', 'F_Clavos', 0)");
        bd.execSQL("INSERT INTO ingrediente(nomIngrediente, unidad, foto, precio) VALUES ('Hojas de laurel', 'gr', 'F_laurel', 0)");
        bd.execSQL("INSERT INTO ingrediente(nomIngrediente, unidad, foto, precio) VALUES ('Espinacas', 'gr', 'F_Espinacas', 0)");
        bd.execSQL("INSERT INTO ingrediente(nomIngrediente, unidad, foto, precio) VALUES ('Jengibre', 'gr', 'F_Jengibre', 0)");

        bd.execSQL("INSERT INTO ingrediente(nomIngrediente, unidad, foto, precio) VALUES ('Coliflor', 'ud', 'F_Coliflor', 0)");
        bd.execSQL("INSERT INTO ingrediente(nomIngrediente, unidad, foto, precio) VALUES ('Yogur natural', 'ud', 'F_Yogur', 0)");
        bd.execSQL("INSERT INTO ingrediente(nomIngrediente, unidad, foto, precio) VALUES ('Tabasco', 'ml', 'F_Tabasco', 0)");
        bd.execSQL("INSERT INTO ingrediente(nomIngrediente, unidad, foto, precio) VALUES ('Aceite de sésamo', 'ml', 'F_AceiteSésamo', 0)");
        bd.execSQL("INSERT INTO ingrediente(nomIngrediente, unidad, foto, precio) VALUES ('Salsa de soja', 'ml', 'F_soja', 0)");
        bd.execSQL("INSERT INTO ingrediente(nomIngrediente, unidad, foto, precio) VALUES ('Queso curado', 'gr', 'F_QuesoC', 0)");

        bd.execSQL("INSERT INTO ingrediente(nomIngrediente, unidad, foto, precio) VALUES ('Barra Pan blanco', 'ud', 'F_Pan', 0)");
        bd.execSQL("INSERT INTO ingrediente(nomIngrediente, unidad, foto, precio) VALUES ('Zumo de naranja', 'ml', 'F_ZumoNA', 0)");
        bd.execSQL("INSERT INTO ingrediente(nomIngrediente, unidad, foto, precio) VALUES ('Azúcar glas', 'gr', 'F_AzúcarG', 0)");
        bd.execSQL("INSERT INTO ingrediente(nomIngrediente, unidad, foto, precio) VALUES ('Sirope de arce', 'ml', 'F_SiropeAlce', 0)");
        bd.execSQL("INSERT INTO ingrediente(nomIngrediente, unidad, foto, precio) VALUES ('Fresas', 'gr', 'F_Fresas', 0)");
        bd.execSQL("INSERT INTO ingrediente(nomIngrediente, unidad, foto, precio) VALUES ('Kiwis', 'ud', 'F_Kiwis', 0)");

        bd.execSQL("INSERT INTO ingrediente(nomIngrediente, unidad, foto, precio) VALUES ('Yemas', 'ud', 'F_Yemas', 0)");
        bd.execSQL("INSERT INTO ingrediente(nomIngrediente, unidad, foto, precio) VALUES ('Nata líquida', 'ml', 'F_NataL', 0)");

        bd.execSQL("INSERT INTO ingrediente(nomIngrediente, unidad, foto, precio) VALUES ('Nata para montar', 'ml', 'F_NataM', 0)");
        bd.execSQL("INSERT INTO ingrediente(nomIngrediente, unidad, foto, precio) VALUES ('Leche condensada', 'ml', 'F_LecheC', 0)");
        bd.execSQL("INSERT INTO ingrediente(nomIngrediente, unidad, foto, precio) VALUES ('Licor de cereza', 'ml', 'F_LicorC', 0)");

        bd.execSQL("INSERT INTO ingrediente(nomIngrediente, unidad, foto, precio) VALUES ('Nuez moscada', 'gr', 'F_NuezM', 0)");
        bd.execSQL("INSERT INTO ingrediente(nomIngrediente, unidad, foto, precio) VALUES ('Brécol', 'gr', 'F_Brécol', 0)");
        bd.execSQL("INSERT INTO ingrediente(nomIngrediente, unidad, foto, precio) VALUES ('Queso de oveja', 'gr', 'F_QuesoO', 0)");
        bd.execSQL("INSERT INTO ingrediente(nomIngrediente, unidad, foto, precio) VALUES ('Queso suizo', 'gr', 'F_QuesoSu', 0)");
        bd.execSQL("INSERT INTO ingrediente(nomIngrediente, unidad, foto, precio) VALUES ('Cayena', 'gr', 'F_Cayena', 0)");

        bd.execSQL("INSERT INTO ingrediente(nomIngrediente, unidad, foto, precio) VALUES ('Calabacines', 'ud', 'F_Calabacines', 0)");
        bd.execSQL("INSERT INTO ingrediente(nomIngrediente, unidad, foto, precio) VALUES ('Pimientos del Piquillo', 'ud', 'F_Pimientosp', 0)");
        bd.execSQL("INSERT INTO ingrediente(nomIngrediente, unidad, foto, precio) VALUES ('Almendras crudas', 'gr', 'F_Almendras', 0)");
        bd.execSQL("INSERT INTO ingrediente(nomIngrediente, unidad, foto, precio) VALUES ('Rebanada de pan de molde', 'ud', 'F_PanM', 0)");
        bd.execSQL("INSERT INTO ingrediente(nomIngrediente, unidad, foto, precio) VALUES ('Azúcar moreno', 'gr', 'F_AzúcarM', 0)");
        bd.execSQL("INSERT INTO ingrediente(nomIngrediente, unidad, foto, precio) VALUES ('Almendras tostadas', 'gr', 'F_Almendras', 0)");

        bd.execSQL("INSERT INTO ingrediente(nomIngrediente, unidad, foto, precio) VALUES ('Mango', 'ud', 'F_Mango', 0)");
        bd.execSQL("INSERT INTO ingrediente(nomIngrediente, unidad, foto, precio) VALUES ('Aguacate', 'ud', 'F_Aguacate', 0)");
        bd.execSQL("INSERT INTO ingrediente(nomIngrediente, unidad, foto, precio) VALUES ('Papaya', 'ud', 'F_Papaya', 0)");
        bd.execSQL("INSERT INTO ingrediente(nomIngrediente, unidad, foto, precio) VALUES ('Plátano', 'ud', 'F_Plátano', 0)");
        bd.execSQL("INSERT INTO ingrediente(nomIngrediente, unidad, foto, precio) VALUES ('Lima', 'ud', 'F_Lima', 0)");
        bd.execSQL("INSERT INTO ingrediente(nomIngrediente, unidad, foto, precio) VALUES ('Hojas de hierbabuena', 'ud', 'F_Hhierbabuena', 0)");
        bd.execSQL("INSERT INTO ingrediente(nomIngrediente, unidad, foto, precio) VALUES ('Coco deshidratado', 'ud', 'F_CocoD', 0)");

        bd.execSQL("INSERT INTO ingrediente(nomIngrediente, unidad, foto, precio) VALUES ('Melón', 'ud', 'F_Melón', 0)");


    }
    public void dieta(SQLiteDatabase bd){
        bd.execSQL("INSERT INTO dieta(nomDieta) VALUES ('Dieta Diaria')");
        bd.execSQL("INSERT INTO dieta(nomDieta) VALUES ('Dieta FinDe')");
        bd.execSQL("INSERT INTO dieta(nomDieta) VALUES ('Dieta Semanal')");
    }
    public void menu(SQLiteDatabase bd){
        bd.execSQL("INSERT INTO menu(tipoComida) VALUES ('Desayuno')");
        bd.execSQL("INSERT INTO menu(tipoComida) VALUES ('Almuerzo')");
        bd.execSQL("INSERT INTO menu(tipoComida) VALUES ('Comida')");
        bd.execSQL("INSERT INTO menu(tipoComida) VALUES ('Merienda')");
        bd.execSQL("INSERT INTO menu(tipoComida) VALUES ('Cena')");
    }
    public void fecha(SQLiteDatabase bd){
        bd.execSQL("INSERT INTO fecha(dia, nomDieta) VALUES ('01 / 1 / 2021', 'Dieta Diaria')");

        bd.execSQL("INSERT INTO fecha(dia, nomDieta) VALUES ('02 / 1 / 2021', 'Dieta FinDe')");
        bd.execSQL("INSERT INTO fecha(dia, nomDieta) VALUES ('03 / 1 / 2021', 'Dieta FinDe')");
    }
    public void contiene(SQLiteDatabase bd){
        bd.execSQL("INSERT INTO contiene(diaSemana, nomDieta, dia, tipoComida, id) VALUES ('Viernes', 'Dieta Diaria', '01 / 1 / 2021', 'Desayuno', 13)");
        bd.execSQL("INSERT INTO contiene(diaSemana, nomDieta, dia, tipoComida, id) VALUES ('Viernes', 'Dieta Diaria', '01 / 1 / 2021', 'Desayuno', 3)");
        bd.execSQL("INSERT INTO contiene(diaSemana, nomDieta, dia, tipoComida, id) VALUES ('Viernes', 'Dieta Diaria', '01 / 1 / 2021', 'Almuerzo', 19)");
        bd.execSQL("INSERT INTO contiene(diaSemana, nomDieta, dia, tipoComida, id) VALUES ('Viernes', 'Dieta Diaria', '01 / 1 / 2021', 'Comida', 4)");
        bd.execSQL("INSERT INTO contiene(diaSemana, nomDieta, dia, tipoComida, id) VALUES ('Viernes', 'Dieta Diaria', '01 / 1 / 2021', 'Comida', 11)");
        bd.execSQL("INSERT INTO contiene(diaSemana, nomDieta, dia, tipoComida, id) VALUES ('Viernes', 'Dieta Diaria', '01 / 1 / 2021', 'Comida', 14)");
        bd.execSQL("INSERT INTO contiene(diaSemana, nomDieta, dia, tipoComida, id) VALUES ('Viernes', 'Dieta Diaria', '01 / 1 / 2021', 'Merienda', 2)");
        bd.execSQL("INSERT INTO contiene(diaSemana, nomDieta, dia, tipoComida, id) VALUES ('Viernes', 'Dieta Diaria', '01 / 1 / 2021', 'Cena', 7)");
        bd.execSQL("INSERT INTO contiene(diaSemana, nomDieta, dia, tipoComida, id) VALUES ('Viernes', 'Dieta Diaria', '01 / 1 / 2021', 'Cena', 16)");

        bd.execSQL("INSERT INTO contiene(diaSemana, nomDieta, dia, tipoComida, id) VALUES ('Sábado', 'Dieta FinDe', '02 / 1 / 2021', 'Desayuno', 13)");
        bd.execSQL("INSERT INTO contiene(diaSemana, nomDieta, dia, tipoComida, id) VALUES ('Sábado', 'Dieta FinDe', '02 / 1 / 2021', 'Desayuno', 3)");
        bd.execSQL("INSERT INTO contiene(diaSemana, nomDieta, dia, tipoComida, id) VALUES ('Sábado', 'Dieta FinDe', '02 / 1 / 2021', 'Almuerzo', 19)");
        bd.execSQL("INSERT INTO contiene(diaSemana, nomDieta, dia, tipoComida, id) VALUES ('Sábado', 'Dieta FinDe', '02 / 1 / 2021', 'Comida', 4)");
        bd.execSQL("INSERT INTO contiene(diaSemana, nomDieta, dia, tipoComida, id) VALUES ('Sábado', 'Dieta FinDe', '02 / 1 / 2021', 'Comida', 11)");
        bd.execSQL("INSERT INTO contiene(diaSemana, nomDieta, dia, tipoComida, id) VALUES ('Sábado', 'Dieta FinDe', '02 / 1 / 2021', 'Comida', 14)");
        bd.execSQL("INSERT INTO contiene(diaSemana, nomDieta, dia, tipoComida, id) VALUES ('Sábado', 'Dieta FinDe', '02 / 1 / 2021', 'Merienda', 2)");
        bd.execSQL("INSERT INTO contiene(diaSemana, nomDieta, dia, tipoComida, id) VALUES ('Sábado', 'Dieta FinDe', '02 / 1 / 2021', 'Cena', 7)");
        bd.execSQL("INSERT INTO contiene(diaSemana, nomDieta, dia, tipoComida, id) VALUES ('Sábado', 'Dieta FinDe', '02 / 1 / 2021', 'Cena', 16)");

        bd.execSQL("INSERT INTO contiene(diaSemana, nomDieta, dia, tipoComida, id) VALUES ('Domingo', 'Dieta FinDe', '03 / 1 / 2021', 'Desayuno', 13)");
        bd.execSQL("INSERT INTO contiene(diaSemana, nomDieta, dia, tipoComida, id) VALUES ('Domingo', 'Dieta FinDe', '03 / 1 / 2021', 'Desayuno', 3)");
        bd.execSQL("INSERT INTO contiene(diaSemana, nomDieta, dia, tipoComida, id) VALUES ('Domingo', 'Dieta FinDe', '03 / 1 / 2021', 'Almuerzo', 19)");
        bd.execSQL("INSERT INTO contiene(diaSemana, nomDieta, dia, tipoComida, id) VALUES ('Domingo', 'Dieta FinDe', '03 / 1 / 2021', 'Comida', 4)");
        bd.execSQL("INSERT INTO contiene(diaSemana, nomDieta, dia, tipoComida, id) VALUES ('Domingo', 'Dieta FinDe', '03 / 1 / 2021', 'Comida', 11)");
        bd.execSQL("INSERT INTO contiene(diaSemana, nomDieta, dia, tipoComida, id) VALUES ('Domingo', 'Dieta FinDe', '03 / 1 / 2021', 'Comida', 14)");
        bd.execSQL("INSERT INTO contiene(diaSemana, nomDieta, dia, tipoComida, id) VALUES ('Domingo', 'Dieta FinDe', '03 / 1 / 2021', 'Merienda', 2)");
        bd.execSQL("INSERT INTO contiene(diaSemana, nomDieta, dia, tipoComida, id) VALUES ('Domingo', 'Dieta FinDe', '03 / 1 / 2021', 'Cena', 7)");
        bd.execSQL("INSERT INTO contiene(diaSemana, nomDieta, dia, tipoComida, id) VALUES ('Domingo', 'Dieta FinDe', '03 / 1 / 2021', 'Cena', 16)");
    }
}
