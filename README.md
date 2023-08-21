# Movie Finder: Taller 1 Aplicaciones Distribuidas (HTTP, Sockets, HTML, JS,MAVEN, GIT)

En este repositorio se encuentra una aplicacion la cual consulta informacion de peliculas de cine, La aplicacion recibe el nombre de la pelicula y esta muestra los datos de la pelicula correspondiente.

## Para empezar

Descargue o copie el repositorio en su maquina local.

### Prerequisitos
* Java
* Maven

### Ejecucion

Para ejecutar el comando por favor ubiquese en la carpeta de taller1 y ahi ingrese el siguiente comando:

```
mvn clean package exec:java -D "exec.mainClass"="edu.escuelaing.arep.app1.HttpServer"
```

Al estar este en estado de "Listo para recibir ..." Dirigase a la siguiente pagina

```
http://localhost:35000/
```

Al estar este en estado de "Listo para recibir ..." Dirigase a la siguiente pagina

```
http://localhost:35000/
```

La pagina deberia verse asi:

![resources](taller1/src/main/java/edu/escuelaing/arep/app1/resources/1.png)

Al realizar la busqueda la veremos asi, en este caso al ser la primera busqueda no esta en el cache:

![resources](taller1/src/main/java/edu/escuelaing/arep/app1/resources/2.png)

Al volver a realizar la busqueda ya la tendremos en cache, por lo cual se vera asi(Igualmente la consola va a ir imprimiento todo el cache que tenemos):

![resources](taller1/src/main/java/edu/escuelaing/arep/app1/resources/3.png)

Esta es un ejemplo de una busqueda nueva:

![resources](taller1/src/main/java/edu/escuelaing/arep/app1/resources/4.png)



## Cache en consola

Para realizar el cache implemente un HashMap el cual va agregando las peliculas que no se encuentran en este.

### Ejemplo cache

Cuando No se encuentra una pelicula en el cache
![resources](taller1/src/main/java/edu/escuelaing/arep/app1/resources/5.png)

Cuando ya se encuentra la pelicula en el cache, como podemos observar aqui tambien imprimimos todas las peliculas que actualmente tenemos en el cache
![resources](taller1/src/main/java/edu/escuelaing/arep/app1/resources/6.png)


## Built With

* [Maven](https://maven.apache.org/) - Gestión de dependencias
* [JAVA](https://rometools.github.io/rome/) - Lenguaje de programacion utlizado




## Authors

* **Juan Sebastian Rodriguez Peña** - [JSebastianRod](https://github.com/JSebastianRod)
